package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModMenus;

public class CEIDCLockGUIProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (getAmountInGUISlot(entity, 0) > 0 && getAmountInGUISlot(entity, 1) > 0) {
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu2 ? _menu2.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CE", false) == false) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private static int getAmountInGUISlot(Entity entity, int sltid) {
		if (entity instanceof Player player && player.containerMenu instanceof Ssc14ModMenus.MenuAccessor menuAccessor) {
			ItemStack stack = menuAccessor.getSlots().get(sltid).getItem();
			if (stack != null)
				return stack.getCount();
		}
		return 0;
	}
}