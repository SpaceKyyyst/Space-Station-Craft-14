package net.ssc14.content.decal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/** Removes decals targeted by the server-side deletion tool. */
public final class DecalRemovalService {
    private static final double REACH = 5.0D;
    private static final double RADIUS = 1.5D;

    private DecalRemovalService() { }

    public static void execute(Player player) {
        if (player == null || player.level().isClientSide()) return;
        HitResult hit = player.pick(REACH, 0.0F, false);
        if (!(hit instanceof BlockHitResult blockHit) || hit.getType() != HitResult.Type.BLOCK) return;
        if (player.level() instanceof ServerLevel level) {
            BlockPos pos = blockHit.getBlockPos();
            DecalService.removeDecalsInRadius(level, pos, RADIUS);
        }
    }
}
