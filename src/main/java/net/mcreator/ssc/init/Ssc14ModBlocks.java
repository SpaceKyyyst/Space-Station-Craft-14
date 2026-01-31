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
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}