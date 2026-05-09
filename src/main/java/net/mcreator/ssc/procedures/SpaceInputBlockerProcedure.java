
package net.mcreator.ssc.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "ssc_14", value = Dist.CLIENT)
public class SpaceInputBlockerProcedure {

    private static final ResourceKey<Level> SPACE_DIM =
        ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced"));

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player == null || !player.level().isClientSide()) return;
        
        // 🔹 Пропускаем креатив/наблюдатель полностью
        if (!player.level().dimension().equals(SPACE_DIM) || 
            !player.isNoGravity() || 
            player.isCreative() || 
            player.isSpectator()) {
            return;
        }

        AABB box = player.getBoundingBox().inflate(1.5);
        boolean hasBlocks = BlockPos.betweenClosedStream(
            Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
            Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ)
        ).anyMatch(p -> !player.level().getBlockState(p).isAir());

        if (!hasBlocks) {
            Vec3 m = player.getDeltaMovement();
            float clientSmoothing = 1.05F;
            
            // 🔹 Y не трогаем на клиенте тоже
            player.setDeltaMovement(m.x * clientSmoothing, m.y, m.z * clientSmoothing);
        }
    }
}
