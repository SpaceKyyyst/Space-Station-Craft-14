/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.mcreator.ssc.Ssc14Mod;

public class Ssc14ModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, Ssc14Mod.MODID);
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_ACTIVE_PARTICLE = REGISTRY.register("grav_gen_active_particle", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_PARTICLE_2 = REGISTRY.register("grav_gen_particle_2", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_PARTICLE_3 = REGISTRY.register("grav_gen_particle_3", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_PARTICLE_4 = REGISTRY.register("grav_gen_particle_4", () -> new SimpleParticleType(true));
}