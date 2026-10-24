package net.ssc14.content.block;

import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.decoration.DirectionalBannerBlock;
import net.ssc14.content.block.decoration.FenceCornerBlock;
import net.ssc14.content.block.decoration.FenceLineBlock;
import net.ssc14.content.block.decoration.FencePointBlock;
import net.ssc14.content.block.decoration.FenceThreeWayBlock;
import net.ssc14.content.block.geology.AsteroidStoneBaseBlock;
import net.ssc14.content.block.geology.AsteroidStoneCoalBlock;
import net.ssc14.content.block.geology.AsteroidStoneDiamondBlock;
import net.ssc14.content.block.geology.AsteroidStoneGoldBlock;
import net.ssc14.content.block.geology.AsteroidStoneIronBlock;
import net.ssc14.content.block.geology.AsteroidStonePlasmaBlock;
import net.ssc14.content.block.geology.AsteroidStoneQuartzBlock;
import net.ssc14.content.block.geology.AsteroidStoneSilverBlock;
import net.ssc14.content.block.geology.AsteroidStoneUranBlock;
import net.ssc14.content.block.geology.CaveFloor1Block;
import net.ssc14.content.block.geology.CaveFloor2Block;
import net.ssc14.content.block.geology.CaveFloor3Block;
import net.ssc14.content.block.geology.CaveFloor4Block;
import net.ssc14.content.block.geology.CaveFloor5Block;
import net.ssc14.content.block.geology.CaveFloor6Block;
import net.ssc14.content.block.geology.CaveFloor7Block;
import net.ssc14.content.block.grille.BrokenGrilleBlock;
import net.ssc14.content.block.grille.GrilleBlock;
import net.ssc14.content.block.planetary.PlanetDirtBlock;
import net.ssc14.content.block.planetary.PlanetGrassPlantBlock;
import net.ssc14.content.block.planetary.PlanetGrassSoilBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeFoliageBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeLogBlock;
import net.ssc14.content.block.furniture.ArmChairBlock;
import net.ssc14.content.block.furniture.BarChairBlock;
import net.ssc14.content.block.furniture.GlassTableBlock;
import net.ssc14.content.block.furniture.OfficeChairBlock;
import net.ssc14.content.block.furniture.SteelChairBlock;
import net.ssc14.content.block.furniture.SteelTableBlock;
import net.ssc14.content.block.furniture.WoodChairBlock;
import net.ssc14.content.block.furniture.WoodTableBlock;
import net.ssc14.content.block.surface.StaircaseBlock;
import net.ssc14.content.block.surface.UpperStaircaseBlock;
import net.ssc14.content.block.structure.PlasteelWallCarcaseBlock;
import net.ssc14.content.block.structure.RodFloorBlock;
import net.ssc14.content.block.structure.RodUpFloorBlock;
import net.ssc14.content.block.structure.UperSheathingBlock;
import net.ssc14.content.block.structure.WallCarcaseBlock;
import net.ssc14.content.block.window.ArmoredWindowBlock;
import net.ssc14.content.block.window.BaseWindowBlock;
import net.ssc14.content.block.window.DirectedArmoredWindowBlock;
import net.ssc14.content.block.window.DirectedBaseWindowBlock;

/** Canonical owner of SSC14 block registrations. */
public final class Ssc14Blocks {
    public static final DeferredRegister.Blocks REGISTRY =
            DeferredRegister.createBlocks(Ssc14Identity.MOD_ID);

