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

import net.mcreator.ssc.item.inventory.IDCardPassangerInventoryCapability;
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
		event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> new IDCardPassangerInventoryCapability(stack), ID_CARD_PASSANGER.get());
	}
}