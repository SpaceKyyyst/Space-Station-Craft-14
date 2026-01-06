/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public class Ssc14ModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Ssc14Mod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14CREATIVEV = REGISTRY.register("ssc_14creativev",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.ssc_14.ssc_14creativev")).icon(() -> new ItemStack(Ssc14ModItems.SSCLOGO.get())).displayItems((parameters, tabData) -> {
				tabData.accept(Ssc14ModBlocks.ROD_FLOOR.get().asItem());
				tabData.accept(Ssc14ModBlocks.SHEATHING.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL.get().asItem());
				tabData.accept(Ssc14ModBlocks.ROD_UP_FLOOR.get().asItem());
				tabData.accept(Ssc14ModBlocks.UPER_SHEATHING.get().asItem());
				tabData.accept(Ssc14ModBlocks.UP_TITLE.get().asItem());
				tabData.accept(Ssc14ModBlocks.STEEL_WALL.get().asItem());
				tabData.accept(Ssc14ModBlocks.WALL_CARCASE.get().asItem());
				tabData.accept(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().asItem());
				tabData.accept(Ssc14ModItems.ROOD.get());
				tabData.accept(Ssc14ModItems.STEEL.get());
				tabData.accept(Ssc14ModItems.PLASTEEL.get());
				tabData.accept(Ssc14ModItems.LOW_VOLTAGE_CABLE.get());
				tabData.accept(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get());
				tabData.accept(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get());
				tabData.accept(Ssc14ModBlocks.CONSOLE_OF_ID.get().asItem());
				tabData.accept(Ssc14ModItems.SCRAP.get());
				tabData.accept(Ssc14ModItems.SPANNER.get());
				tabData.accept(Ssc14ModItems.NIPPERS.get());
				tabData.accept(Ssc14ModItems.SCREWDRIVER.get());
				tabData.accept(Ssc14ModItems.WELDING.get());
				tabData.accept(Ssc14ModItems.GAS_ANALYSER.get());
				tabData.accept(Ssc14ModItems.FIRE_AXE.get());
				tabData.accept(Ssc14ModItems.ID_CARD_PASSANGER.get());
				tabData.accept(Ssc14ModItems.BASE_UPLINK_RADIO.get());
				tabData.accept(Ssc14ModItems.TELECRYSTAL.get());
			}).build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
			if (tabData.hasPermissions()) {
				tabData.accept(Ssc14ModItems.WALL_CARCASE_ENTIT_SPAWN_EGG.get());
				tabData.accept(Ssc14ModItems.ID_CONSOLE_ENTITY_SPAWN_EGG.get());
				tabData.accept(Ssc14ModBlocks.ATMOS_BLOCK.get().asItem());
				tabData.accept(Ssc14ModItems.O_2_DEBU_GHANDGENERATOR.get());
			}
		}
	}
}