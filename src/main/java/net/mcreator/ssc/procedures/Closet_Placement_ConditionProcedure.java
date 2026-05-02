package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class Closet_Placement_ConditionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).is(BlockTags.create(ResourceLocation.parse("minecraft:replaceable"))) || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Ssc14ModBlocks.UP_CLOSET_PLUG.get()) {
			return true;
		}
		return false;
	}
}