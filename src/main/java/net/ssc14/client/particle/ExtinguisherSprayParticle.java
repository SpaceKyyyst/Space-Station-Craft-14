package net.ssc14.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/** Full-bright expanding spray emitted by fire extinguishers. */
public final class ExtinguisherSprayParticle extends SingleQuadParticle {
    private static final int FULL_BRIGHT = 15_728_880;
    private static final int FRAME_COUNT = 4;
    private static final double INITIAL_VELOCITY_DIVISOR = 0.01D;
    private static final double VELOCITY_DAMPING = 0.93D;
    private static final float AGE_SCALE_DIVISOR = 7.0F;

    private final SpriteSet sprites;

    private ExtinguisherSprayParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            SpriteSet sprites,
            RandomSource random) {
        super(level, Math.round(x), Math.round(y), Math.round(z), sprites.first());
        this.sprites = sprites;
        setSize(0.5F, 0.5F);
        lifetime = Math.max(1, 30 + random.nextInt(20) - 10);
        gravity = 0.0F;
        hasPhysics = true;
        xd = (x - Math.round(x)) / INITIAL_VELOCITY_DIVISOR;
        yd = (y - Math.round(y)) / INITIAL_VELOCITY_DIVISOR;
        zd = (z - Math.round(z)) / INITIAL_VELOCITY_DIVISOR;
        setSpriteFromAge(sprites);
    }

    public static ParticleProvider<SimpleParticleType> provider(SpriteSet sprites) {
        return new Provider(sprites);
    }

    @Override
    public int getLightCoords(float partialTick) {
        return FULL_BRIGHT;
    }

    @Override
    public Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public float getQuadSize(float partialTick) {
        return super.getQuadSize(partialTick) * (1.0F + age / AGE_SCALE_DIVISOR);
    }

    @Override
    public void tick() {
        super.tick();
        xd *= VELOCITY_DAMPING;
        yd *= VELOCITY_DAMPING;
        zd *= VELOCITY_DAMPING;
        if (!removed) {
            int frame = age * FRAME_COUNT / lifetime;
            setSprite(sprites.get(Math.min(frame, FRAME_COUNT - 1), FRAME_COUNT));
        }
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
            return new ExtinguisherSprayParticle(level, x, y, z, sprites, random);
        }
    }
}
