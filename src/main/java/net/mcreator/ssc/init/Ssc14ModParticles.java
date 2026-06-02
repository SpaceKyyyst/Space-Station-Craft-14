/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.particle.*;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_ACTIVE_PARTICLE.get(), GravGenActiveParticleParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_2.get(), GravGenParticle2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_3.get(), GravGenParticle3Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_4.get(), GravGenParticle4Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.SPARK.get(), SparkParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.PLASMA_PARTICLES_1.get(), PlasmaParticles1Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.PLASMA_PARTICLES_2.get(), PlasmaParticles2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_1.get(), WaterVaporParticles1Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_2.get(), WaterVaporParticles2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_3.get(), WaterVaporParticles3Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_4.get(), WaterVaporParticles4Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.AMMONIA_PARTICLES_1.get(), AmmoniaParticles1Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.AMMONIA_PARTICLES_2.get(), AmmoniaParticles2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.AMMONIA_PARTICLES_3.get(), AmmoniaParticles3Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.AMMONIA_PARTICLES_4.get(), AmmoniaParticles4Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.FREON_PARTICLES_1.get(), FreonParticles1Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.FREON_PARTICLES_2.get(), FreonParticles2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.FREON_PARTICLES_3.get(), FreonParticles3Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.FREON_PARTICLES_4.get(), FreonParticles4Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.HELIUM_PARTICLES_1.get(), HeliumParticles1Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.HELIUM_PARTICLES_2.get(), HeliumParticles2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.HELIUM_PARTICLES_3.get(), HeliumParticles3Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.HELIUM_PARTICLES_4.get(), HeliumParticles4Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.TRITIUM_PARTICLES_1.get(), TritiumParticles1Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.TRITIUM_PARTICLES_2.get(), TritiumParticles2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.EXTINGUISHER_SPRAY.get(), ExtinguisherSprayParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.DECONSTRUCT_P.get(), DeconstructPParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.DECONSTRUCT_PX.get(), DeconstructPXParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.DECONSTRUCT_PZ.get(), DeconstructPZParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.RCD_INTERFERENCE_P.get(), RCDInterferencePParticle::provider);
	}
}