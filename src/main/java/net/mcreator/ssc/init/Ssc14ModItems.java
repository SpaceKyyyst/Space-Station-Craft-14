/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.ssc.item.inventory.AccessConfigInventoryCapability;
import net.mcreator.ssc.item.*;
import net.mcreator.ssc.Ssc14Mod;

import java.util.function.Function;

@EventBusSubscriber
public class Ssc14ModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Ssc14Mod.MODID);
	public static final DeferredItem<Item> SSCLOGO;
	public static final DeferredItem<Item> ROD_FLOOR;
	public static final DeferredItem<Item> ROOD;
	public static final DeferredItem<Item> SHEATHING;
	public static final DeferredItem<Item> STEEL;
	public static final DeferredItem<Item> PLASTEEL;
	public static final DeferredItem<Item> LOW_VOLTAGE_CABLE;
	public static final DeferredItem<Item> MEDIUM_VOLTAGE_CABLE;
	public static final DeferredItem<Item> HIGH_VOLTAGE_CABLE;
	public static final DeferredItem<Item> SCRAP;
	public static final DeferredItem<Item> TITLE_STEEL;
	public static final DeferredItem<Item> STEEL_WALL;
	public static final DeferredItem<Item> WALL_CARCASE;
	public static final DeferredItem<Item> WELDING;
	public static final DeferredItem<Item> ACTIVE_WELDER;
	public static final DeferredItem<Item> NIPPERS;
	public static final DeferredItem<Item> FIRE_AXE;
	public static final DeferredItem<Item> UPER_SHEATHING;
	public static final DeferredItem<Item> ROD_UP_FLOOR;
	public static final DeferredItem<Item> SCREWDRIVER;
	public static final DeferredItem<Item> SPANNER;
	public static final DeferredItem<Item> BASE_AIRLOCK_D_1;
	public static final DeferredItem<Item> UP_TITLE;
	public static final DeferredItem<Item> WALL_CARCASE_ENTIT_SPAWN_EGG;
	public static final DeferredItem<Item> ID_CARD_PASSANGER;
	public static final DeferredItem<Item> CONSOLE_OF_ID;
	public static final DeferredItem<Item> ID_CONSOLE_ENTITY_SPAWN_EGG;
	public static final DeferredItem<Item> ATMOS_BLOCK;
	public static final DeferredItem<Item> GAS_ANALYSER;
	public static final DeferredItem<Item> O_2_DEBU_GHANDGENERATOR;
	public static final DeferredItem<Item> BASE_WINDOW;
	public static final DeferredItem<Item> GLASS_SHARD;
	public static final DeferredItem<Item> GLASS;
	public static final DeferredItem<Item> ACCESS_CONFIG;
	public static final DeferredItem<Item> ID_CARD_CAPITAN;
	public static final DeferredItem<Item> ID_CARD_PNT;
	public static final DeferredItem<Item> ID_CARD_ADUTANT;
	public static final DeferredItem<Item> ID_CARD_AVD;
	public static final DeferredItem<Item> ID_CARD_OSH;
	public static final DeferredItem<Item> ID_CARD_MIME;
	public static final DeferredItem<Item> ID_CARD_CLOWN;
	public static final DeferredItem<Item> ID_CARD_HO_P;
	public static final DeferredItem<Item> ID_CARD_SERVICE_W;
	public static final DeferredItem<Item> ID_CARD_CLEANER;
	public static final DeferredItem<Item> ID_CARD_CHEF;
	public static final DeferredItem<Item> ID_CARD_BOTANIK;
	public static final DeferredItem<Item> ID_CARD_BARMEN;
	public static final DeferredItem<Item> ID_CARD_PRIEST;
	public static final DeferredItem<Item> ID_CARD_QM;
	public static final DeferredItem<Item> ID_CARD_UTILIZATOR;
	public static final DeferredItem<Item> ID_CARD_LOADER;
	public static final DeferredItem<Item> ID_CARD_REPORTER;
	public static final DeferredItem<Item> ID_CARD_BOXER;
	public static final DeferredItem<Item> ID_CARD_BIBLIOTEKAR;
	public static final DeferredItem<Item> ID_CARD_ZOOTECHNIK;
	public static final DeferredItem<Item> ID_CARD_CE;
	public static final DeferredItem<Item> ID_CARD_VE;
	public static final DeferredItem<Item> ID_CARD_ATMOS;
	public static final DeferredItem<Item> ID_CARD_ENGENEER;
	public static final DeferredItem<Item> ID_CARD_TECH_ASSISTENT;
	public static final DeferredItem<Item> ID_CARD_MUSICANT;
	public static final DeferredItem<Item> ID_CARD_HO_S;
	public static final DeferredItem<Item> ID_CARD_INSTRUCTOR_S;
	public static final DeferredItem<Item> ID_CARD_WARD;
	public static final DeferredItem<Item> ID_CARD_DETECTIVE;
	public static final DeferredItem<Item> ID_CARD_BRIG_MED;
	public static final DeferredItem<Item> ID_CARD_OFICER_S;
	public static final DeferredItem<Item> ID_CARD_KADET_S;
	public static final DeferredItem<Item> ID_CARD_CMO;
	public static final DeferredItem<Item> ID_CARD_VM;
	public static final DeferredItem<Item> ID_CARD_CHIMIC;
	public static final DeferredItem<Item> ID_CARD_PARAMED;
	public static final DeferredItem<Item> ID_CARD_MEDIC;
	public static final DeferredItem<Item> ID_CARD_PSIHOLOG;
	public static final DeferredItem<Item> ID_CARD_INTERN;
	public static final DeferredItem<Item> ID_CARD_RD;
	public static final DeferredItem<Item> ID_CARD_VS;
	public static final DeferredItem<Item> ID_CARD_SCIENTIST;
	public static final DeferredItem<Item> ID_CARD_SCI_ASSISTENT;
	public static final DeferredItem<Item> HEATER_DEBU_GHANDGENERATOR;
	public static final DeferredItem<Item> COOLER_DEBU_GHANDGENERATOR;
	public static final DeferredItem<Item> PORTABLE_SHEATER;
	public static final DeferredItem<Item> SILVER;
	public static final DeferredItem<Item> SSC_14_GOLD;
	public static final DeferredItem<Item> URAN;
	public static final DeferredItem<Item> PLASMA;
	public static final DeferredItem<Item> PLASTIC;
	static {
		SSCLOGO = register("ssclogo", SsclogoItem::new);
		ROD_FLOOR = block(Ssc14ModBlocks.ROD_FLOOR, new Item.Properties().stacksTo(1).fireResistant());
		ROOD = register("rood", RoodItem::new);
		SHEATHING = block(Ssc14ModBlocks.SHEATHING, new Item.Properties().stacksTo(1).fireResistant());
		STEEL = register("steel", SteelItem::new);
		PLASTEEL = register("plasteel", PlasteelItem::new);
		LOW_VOLTAGE_CABLE = register("low_voltage_cable", LowVoltageCableItem::new);
		MEDIUM_VOLTAGE_CABLE = register("medium_voltage_cable", MediumVoltageCableItem::new);
		HIGH_VOLTAGE_CABLE = register("high_voltage_cable", HighVoltageCableItem::new);
		SCRAP = register("scrap", ScrapItem::new);
		TITLE_STEEL = block(Ssc14ModBlocks.TITLE_STEEL, new Item.Properties().stacksTo(30).fireResistant());
		STEEL_WALL = block(Ssc14ModBlocks.STEEL_WALL, new Item.Properties().stacksTo(1).fireResistant());
		WALL_CARCASE = block(Ssc14ModBlocks.WALL_CARCASE, new Item.Properties().stacksTo(1).fireResistant());
		WELDING = register("welding", WeldingItem::new);
		ACTIVE_WELDER = register("active_welder", ActiveWelderItem::new);
		NIPPERS = register("nippers", NippersItem::new);
		FIRE_AXE = register("fire_axe", FireAxeItem::new);
		UPER_SHEATHING = block(Ssc14ModBlocks.UPER_SHEATHING, new Item.Properties().stacksTo(1).fireResistant());
		ROD_UP_FLOOR = block(Ssc14ModBlocks.ROD_UP_FLOOR, new Item.Properties().stacksTo(1).fireResistant());
		SCREWDRIVER = register("screwdriver", ScrewdriverItem::new);
		SPANNER = register("spanner", SpannerItem::new);
		BASE_AIRLOCK_D_1 = block(Ssc14ModBlocks.BASE_AIRLOCK_D_1, new Item.Properties().stacksTo(1).fireResistant());
		UP_TITLE = block(Ssc14ModBlocks.UP_TITLE, new Item.Properties().stacksTo(30).fireResistant());
		WALL_CARCASE_ENTIT_SPAWN_EGG = register("wall_carcase_entit_spawn_egg", properties -> new SpawnEggItem(Ssc14ModEntities.WALL_CARCASE_ENTIT.get(), properties));
		ID_CARD_PASSANGER = register("id_card_passanger", IDCardPassangerItem::new);
		CONSOLE_OF_ID = block(Ssc14ModBlocks.CONSOLE_OF_ID, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant());
		ID_CONSOLE_ENTITY_SPAWN_EGG = register("id_console_entity_spawn_egg", properties -> new SpawnEggItem(Ssc14ModEntities.ID_CONSOLE_ENTITY.get(), properties));
		ATMOS_BLOCK = block(Ssc14ModBlocks.ATMOS_BLOCK, new Item.Properties().stacksTo(1));
		GAS_ANALYSER = register("gas_analyser", GasAnalyserItem::new);
		O_2_DEBU_GHANDGENERATOR = register("o_2_debu_ghandgenerator", O2DEBUGhandgeneratorItem::new);
		BASE_WINDOW = block(Ssc14ModBlocks.BASE_WINDOW, new Item.Properties().stacksTo(1).fireResistant());
		GLASS_SHARD = register("glass_shard", GlassShardItem::new);
		GLASS = register("glass", GlassItem::new);
		ACCESS_CONFIG = register("access_config", AccessConfigItem::new);
		ID_CARD_CAPITAN = register("id_card_capitan", IDCardCapitanItem::new);
		ID_CARD_PNT = register("id_card_pnt", IDCardPNTItem::new);
		ID_CARD_ADUTANT = register("id_card_adutant", IDCardAdutantItem::new);
		ID_CARD_AVD = register("id_card_avd", IDCardAVDItem::new);
		ID_CARD_OSH = register("id_card_osh", IDCardOSHItem::new);
		ID_CARD_MIME = register("id_card_mime", IDCardMimeItem::new);
		ID_CARD_CLOWN = register("id_card_clown", IDCardClownItem::new);
		ID_CARD_HO_P = register("id_card_ho_p", IDCardHoPItem::new);
		ID_CARD_SERVICE_W = register("id_card_service_w", IDCardServiceWItem::new);
		ID_CARD_CLEANER = register("id_card_cleaner", IDCardCleanerItem::new);
		ID_CARD_CHEF = register("id_card_chef", IDCardChefItem::new);
		ID_CARD_BOTANIK = register("id_card_botanik", IDCardBotanikItem::new);
		ID_CARD_BARMEN = register("id_card_barmen", IDCardBarmenItem::new);
		ID_CARD_PRIEST = register("id_card_priest", IDCardPriestItem::new);
		ID_CARD_QM = register("id_card_qm", IDCardQmItem::new);
		ID_CARD_UTILIZATOR = register("id_card_utilizator", IDCardUtilizatorItem::new);
		ID_CARD_LOADER = register("id_card_loader", IDCardLoaderItem::new);
		ID_CARD_REPORTER = register("id_card_reporter", IDCardReporterItem::new);
		ID_CARD_BOXER = register("id_card_boxer", IDCardBoxerItem::new);
		ID_CARD_BIBLIOTEKAR = register("id_card_bibliotekar", IDCardBibliotekarItem::new);
		ID_CARD_ZOOTECHNIK = register("id_card_zootechnik", IDCardZootechnikItem::new);
		ID_CARD_CE = register("id_card_ce", IDCardCEItem::new);
		ID_CARD_VE = register("id_card_ve", IDCardVEItem::new);
		ID_CARD_ATMOS = register("id_card_atmos", IDCardAtmosItem::new);
		ID_CARD_ENGENEER = register("id_card_engeneer", IDCardEngeneerItem::new);
		ID_CARD_TECH_ASSISTENT = register("id_card_tech_assistent", IDCardTechAssistentItem::new);
		ID_CARD_MUSICANT = register("id_card_musicant", IDCardMusicantItem::new);
		ID_CARD_HO_S = register("id_card_ho_s", IDCardHoSItem::new);
		ID_CARD_INSTRUCTOR_S = register("id_card_instructor_s", IDCardInstructorSItem::new);
		ID_CARD_WARD = register("id_card_ward", IDCardWardItem::new);
		ID_CARD_DETECTIVE = register("id_card_detective", IDCardDetectiveItem::new);
		ID_CARD_BRIG_MED = register("id_card_brig_med", IDCardBrigMedItem::new);
		ID_CARD_OFICER_S = register("id_card_oficer_s", IDCardOficerSItem::new);
		ID_CARD_KADET_S = register("id_card_kadet_s", IDCardKadetSItem::new);
		ID_CARD_CMO = register("id_card_cmo", IDCardCMOItem::new);
		ID_CARD_VM = register("id_card_vm", IDCardVMItem::new);
		ID_CARD_CHIMIC = register("id_card_chimic", IDCardChimicItem::new);
		ID_CARD_PARAMED = register("id_card_paramed", IDCardParamedItem::new);
		ID_CARD_MEDIC = register("id_card_medic", IDCardMedicItem::new);
		ID_CARD_PSIHOLOG = register("id_card_psiholog", IDCardPsihologItem::new);
		ID_CARD_INTERN = register("id_card_intern", IDCardInternItem::new);
		ID_CARD_RD = register("id_card_rd", IDCardRDItem::new);
		ID_CARD_VS = register("id_card_vs", IDCardVSItem::new);
		ID_CARD_SCIENTIST = register("id_card_scientist", IDCardScientistItem::new);
		ID_CARD_SCI_ASSISTENT = register("id_card_sci_assistent", IDCardSciAssistentItem::new);
		HEATER_DEBU_GHANDGENERATOR = register("heater_debu_ghandgenerator", HeaterDEBUGhandgeneratorItem::new);
		COOLER_DEBU_GHANDGENERATOR = register("cooler_debu_ghandgenerator", CoolerDEBUGhandgeneratorItem::new);
		PORTABLE_SHEATER = block(Ssc14ModBlocks.PORTABLE_SHEATER, new Item.Properties().stacksTo(1));
		SILVER = register("silver", SilverItem::new);
		SSC_14_GOLD = register("ssc_14_gold", SSC14GoldItem::new);
		URAN = register("uran", UranItem::new);
		PLASMA = register("plasma", PlasmaItem::new);
		PLASTIC = register("plastic", PlasticItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> new AccessConfigInventoryCapability(stack), ACCESS_CONFIG.get());
	}
}