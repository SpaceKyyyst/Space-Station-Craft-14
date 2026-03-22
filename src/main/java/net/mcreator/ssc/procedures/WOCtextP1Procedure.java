package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class WOCtextP1Procedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String loc_buf_t = "";
		double X = 0;
		if (entity.onGround() || !entity.onGround()) {
			if (entity instanceof Player player) {
				// Получаем блок, на который смотрит игрок (луч длиной 5 блоков)
				BlockHitResult hitResult = (BlockHitResult) player.pick(5.0, 0.0F, false);
				if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
					BlockPos pos = hitResult.getBlockPos();
					BlockState blockState = player.level().getBlockState(pos);
					// Вариант 1: Через ItemStack (рекомендуется)
					ItemStack stack = new ItemStack(blockState.getBlock());
					loc_buf_t = stack.getHoverName().getString();
					// Вариант 2: Через систему переводов (если нет блок-предмета)
					// loc_buf_t = Component.translatable(blockState.getBlock().getDescriptionId()).getString();
					System.out.println("[LOOK] Block name: " + loc_buf_t);
				} else {
					// Нет блока под прицелом
					loc_buf_t = "Nothing";
					System.out.println("[LOOK] No block targeted");
				}
			}
		}
		return "" + loc_buf_t;
	}
}