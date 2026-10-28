package net.ssc14.content.player.action;

import java.util.Optional;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.ssc14.content.particle.Ssc14ParticleTypes;

/** Resolves the nearest visible block/entity target and marks it for the player. */
public final class SpecifyTargetService {
    private static final double MAX_DISTANCE = 15.0;
    private static final double SEARCH_INFLATION = 1.0;
    private static final double PLAYER_OFFSET = 0.125;
    private static final double HEIGHT_OFFSET = 0.25;
    private static final double MIN_DIRECTION_LENGTH_SQUARED = 1.0E-4;

    private SpecifyTargetService() { }

    public static void mark(Entity source) {
        if (source == null) return;

        Vec3 start = source.getEyePosition(1.0F);
        Vec3 view = source.getViewVector(1.0F);
        Vec3 end = start.add(view.scale(MAX_DISTANCE));
        BlockHitResult blockHit = source.level().clip(new ClipContext(
                start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, source));
        Vec3 hitPosition = blockHit.getLocation();
        double closestDistance = blockHit.getType() == HitResult.Type.MISS
                ? MAX_DISTANCE : hitPosition.distanceTo(start);
        boolean hitEntity = false;

        AABB searchBox = source.getBoundingBox()
                .expandTowards(view.scale(closestDistance)).inflate(SEARCH_INFLATION);
        for (Entity target : source.level().getEntities(source, searchBox,
                candidate -> !candidate.isSpectator() && candidate.isPickable())) {
            Optional<Vec3> intersection = target.getBoundingBox().inflate(target.getPickRadius()).clip(start, end);
            if (intersection.isEmpty()) continue;
            double distance = start.distanceTo(intersection.get());
            if (distance >= closestDistance) continue;
            closestDistance = distance;
            hitPosition = intersection.get();
            hitEntity = true;
        }

        if (!hitEntity && blockHit.getType() != HitResult.Type.BLOCK) return;
        Vec3 towardPlayer = start.subtract(hitPosition);
        towardPlayer = towardPlayer.lengthSqr() > MIN_DIRECTION_LENGTH_SQUARED
                ? towardPlayer.normalize() : Vec3.ZERO;
        Vec3 particlePosition = hitPosition.add(
                towardPlayer.x * PLAYER_OFFSET,
                towardPlayer.y * PLAYER_OFFSET + HEIGHT_OFFSET,
                towardPlayer.z * PLAYER_OFFSET);
        if (source.level() instanceof ServerLevel level) {
            level.sendParticles((SimpleParticleType) Ssc14ParticleTypes.SPECIFY_P.get(),
                    particlePosition.x, particlePosition.y, particlePosition.z, 1, 0, 0, 0, 0);
        }
    }
}
