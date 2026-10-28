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
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Chemistry");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Medical");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "RD");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Scientist");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Technical");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Church");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "HoP");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Service");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Kitchen");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Gidroponic");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Bar");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Teatre");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Cleaner");
			}
			if (entity instanceof Player player) {
				IdJobAssignmentService.syncAccessField(player, "Capitan");
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
