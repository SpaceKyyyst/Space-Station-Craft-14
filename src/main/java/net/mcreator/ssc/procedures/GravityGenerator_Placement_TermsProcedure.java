package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class GravityGenerator_Placement_TermsProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (((world.getBlockState(BlockPos.containing(x + 1, y, z))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
				|| (world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x - 1, y, z))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x, y, z + 1))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x, y, z - 1))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x, y + 1, z + 1))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x, y + 1, z + 1))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x, y + 1, z - 1))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x, y + 1, z - 1))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x + 1, y + 1, z))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x + 1, y + 1, z))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())
				&& ((world.getBlockState(BlockPos.containing(x - 1, y + 1, z))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable")))
						|| (world.getBlockState(BlockPos.containing(x - 1, y + 1, z))).getBlock() == Ssc14ModBlocks.GRAV_GEN_PLUG_BLOCK.get())) {
			return true;
		}
		return false;
	}
}