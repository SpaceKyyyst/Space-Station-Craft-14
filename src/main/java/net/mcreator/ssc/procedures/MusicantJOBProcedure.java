package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModMenus;
import net.mcreator.ssc.Ssc14Mod;

public class MusicantJOBProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (getAmountInGUISlot(entity, 0) > 0 && getAmountInGUISlot(entity, 1) > 0) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putBoolean("Bufer_LG", true);
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			Ssc14Mod.queueServerWork(1, () -> {
				if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu3 ? _menu3.getSlots().get(0).getItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
						.copyTag().getBooleanOr("Technical", false) == true
						&& (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu5 ? _menu5.getSlots().get(0).getItem() : ItemStack.EMPTY)
								.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Teatre", false) == true) {
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu7 ? _menu7.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("gun_room", false) == true) {
						{
							final String _tagName = "gun_room";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu9 ? _menu9.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu11 ? _menu11.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("HoS", false) == true) {
						{
							final String _tagName = "HoS";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu13 ? _menu13.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu15 ? _menu15.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Brig", false) == true) {
						{
							final String _tagName = "Brig";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu17 ? _menu17.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu19 ? _menu19.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Security", false) == true) {
						{
							final String _tagName = "Security";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu21 ? _menu21.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu23 ? _menu23.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Detective", false) == true) {
						{
							final String _tagName = "Detective";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu25 ? _menu25.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu27 ? _menu27.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("PNT", false) == true) {
						{
							final String _tagName = "PNT";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu29 ? _menu29.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu31 ? _menu31.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Crio", false) == true) {
						{
							final String _tagName = "Crio";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu33 ? _menu33.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu35 ? _menu35.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("CE", false) == true) {
						{
							final String _tagName = "CE";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu37 ? _menu37.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu39 ? _menu39.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Atmos", false) == true) {
						{
							final String _tagName = "Atmos";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu41 ? _menu41.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu43 ? _menu43.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Ingeneer", false) == true) {
						{
							final String _tagName = "Ingeneer";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu45 ? _menu45.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu47 ? _menu47.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Out", false) == true) {
						{
							final String _tagName = "Out";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu49 ? _menu49.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu51 ? _menu51.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Qm", false) == true) {
						{
							final String _tagName = "Qm";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu53 ? _menu53.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu55 ? _menu55.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Utilizat", false) == true) {
						{
							final String _tagName = "Utilizat";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu57 ? _menu57.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu59 ? _menu59.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Supply_Deportament", false) == true) {
						{
							final String _tagName = "Supply_Deportament";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu61 ? _menu61.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu63 ? _menu63.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("CMO", false) == true) {
						{
							final String _tagName = "CMO";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu65 ? _menu65.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu67 ? _menu67.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Chemistry", false) == true) {
						{
							final String _tagName = "Chemistry";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu69 ? _menu69.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu71 ? _menu71.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Medical", false) == true) {
						{
							final String _tagName = "Medical";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu73 ? _menu73.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu75 ? _menu75.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("RD", false) == true) {
						{
							final String _tagName = "RD";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu77 ? _menu77.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu79 ? _menu79.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Scientist", false) == true) {
						{
							final String _tagName = "Scientist";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu81 ? _menu81.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu83 ? _menu83.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Technical", false) == true) {
						{
							final String _tagName = "Technical";
							final boolean _tagValue = true;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu85 ? _menu85.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu87 ? _menu87.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Church", false) == true) {
						{
							final String _tagName = "Church";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu89 ? _menu89.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu91 ? _menu91.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("HoP", false) == true) {
						{
							final String _tagName = "HoP";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu93 ? _menu93.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu95 ? _menu95.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Service", false) == true) {
						{
							final String _tagName = "Service";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu97 ? _menu97.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu99 ? _menu99.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Kitchen", false) == true) {
						{
							final String _tagName = "Kitchen";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu101 ? _menu101.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu103 ? _menu103.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Gidroponic", false) == true) {
						{
							final String _tagName = "Gidroponic";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu105 ? _menu105.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu107 ? _menu107.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Bar", false) == true) {
						{
							final String _tagName = "Bar";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu109 ? _menu109.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu111 ? _menu111.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Teatre", false) == true) {
						{
							final String _tagName = "Teatre";
							final boolean _tagValue = true;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu113 ? _menu113.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu115 ? _menu115.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Cleaner", false) == true) {
						{
							final String _tagName = "Cleaner";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu117 ? _menu117.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu119 ? _menu119.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Capitan", false) == true) {
						{
							final String _tagName = "Capitan";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu121 ? _menu121.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu123 ? _menu123.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Command", false) == true) {
						{
							final String _tagName = "Command";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu125 ? _menu125.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu127 ? _menu127.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Blue_Sh", false) == true) {
						{
							final String _tagName = "Blue_Sh";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu129 ? _menu129.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu131 ? _menu131.getSlots().get(0).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("Uridic", false) == true) {
						{
							final String _tagName = "Uridic";
							final boolean _tagValue = false;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu133 ? _menu133.getSlots().get(1).getItem() : ItemStack.EMPTY),
									tag -> tag.putBoolean(_tagName, _tagValue));
						}
					}
					if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
						_menu.sendMenuStateUpdate(_player, 0, "Job_Name", "\u041C\u0443\u0437\u044B\u043A\u0430\u043D\u0442", true);
					(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu137 ? _menu137.getSlots().get(1).getItem() : ItemStack.EMPTY).set(DataComponents.CUSTOM_NAME, Component
							.literal(("ID \u043A\u0430\u0440\u0442\u0430 (" + ((entity instanceof Player _entity136 && _entity136.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu136) ? _menu136.getMenuState(0, "Job_Name", "") : "") + ")")));
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putBoolean("Bufer_LG", false);
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			});
		} else {
			if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu)
				_menu.sendMenuStateUpdate(_player, 0, "Job_Name", "\u0412\u0421\u0422\u0410\u0412\u042C\u0422\u0415 \u041A\u0410\u0420\u0422\u042B", true);
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