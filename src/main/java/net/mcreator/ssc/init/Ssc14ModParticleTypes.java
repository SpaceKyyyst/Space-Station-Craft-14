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
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARK = REGISTRY.register("spark", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PLASMA_PARTICLES_1 = REGISTRY.register("plasma_particles_1", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PLASMA_PARTICLES_2 = REGISTRY.register("plasma_particles_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_1 = REGISTRY.register("water_vapor_particles_1", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_2 = REGISTRY.register("water_vapor_particles_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_3 = REGISTRY.register("water_vapor_particles_3", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_4 = REGISTRY.register("water_vapor_particles_4", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_1 = REGISTRY.register("ammonia_particles_1", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_2 = REGISTRY.register("ammonia_particles_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_3 = REGISTRY.register("ammonia_particles_3", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_4 = REGISTRY.register("ammonia_particles_4", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_1 = REGISTRY.register("freon_particles_1", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_2 = REGISTRY.register("freon_particles_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_3 = REGISTRY.register("freon_particles_3", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_4 = REGISTRY.register("freon_particles_4", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_1 = REGISTRY.register("helium_particles_1", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_2 = REGISTRY.register("helium_particles_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_3 = REGISTRY.register("helium_particles_3", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_4 = REGISTRY.register("helium_particles_4", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TRITIUM_PARTICLES_1 = REGISTRY.register("tritium_particles_1", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TRITIUM_PARTICLES_2 = REGISTRY.register("tritium_particles_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXTINGUISHER_SPRAY = REGISTRY.register("extinguisher_spray", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DECONSTRUCT_P = REGISTRY.register("deconstruct_p", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DECONSTRUCT_PX = REGISTRY.register("deconstruct_px", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DECONSTRUCT_PZ = REGISTRY.register("deconstruct_pz", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RCD_INTERFERENCE_P = REGISTRY.register("rcd_interference_p", () -> new SimpleParticleType(true));
}