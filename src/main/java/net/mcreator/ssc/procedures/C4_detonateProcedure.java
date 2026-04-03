package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.mcreator.ssc.init.Ssc14ModEntities;

public class C4_detonateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, (x + 0.5), (y + 0.5), (z + 0.5), 5, Level.ExplosionInteraction.TNT);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (x + 0.5), (y + 0.5), (z + 0.5), 50, 0.5, 0.5, 0.5, 0.1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (x + 0.5), (y + 0.5), (z + 0.5), 10, 0, 0, 0, 0.4);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 100, 0, 0, 0, 0.3);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = Ssc14ModEntities.C_4_CRUTCH_ENT.get().spawn(_level, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), EntitySpawnReason.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setDeltaMovement(0, 0, 0);
			}
		}
	}
}