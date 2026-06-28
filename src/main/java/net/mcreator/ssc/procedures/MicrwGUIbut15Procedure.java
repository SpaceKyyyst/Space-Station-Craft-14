package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class MicrwGUIbut15Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "on_off") instanceof BooleanProperty _getbp1 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp1)
				&& !(getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "active") instanceof BooleanProperty _getbp3 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp3))) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("timer", 15);
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
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