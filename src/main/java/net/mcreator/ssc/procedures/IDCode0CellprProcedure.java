package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModMenus;

public class IDCode0CellprProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (getAmountInGUISlot(entity, 0) > 0 && getAmountInGUISlot(entity, 1) > 0) {
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu2 ? _menu2.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("gun_room", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "gun_room", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "gun_room", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu6 ? _menu6.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoS", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "HoS", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "HoS", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu10 ? _menu10.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Brig", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Brig", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Brig", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu14 ? _menu14.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Security", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Security", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Security", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu18 ? _menu18.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Detective", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Detective", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Detective", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu22 ? _menu22.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("PNT", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "PNT", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "PNT", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu26 ? _menu26.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Crio", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Crio", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Crio", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu30 ? _menu30.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CE", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "CE", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "CE", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu34 ? _menu34.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Atmos", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Atmos", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Atmos", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu38 ? _menu38.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Ingeneer", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Ingeneer", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Ingeneer", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu42 ? _menu42.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Out", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Out", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Out", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu46 ? _menu46.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Qm", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Qm", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Qm", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu50 ? _menu50.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Utilizat", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Utilizat", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Utilizat", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu54 ? _menu54.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Supply_Deportament", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Supply_Deportament", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Supply_Deportament", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu58 ? _menu58.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CMO", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "CMO", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "CMO", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu62 ? _menu62.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Chemistry", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Chemistry", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Chemistry", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu66 ? _menu66.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Medical", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Medical", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Medical", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu70 ? _menu70.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("RD", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "RD", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "RD", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu74 ? _menu74.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Scientist", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Scientist", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Scientist", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu78 ? _menu78.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Technical", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Technical", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Technical", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu82 ? _menu82.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Church", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Church", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Church", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu86 ? _menu86.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoP", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "HoP", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "HoP", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu90 ? _menu90.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Service", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Service", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Service", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu94 ? _menu94.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Kitchen", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Kitchen", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Kitchen", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu98 ? _menu98.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Gidroponic", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Gidroponic", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Gidroponic", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu102 ? _menu102.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Bar", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Bar", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Bar", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu106 ? _menu106.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Teatre", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Teatre", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Teatre", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu110 ? _menu110.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Cleaner", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Cleaner", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Cleaner", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu114 ? _menu114.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Capitan", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Capitan", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Capitan", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu118 ? _menu118.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Command", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Command", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Command", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu122 ? _menu122.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Blue_Sh", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Blue_Sh", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Blue_Sh", false, true);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu126 ? _menu126.getSlots().get(1).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Uridic", false) == true) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Uridic", true, true);
			} else {
				if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
					_menu.sendMenuStateUpdate(_player, 1, "Uridic", false, true);
			}
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "gun_room", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "HoS", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Brig", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Security", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Detective", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "PNT", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Crio", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "CE", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Atmos", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Ingeneer", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Out", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Qm", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Utilizat", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Supply_Deportament", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "CMO", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Chemistry", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Medical", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "RD", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Scientist", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Technical", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Church", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "HoP", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Service", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Kitchen", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Gidroponic", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Bar", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Teatre", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Cleaner", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Capitan", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Command", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Blue_Sh", false, true);
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Uridic", false, true);
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