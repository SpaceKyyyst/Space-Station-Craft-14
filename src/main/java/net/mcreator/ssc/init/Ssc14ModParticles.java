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
	}
}