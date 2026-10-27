package net.ssc14.content.rotation;

import net.ssc14.content.medical.MedicalActionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/** Authoritative intent boundary shared by packets and the generated compatibility facade. */
public final class RotateCommand {
    private static final int PRESS = 0;
    private static final double MAX_ROTATION_RANGE = 5.0;
    // Rotation changes one existing state. Generic neighbor physics and placement
    // hooks can escape the loaded-only view and are outside this command's contract.
    private static final int STATE_ONLY_UPDATE_FLAGS = Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_SKIP_ON_PLACE;

    private RotateCommand() { }

    public static boolean execute(ServerPlayer player, int eventType) {
        if (player == null || eventType != PRESS) return false;
        var level = player.level();
        if (!level.getServer().isSameThread() || player.isRemoved() || player.isSpectator()
                || !player.getAbilities().mayBuild || MedicalActionEvents.isBuildRestricted(player)) return false;
        double range = Math.min(MAX_ROTATION_RANGE, player.blockInteractionRange());
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getViewVector(1).scale(range));
        if (!Double.isFinite(range) || range <= 0 || !finite(eye) || !finite(end)) return false;

        var view = new LoadedRotationView(level);
        try {
            // BlockGetter's default clip uses this view for traversal AND all shape reads.
            // Level.clip is intentionally forbidden: its reads can generate a chunk.
            var hit = view.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if (hit.getType() != HitResult.Type.BLOCK) return false;
            var pos = hit.getBlockPos();
            if (level.isOutsideBuildHeight(pos) || !level.getWorldBorder().isWithinBounds(pos)
                    || eye.distanceToSqr(hit.getLocation()) > range * range
                    || !player.isWithinBlockInteractionRange(pos, 0) || !level.mayInteract(player, pos)) return false;
            var current = view.getBlockState(pos);
            var rotated = RotationPolicy.rotate(current);
            if (rotated.isEmpty() || view.getBlockState(pos) != current) return false;
            return level.setBlock(pos, rotated.get(), STATE_ONLY_UPDATE_FLAGS);
        } catch (LoadedRotationView.UnavailableChunk ignored) {
            return false;
        }
    }

    private static boolean finite(Vec3 vector) {
        return Double.isFinite(vector.x) && Double.isFinite(vector.y) && Double.isFinite(vector.z);
    }
}
