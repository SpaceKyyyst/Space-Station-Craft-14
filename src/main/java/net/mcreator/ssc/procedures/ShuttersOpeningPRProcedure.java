package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class ShuttersOpeningPRProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (Ssc14ModBlocks.SHUTTERS.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			if (false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "open") instanceof BooleanProperty _getbp3 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp3))
					&& false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "opening") instanceof BooleanProperty _getbp5 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp5))) {
				if (Ssc14ModBlocks.SHUTTERS.get() == (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock()
						&& false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y - 1, z))), "open") instanceof BooleanProperty _getbp9 && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getValue(_getbp9))) {
					ShuttersOpeningPRProcedure.execute(world, x, y - 1, z, blockstate, entity);
				} else {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:machines_shutter")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound((x + 0.5), (y + 0.5), (z + 0.5), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:machines_shutter")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					{
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("opening") instanceof BooleanProperty _booleanProp)
							world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
					}
					Ssc14Mod.queueServerWork(5, () -> {
						ShuttersOpeningPRProcedure.execute(world, x, y, z, blockstate, entity);
					});
				}
			} else if (false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "open") instanceof BooleanProperty _getbp14 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp14))
					&& true == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "opening") instanceof BooleanProperty _getbp16 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp16))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("opening") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
				Ssc14Mod.queueServerWork(5, () -> {
					if (Ssc14ModBlocks.SHUTTERS.get() == (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock()) {
						ShuttersOpeningPRProcedure.execute(world, x, y + 1, z, blockstate, entity);
					}
				});
				// ОТПРАВКА ТРИГГЕРА В СЕТЬ (только на сервере!)
				if (world instanceof net.minecraft.world.level.Level _level && !_level.isClientSide()) {
					net.mcreator.ssc.NetworkManager.trigger(_level, BlockPos.containing(x, y, z), "toggle");
				}
			} else if (true == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "open") instanceof BooleanProperty _getbp23 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp23))
					&& false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "opening") instanceof BooleanProperty _getbp25 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp25))) {
				Ssc14Mod.queueServerWork(5, () -> {
					if (Ssc14ModBlocks.SHUTTERS.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
						ShuttersOpeningPRProcedure.execute(world, x, y, z, blockstate, entity);
					}
				});
			}
		}
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}