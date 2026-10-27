package net.ssc14.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/** Parameterized implementation for small utility particle effects. */
public final class UtilityEffectParticle extends SingleQuadParticle {
    private static final int FULL_BRIGHT = 15_728_880;

    private static final Config SPARK = new Config(
            0.1F, 0.1F, false, 14, 4, 2, 0.3F, true, 1.0D, Layer.OPAQUE, true, 2, 9);
    private static final Config BULLET = new Config(
            0.1F, 2.5F, false, 2, 2, 1, 0.0F, false, 0.1D, Layer.TRANSLUCENT, false, 1, 2);
    private static final Config RCD_INTERFERENCE = new Config(
            0.07F, 0.03F, true, 5, 10, 5, 0.0F, true, 0.0D, Layer.OPAQUE, true, 0, 0);
    private static final Config SPECIFY = new Config(
            1.0F, 2.0F, false, 80, 0, 0, 0.0F, true, 0.0D, Layer.OPAQUE, false, 5, 4);

    private final SpriteSet sprites;
    private final Config config;

    private UtilityEffectParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ,
            SpriteSet sprites,
            Config config) {
        super(level, x, y, z, sprites.first());
        this.sprites = sprites;
        this.config = config;
        setSize(config.hitboxSize(), config.hitboxSize());
        quadSize = config.absoluteQuadSize() ? config.quadSize() : quadSize * config.quadSize();
        lifetime = config.lifetimeVariation() == 0
                ? config.baseLifetime()
                : Math.max(1, config.baseLifetime()
                        + random.nextInt(config.lifetimeVariation()) - config.lifetimeOffset());
        gravity = config.gravity();
        hasPhysics = config.hasPhysics();
        xd = velocityX * config.velocityScale();
        yd = velocityY * config.velocityScale();
        zd = velocityZ * config.velocityScale();
        if (config.frameCount() > 0) {
            setSpriteFromAge(sprites);
        }
    }

    public static ParticleProvider<SimpleParticleType> sparkProvider(SpriteSet sprites) {
        return new Provider(sprites, SPARK);
    }

    public static ParticleProvider<SimpleParticleType> bulletProvider(SpriteSet sprites) {
        return new Provider(sprites, BULLET);
    }

    public static ParticleProvider<SimpleParticleType> rcdInterferenceProvider(SpriteSet sprites) {
        return new Provider(sprites, RCD_INTERFERENCE);
    }

    public static ParticleProvider<SimpleParticleType> specifyProvider(SpriteSet sprites) {
        return new Provider(sprites, SPECIFY);
    }

    @Override
    public int getLightCoords(float partialTick) {
        return config.fullBright() ? FULL_BRIGHT : super.getLightCoords(partialTick);
    }

    @Override
    public Layer getLayer() {
        return config.layer();
    }

    @Override
    public void tick() {
        super.tick();
        if (!removed && config.frameCount() > 0) {
            setSprite(sprites.get(
                    (age / config.ticksPerFrame()) % config.frameCount() + 1,
                    config.frameCount()));
        }
    }

    private record Provider(SpriteSet sprites, Config config)
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
            return new UtilityEffectParticle(
                    level, x, y, z, velocityX, velocityY, velocityZ, sprites, config);
        }
    }

    private record Config(
            float hitboxSize,
            float quadSize,
            boolean absoluteQuadSize,
            int baseLifetime,
            int lifetimeVariation,
            int lifetimeOffset,
            float gravity,
            boolean hasPhysics,
            double velocityScale,
            Layer layer,
            boolean fullBright,
            int ticksPerFrame,
            int frameCount) { }
}
