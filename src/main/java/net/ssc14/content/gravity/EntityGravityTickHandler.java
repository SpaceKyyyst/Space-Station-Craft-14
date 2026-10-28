package net.ssc14.content.gravity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.ssc14.common.gravity.GravityPolicy;
import net.ssc14.content.gravity.EntityGravityController;
import net.ssc14.content.gravity.StationGravityState;
import net.ssc14.content.item.Ssc14Items;

@EventBusSubscriber
public final class EntityGravityTickHandler {
    private static final int SUPPORT_DEPTH = 7;
    private static final double INERTIA_SCAN_RADIUS = 1.5D;
    private static final double MINIMUM_HORIZONTAL_SPEED = 0.0005D;
    private static final double MAXIMUM_HORIZONTAL_SPEED = 3.0D;
    private static final double VACUUM_HORIZONTAL_FACTOR = 1.08D;
    private static final double VACUUM_VERTICAL_FACTOR = 0.975D;
    private static final ResourceKey<Level> SPACE_DIMENSION =
            ResourceKey.create(Registries.DIMENSION, Identifier.parse("ssc_14:spaced"));
    private static final net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> AIR_BLOCKS =
            BlockTags.create(Identifier.parse("ssc14:airs"));

    private EntityGravityTickHandler() { }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        apply(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    public static void apply(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || entity.level().isClientSide()) {
            return;
        }

        boolean inSpace = entity.level().dimension() == SPACE_DIMENSION;
        boolean excluded = entity.isRemoved();
        boolean stationGravity = StationGravityState.isEnabled();
        boolean boots = inSpace && !excluded && !stationGravity
                && hasItem(entity, Ssc14Items.MAGNETIC_BOOTS_ACTIVE_ITEM.get().getDefaultInstance());
        boolean support = inSpace && !excluded && (stationGravity || boots)
                && hasSupport(world, x, y, z);

        EntityGravityController.apply(entity, GravityPolicy.evaluate(
                new GravityPolicy.EntitySnapshot(
                        inSpace, stationGravity, boots, support, excluded)));

        if (inSpace && entity instanceof ServerPlayer player && entity.isNoGravity()
                && !player.isCreative() && !player.isSpectator()) {
            applyVacuumInertia(player, world);
        }
    }

    private static boolean hasSupport(LevelAccessor world, double x, double y, double z) {
        for (int depth = 0; depth < SUPPORT_DEPTH; depth++) {
            if (!world.getBlockState(BlockPos.containing(x, y - depth, z)).is(AIR_BLOCKS)) {
                return true;
            }
        }
        return false;
    }

    private static void applyVacuumInertia(ServerPlayer player, LevelAccessor world) {
        AABB bounds = player.getBoundingBox().inflate(INERTIA_SCAN_RADIUS);
        boolean hasBlocks = BlockPos.betweenClosedStream(
                Mth.floor(bounds.minX), Mth.floor(bounds.minY), Mth.floor(bounds.minZ),
                Mth.floor(bounds.maxX), Mth.floor(bounds.maxY), Mth.floor(bounds.maxZ))
                .anyMatch(position -> !world.getBlockState(position).isAir());
        if (hasBlocks) {
            return;
        }

        Vec3 current = player.getDeltaMovement();
        double x = current.x * VACUUM_HORIZONTAL_FACTOR;
        double z = current.z * VACUUM_HORIZONTAL_FACTOR;
        if (Math.abs(x) <= MINIMUM_HORIZONTAL_SPEED) {
            x = 0.0D;
        }
        if (Math.abs(z) <= MINIMUM_HORIZONTAL_SPEED) {
            z = 0.0D;
        }
        player.setDeltaMovement(
                Mth.clamp(x, -MAXIMUM_HORIZONTAL_SPEED, MAXIMUM_HORIZONTAL_SPEED),
                current.y * VACUUM_VERTICAL_FACTOR,
                Mth.clamp(z, -MAXIMUM_HORIZONTAL_SPEED, MAXIMUM_HORIZONTAL_SPEED));
        player.hurtMarked = true;
    }

    private static boolean hasItem(Entity entity, ItemStack expected) {
        if (!(entity instanceof Player player)) {
            return false;
        }
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (!stack.isEmpty() && ItemStack.isSameItem(stack, expected)) {
                return true;
            }
        }
        return false;
    }
}