    public static final DeferredBlock<Block> ASTEROID_STONE_BASE =
            register("asteroid_stone_base", AsteroidStoneBaseBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_IRON =
            register("asteroid_stone_iron", AsteroidStoneIronBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_QUARTZ =
            register("asteroid_stone_quartz", AsteroidStoneQuartzBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_COAL =
            register("asteroid_stone_coal", AsteroidStoneCoalBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_GOLD =
            register("asteroid_stone_gold", AsteroidStoneGoldBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_PLASMA =
            register("asteroid_stone_plasma", AsteroidStonePlasmaBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_SILVER =
            register("asteroid_stone_silver", AsteroidStoneSilverBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_URAN =
            register("asteroid_stone_uran", AsteroidStoneUranBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_DIAMOND =
            register("asteroid_stone_diamond", AsteroidStoneDiamondBlock::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_1 =
            register("cave_floor_1", CaveFloor1Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_2 =
            register("cave_floor_2", CaveFloor2Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_3 =
            register("cave_floor_3", CaveFloor3Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_4 =
            register("cave_floor_4", CaveFloor4Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_5 =
            register("cave_floor_5", CaveFloor5Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_6 =
            register("cave_floor_6", CaveFloor6Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_7 =
            register("cave_floor_7", CaveFloor7Block::new);
    public static final DeferredBlock<Block> STEEL_STAIRCASE =
            register("steel_staircase", StaircaseBlock::new);
    public static final DeferredBlock<Block> WHITE_STAIRCASE =
            register("white_staircase", StaircaseBlock::new);
    public static final DeferredBlock<Block> DARK_STAIRCASE =
            register("dark_staircase", StaircaseBlock::new);
    public static final DeferredBlock<Block> UP_STEEL_STAIRCASE =
            register("up_steel_staircase", UpperStaircaseBlock::new);
    public static final DeferredBlock<Block> UP_WHITE_STAIRCASE =
            register("up_white_staircase", UpperStaircaseBlock::new);
    public static final DeferredBlock<Block> UP_DARK_STAIRCASE =
            register("up_dark_staircase", UpperStaircaseBlock::new);
    public static final DeferredBlock<Block> BANNER_NT =
            register("banner_nt", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> BANNER_MED =
            register("banner_med", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> BANNER_RN_D =
            register("banner_rn_d", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> BANNER_SEC =
            register("banner_sec", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> BANNER_CARGO =
            register("banner_cargo", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> BANNER_ENG =
            register("banner_eng", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> BANNER_SYND =
            register("banner_synd", DirectionalBannerBlock::new);
    public static final DeferredBlock<Block> FENCE_POINT =
            register("fence_point", FencePointBlock::new);
    public static final DeferredBlock<Block> FENCE_LINE =
            register("fence_line", FenceLineBlock::new);
    public static final DeferredBlock<Block> FENCE_G =
            register("fence_g", FenceCornerBlock::new);
    public static final DeferredBlock<Block> FENCE_P =
            register("fence_p", FenceThreeWayBlock::new);
    public static final DeferredBlock<Block> STEEL_CHAIR =
            register("steel_chair", SteelChairBlock::new);
    public static final DeferredBlock<Block> WOOD_CHAIR =
            register("wood_chair", WoodChairBlock::new);
    public static final DeferredBlock<Block> OFICE_BLACK_CHAIR =
            register("ofice_black_chair", OfficeChairBlock::new);
    public static final DeferredBlock<Block> OFICE_WHITE_CHAIR =
            register("ofice_white_chair", OfficeChairBlock::new);
    public static final DeferredBlock<Block> BAR_CHAIR =
            register("bar_chair", BarChairBlock::new);
    public static final DeferredBlock<Block> STEEL_TABLE =
            register("steel_table", SteelTableBlock::new);
    public static final DeferredBlock<Block> WOOD_TABLE =
            register("wood_table", WoodTableBlock::new);
    public static final DeferredBlock<Block> GLASS_TABLE =
            register("glass_table", GlassTableBlock::new);
    public static final DeferredBlock<Block> ARM_CHAIR_BASE =
            register("arm_chair_base", ArmChairBlock::new);
    public static final DeferredBlock<Block> ARM_CHAIR_BROWN =
            register("arm_chair_brown", ArmChairBlock::new);
    public static final DeferredBlock<Block> ARM_CHAIR_CARP =
            register("arm_chair_carp", ArmChairBlock::new);
    public static final DeferredBlock<Block> DIRT =
            register("dirt", PlanetDirtBlock::new);
    public static final DeferredBlock<Block> GRASS_LIGHT_BLOCK =
            register("grass_light_block", PlanetGrassSoilBlock::new);
    public static final DeferredBlock<Block> LOW_PLANET_GRASS =
            register("low_planet_grass", PlanetGrassPlantBlock::new);
    public static final DeferredBlock<Block> MED_PLANET_GRASS =
            register("med_planet_grass", PlanetGrassPlantBlock::new);
    public static final DeferredBlock<Block> PLANETARY_TREE_LOG =
            register("planetary_tree_log", PlanetaryTreeLogBlock::new);
    public static final DeferredBlock<Block> PLANETARY_TREE_FOLIAGE =
            register("planetary_tree_foliage", PlanetaryTreeFoliageBlock::new);
    public static final DeferredBlock<Block> BASE_WINDOW =
            register("base_window", BaseWindowBlock::new);
    public static final DeferredBlock<Block> ARMORED_WINDOW =
            register("armored_window", ArmoredWindowBlock::new);
    public static final DeferredBlock<Block> DIRECTED_BASE_WINDOW =
            register("directed_base_window", DirectedBaseWindowBlock::new);
    public static final DeferredBlock<Block> DIRECTED_ARMORED_WINDOW =
            register("directed_armored_window", DirectedArmoredWindowBlock::new);
    public static final DeferredBlock<Block> ROD_FLOOR =
            register("rod_floor", RodFloorBlock::new);
    public static final DeferredBlock<Block> ROD_UP_FLOOR =
            register("rod_up_floor", RodUpFloorBlock::new);
    public static final DeferredBlock<Block> UPER_SHEATHING =
            register("uper_sheathing", UperSheathingBlock::new);
    public static final DeferredBlock<Block> WALL_CARCASE =
            register("wall_carcase", WallCarcaseBlock::new);
    public static final DeferredBlock<Block> PLASTEEL_WALL_CARCASE =
            register("plasteel_wall_carcase", PlasteelWallCarcaseBlock::new);
    public static final DeferredBlock<Block> GRILLE =
            register("grille", GrilleBlock::new);
    public static final DeferredBlock<Block> BROKEN_GRILLE =
            register("broken_grille", BrokenGrilleBlock::new);

    private Ssc14Blocks() {
    }

    private static <B extends Block> DeferredBlock<B> register(
            String name, Function<BlockBehaviour.Properties, ? extends B> factory) {
        return REGISTRY.registerBlock(name, factory);
    }
}
