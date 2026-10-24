package net.ssc14.content.block;

import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.catwalk.CatwalkFloorBlock;
import net.ssc14.content.block.ceiling.CeilingTileBlock;
import net.ssc14.content.block.commerce.SalePalletBlock;
import net.ssc14.content.block.commerce.OrderPalletBlock;
import net.ssc14.content.block.fuel.FuelTankBlock;
import net.ssc14.content.block.debug.StationCodeDebugBlock;
import net.ssc14.content.block.decoration.DirectionalBannerBlock;
import net.ssc14.content.block.decoration.FenceCornerBlock;
import net.ssc14.content.block.decoration.FenceLineBlock;
import net.ssc14.content.block.decoration.FencePointBlock;
import net.ssc14.content.block.decoration.FenceThreeWayBlock;
import net.ssc14.content.block.energy.SheathingBlock;
import net.ssc14.content.block.energy.ApcBlock;
import net.ssc14.content.block.energy.PodstationBlock;
import net.ssc14.content.block.energy.DebugGeneratorBlock;
import net.ssc14.content.block.energy.SmesBlock;
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
import net.ssc14.content.block.lighting.DebugLightBlock;
import net.ssc14.content.block.lighting.EmergencyLightBlock;
import net.ssc14.content.block.lighting.EnergyLampBlock;
import net.ssc14.content.block.lighting.LightingBlockEntity;
import net.ssc14.content.block.lighting.ServiceableLightBlock;
import net.ssc14.content.block.lighting.WallMountedLightBlock;
import net.ssc14.content.block.machinery.AssembledMachineFrameBlock;
import net.ssc14.content.block.machinery.MachineFrameBlock;
import net.ssc14.content.block.floor.FloorTileBlock;
import net.ssc14.content.block.floor.FiveVariantFloorTileBlock;
import net.ssc14.content.block.floor.FourVariantFloorTileBlock;
import net.ssc14.content.block.floor.InsetFloorTileBlock;
import net.ssc14.content.block.planetary.PlanetDirtBlock;
import net.ssc14.content.block.planetary.PlanetGrassPlantBlock;
import net.ssc14.content.block.planetary.PlanetGrassSoilBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeFoliageBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeLogBlock;
import net.ssc14.content.block.wall.ArmoredShuttleWallBlock;
import net.ssc14.content.block.wall.PlasteelWallBlock;
import net.ssc14.content.block.wall.ShuttleWallBlock;
import net.ssc14.content.block.wall.SteelWallBlock;
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
import net.ssc14.content.block.storage.ClosetBlockEntity;
import net.ssc14.content.block.storage.StationClosetBlock;
import net.ssc14.content.block.window.ArmoredWindowBlock;
import net.ssc14.content.block.window.BaseWindowBlock;
import net.ssc14.content.block.window.DirectedArmoredWindowBlock;
import net.ssc14.content.block.window.DirectedBaseWindowBlock;

/** Canonical owner of SSC14 block registrations. */
public final class Ssc14Blocks {
    public static final DeferredRegister.Blocks REGISTRY =
            DeferredRegister.createBlocks(Ssc14Identity.MOD_ID);

