package net.ssc14.content.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;

public final class Ssc14ParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY =
            DeferredRegister.create(Registries.PARTICLE_TYPE, Ssc14Identity.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_ACTIVE_PARTICLE = register("grav_gen_active_particle", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_PARTICLE_2 = register("grav_gen_particle_2", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_PARTICLE_3 = register("grav_gen_particle_3", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAV_GEN_PARTICLE_4 = register("grav_gen_particle_4", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARK = register("spark", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PLASMA_PARTICLES_1 = register("plasma_particles_1", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PLASMA_PARTICLES_2 = register("plasma_particles_2", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_1 = register("water_vapor_particles_1", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_2 = register("water_vapor_particles_2", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_3 = register("water_vapor_particles_3", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_VAPOR_PARTICLES_4 = register("water_vapor_particles_4", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_1 = register("ammonia_particles_1", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_2 = register("ammonia_particles_2", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_3 = register("ammonia_particles_3", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMMONIA_PARTICLES_4 = register("ammonia_particles_4", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_1 = register("freon_particles_1", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_2 = register("freon_particles_2", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_3 = register("freon_particles_3", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREON_PARTICLES_4 = register("freon_particles_4", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_1 = register("helium_particles_1", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_2 = register("helium_particles_2", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_3 = register("helium_particles_3", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HELIUM_PARTICLES_4 = register("helium_particles_4", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TRITIUM_PARTICLES_1 = register("tritium_particles_1", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TRITIUM_PARTICLES_2 = register("tritium_particles_2", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXTINGUISHER_SPRAY = register("extinguisher_spray", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RCD_INTERFERENCE_P = register("rcd_interference_p", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPECIFY_P = register("specify_p", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BULLET_FLIGHT_P = register("bullet_flight_p", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DECONSTRUCT_P = register("deconstruct_p", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DECONSTRUCT_PX = register("deconstruct_px", true);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DECONSTRUCT_PZ = register("deconstruct_pz", true);

    private Ssc14ParticleTypes() {
    }

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(
            String path, boolean overrideLimiter) {
        return REGISTRY.register(path, () -> new SimpleParticleType(overrideLimiter));
    }
}
