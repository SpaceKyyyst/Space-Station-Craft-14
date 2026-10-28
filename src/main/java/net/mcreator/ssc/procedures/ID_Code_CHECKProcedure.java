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

import net.ssc14.common.network.SlotMenuStateAccess;
import net.ssc14.content.access.IdJobAssignmentService;
import net.mcreator.ssc.Ssc14Mod;

public class ID_Code_CHECKProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(world instanceof net.minecraft.server.level.ServerLevel serverOwner)) return;
		if (entity == null)
			return;
		if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Bufer_LG") == false && getAmountInGUISlot(entity, 0) > 0 && getAmountInGUISlot(entity, 1) > 0) {
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "gun_room");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "HoS");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Brig");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Security");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Detective");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "PNT");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Crio");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "CE");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Atmos");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Ingeneer");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Out");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Qm");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Utilizat");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Supply_Deportament");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "CMO");
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu153 ? _menu153.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Chemistry", false) == true && (entity instanceof Player _entity155 && _entity155.containerMenu instanceof SlotMenuStateAccess _menu155) && _menu155.getMenuState(1, "Chemistry", false)) {
				{
					final String _tagName = "Chemistry";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu156 ? _menu156.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu158 ? _menu158.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Chemistry", false) == true
					&& !((entity instanceof Player _entity160 && _entity160.containerMenu instanceof SlotMenuStateAccess _menu160) && _menu160.getMenuState(1, "Chemistry", false))) {
				{
					final String _tagName = "Chemistry";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu161 ? _menu161.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu163 ? _menu163.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Medical", false) == true && (entity instanceof Player _entity165 && _entity165.containerMenu instanceof SlotMenuStateAccess _menu165) && _menu165.getMenuState(1, "Medical", false)) {
				{
					final String _tagName = "Medical";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu166 ? _menu166.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu168 ? _menu168.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Medical", false) == true
					&& !((entity instanceof Player _entity170 && _entity170.containerMenu instanceof SlotMenuStateAccess _menu170) && _menu170.getMenuState(1, "Medical", false))) {
				{
					final String _tagName = "Medical";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu171 ? _menu171.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu173 ? _menu173.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("RD", false) == true && (entity instanceof Player _entity175 && _entity175.containerMenu instanceof SlotMenuStateAccess _menu175) && _menu175.getMenuState(1, "RD", false)) {
				{
					final String _tagName = "RD";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu176 ? _menu176.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu178 ? _menu178.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("RD", false) == true
					&& !((entity instanceof Player _entity180 && _entity180.containerMenu instanceof SlotMenuStateAccess _menu180) && _menu180.getMenuState(1, "RD", false))) {
				{
					final String _tagName = "RD";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu181 ? _menu181.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu183 ? _menu183.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Scientist", false) == true && (entity instanceof Player _entity185 && _entity185.containerMenu instanceof SlotMenuStateAccess _menu185) && _menu185.getMenuState(1, "Scientist", false)) {
				{
					final String _tagName = "Scientist";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu186 ? _menu186.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu188 ? _menu188.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Scientist", false) == true
					&& !((entity instanceof Player _entity190 && _entity190.containerMenu instanceof SlotMenuStateAccess _menu190) && _menu190.getMenuState(1, "Scientist", false))) {
				{
					final String _tagName = "Scientist";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu191 ? _menu191.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu193 ? _menu193.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Technical", false) == true && (entity instanceof Player _entity195 && _entity195.containerMenu instanceof SlotMenuStateAccess _menu195) && _menu195.getMenuState(1, "Technical", false)) {
				{
					final String _tagName = "Technical";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu196 ? _menu196.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu198 ? _menu198.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Technical", false) == true
					&& !((entity instanceof Player _entity200 && _entity200.containerMenu instanceof SlotMenuStateAccess _menu200) && _menu200.getMenuState(1, "Technical", false))) {
				{
					final String _tagName = "Technical";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu201 ? _menu201.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu203 ? _menu203.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Church", false) == true && (entity instanceof Player _entity205 && _entity205.containerMenu instanceof SlotMenuStateAccess _menu205) && _menu205.getMenuState(1, "Church", false)) {
				{
					final String _tagName = "Church";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu206 ? _menu206.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu208 ? _menu208.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Church", false) == true
					&& !((entity instanceof Player _entity210 && _entity210.containerMenu instanceof SlotMenuStateAccess _menu210) && _menu210.getMenuState(1, "Church", false))) {
				{
					final String _tagName = "Church";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu211 ? _menu211.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu213 ? _menu213.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("HoP", false) == true && (entity instanceof Player _entity215 && _entity215.containerMenu instanceof SlotMenuStateAccess _menu215) && _menu215.getMenuState(1, "HoP", false)) {
				{
					final String _tagName = "HoP";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu216 ? _menu216.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu218 ? _menu218.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("HoP", false) == true
					&& !((entity instanceof Player _entity220 && _entity220.containerMenu instanceof SlotMenuStateAccess _menu220) && _menu220.getMenuState(1, "HoP", false))) {
				{
					final String _tagName = "HoP";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu221 ? _menu221.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu223 ? _menu223.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Service", false) == true && (entity instanceof Player _entity225 && _entity225.containerMenu instanceof SlotMenuStateAccess _menu225) && _menu225.getMenuState(1, "Service", false)) {
				{
					final String _tagName = "Service";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu226 ? _menu226.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu228 ? _menu228.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Service", false) == true
					&& !((entity instanceof Player _entity230 && _entity230.containerMenu instanceof SlotMenuStateAccess _menu230) && _menu230.getMenuState(1, "Service", false))) {
				{
					final String _tagName = "Service";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu231 ? _menu231.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu233 ? _menu233.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Kitchen", false) == true && (entity instanceof Player _entity235 && _entity235.containerMenu instanceof SlotMenuStateAccess _menu235) && _menu235.getMenuState(1, "Kitchen", false)) {
				{
					final String _tagName = "Kitchen";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu236 ? _menu236.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu238 ? _menu238.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Kitchen", false) == true
					&& !((entity instanceof Player _entity240 && _entity240.containerMenu instanceof SlotMenuStateAccess _menu240) && _menu240.getMenuState(1, "Kitchen", false))) {
				{
					final String _tagName = "Kitchen";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu241 ? _menu241.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu243 ? _menu243.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Gidroponic", false) == true && (entity instanceof Player _entity245 && _entity245.containerMenu instanceof SlotMenuStateAccess _menu245) && _menu245.getMenuState(1, "Gidroponic", false)) {
				{
					final String _tagName = "Gidroponic";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu246 ? _menu246.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu248 ? _menu248.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Gidroponic", false) == true
					&& !((entity instanceof Player _entity250 && _entity250.containerMenu instanceof SlotMenuStateAccess _menu250) && _menu250.getMenuState(1, "Gidroponic", false))) {
				{
					final String _tagName = "Gidroponic";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu251 ? _menu251.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu253 ? _menu253.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Bar", false) == true && (entity instanceof Player _entity255 && _entity255.containerMenu instanceof SlotMenuStateAccess _menu255) && _menu255.getMenuState(1, "Bar", false)) {
				{
					final String _tagName = "Bar";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu256 ? _menu256.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu258 ? _menu258.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Bar", false) == true
					&& !((entity instanceof Player _entity260 && _entity260.containerMenu instanceof SlotMenuStateAccess _menu260) && _menu260.getMenuState(1, "Bar", false))) {
				{
					final String _tagName = "Bar";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu261 ? _menu261.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu263 ? _menu263.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Teatre", false) == true && (entity instanceof Player _entity265 && _entity265.containerMenu instanceof SlotMenuStateAccess _menu265) && _menu265.getMenuState(1, "Teatre", false)) {
				{
					final String _tagName = "Teatre";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu266 ? _menu266.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu268 ? _menu268.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Teatre", false) == true
					&& !((entity instanceof Player _entity270 && _entity270.containerMenu instanceof SlotMenuStateAccess _menu270) && _menu270.getMenuState(1, "Teatre", false))) {
				{
					final String _tagName = "Teatre";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu271 ? _menu271.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu273 ? _menu273.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Cleaner", false) == true && (entity instanceof Player _entity275 && _entity275.containerMenu instanceof SlotMenuStateAccess _menu275) && _menu275.getMenuState(1, "Cleaner", false)) {
				{
					final String _tagName = "Cleaner";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu276 ? _menu276.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu278 ? _menu278.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Cleaner", false) == true
					&& !((entity instanceof Player _entity280 && _entity280.containerMenu instanceof SlotMenuStateAccess _menu280) && _menu280.getMenuState(1, "Cleaner", false))) {
				{
					final String _tagName = "Cleaner";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu281 ? _menu281.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu283 ? _menu283.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Capitan", false) == true && (entity instanceof Player _entity285 && _entity285.containerMenu instanceof SlotMenuStateAccess _menu285) && _menu285.getMenuState(1, "Capitan", false)) {
				{
					final String _tagName = "Capitan";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu286 ? _menu286.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu288 ? _menu288.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Capitan", false) == true
					&& !((entity instanceof Player _entity290 && _entity290.containerMenu instanceof SlotMenuStateAccess _menu290) && _menu290.getMenuState(1, "Capitan", false))) {
				{
					final String _tagName = "Capitan";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu291 ? _menu291.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu293 ? _menu293.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Command", false) == true && (entity instanceof Player _entity295 && _entity295.containerMenu instanceof SlotMenuStateAccess _menu295) && _menu295.getMenuState(1, "Command", false)) {
				{
					final String _tagName = "Command";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu296 ? _menu296.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu298 ? _menu298.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Command", false) == true
					&& !((entity instanceof Player _entity300 && _entity300.containerMenu instanceof SlotMenuStateAccess _menu300) && _menu300.getMenuState(1, "Command", false))) {
				{
					final String _tagName = "Command";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu301 ? _menu301.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu303 ? _menu303.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Blue_Sh", false) == true && (entity instanceof Player _entity305 && _entity305.containerMenu instanceof SlotMenuStateAccess _menu305) && _menu305.getMenuState(1, "Blue_Sh", false)) {
				{
					final String _tagName = "Blue_Sh";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu306 ? _menu306.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu308 ? _menu308.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Blue_Sh", false) == true
					&& !((entity instanceof Player _entity310 && _entity310.containerMenu instanceof SlotMenuStateAccess _menu310) && _menu310.getMenuState(1, "Blue_Sh", false))) {
				{
					final String _tagName = "Blue_Sh";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu311 ? _menu311.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu313 ? _menu313.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
					.copyTag().getBooleanOr("Uridic", false) == true && (entity instanceof Player _entity315 && _entity315.containerMenu instanceof SlotMenuStateAccess _menu315) && _menu315.getMenuState(1, "Uridic", false)) {
				{
					final String _tagName = "Uridic";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu316 ? _menu316.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu318 ? _menu318.getSlots().get(0).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Uridic", false) == true
					&& !((entity instanceof Player _entity320 && _entity320.containerMenu instanceof SlotMenuStateAccess _menu320) && _menu320.getMenuState(1, "Uridic", false))) {
				{
					final String _tagName = "Uridic";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu321 ? _menu321.getSlots().get(1).getItem() : ItemStack.EMPTY),
							tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
			if ((entity instanceof Player _entity323 && _entity323.containerMenu instanceof SlotMenuStateAccess _menu323) && _menu323.getMenuState(1, "Job_Name_Readact", false)) {
				(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof SlotMenuStateAccess _menu325 ? _menu325.getSlots().get(1).getItem() : ItemStack.EMPTY).set(DataComponents.CUSTOM_NAME, Component
						.literal(("ID \u043A\u0430\u0440\u0442\u0430 (" + ((entity instanceof Player _entity324 && _entity324.containerMenu instanceof SlotMenuStateAccess _menu324) ? _menu324.getMenuState(0, "Job_Name", "") : "") + ")")));
			}
		}
		Ssc14Mod.queueServerWork(serverOwner, 1, () -> {
			IDCode0CellprProcedure.execute(entity);
		}).orThrow();
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}

	private static int getAmountInGUISlot(Entity entity, int sltid) {
		if (entity instanceof Player player && player.containerMenu instanceof SlotMenuStateAccess menuAccessor) {
			ItemStack stack = menuAccessor.getSlots().get(sltid).getItem();
			if (stack != null)
				return stack.getCount();
		}
		return 0;
	}
}