    public static final DeferredBlock<Block> MACHINE_FRAME =
            register("machine_frame", MachineFrameBlock::new);
    public static final DeferredBlock<Block> MACHINE_FRAME_2 =
            register("machine_frame_2", AssembledMachineFrameBlock::new);
    public static final DeferredBlock<Block> SHEATHING =
            register("sheathing", SheathingBlock::new);
    public static final DeferredBlock<Block> APC = register("apc", ApcBlock::new);
    public static final DeferredBlock<Block> PODSTATION =
            register("podstation", PodstationBlock::new);
    public static final DeferredBlock<Block> DEBU_GGENERATOR =
            register("debu_ggenerator", DebugGeneratorBlock::new);
    public static final DeferredBlock<Block> SMES = register("smes", SmesBlock::new);
    public static final DeferredBlock<Block> SALE_PALLET =
            register("sale_pallet", SalePalletBlock::new);
    public static final DeferredBlock<Block> ORDER_PALLET =
            register("order_pallet", OrderPalletBlock::new);
    public static final DeferredBlock<Block> FUEL_TANK =
            register("fuel_tank", FuelTankBlock::new);
    public static final DeferredBlock<Block> DEBUG_13 =
            register("debug_13", StationCodeDebugBlock::new);

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
    public static final DeferredBlock<Block> LAMP =
            register("lamp", properties -> new EnergyLampBlock(properties,
                    (pos, state) -> new LightingBlockEntity(
                            "lamp", "Lamp", true, pos, state)));
    public static final DeferredBlock<Block> TECH_LAMP =
            register("tech_lamp", properties -> new ServiceableLightBlock(
                    properties, WallMountedLightBlock.Geometry.TECHNICAL));
    public static final DeferredBlock<Block> DEBUG_LAMP =
            register("debug_lamp", properties -> new DebugLightBlock(
                    properties, WallMountedLightBlock.Geometry.TUBE));
    public static final DeferredBlock<Block> DEBUG_TECH_LAMP =
            register("debug_tech_lamp", properties -> new DebugLightBlock(
                    properties, WallMountedLightBlock.Geometry.TECHNICAL));
    public static final DeferredBlock<Block> EMERGENCY_LIGHT =
            register("emergency_light", properties -> new EmergencyLightBlock(properties,
                    (pos, state) -> new LightingBlockEntity(
                            "emergency_light", "Emergency Light", false, pos, state)));
    public static final DeferredBlock<Block> CLOSET =
            register("closet", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet", "Closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_EMERG =
            register("closet_emerg", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_emerg", "Emergency closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_FIRE =
            register("closet_fire", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_fire", "Fire closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_BLACK =
            register("closet_black", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_black", "Black closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_BLUE =
            register("closet_blue", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_blue", "Blue closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_GRAY =
            register("closet_gray", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_gray", "Gray closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_GREEN =
            register("closet_green", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_green", "Green closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_MIXED =
            register("closet_mixed", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_mixed", "Mixed closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_N_2 =
            register("closet_n_2", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_n_2", "N2 closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_ORANGE =
            register("closet_orange", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_orange", "Orange closet", pos, state)));
    public static final DeferredBlock<Block> CLOSET_PINK =
            register("closet_pink", properties ->
                    new StationClosetBlock(properties, (pos, state) ->
                            new ClosetBlockEntity("closet_pink", "Pink closet", pos, state)));
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
    public static final DeferredBlock<Block> STEEL_WALL =
            register("steel_wall", SteelWallBlock::new);
    public static final DeferredBlock<Block> PLASTEEL_WALL =
            register("plasteel_wall", PlasteelWallBlock::new);
    public static final DeferredBlock<Block> SHUTTLE_WALL =
            register("shuttle_wall", ShuttleWallBlock::new);
    public static final DeferredBlock<Block> ARMORED_SHUTTLE_WALL =
            register("armored_shuttle_wall", ArmoredShuttleWallBlock::new);
    public static final DeferredBlock<Block> GRILLE =
            register("grille", GrilleBlock::new);
    public static final DeferredBlock<Block> BROKEN_GRILLE =
            register("broken_grille", BrokenGrilleBlock::new);
    public static final DeferredBlock<Block> CATWALK_FLOOR =
            register("catwalk_floor", CatwalkFloorBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL =
            register("title_steel", InsetFloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK =
            register("title_dark", InsetFloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE =
            register("title_white", InsetFloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_ARCADEBLUE =
            register("title_arcadeblue", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLEARCADEBLUE_2 =
            register("titlearcadeblue_2", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_ARCADERED =
            register("title_arcadered", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_BAR =
            register("title_bar", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_BLUE_CIRCUIT =
            register("title_blue_circuit", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_BLUE =
            register("title_blue", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_BOXING =
            register("title_boxing", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_CAFETERIA =
            register("title_cafeteria", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_CARPETCLOWN =
            register("title_carpetclown", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_CARPETOFFICE =
            register("title_carpetoffice", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_CHECKERDARK =
            register("title_checkerdark", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_CLOWN =
            register("title_clown", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARKTEST =
            register("title_darktest", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLEEIGHTIES =
            register("titleeighties", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_ELEVATORSHAFT =
            register("title_elevatorshaft", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_EXOBORG =
            register("title_exoborg", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_FREEZER =
            register("title_freezer", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_GOLD =
            register("title_gold", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_GRATINGMAINT =
            register("title_gratingmaint", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_GREENCIRCUIT =
            register("title_greencircuit", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_GYM =
            register("title_gym", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_HULLREINFORCED =
            register("title_hullreinforced", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_HULL =
            register("title_hull", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_HYDRO =
            register("title_hydro", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_KITCHEN =
            register("title_kitchen", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_LAUNDRY =
            register("title_laundry", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_LIME =
            register("title_lime", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_LINO =
            register("title_lino", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_MEAT =
            register("title_meat", FourVariantFloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_METALDIAMOND =
            register("title_metaldiamond", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_MIME =
            register("title_mime", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_MININGFLOORDARK =
            register("title_miningfloordark", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_MININGFLOORLIGHT =
            register("title_miningfloorlight", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_MININGFLOOR =
            register("title_miningfloor", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_MONO =
            register("title_mono", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_REDCIRCUIT =
            register("title_redcircuit", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_REINFORCED =
            register("title_reinforced", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_ROCKVAULT =
            register("title_rockvault", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHOWROOM =
            register("title_showroom", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLEBLACK =
            register("title_shuttleblack", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLEBLUE =
            register("title_shuttleblue", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLEGREY =
            register("title_shuttlegrey", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLEORANGE =
            register("title_shuttleorange", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLEPURPLE =
            register("title_shuttlepurple", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLERED =
            register("title_shuttlered", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SHUTTLEWHITE =
            register("title_shuttlewhite", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SILVER =
            register("title_silver", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEELDAMAGED =
            register("title_steeldamaged", FiveVariantFloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEELDIRTY =
            register("title_steeldirty", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEELMAINT =
            register("title_steelmaint", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_SUPERREINFORCED =
            register("title_superreinforced", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_TECHMAINTDARK =
            register("title_techmaintdark", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_TECHMAINT =
            register("title_techmaint", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WOODLARGE =
            register("title_woodlarge", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WOOD =
            register("title_wood", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WOODBROKEN =
            register("title_woodbroken", FiveVariantFloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WOODTILE =
            register("title_woodtile", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_DIAGONAL_MINI =
            register("title_dark_diagonal_mini", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_DIAGONAL =
            register("title_dark_diagonal", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_MINI =
            register("title_dark_mini", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_MONO =
            register("title_dark_mono", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_OFFSET =
            register("title_dark_offset", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_PAVEMENT =
            register("title_dark_pavement", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_PAVEMENT_VERTICAL =
            register("title_dark_pavement_vertical", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_HERRINGBONE =
            register("title_dark_herringbone", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_DARK_PLASTIC =
            register("title_dark_plastic", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_PLASTIC =
            register("title_plastic", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_DIAGONAL_MINI =
            register("title_steel_diagonal_mini", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_DIAGONAL =
            register("title_steel_diagonal", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_MINI =
            register("title_steel_mini", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_MONO =
            register("title_steel_mono", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_OFFSET =
            register("title_steel_offset", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_PAVEMENT =
            register("title_steel_pavement", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_HERRINGBONE =
            register("title_steel_herringbone", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_STEEL_PAVEMENT_VERTICAL =
            register("title_steel_pavement_vertical", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_DIAGONAL_MINI =
            register("title_white_diagonal_mini", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_DIAGONAL =
            register("title_white_diagonal", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_HERRINGBONE =
            register("title_white_herringbone", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_MINI =
            register("title_white_mini", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_MONO =
            register("title_white_mono", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_OFFSET =
            register("title_white_offset", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_PAVEMENT =
            register("title_white_pavement", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_PAVEMENT_VERTICAL =
            register("title_white_pavement_vertical", FloorTileBlock::new);
    public static final DeferredBlock<Block> TITLE_WHITE_PLASTIC =
            register("title_white_plastic", FloorTileBlock::new);
    public static final DeferredBlock<Block> UP_TITLE =
            register("up_title", CeilingTileBlock::new);
    public static final DeferredBlock<Block> UP_TITLE_STEEL =
            register("up_title_steel", CeilingTileBlock::new);
    public static final DeferredBlock<Block> UP_TITLE_DARK =
            register("up_title_dark", CeilingTileBlock::new);

    private Ssc14Blocks() {
    }

    private static <B extends Block> DeferredBlock<B> register(
            String name, Function<BlockBehaviour.Properties, ? extends B> factory) {
        return REGISTRY.registerBlock(name, factory);
    }
}
