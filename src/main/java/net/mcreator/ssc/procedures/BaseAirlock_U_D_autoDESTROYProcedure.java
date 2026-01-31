package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

public class BaseAirlock_U_D_autoDESTROYProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(
					Component.literal("! \u0423\u0414\u0410\u041B\u0415\u041D\u0418\u0415 \u041D\u0418\u0416\u041D\u0415\u0413\u041E \u0411\u041B\u041E\u041A\u0410 !").withColor(0xff0000).withStyle(ChatFormatting.BOLD), false);
		}
	}
}