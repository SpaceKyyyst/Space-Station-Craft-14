package net.ssc14.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/** Stationary full-bright animation used by gravity-generator effects. */
public final class GravityFieldParticle extends SingleQuadParticle {
    private static final int FULL_BRIGHT = 15_728_880;
    private static final int LIFETIME_TICKS = 10;

    private final SpriteSet sprites;
    private final int ticksPerFrame;
    private final int frameCount;

    private GravityFieldParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            SpriteSet sprites,
            int ticksPerFrame,
            int frameCount) {
        super(level, x, y, z, sprites.first());
        this.sprites = sprites;
        this.ticksPerFrame = ticksPerFrame;
        this.frameCount = frameCount;
        setSize(1.0F, 1.0F);
        quadSize *= 2.0F;
        lifetime = LIFETIME_TICKS;
        gravity = 0.0F;
        hasPhysics = false;
        xd = 0.0D;
        yd = 0.0D;
        zd = 0.0D;
        setSpriteFromAge(sprites);
    }

    public static ParticleProvider<SimpleParticleType> provider(
            SpriteSet sprites, int ticksPerFrame, int frameCount) {
        if (ticksPerFrame <= 0 || frameCount <= 0) {
            throw new IllegalArgumentException("Animation parameters must be positive");
        }
        return new Provider(sprites, ticksPerFrame, frameCount);
    }

    @Override
    public int getLightCoords(float partialTick) {
        return FULL_BRIGHT;
    }

    @Override
    public Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        if (!removed) {
            setSprite(sprites.get((age / ticksPerFrame) % frameCount + 1, frameCount));
        }
    }

    private record Provider(SpriteSet sprites, int ticksPerFrame, int frameCount)
            implements ParticleProvider<SimpleParticleType> {
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
            return new GravityFieldParticle(level, x, y, z, sprites, ticksPerFrame, frameCount);
        }
    }
}
