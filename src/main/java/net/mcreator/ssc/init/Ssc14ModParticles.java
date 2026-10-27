package net.mcreator.ssc.init;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.ssc14.client.particle.Ssc14ParticleProviderBinder;
import net.ssc14.client.particle.GasCloudParticle;
import net.ssc14.client.particle.DeconstructionParticle;
import net.ssc14.client.particle.GravityFieldParticle;
import net.ssc14.client.particle.UtilityEffectParticle;
import net.ssc14.client.particle.ExtinguisherSprayParticle;
import net.ssc14.content.particle.Ssc14ParticleTypes;

/** Compatibility bootstrap for transitional particle provider implementations. */
@Deprecated(forRemoval = false)
@EventBusSubscriber(Dist.CLIENT)
public final class Ssc14ModParticles {
    static {
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_ACTIVE_PARTICLE,
                sprites -> GravityFieldParticle.provider(sprites, 2, 4));
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_PARTICLE_2,
                sprites -> GravityFieldParticle.provider(sprites, 3, 3));
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_PARTICLE_3,
                sprites -> GravityFieldParticle.provider(sprites, 2, 7));
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_PARTICLE_4,
                sprites -> GravityFieldParticle.provider(sprites, 1, 9));
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.SPARK, UtilityEffectParticle::sparkProvider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.PLASMA_PARTICLES_1, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.PLASMA_PARTICLES_2, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_1, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_2, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_3, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_4, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_1, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_2, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_3, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_4, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_1, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_2, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_3, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_4, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_1, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_2, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_3, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_4, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.TRITIUM_PARTICLES_1, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.TRITIUM_PARTICLES_2, GasCloudParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.EXTINGUISHER_SPRAY, ExtinguisherSprayParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.RCD_INTERFERENCE_P, UtilityEffectParticle::rcdInterferenceProvider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.SPECIFY_P, UtilityEffectParticle::specifyProvider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.BULLET_FLIGHT_P, UtilityEffectParticle::bulletProvider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.DECONSTRUCT_P, DeconstructionParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.DECONSTRUCT_PX, DeconstructionParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.DECONSTRUCT_PZ, DeconstructionParticle::provider);
    }

    private Ssc14ModParticles() { }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Ssc14ParticleProviderBinder.registerProviders(event);
    }
}
