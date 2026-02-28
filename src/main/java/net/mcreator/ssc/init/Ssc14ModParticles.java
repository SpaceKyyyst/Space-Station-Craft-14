/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.particle.GravGenParticle4Particle;
import net.mcreator.ssc.client.particle.GravGenParticle3Particle;
import net.mcreator.ssc.client.particle.GravGenParticle2Particle;
import net.mcreator.ssc.client.particle.GravGenActiveParticleParticle;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_ACTIVE_PARTICLE.get(), GravGenActiveParticleParticle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_2.get(), GravGenParticle2Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_3.get(), GravGenParticle3Particle::provider);
		event.registerSpriteSet(Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_4.get(), GravGenParticle4Particle::provider);
	}
}