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
	public static final DeferredBlock<Block> BASE_AIRLOCK_U_1;
	public static final DeferredBlock<Block> UP_TITLE;
	public static final DeferredBlock<Block> CONSOLE_OF_ID;
	public static final DeferredBlock<Block> ATMOS_BLOCK;
	public static final DeferredBlock<Block> BASE_WINDOW;
	public static final DeferredBlock<Block> BASE_AIRLOCK_D_1OPEN;
	public static final DeferredBlock<Block> BASE_AIRLOCK_U_1OPEN;
	public static final DeferredBlock<Block> PORTABLE_SHEATER;
	public static final DeferredBlock<Block> PLASTEEL_WALL;
	public static final DeferredBlock<Block> PLASTEEL_WALL_CARCASE;
	public static final DeferredBlock<Block> TITLE_DARK;
	public static final DeferredBlock<Block> APC;
	public static final DeferredBlock<Block> PODSTATION;
	public static final DeferredBlock<Block> DEBU_GGENERATOR;
	public static final DeferredBlock<Block> LAMP;
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
		BASE_AIRLOCK_U_1 = register("base_airlock_u_1", BaseAirlockU1Block::new);
		UP_TITLE = register("up_title", UpTitleBlock::new);
		CONSOLE_OF_ID = register("console_of_id", ConsoleOfIDBlock::new);
		ATMOS_BLOCK = register("atmos_block", AtmosBlockBlock::new);
		BASE_WINDOW = register("base_window", BaseWindowBlock::new);
		BASE_AIRLOCK_D_1OPEN = register("base_airlock_d_1open", BaseAirlockD1openBlock::new);
		BASE_AIRLOCK_U_1OPEN = register("base_airlock_u_1open", BaseAirlockU1openBlock::new);
		PORTABLE_SHEATER = register("portable_sheater", PortableSheaterBlock::new);
		PLASTEEL_WALL = register("plasteel_wall", PlasteelWallBlock::new);
		PLASTEEL_WALL_CARCASE = register("plasteel_wall_carcase", PlasteelWallCarcaseBlock::new);
		TITLE_DARK = register("title_dark", TitleDarkBlock::new);
		APC = register("apc", APCBlock::new);
		PODSTATION = register("podstation", PodstationBlock::new);
		DEBU_GGENERATOR = register("debu_ggenerator", DEBUGgeneratorBlock::new);
		LAMP = register("lamp", LampBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}