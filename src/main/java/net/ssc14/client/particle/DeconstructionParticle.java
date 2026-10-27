package net.ssc14.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/** Shared animated particle used by all deconstruction orientations. */
public final class DeconstructionParticle extends SingleQuadParticle {
    private static final float HITBOX_SIZE = 1.0F;
    private static final float QUAD_SIZE = 0.505F;
    private static final int LIFETIME_TICKS = 160;
    private static final int FRAME_COUNT = 52;
    private static final int TICKS_PER_FRAME = 3;

    private final SpriteSet sprites;

    private DeconstructionParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, sprites.first());
        this.sprites = sprites;
        setSize(HITBOX_SIZE, HITBOX_SIZE);
        quadSize = QUAD_SIZE;
        lifetime = LIFETIME_TICKS;
        gravity = 0.0F;
        hasPhysics = false;
        xd = 0.0D;
        yd = 0.0D;
        zd = 0.0D;
        setSpriteFromAge(sprites);
    }

    public static ParticleProvider<SimpleParticleType> provider(SpriteSet sprites) {
        return new Provider(sprites);
    }

    @Override
    public Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!removed) {
            setSprite(sprites.get((age / TICKS_PER_FRAME) % FRAME_COUNT + 1, FRAME_COUNT));
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
            return new DeconstructionParticle(level, x, y, z, sprites);
        }
    }
}
