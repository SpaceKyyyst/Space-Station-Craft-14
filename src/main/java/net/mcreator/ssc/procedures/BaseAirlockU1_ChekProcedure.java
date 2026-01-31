package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class BaseAirlockU1_ChekProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get() || (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get())) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof ServerLevel _level) {
				_level.getServer().getPlayerList().broadcastSystemMessage(
						Component.literal("! \u0423\u0414\u0410\u041B\u0415\u041D\u0418\u0415 \u0412\u0415\u0420\u0425\u041D\u0415\u0413\u041E \u0411\u041B\u041E\u041A\u0410 !").withColor(0xff0000).withStyle(ChatFormatting.BOLD), false);
			}
		}
	}
}