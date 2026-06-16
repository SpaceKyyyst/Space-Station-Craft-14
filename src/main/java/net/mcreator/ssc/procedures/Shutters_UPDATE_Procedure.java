package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class Shutters_UPDATE_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (Ssc14ModBlocks.SHUTTERS.get() == (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock()) {
			if ((Direction.NORTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y + 1, z))))) || Direction.SOUTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y + 1, z))))))
					&& (Direction.NORTH == (getDirectionFromBlockState(blockstate)) || Direction.SOUTH == (getDirectionFromBlockState(blockstate)))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("up") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
			} else if ((Direction.WEST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y + 1, z))))) || Direction.EAST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y + 1, z))))))
					&& (Direction.WEST == (getDirectionFromBlockState(blockstate)) || Direction.EAST == (getDirectionFromBlockState(blockstate)))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("up") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
			} else {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("up") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
			}
		} else {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("up") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
			}
		}
		if (Ssc14ModBlocks.SHUTTERS.get() == (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock()) {
			if ((Direction.NORTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y - 1, z))))) || Direction.SOUTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y - 1, z))))))
					&& (Direction.NORTH == (getDirectionFromBlockState(blockstate)) || Direction.SOUTH == (getDirectionFromBlockState(blockstate)))
					&& (true == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y - 1, z))), "opening") instanceof BooleanProperty _getbp45 && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getValue(_getbp45))
							|| false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y - 1, z))), "open") instanceof BooleanProperty _getbp47 && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getValue(_getbp47)))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("down") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
			} else if ((Direction.WEST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y - 1, z))))) || Direction.EAST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y - 1, z))))))
					&& (Direction.WEST == (getDirectionFromBlockState(blockstate)) || Direction.EAST == (getDirectionFromBlockState(blockstate)))
					&& (true == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y - 1, z))), "opening") instanceof BooleanProperty _getbp62 && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getValue(_getbp62))
							|| false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y - 1, z))), "open") instanceof BooleanProperty _getbp64 && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getValue(_getbp64)))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("down") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
			} else {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("down") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
			}
		} else {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("down") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
			}
		}
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		if (getPropertyByName(blockState, "facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class)
			return (Direction) blockState.getValue(ep);
		if (getPropertyByName(blockState, "axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class)
			return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
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