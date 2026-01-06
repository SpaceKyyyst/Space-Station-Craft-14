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
	public static final DeferredBlock<Block> SHEATHING_LVC;
	public static final DeferredBlock<Block> SHEATHING_MVC;
	public static final DeferredBlock<Block> SHEATHING_HVC;
	public static final DeferredBlock<Block> SHEATHING_LMVC;
	public static final DeferredBlock<Block> SHEATHING_LHVC;
	public static final DeferredBlock<Block> SHEATHING_MHVC;
	public static final DeferredBlock<Block> SHEATHING_LMHVC;
	public static final DeferredBlock<Block> ROD_FLOOR_LVC;
	public static final DeferredBlock<Block> ROD_FLOOR_MVC;
	public static final DeferredBlock<Block> ROD_FLOOR_HVC;
	public static final DeferredBlock<Block> ROD_FLOOR_L_MVC;
	public static final DeferredBlock<Block> ROD_FLOOR_L_HVC;
	public static final DeferredBlock<Block> ROD_FLOOR_M_HVC;
	public static final DeferredBlock<Block> ROD_FLOOR_LM_HVC;
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
	static {
		VOIDB = register("voidb", VoidbBlock::new);
		SPACED_PORTAL = register("spaced_portal", SpacedPortalBlock::new);
		ROD_FLOOR = register("rod_floor", RodFloorBlock::new);
		SHEATHING = register("sheathing", SheathingBlock::new);
		SHEATHING_LVC = register("sheathing_lvc", SheathingLVCBlock::new);
		SHEATHING_MVC = register("sheathing_mvc", SheathingMVCBlock::new);
		SHEATHING_HVC = register("sheathing_hvc", SheathingHVCBlock::new);
		SHEATHING_LMVC = register("sheathing_lmvc", SheathingLMVCBlock::new);
		SHEATHING_LHVC = register("sheathing_lhvc", SheathingLHVCBlock::new);
		SHEATHING_MHVC = register("sheathing_mhvc", SheathingMHVCBlock::new);
		SHEATHING_LMHVC = register("sheathing_lmhvc", SheathingLMHVCBlock::new);
		ROD_FLOOR_LVC = register("rod_floor_lvc", RodFloorLvcBlock::new);
		ROD_FLOOR_MVC = register("rod_floor_mvc", RodFloorMvcBlock::new);
		ROD_FLOOR_HVC = register("rod_floor_hvc", RodFloorHvcBlock::new);
		ROD_FLOOR_L_MVC = register("rod_floor_l_mvc", RodFloorLMvcBlock::new);
		ROD_FLOOR_L_HVC = register("rod_floor_l_hvc", RodFloorLHvcBlock::new);
		ROD_FLOOR_M_HVC = register("rod_floor_m_hvc", RodFloorMHvcBlock::new);
		ROD_FLOOR_LM_HVC = register("rod_floor_lm_hvc", RodFloorLMHvcBlock::new);
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
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}