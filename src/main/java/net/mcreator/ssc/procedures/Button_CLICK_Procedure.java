package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class Button_CLICK_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "active") instanceof BooleanProperty _getbp1 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp1))) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x + 1, y + 1, z + 1), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:machines_button")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound((x + 1), (y + 1), (z + 1), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:machines_button")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("active") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
			}
			Ssc14Mod.queueServerWork(20, () -> {
				if (Ssc14ModBlocks.BUTTON.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
						&& true == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "active") instanceof BooleanProperty _getbp7 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp7))) {
					{
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("active") instanceof BooleanProperty _booleanProp)
							world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
					}
				}
			});
			// ОТПРАВКА ТРИГГЕРА В СЕТЬ (только на сервере!)
			if (world instanceof Level _level && !_level.isClientSide()) {
				net.mcreator.ssc.NetworkManager.trigger(_level, BlockPos.containing(x, y, z), "activate");
			}
		} else {
			if (world instanceof Level _level)
				_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
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