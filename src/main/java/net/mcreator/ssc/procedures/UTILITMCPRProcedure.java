package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;

public class UTILITMCPRProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		world.addParticle((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), x, y, z, 0, 2, 1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CLOUD, x, y, z, 5, 3, 3, 3, 1);
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}