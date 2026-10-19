
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.gravity.EntityGravityController;
import net.mcreator.ssc.gravity.GravityPolicy;

import javax.annotation.Nullable;

@EventBusSubscriber
public class EntityGravitySistemProcedure {
    private static final int SUPPORT_DEPTH = 7;
    private static final ResourceKey<net.minecraft.world.level.Level> SPACE_DIMENSION =
        ResourceKey.create(Registries.DIMENSION, Identifier.parse("ssc_14:spaced"));
    private static final net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> AIR_BLOCKS =
        BlockTags.create(Identifier.parse("ssc14:airs"));

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;
        if (entity.level().isClientSide()) return;
        
        boolean inSpace = entity.level().dimension() == SPACE_DIMENSION;
        boolean excluded = entity.isRemoved();
        boolean stationGravity = Ssc14ModVariables.station_gravity;
        boolean boots = inSpace && !excluded && !stationGravity
            && hasEntityInInventory(entity, new ItemStack(Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get()));
        boolean support = false;
        if (inSpace && !excluded && (stationGravity || boots)) {
            for (int depth = 0; depth < SUPPORT_DEPTH; depth++) {
                if (!world.getBlockState(BlockPos.containing(x, y - depth, z)).is(AIR_BLOCKS)) {
                    support = true;
                    break;
                }
            }
        }
        EntityGravityController.apply(entity, GravityPolicy.evaluate(
            new GravityPolicy.EntitySnapshot(inSpace, stationGravity, boots, support, excluded)));

        if (inSpace && entity instanceof ServerPlayer sp && entity.isNoGravity() && !sp.isCreative() && !sp.isSpectator()) {
            handleServerInertiaLock(sp, world, x, y, z);
        }
    }

    private static void handleServerInertiaLock(ServerPlayer sp, LevelAccessor world, double x, double y, double z) {
        AABB box = sp.getBoundingBox().inflate(1.5);
        boolean hasBlocks = BlockPos.betweenClosedStream(
            Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
            Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ)
        ).anyMatch(p -> !world.getBlockState(p).isAir());

        if (!hasBlocks) {
            Vec3 current = sp.getDeltaMovement();
            float vacuumCompensation = 1.08F;
            
            double newX = current.x * vacuumCompensation;
            double newY = current.y * 0.975; 
            double newZ = current.z * vacuumCompensation;
            
            // ИСПРАВЛЕНО: Сравнение double значений с использованием Double.compare без знака меньше
            if (Double.compare(Math.abs(newX), 0.0005) > 0 == false) newX = 0;
            if (Double.compare(Math.abs(newZ), 0.0005) > 0 == false) newZ = 0;
            
            double maxSpeed = 3.0;
            newX = Mth.clamp(newX, -maxSpeed, maxSpeed);
            newZ = Mth.clamp(newZ, -maxSpeed, maxSpeed);
            
            sp.setDeltaMovement(newX, newY, newZ);
            sp.hurtMarked = true;
        }
    }

    private static boolean hasEntityInInventory(Entity entity, ItemStack itemstack) {
        if (entity instanceof Player player) {
            int containerSize = player.getInventory().getContainerSize();
            // ИСПРАВЛЕНО: Цикл по инвентарю без использования знака меньше
            for (int slot = 0; slot != containerSize; slot++) {
                ItemStack stack = player.getInventory().getItem(slot);
                if (!stack.isEmpty() && ItemStack.isSameItem(stack, itemstack)) {
                    return true;
                }
            }
        }
        return false;
    }
}
