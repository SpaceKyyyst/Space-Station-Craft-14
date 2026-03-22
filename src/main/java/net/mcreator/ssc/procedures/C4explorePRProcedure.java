package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class C4explorePRProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "c4timer") >= getBlockNBTNumber(world, BlockPos.containing(x, y, z), "c4setting") * 20) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, (x + 0.5), (y + 0.5), (z + 0.5), 5, Level.ExplosionInteraction.TNT);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (x + 0.5), (y + 0.5), (z + 0.5), 50, 0.5, 0.5, 0.5, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (x + 0.5), (y + 0.5), (z + 0.5), 10, 0, 0, 0, 0.4);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 100, 0, 0, 0, 0.3);
		} else {
			Ssc14Mod.queueServerWork(1, () -> {
				if (Ssc14ModBlocks.C_4.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("c4timer", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "c4timer") + 1));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					C4explorePRProcedure.execute(world, x, y, z);
				} else {
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("c4timer", 0);
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			});
		}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}