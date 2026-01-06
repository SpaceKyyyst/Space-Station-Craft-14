package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModMenus;
import net.mcreator.ssc.Ssc14Mod;

public class ID_Code_CHECKProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Bufer_LG") == false && getAmountInGUISlot(entity, 0) > 0 && getAmountInGUISlot(entity, 1) > 0) {
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu3 ? _menu3.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("gun_room", false) == true && (entity instanceof Player _entity5 && _entity5.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu5) && _menu5.getMenuState(1, "gun_room", false)) {
				{
					final String _tagName = "gun_room";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu6 ? _menu6.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu8 ? _menu8.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("gun_room", false) == true
					&& !((entity instanceof Player _entity10 && _entity10.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu10) && _menu10.getMenuState(1, "gun_room", false))) {
				{
					final String _tagName = "gun_room";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu11 ? _menu11.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu13 ? _menu13.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoS", false) == true && (entity instanceof Player _entity15 && _entity15.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu15) && _menu15.getMenuState(1, "HoS", false)) {
				{
					final String _tagName = "HoS";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu16 ? _menu16.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu18 ? _menu18.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("HoS", false) == true
					&& !((entity instanceof Player _entity20 && _entity20.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu20) && _menu20.getMenuState(1, "HoS", false))) {
				{
					final String _tagName = "HoS";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu21 ? _menu21.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu23 ? _menu23.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Brig", false) == true && (entity instanceof Player _entity25 && _entity25.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu25) && _menu25.getMenuState(1, "Brig", false)) {
				{
					final String _tagName = "Brig";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu26 ? _menu26.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu28 ? _menu28.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Brig", false) == true
					&& !((entity instanceof Player _entity30 && _entity30.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu30) && _menu30.getMenuState(1, "Brig", false))) {
				{
					final String _tagName = "Brig";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu31 ? _menu31.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu33 ? _menu33.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Security", false) == true && (entity instanceof Player _entity35 && _entity35.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu35) && _menu35.getMenuState(1, "Security", false)) {
				{
					final String _tagName = "Security";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu36 ? _menu36.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu38 ? _menu38.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Security", false) == true
					&& !((entity instanceof Player _entity40 && _entity40.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu40) && _menu40.getMenuState(1, "Security", false))) {
				{
					final String _tagName = "Security";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu41 ? _menu41.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu43 ? _menu43.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Detective", false) == true && (entity instanceof Player _entity45 && _entity45.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu45) && _menu45.getMenuState(1, "Detective", false)) {
				{
					final String _tagName = "Detective";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu46 ? _menu46.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu48 ? _menu48.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Detective", false) == true
					&& !((entity instanceof Player _entity50 && _entity50.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu50) && _menu50.getMenuState(1, "Detective", false))) {
				{
					final String _tagName = "Detective";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu51 ? _menu51.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu53 ? _menu53.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("PNT", false) == true && (entity instanceof Player _entity55 && _entity55.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu55) && _menu55.getMenuState(1, "PNT", false)) {
				{
					final String _tagName = "PNT";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu56 ? _menu56.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu58 ? _menu58.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("PNT", false) == true
					&& !((entity instanceof Player _entity60 && _entity60.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu60) && _menu60.getMenuState(1, "PNT", false))) {
				{
					final String _tagName = "PNT";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu61 ? _menu61.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu63 ? _menu63.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Crio", false) == true && (entity instanceof Player _entity65 && _entity65.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu65) && _menu65.getMenuState(1, "Crio", false)) {
				{
					final String _tagName = "Crio";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu66 ? _menu66.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu68 ? _menu68.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Crio", false) == true
					&& !((entity instanceof Player _entity70 && _entity70.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu70) && _menu70.getMenuState(1, "Crio", false))) {
				{
					final String _tagName = "Crio";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu71 ? _menu71.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu73 ? _menu73.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CE", false) == true && (entity instanceof Player _entity75 && _entity75.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu75) && _menu75.getMenuState(1, "CE", false)) {
				{
					final String _tagName = "CE";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu76 ? _menu76.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu78 ? _menu78.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("CE", false) == true
					&& !((entity instanceof Player _entity80 && _entity80.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu80) && _menu80.getMenuState(1, "CE", false))) {
				{
					final String _tagName = "CE";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu81 ? _menu81.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu83 ? _menu83.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Atmos", false) == true && (entity instanceof Player _entity85 && _entity85.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu85) && _menu85.getMenuState(1, "Atmos", false)) {
				{
					final String _tagName = "Atmos";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu86 ? _menu86.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu88 ? _menu88.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Atmos", false) == true
					&& !((entity instanceof Player _entity90 && _entity90.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu90) && _menu90.getMenuState(1, "Atmos", false))) {
				{
					final String _tagName = "Atmos";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu91 ? _menu91.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu93 ? _menu93.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Ingeneer", false) == true && (entity instanceof Player _entity95 && _entity95.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu95) && _menu95.getMenuState(1, "Ingeneer", false)) {
				{
					final String _tagName = "Ingeneer";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu96 ? _menu96.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu98 ? _menu98.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Ingeneer", false) == true
					&& !((entity instanceof Player _entity100 && _entity100.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu100) && _menu100.getMenuState(1, "Ingeneer", false))) {
				{
					final String _tagName = "Ingeneer";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu101 ? _menu101.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu103 ? _menu103.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Out", false) == true && (entity instanceof Player _entity105 && _entity105.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu105) && _menu105.getMenuState(1, "Out", false)) {
				{
					final String _tagName = "Out";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu106 ? _menu106.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu108 ? _menu108.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Out", false) == true
					&& !((entity instanceof Player _entity110 && _entity110.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu110) && _menu110.getMenuState(1, "Out", false))) {
				{
					final String _tagName = "Out";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu111 ? _menu111.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu113 ? _menu113.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Qm", false) == true && (entity instanceof Player _entity115 && _entity115.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu115) && _menu115.getMenuState(1, "Qm", false)) {
				{
					final String _tagName = "Qm";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu116 ? _menu116.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu118 ? _menu118.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Qm", false) == true
					&& !((entity instanceof Player _entity120 && _entity120.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu120) && _menu120.getMenuState(1, "Qm", false))) {
				{
					final String _tagName = "Qm";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu121 ? _menu121.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu123 ? _menu123.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Utilizat", false) == true && (entity instanceof Player _entity125 && _entity125.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu125) && _menu125.getMenuState(1, "Utilizat", false)) {
				{
					final String _tagName = "Utilizat";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu126 ? _menu126.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu128 ? _menu128.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Utilizat", false) == true
					&& !((entity instanceof Player _entity130 && _entity130.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu130) && _menu130.getMenuState(1, "Utilizat", false))) {
				{
					final String _tagName = "Utilizat";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu131 ? _menu131.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu133 ? _menu133.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Supply_Deportament", false) == true && (entity instanceof Player _entity135 && _entity135.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu135)
					&& _menu135.getMenuState(1, "Supply_Deportament", false)) {
				{
					final String _tagName = "Supply_Deportament";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu136 ? _menu136.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu138 ? _menu138.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Supply_Deportament", false) == true
					&& !((entity instanceof Player _entity140 && _entity140.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu140) && _menu140.getMenuState(1, "Supply_Deportament", false))) {
				{
					final String _tagName = "Supply_Deportament";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu141 ? _menu141.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu143 ? _menu143.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("CMO", false) == true && (entity instanceof Player _entity145 && _entity145.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu145) && _menu145.getMenuState(1, "CMO", false)) {
				{
					final String _tagName = "CMO";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu146 ? _menu146.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu148 ? _menu148.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("CMO", false) == true
					&& !((entity instanceof Player _entity150 && _entity150.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu150) && _menu150.getMenuState(1, "CMO", false))) {
				{
					final String _tagName = "CMO";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu151 ? _menu151.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu153 ? _menu153.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Chemistry", false) == true && (entity instanceof Player _entity155 && _entity155.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu155) && _menu155.getMenuState(1, "Chemistry", false)) {
				{
					final String _tagName = "Chemistry";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu156 ? _menu156.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu158 ? _menu158.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Chemistry", false) == true
					&& !((entity instanceof Player _entity160 && _entity160.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu160) && _menu160.getMenuState(1, "Chemistry", false))) {
				{
					final String _tagName = "Chemistry";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu161 ? _menu161.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu163 ? _menu163.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Medical", false) == true && (entity instanceof Player _entity165 && _entity165.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu165) && _menu165.getMenuState(1, "Medical", false)) {
				{
					final String _tagName = "Medical";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu166 ? _menu166.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu168 ? _menu168.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Medical", false) == true
					&& !((entity instanceof Player _entity170 && _entity170.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu170) && _menu170.getMenuState(1, "Medical", false))) {
				{
					final String _tagName = "Medical";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu171 ? _menu171.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu173 ? _menu173.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("RD", false) == true && (entity instanceof Player _entity175 && _entity175.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu175) && _menu175.getMenuState(1, "RD", false)) {
				{
					final String _tagName = "RD";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu176 ? _menu176.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu178 ? _menu178.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("RD", false) == true
					&& !((entity instanceof Player _entity180 && _entity180.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu180) && _menu180.getMenuState(1, "RD", false))) {
				{
					final String _tagName = "RD";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu181 ? _menu181.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu183 ? _menu183.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Scientist", false) == true && (entity instanceof Player _entity185 && _entity185.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu185) && _menu185.getMenuState(1, "Scientist", false)) {
				{
					final String _tagName = "Scientist";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu186 ? _menu186.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu188 ? _menu188.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Scientist", false) == true
					&& !((entity instanceof Player _entity190 && _entity190.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu190) && _menu190.getMenuState(1, "Scientist", false))) {
				{
					final String _tagName = "Scientist";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu191 ? _menu191.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu193 ? _menu193.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Technical", false) == true && (entity instanceof Player _entity195 && _entity195.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu195) && _menu195.getMenuState(1, "Technical", false)) {
				{
					final String _tagName = "Technical";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu196 ? _menu196.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu198 ? _menu198.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Technical", false) == true
					&& !((entity instanceof Player _entity200 && _entity200.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu200) && _menu200.getMenuState(1, "Technical", false))) {
				{
					final String _tagName = "Technical";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu201 ? _menu201.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu203 ? _menu203.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Church", false) == true && (entity instanceof Player _entity205 && _entity205.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu205) && _menu205.getMenuState(1, "Church", false)) {
				{
					final String _tagName = "Church";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu206 ? _menu206.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu208 ? _menu208.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Church", false) == true
					&& !((entity instanceof Player _entity210 && _entity210.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu210) && _menu210.getMenuState(1, "Church", false))) {
				{
					final String _tagName = "Church";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu211 ? _menu211.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu213 ? _menu213.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoP", false) == true && (entity instanceof Player _entity215 && _entity215.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu215) && _menu215.getMenuState(1, "HoP", false)) {
				{
					final String _tagName = "HoP";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu216 ? _menu216.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu218 ? _menu218.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("HoP", false) == true
					&& !((entity instanceof Player _entity220 && _entity220.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu220) && _menu220.getMenuState(1, "HoP", false))) {
				{
					final String _tagName = "HoP";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu221 ? _menu221.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu223 ? _menu223.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Service", false) == true && (entity instanceof Player _entity225 && _entity225.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu225) && _menu225.getMenuState(1, "Service", false)) {
				{
					final String _tagName = "Service";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu226 ? _menu226.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu228 ? _menu228.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Service", false) == true
					&& !((entity instanceof Player _entity230 && _entity230.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu230) && _menu230.getMenuState(1, "Service", false))) {
				{
					final String _tagName = "Service";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu231 ? _menu231.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu233 ? _menu233.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Kitchen", false) == true && (entity instanceof Player _entity235 && _entity235.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu235) && _menu235.getMenuState(1, "Kitchen", false)) {
				{
					final String _tagName = "Kitchen";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu236 ? _menu236.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu238 ? _menu238.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Kitchen", false) == true
					&& !((entity instanceof Player _entity240 && _entity240.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu240) && _menu240.getMenuState(1, "Kitchen", false))) {
				{
					final String _tagName = "Kitchen";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu241 ? _menu241.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu243 ? _menu243.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Gidroponic", false) == true && (entity instanceof Player _entity245 && _entity245.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu245) && _menu245.getMenuState(1, "Gidroponic", false)) {
				{
					final String _tagName = "Gidroponic";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu246 ? _menu246.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu248 ? _menu248.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Gidroponic", false) == true
					&& !((entity instanceof Player _entity250 && _entity250.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu250) && _menu250.getMenuState(1, "Gidroponic", false))) {
				{
					final String _tagName = "Gidroponic";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu251 ? _menu251.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu253 ? _menu253.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Bar", false) == true && (entity instanceof Player _entity255 && _entity255.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu255) && _menu255.getMenuState(1, "Bar", false)) {
				{
					final String _tagName = "Bar";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu256 ? _menu256.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu258 ? _menu258.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Bar", false) == true
					&& !((entity instanceof Player _entity260 && _entity260.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu260) && _menu260.getMenuState(1, "Bar", false))) {
				{
					final String _tagName = "Bar";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu261 ? _menu261.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu263 ? _menu263.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Teatre", false) == true && (entity instanceof Player _entity265 && _entity265.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu265) && _menu265.getMenuState(1, "Teatre", false)) {
				{
					final String _tagName = "Teatre";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu266 ? _menu266.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu268 ? _menu268.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Teatre", false) == true
					&& !((entity instanceof Player _entity270 && _entity270.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu270) && _menu270.getMenuState(1, "Teatre", false))) {
				{
					final String _tagName = "Teatre";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu271 ? _menu271.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu273 ? _menu273.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Cleaner", false) == true && (entity instanceof Player _entity275 && _entity275.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu275) && _menu275.getMenuState(1, "Cleaner", false)) {
				{
					final String _tagName = "Cleaner";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu276 ? _menu276.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu278 ? _menu278.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Cleaner", false) == true
					&& !((entity instanceof Player _entity280 && _entity280.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu280) && _menu280.getMenuState(1, "Cleaner", false))) {
				{
					final String _tagName = "Cleaner";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu281 ? _menu281.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu283 ? _menu283.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Capitan", false) == true && (entity instanceof Player _entity285 && _entity285.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu285) && _menu285.getMenuState(1, "Capitan", false)) {
				{
					final String _tagName = "Capitan";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu286 ? _menu286.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu288 ? _menu288.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Capitan", false) == true
					&& !((entity instanceof Player _entity290 && _entity290.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu290) && _menu290.getMenuState(1, "Capitan", false))) {
				{
					final String _tagName = "Capitan";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu291 ? _menu291.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu293 ? _menu293.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Command", false) == true && (entity instanceof Player _entity295 && _entity295.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu295) && _menu295.getMenuState(1, "Command", false)) {
				{
					final String _tagName = "Command";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu296 ? _menu296.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu298 ? _menu298.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Command", false) == true
					&& !((entity instanceof Player _entity300 && _entity300.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu300) && _menu300.getMenuState(1, "Command", false))) {
				{
					final String _tagName = "Command";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu301 ? _menu301.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu303 ? _menu303.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Blue_Sh", false) == true && (entity instanceof Player _entity305 && _entity305.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu305) && _menu305.getMenuState(1, "Blue_Sh", false)) {
				{
					final String _tagName = "Blue_Sh";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu306 ? _menu306.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu308 ? _menu308.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Blue_Sh", false) == true
					&& !((entity instanceof Player _entity310 && _entity310.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu310) && _menu310.getMenuState(1, "Blue_Sh", false))) {
				{
					final String _tagName = "Blue_Sh";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu311 ? _menu311.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu313 ? _menu313.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Uridic", false) == true && (entity instanceof Player _entity315 && _entity315.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu315) && _menu315.getMenuState(1, "Uridic", false)) {
				{
					final String _tagName = "Uridic";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu316 ? _menu316.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu318 ? _menu318.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Uridic", false) == true
					&& !((entity instanceof Player _entity320 && _entity320.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu320) && _menu320.getMenuState(1, "Uridic", false))) {
				{
					final String _tagName = "Uridic";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu321 ? _menu321.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _entity323 && _entity323.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu323) && _menu323.getMenuState(1, "Job_Name_Readact", false)) {
				(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu325 ? _menu325.getSlots().get(1).getItem() : ItemStack.EMPTY).set(DataComponents.CUSTOM_NAME, Component
						.literal(("ID \u043A\u0430\u0440\u0442\u0430 (" + ((entity instanceof Player _entity324 && _entity324.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu324) ? _menu324.getMenuState(0, "Job_Name", "") : "") + ")")));
			}
		}
		Ssc14Mod.queueServerWork(1, () -> {
			IDCode0CellprProcedure.execute(entity);
		});
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
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