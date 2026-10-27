package net.mcreator.ssc.init;

import net.mcreator.ssc.client.particle.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.ssc14.client.particle.Ssc14ParticleProviderBinder;
import net.ssc14.content.particle.Ssc14ParticleTypes;

/** Compatibility bootstrap for transitional particle provider implementations. */
@Deprecated(forRemoval = false)
@EventBusSubscriber(Dist.CLIENT)
public final class Ssc14ModParticles {
    static {
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_ACTIVE_PARTICLE, GravGenActiveParticleParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_PARTICLE_2, GravGenParticle2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_PARTICLE_3, GravGenParticle3Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.GRAV_GEN_PARTICLE_4, GravGenParticle4Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.SPARK, SparkParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.PLASMA_PARTICLES_1, PlasmaParticles1Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.PLASMA_PARTICLES_2, PlasmaParticles2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_1, WaterVaporParticles1Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_2, WaterVaporParticles2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_3, WaterVaporParticles3Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_4, WaterVaporParticles4Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_1, AmmoniaParticles1Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_2, AmmoniaParticles2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_3, AmmoniaParticles3Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.AMMONIA_PARTICLES_4, AmmoniaParticles4Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_1, FreonParticles1Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_2, FreonParticles2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_3, FreonParticles3Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.FREON_PARTICLES_4, FreonParticles4Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_1, HeliumParticles1Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_2, HeliumParticles2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_3, HeliumParticles3Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.HELIUM_PARTICLES_4, HeliumParticles4Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.TRITIUM_PARTICLES_1, TritiumParticles1Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.TRITIUM_PARTICLES_2, TritiumParticles2Particle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.EXTINGUISHER_SPRAY, ExtinguisherSprayParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.RCD_INTERFERENCE_P, RCDInterferencePParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.SPECIFY_P, SpecifyPParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.BULLET_FLIGHT_P, BulletFlightPParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.DECONSTRUCT_P, DeconstructPParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.DECONSTRUCT_PX, DeconstructPXParticle::provider);
        Ssc14ParticleProviderBinder.bindSpriteSet(Ssc14ParticleTypes.DECONSTRUCT_PZ, DeconstructPZParticle::provider);
    }

    private Ssc14ModParticles() { }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Ssc14ParticleProviderBinder.registerProviders(event);
    }
}
