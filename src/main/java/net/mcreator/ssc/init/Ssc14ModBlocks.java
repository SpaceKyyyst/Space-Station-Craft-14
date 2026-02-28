/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.mcreator.ssc.block.*;
import net.mcreator.ssc.Ssc14Mod;

import java.util.function.Function;

public class Ssc14ModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Ssc14Mod.MODID);
	public static final DeferredBlock<Block> VOIDB;
	public static final DeferredBlock<Block> SPACED_PORTAL;
	public static final DeferredBlock<Block> ROD_FLOOR;
	public static final DeferredBlock<Block> SHEATHING;
	public static final DeferredBlock<Block> TITLE_STEEL;
	public static final DeferredBlock<Block> STEEL_WALL;
	public static final DeferredBlock<Block> WALL_CARCASE;
	public static final DeferredBlock<Block> UPER_SHEATHING;
	public static final DeferredBlock<Block> ROD_UP_FLOOR;
	public static final DeferredBlock<Block> BASE_AIRLOCK_D_1;
	public static final DeferredBlock<Block> UP_TITLE;
	public static final DeferredBlock<Block> CONSOLE_OF_ID;
	public static final DeferredBlock<Block> ATMOS_BLOCK;
	public static final DeferredBlock<Block> BASE_WINDOW;
	public static final DeferredBlock<Block> BASE_AIRLOCK_D_1OPEN;
	public static final DeferredBlock<Block> PORTABLE_SHEATER;
	public static final DeferredBlock<Block> PLASTEEL_WALL;
	public static final DeferredBlock<Block> PLASTEEL_WALL_CARCASE;
	public static final DeferredBlock<Block> TITLE_DARK;
	public static final DeferredBlock<Block> APC;
	public static final DeferredBlock<Block> PODSTATION;
	public static final DeferredBlock<Block> DEBU_GGENERATOR;
	public static final DeferredBlock<Block> LAMP;
	public static final DeferredBlock<Block> AIRLOCK_UP_PLUG;
	public static final DeferredBlock<Block> AIRLOCK_UP_PLUG_OPEN;
	public static final DeferredBlock<Block> TITLE_STEEL_DIAGONAL_MINI;
	public static final DeferredBlock<Block> TITLE_STEEL_DIAGONAL;
	public static final DeferredBlock<Block> UP_TITLE_STEEL;
	public static final DeferredBlock<Block> UP_TITLE_DARK;
	public static final DeferredBlock<Block> TITLE_STEEL_MINI;
	public static final DeferredBlock<Block> TITLE_STEEL_MONO;
	public static final DeferredBlock<Block> TITLE_STEEL_OFFSET;
	public static final DeferredBlock<Block> TITLE_STEEL_PAVEMENT;
	public static final DeferredBlock<Block> TITLE_STEEL_HERRINGBONE;
	public static final DeferredBlock<Block> TITLE_PLASTIC;
	public static final DeferredBlock<Block> TITLE_DARK_DIAGONAL_MINI;
	public static final DeferredBlock<Block> TITLE_DARK_DIAGONAL;
	public static final DeferredBlock<Block> TITLE_DARK_MINI;
	public static final DeferredBlock<Block> TITLE_DARK_MONO;
	public static final DeferredBlock<Block> TITLE_DARK_OFFSET;
	public static final DeferredBlock<Block> TITLE_DARK_PAVEMENT;
	public static final DeferredBlock<Block> TITLE_DARK_PAVEMENT_VERTICAL;
	public static final DeferredBlock<Block> TITLE_DARK_HERRINGBONE;
	public static final DeferredBlock<Block> TITLE_DARK_PLASTIC;
	public static final DeferredBlock<Block> SMES;
	public static final DeferredBlock<Block> TECH_LAMP;
	public static final DeferredBlock<Block> ARMORED_WINDOW;
	public static final DeferredBlock<Block> DEBUG_LAMP;
	public static final DeferredBlock<Block> DEBUG_TECH_LAMP;
	public static final DeferredBlock<Block> GRILLE;
	public static final DeferredBlock<Block> BROKEN_GRILLE;
	public static final DeferredBlock<Block> ASTEROID_STONE_BASE;
	public static final DeferredBlock<Block> ASTEROID_STONE_IRON;
	public static final DeferredBlock<Block> ASTEROID_STONE_QUARTZ;
	public static final DeferredBlock<Block> ASTEROID_STONE_COAL;
	public static final DeferredBlock<Block> ASTEROID_STONE_GOLD;
	public static final DeferredBlock<Block> ASTEROID_STONE_PLASMA;
	public static final DeferredBlock<Block> ASTEROID_STONE_SILVER;
	public static final DeferredBlock<Block> ASTEROID_STONE_URAN;
	public static final DeferredBlock<Block> ASTEROID_STONE_DIAMOND;
	public static final DeferredBlock<Block> CAVE_FLOOR_1;
	public static final DeferredBlock<Block> CAVE_FLOOR_2;
	public static final DeferredBlock<Block> CAVE_FLOOR_3;
	public static final DeferredBlock<Block> CAVE_FLOOR_4;
	public static final DeferredBlock<Block> CAVE_FLOOR_5;
	public static final DeferredBlock<Block> CAVE_FLOOR_6;
	public static final DeferredBlock<Block> CAVE_FLOOR_7;
	public static final DeferredBlock<Block> GRAVITY_GENERATOR;
	public static final DeferredBlock<Block> TITLE_WHITE;
	public static final DeferredBlock<Block> TITLE_WHITE_DIAGONAL_MINI;
	public static final DeferredBlock<Block> CATWALK_FLOOR;
	static {
		VOIDB = register("voidb", VoidbBlock::new);
		SPACED_PORTAL = register("spaced_portal", SpacedPortalBlock::new);
		ROD_FLOOR = register("rod_floor", RodFloorBlock::new);
		SHEATHING = register("sheathing", SheathingBlock::new);
		TITLE_STEEL = register("title_steel", TitleSteelBlock::new);
		STEEL_WALL = register("steel_wall", SteelWallBlock::new);
		WALL_CARCASE = register("wall_carcase", WallCarcaseBlock::new);
		UPER_SHEATHING = register("uper_sheathing", UperSheathingBlock::new);
		ROD_UP_FLOOR = register("rod_up_floor", RodUpFloorBlock::new);
		BASE_AIRLOCK_D_1 = register("base_airlock_d_1", BaseAirlockD1Block::new);
		UP_TITLE = register("up_title", UpTitleBlock::new);
		CONSOLE_OF_ID = register("console_of_id", ConsoleOfIDBlock::new);
		ATMOS_BLOCK = register("atmos_block", AtmosBlockBlock::new);
		BASE_WINDOW = register("base_window", BaseWindowBlock::new);
		BASE_AIRLOCK_D_1OPEN = register("base_airlock_d_1open", BaseAirlockD1openBlock::new);
		PORTABLE_SHEATER = register("portable_sheater", PortableSheaterBlock::new);
		PLASTEEL_WALL = register("plasteel_wall", PlasteelWallBlock::new);
		PLASTEEL_WALL_CARCASE = register("plasteel_wall_carcase", PlasteelWallCarcaseBlock::new);
		TITLE_DARK = register("title_dark", TitleDarkBlock::new);
		APC = register("apc", APCBlock::new);
		PODSTATION = register("podstation", PodstationBlock::new);
		DEBU_GGENERATOR = register("debu_ggenerator", DEBUGgeneratorBlock::new);
		LAMP = register("lamp", LampBlock::new);
		AIRLOCK_UP_PLUG = register("airlock_up_plug", AirlockUpPlugBlock::new);
		AIRLOCK_UP_PLUG_OPEN = register("airlock_up_plug_open", AirlockUpPlugOPENBlock::new);
		TITLE_STEEL_DIAGONAL_MINI = register("title_steel_diagonal_mini", TitleSteelDiagonalMiniBlock::new);
		TITLE_STEEL_DIAGONAL = register("title_steel_diagonal", TitleSteelDiagonalBlock::new);
		UP_TITLE_STEEL = register("up_title_steel", UpTitleSteelBlock::new);
		UP_TITLE_DARK = register("up_title_dark", UpTitleDarkBlock::new);
		TITLE_STEEL_MINI = register("title_steel_mini", TitleSteelMiniBlock::new);
		TITLE_STEEL_MONO = register("title_steel_mono", TitleSteelMonoBlock::new);
		TITLE_STEEL_OFFSET = register("title_steel_offset", TitleSteelOffsetBlock::new);
		TITLE_STEEL_PAVEMENT = register("title_steel_pavement", TitleSteelPavementBlock::new);
		TITLE_STEEL_HERRINGBONE = register("title_steel_herringbone", TitleSteelHerringboneBlock::new);
		TITLE_PLASTIC = register("title_plastic", TitlePlasticBlock::new);
		TITLE_DARK_DIAGONAL_MINI = register("title_dark_diagonal_mini", TitleDarkDiagonalMiniBlock::new);
		TITLE_DARK_DIAGONAL = register("title_dark_diagonal", TitleDarkDiagonalBlock::new);
		TITLE_DARK_MINI = register("title_dark_mini", TitleDarkMiniBlock::new);
		TITLE_DARK_MONO = register("title_dark_mono", TitleDarkMonoBlock::new);
		TITLE_DARK_OFFSET = register("title_dark_offset", TitleDarkOffsetBlock::new);
		TITLE_DARK_PAVEMENT = register("title_dark_pavement", TitleDarkPavementBlock::new);
		TITLE_DARK_PAVEMENT_VERTICAL = register("title_dark_pavement_vertical", TitleDarkPavementVerticalBlock::new);
		TITLE_DARK_HERRINGBONE = register("title_dark_herringbone", TitleDarkHerringboneBlock::new);
		TITLE_DARK_PLASTIC = register("title_dark_plastic", TitleDarkPlasticBlock::new);
		SMES = register("smes", SMESBlock::new);
		TECH_LAMP = register("tech_lamp", TechLampBlock::new);
		ARMORED_WINDOW = register("armored_window", ArmoredWindowBlock::new);
		DEBUG_LAMP = register("debug_lamp", DEBUGLampBlock::new);
		DEBUG_TECH_LAMP = register("debug_tech_lamp", DEBUGTechLampBlock::new);
		GRILLE = register("grille", GrilleBlock::new);
		BROKEN_GRILLE = register("broken_grille", BrokenGrilleBlock::new);
		ASTEROID_STONE_BASE = register("asteroid_stone_base", AsteroidStoneBaseBlock::new);
		ASTEROID_STONE_IRON = register("asteroid_stone_iron", AsteroidStoneIronBlock::new);
		ASTEROID_STONE_QUARTZ = register("asteroid_stone_quartz", AsteroidStoneQuartzBlock::new);
		ASTEROID_STONE_COAL = register("asteroid_stone_coal", AsteroidStoneCoalBlock::new);
		ASTEROID_STONE_GOLD = register("asteroid_stone_gold", AsteroidStoneGoldBlock::new);
		ASTEROID_STONE_PLASMA = register("asteroid_stone_plasma", AsteroidStonePlasmaBlock::new);
		ASTEROID_STONE_SILVER = register("asteroid_stone_silver", AsteroidStoneSilverBlock::new);
		ASTEROID_STONE_URAN = register("asteroid_stone_uran", AsteroidStoneUranBlock::new);
		ASTEROID_STONE_DIAMOND = register("asteroid_stone_diamond", AsteroidStoneDiamondBlock::new);
		CAVE_FLOOR_1 = register("cave_floor_1", CaveFloor1Block::new);
		CAVE_FLOOR_2 = register("cave_floor_2", CaveFloor2Block::new);
		CAVE_FLOOR_3 = register("cave_floor_3", CaveFloor3Block::new);
		CAVE_FLOOR_4 = register("cave_floor_4", CaveFloor4Block::new);
		CAVE_FLOOR_5 = register("cave_floor_5", CaveFloor5Block::new);
		CAVE_FLOOR_6 = register("cave_floor_6", CaveFloor6Block::new);
		CAVE_FLOOR_7 = register("cave_floor_7", CaveFloor7Block::new);
		GRAVITY_GENERATOR = register("gravity_generator", GravityGeneratorBlock::new);
		TITLE_WHITE = register("title_white", TitleWhiteBlock::new);
		TITLE_WHITE_DIAGONAL_MINI = register("title_white_diagonal_mini", TitleWhiteDiagonalMiniBlock::new);
		CATWALK_FLOOR = register("catwalk_floor", CatwalkFloorBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}