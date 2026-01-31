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
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_COATING = REGISTRY.register("ssc_14_coating",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.ssc_14.ssc_14_coating")).icon(() -> new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get())).displayItems((parameters, tabData) -> {
				tabData.accept(Ssc14ModBlocks.ROD_FLOOR.get().asItem());
				tabData.accept(Ssc14ModBlocks.SHEATHING.get().asItem());
				tabData.accept(Ssc14ModBlocks.ROD_UP_FLOOR.get().asItem());
				tabData.accept(Ssc14ModBlocks.UPER_SHEATHING.get().asItem());
				tabData.accept(Ssc14ModBlocks.UP_TITLE.get().asItem());
				tabData.accept(Ssc14ModBlocks.UP_TITLE_STEEL.get().asItem());
				tabData.accept(Ssc14ModBlocks.UP_TITLE_DARK.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_DIAGONAL_MINI.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_DIAGONAL.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_MINI.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_MONO.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_OFFSET.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_PAVEMENT.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_STEEL_HERRINGBONE.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_PLASTIC.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_DIAGONAL_MINI.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_DIAGONAL.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_MINI.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_MONO.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_OFFSET.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_PAVEMENT.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_PAVEMENT_VERTICAL.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_HERRINGBONE.get().asItem());
				tabData.accept(Ssc14ModBlocks.TITLE_DARK_PLASTIC.get().asItem());
			}).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_WALLS = REGISTRY.register("ssc_14_walls",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.ssc_14.ssc_14_walls")).icon(() -> new ItemStack(Ssc14ModBlocks.STEEL_WALL.get())).displayItems((parameters, tabData) -> {
				tabData.accept(Ssc14ModBlocks.STEEL_WALL.get().asItem());
				tabData.accept(Ssc14ModBlocks.WALL_CARCASE.get().asItem());
				tabData.accept(Ssc14ModBlocks.PLASTEEL_WALL.get().asItem());
				tabData.accept(Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get().asItem());
				tabData.accept(Ssc14ModBlocks.BASE_WINDOW.get().asItem());
				tabData.accept(Ssc14ModBlocks.ARMORED_WINDOW.get().asItem());
			}).withTabsBefore(SSC_14_COATING.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_OBJECTS = REGISTRY.register("ssc_14_objects",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.ssc_14.ssc_14_objects")).icon(() -> new ItemStack(Ssc14ModBlocks.CONSOLE_OF_ID.get())).displayItems((parameters, tabData) -> {
				tabData.accept(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().asItem());
				tabData.accept(Ssc14ModBlocks.WALL_CARCASE.get().asItem());
				tabData.accept(Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get().asItem());
				tabData.accept(Ssc14ModBlocks.APC.get().asItem());
				tabData.accept(Ssc14ModBlocks.PODSTATION.get().asItem());
				tabData.accept(Ssc14ModBlocks.SMES.get().asItem());
				tabData.accept(Ssc14ModBlocks.LAMP.get().asItem());
				tabData.accept(Ssc14ModBlocks.TECH_LAMP.get().asItem());
				tabData.accept(Ssc14ModBlocks.CONSOLE_OF_ID.get().asItem());
				tabData.accept(Ssc14ModBlocks.PORTABLE_SHEATER.get().asItem());
			}).withTabsBefore(SSC_14_WALLS.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_ITEMS = REGISTRY.register("ssc_14_items",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.ssc_14.ssc_14_items")).icon(() -> new ItemStack(Ssc14ModItems.GLASS.get())).displayItems((parameters, tabData) -> {
				tabData.accept(Ssc14ModItems.CROWBAR.get());
				tabData.accept(Ssc14ModItems.SPANNER.get());
				tabData.accept(Ssc14ModItems.SCREWDRIVER.get());
				tabData.accept(Ssc14ModItems.NIPPERS.get());
				tabData.accept(Ssc14ModItems.WELDING.get());
				tabData.accept(Ssc14ModItems.FIRE_AXE.get());
				tabData.accept(Ssc14ModItems.GAS_ANALYSER.get());
				tabData.accept(Ssc14ModItems.ACCESS_CONFIG.get());
				tabData.accept(Ssc14ModItems.LOW_VOLTAGE_CABLE.get());
				tabData.accept(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get());
				tabData.accept(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get());
				tabData.accept(Ssc14ModItems.ROOD.get());
				tabData.accept(Ssc14ModItems.STEEL.get());
				tabData.accept(Ssc14ModItems.PLASTEEL.get());
				tabData.accept(Ssc14ModItems.GLASS.get());
				tabData.accept(Ssc14ModItems.ARM_GLASS.get());
				tabData.accept(Ssc14ModItems.PLASTIC.get());
				tabData.accept(Ssc14ModItems.SILVER.get());
				tabData.accept(Ssc14ModItems.SSC_14_GOLD.get());
				tabData.accept(Ssc14ModItems.URAN.get());
				tabData.accept(Ssc14ModItems.PLASMA.get());
				tabData.accept(Ssc14ModItems.LED_LIGHT_TUBE.get());
				tabData.accept(Ssc14ModItems.LIGHT_BULB.get());
				tabData.accept(Ssc14ModItems.ENERGY_BAR.get());
				tabData.accept(Ssc14ModItems.ENERGY_BAR_2.get());
				tabData.accept(Ssc14ModItems.ID_CARD_PASSANGER.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CAPITAN.get());
				tabData.accept(Ssc14ModItems.ID_CARD_PNT.get());
				tabData.accept(Ssc14ModItems.ID_CARD_ADUTANT.get());
				tabData.accept(Ssc14ModItems.ID_CARD_AVD.get());
				tabData.accept(Ssc14ModItems.ID_CARD_OSH.get());
				tabData.accept(Ssc14ModItems.ID_CARD_MIME.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CLOWN.get());
				tabData.accept(Ssc14ModItems.ID_CARD_HO_P.get());
				tabData.accept(Ssc14ModItems.ID_CARD_SERVICE_W.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CLEANER.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CHEF.get());
				tabData.accept(Ssc14ModItems.ID_CARD_BOTANIK.get());
				tabData.accept(Ssc14ModItems.ID_CARD_BARMEN.get());
				tabData.accept(Ssc14ModItems.ID_CARD_PRIEST.get());
				tabData.accept(Ssc14ModItems.ID_CARD_QM.get());
				tabData.accept(Ssc14ModItems.ID_CARD_UTILIZATOR.get());
				tabData.accept(Ssc14ModItems.ID_CARD_LOADER.get());
				tabData.accept(Ssc14ModItems.ID_CARD_REPORTER.get());
				tabData.accept(Ssc14ModItems.ID_CARD_BOXER.get());
				tabData.accept(Ssc14ModItems.ID_CARD_BIBLIOTEKAR.get());
				tabData.accept(Ssc14ModItems.ID_CARD_ZOOTECHNIK.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CE.get());
				tabData.accept(Ssc14ModItems.ID_CARD_VE.get());
				tabData.accept(Ssc14ModItems.ID_CARD_ATMOS.get());
				tabData.accept(Ssc14ModItems.ID_CARD_ENGENEER.get());
				tabData.accept(Ssc14ModItems.ID_CARD_TECH_ASSISTENT.get());
				tabData.accept(Ssc14ModItems.ID_CARD_MUSICANT.get());
				tabData.accept(Ssc14ModItems.ID_CARD_HO_S.get());
				tabData.accept(Ssc14ModItems.ID_CARD_INSTRUCTOR_S.get());
				tabData.accept(Ssc14ModItems.ID_CARD_WARD.get());
				tabData.accept(Ssc14ModItems.ID_CARD_DETECTIVE.get());
				tabData.accept(Ssc14ModItems.ID_CARD_BRIG_MED.get());
				tabData.accept(Ssc14ModItems.ID_CARD_OFICER_S.get());
				tabData.accept(Ssc14ModItems.ID_CARD_KADET_S.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CMO.get());
				tabData.accept(Ssc14ModItems.ID_CARD_VM.get());
				tabData.accept(Ssc14ModItems.ID_CARD_CHIMIC.get());
				tabData.accept(Ssc14ModItems.ID_CARD_PARAMED.get());
				tabData.accept(Ssc14ModItems.ID_CARD_MEDIC.get());
				tabData.accept(Ssc14ModItems.ID_CARD_PSIHOLOG.get());
				tabData.accept(Ssc14ModItems.ID_CARD_INTERN.get());
				tabData.accept(Ssc14ModItems.ID_CARD_RD.get());
				tabData.accept(Ssc14ModItems.ID_CARD_VS.get());
				tabData.accept(Ssc14ModItems.ID_CARD_SCIENTIST.get());
				tabData.accept(Ssc14ModItems.ID_CARD_SCI_ASSISTENT.get());
				tabData.accept(Ssc14ModItems.GLASS_SHARD.get());
				tabData.accept(Ssc14ModItems.ARM_GLASS_SHARD.get());
				tabData.accept(Ssc14ModItems.LED_LIGHT_TUBE_BROKEN.get());
				tabData.accept(Ssc14ModItems.LIGHT_BULB_BROKEN.get());
				tabData.accept(Ssc14ModItems.ENERGY_BAR_TRASH.get());
			}).withTabsBefore(SSC_14_OBJECTS.getId()).build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
			if (tabData.hasPermissions()) {
				tabData.accept(Ssc14ModItems.WALL_CARCASE_ENTIT_SPAWN_EGG.get());
				tabData.accept(Ssc14ModItems.ID_CONSOLE_ENTITY_SPAWN_EGG.get());
				tabData.accept(Ssc14ModItems.PLASSTEEL_WALL_CARCASE_ENTIT_SPAWN_EGG.get());
				tabData.accept(Ssc14ModBlocks.ATMOS_BLOCK.get().asItem());
				tabData.accept(Ssc14ModItems.O_2_DEBU_GHANDGENERATOR.get());
				tabData.accept(Ssc14ModItems.HEATER_DEBU_GHANDGENERATOR.get());
				tabData.accept(Ssc14ModItems.COOLER_DEBU_GHANDGENERATOR.get());
				tabData.accept(Ssc14ModItems.N_2_DEBU_GHANDGENERATOR.get());
				tabData.accept(Ssc14ModItems.AIRLOCK_CONTROLLER.get());
				tabData.accept(Ssc14ModBlocks.DEBU_GGENERATOR.get().asItem());
			}
		}
	}
}