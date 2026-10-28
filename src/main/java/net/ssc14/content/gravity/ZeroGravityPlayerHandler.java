package net.ssc14.content.gravity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Applies the legacy zero-gravity wall movement controls on the server/client tick. */
@EventBusSubscriber
public final class ZeroGravityPlayerHandler {
    private static final double VERTICAL_SPEED = 0.15;
    private static final double DAMPING = 0.9;

    private ZeroGravityPlayerHandler() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.isNoGravity()) return;

        AABB box = player.getBoundingBox().inflate(0.5);
        boolean touchingBlock = BlockPos.betweenClosedStream(
                Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
                Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))
                .anyMatch(pos -> !player.level().getBlockState(pos).isAir());
        Vec3 motion = player.getDeltaMovement();
        double newY = motion.y;
        if (touchingBlock) {
            if (player.isJumping()) newY = VERTICAL_SPEED;
            else if (player.isShiftKeyDown()) newY = -VERTICAL_SPEED;
            else newY *= DAMPING;
        }
        player.setDeltaMovement(motion.x, newY, motion.z);
    }
}
