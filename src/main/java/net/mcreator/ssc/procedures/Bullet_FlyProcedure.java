package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.ssc14.content.particle.Ssc14ParticleTypes;

public class Bullet_FlyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (Ssc14ParticleTypes.BULLET_FLIGHT_P.get()), x, y, z, 1, 0.0625, 0.0625, 0.0625, 0.1);
	}
}
