package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class GravityGenerator_PlugBlock_Generate_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (Direction.NORTH == (getDirectionFromBlockState(blockstate))) {
			world.setBlock(BlockPos.containing(x, y, z + 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y + 1, z + 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y + 1, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x - 1, y, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x - 1, y + 1, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
		} else if (Direction.SOUTH == (getDirectionFromBlockState(blockstate))) {
			world.setBlock(BlockPos.containing(x, y, z - 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y + 1, z - 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y + 1, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x - 1, y, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x - 1, y + 1, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
		} else if (Direction.WEST == (getDirectionFromBlockState(blockstate))) {
			world.setBlock(BlockPos.containing(x, y, z + 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y + 1, z + 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y, z - 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y + 1, z - 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y + 1, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
		} else if (Direction.EAST == (getDirectionFromBlockState(blockstate))) {
			world.setBlock(BlockPos.containing(x, y, z + 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y + 1, z + 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y, z - 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y + 1, z - 1), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x - 1, y, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x - 1, y + 1, z), Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState(), 3);
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