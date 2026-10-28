package net.ssc14.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/** Shared client implementation for gas cloud particle variants. */
public final class GasCloudParticle extends SingleQuadParticle {
    private static final float HITBOX_SIZE = 1.0F;
    private static final float QUAD_SCALE = 4.0F;
    private static final int BASE_LIFETIME_TICKS = 60;
    private static final int LIFETIME_VARIATION = 20;
    private static final int LIFETIME_OFFSET = 10;
    private static final double VELOCITY_SCALE = 0.1D;

    private GasCloudParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ,
            SpriteSet sprites) {
        super(level, x, y, z, sprites.first());
        setSize(HITBOX_SIZE, HITBOX_SIZE);
        quadSize *= QUAD_SCALE;
        lifetime = Math.max(1, BASE_LIFETIME_TICKS
                + random.nextInt(LIFETIME_VARIATION) - LIFETIME_OFFSET);
        gravity = 0.0F;
        hasPhysics = true;
        xd = velocityX * VELOCITY_SCALE;
        yd = velocityY * VELOCITY_SCALE;
        zd = velocityZ * VELOCITY_SCALE;
    }

    public static ParticleProvider<SimpleParticleType> provider(SpriteSet sprites) {
        return new Provider(sprites);
    }

    @Override
    public Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    private record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double velocityX,
                double velocityY,
                double velocityZ,
                RandomSource random) {
            return new GasCloudParticle(
                    level, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
}
