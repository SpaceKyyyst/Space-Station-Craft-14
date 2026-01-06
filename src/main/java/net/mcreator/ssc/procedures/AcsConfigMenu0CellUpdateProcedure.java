package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModMenus;

public class AcsConfigMenu0CellUpdateProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"gun_room") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "gun_room", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "gun_room", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"HoS") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "HoS", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "HoS", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Brig") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Brig", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Brig", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Security") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Security", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Security", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Detective") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Detective", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Detective", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"PNT") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "PNT", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "PNT", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Crio") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Crio", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Crio", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"CE") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "CE", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "CE", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Atmos") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Atmos", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Atmos", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Ingeneer") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Ingeneer", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Ingeneer", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Out") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Out", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Out", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Qm") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Qm", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Qm", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Utilizat") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Utilizat", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Utilizat", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Supply_Deportament") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Supply_Deportament", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Supply_Deportament", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"CMO") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "CMO", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "CMO", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Chemistry") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Chemistry", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Chemistry", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Medical") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Medical", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Medical", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"RD") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "RD", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "RD", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Scientist") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Scientist", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Scientist", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Technical") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Technical", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Technical", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Church") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Church", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Church", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"HoP") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "HoP", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "HoP", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Service") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Service", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Service", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Kitchen") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Kitchen", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Kitchen", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Gidroponic") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Gidroponic", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Gidroponic", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Bar") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Bar", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Bar", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Teatre") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Teatre", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Teatre", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Cleaner") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Cleaner", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Cleaner", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Capitan") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Capitan", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Capitan", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Command") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Command", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Command", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Blue_Sh") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Blue_Sh", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Blue_Sh", false, true);
		}
		if (getBlockNBTLogic(world,
				BlockPos.containing((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_X", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Y", 0),
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("ACSC_Z", 0)),
				"Uridic") == true) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Uridic", true, true);
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 1, "Uridic", false, true);
		}
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}