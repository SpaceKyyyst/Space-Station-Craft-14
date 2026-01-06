package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModMenus;

public class IDCodeALLONProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (getAmountInGUISlot(entity, 0) > 0 && getAmountInGUISlot(entity, 1) > 0) {
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu2 ? _menu2.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("gun_room", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "gun_room", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu5 ? _menu5.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoS", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "HoS", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu8 ? _menu8.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Brig", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Brig", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu11 ? _menu11.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Security", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Security", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu14 ? _menu14.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Detective", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Detective", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu17 ? _menu17.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("PNT", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "PNT", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu20 ? _menu20.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Crio", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Crio", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu23 ? _menu23.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CE", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "CE", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu26 ? _menu26.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Atmos", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Atmos", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu29 ? _menu29.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Ingeneer", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Ingeneer", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu32 ? _menu32.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Out", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Out", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu35 ? _menu35.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Qm", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Qm", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu38 ? _menu38.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Utilizat", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Utilizat", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu41 ? _menu41.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Supply_Deportament", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Supply_Deportament", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu44 ? _menu44.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CMO", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "CMO", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu47 ? _menu47.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Chemistry", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Chemistry", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu50 ? _menu50.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Medical", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Medical", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu53 ? _menu53.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("RD", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "RD", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu56 ? _menu56.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Scientist", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Scientist", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu59 ? _menu59.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Technical", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Technical", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu62 ? _menu62.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Church", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Church", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu65 ? _menu65.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoP", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "HoP", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu68 ? _menu68.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Service", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Service", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu71 ? _menu71.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Kitchen", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Kitchen", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu74 ? _menu74.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Gidroponic", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Gidroponic", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu77 ? _menu77.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Bar", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Bar", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu80 ? _menu80.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Teatre", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Teatre", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu83 ? _menu83.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Cleaner", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Cleaner", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu86 ? _menu86.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Capitan", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Capitan", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu89 ? _menu89.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Command", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Command", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu92 ? _menu92.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Blue_Sh", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Blue_Sh", true, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu95 ? _menu95.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Uridic", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Uridic", true, true);
			}
		}
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