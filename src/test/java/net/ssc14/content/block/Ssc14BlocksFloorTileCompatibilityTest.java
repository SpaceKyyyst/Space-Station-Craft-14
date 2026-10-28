package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.floor.AbstractFloorTileBlock;
import net.ssc14.content.block.floor.RandomizedFloorTileBlock;
import net.ssc14.content.block.floor.FloorTileSupportPolicy;
import org.junit.jupiter.api.Test;

class Ssc14BlocksFloorTileCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            expected("TITLE_STEEL", "title_steel", "TitleSteelBlock", true),
            expected("TITLE_DARK", "title_dark", "TitleDarkBlock", true),
            expected("TITLE_WHITE", "title_white", "TitleWhiteBlock", true),
            expected("TITLE_ARCADEBLUE", "title_arcadeblue", "TitleArcadeblueBlock", false),
            expected("TITLEARCADEBLUE_2", "titlearcadeblue_2", "Titlearcadeblue2Block", false),
            expected("TITLE_ARCADERED", "title_arcadered", "TitleArcaderedBlock", false),
            expected("TITLE_BAR", "title_bar", "TitleBarBlock", false),
            expected("TITLE_BLUE_CIRCUIT", "title_blue_circuit", "TitleBlueCircuitBlock", false),
            expected("TITLE_BLUE", "title_blue", "TitleBlueBlock", false),
            expected("TITLE_BOXING", "title_boxing", "TitleBoxingBlock", false),
            expected("TITLE_CAFETERIA", "title_cafeteria", "TitleCafeteriaBlock", false),
            expected("TITLE_CARPETCLOWN", "title_carpetclown", "TitleCarpetclownBlock", false),
            expected("TITLE_CARPETOFFICE", "title_carpetoffice", "TitleCarpetofficeBlock", false),
            expected("TITLE_CHECKERDARK", "title_checkerdark", "TitleCheckerdarkBlock", false),
            expected("TITLE_CLOWN", "title_clown", "TitleClownBlock", false),
            expected("TITLE_DARKTEST", "title_darktest", "TitleDarktestBlock", false),
            expected("TITLEEIGHTIES", "titleeighties", "TitleeightiesBlock", false),
            expected("TITLE_ELEVATORSHAFT", "title_elevatorshaft", "TitleElevatorshaftBlock", false),
            expected("TITLE_EXOBORG", "title_exoborg", "TitleExoborgBlock", false),
            expected("TITLE_FREEZER", "title_freezer", "TitleFreezerBlock", false),
            expected("TITLE_GOLD", "title_gold", "TitleGoldBlock", false),
            expected("TITLE_GRATINGMAINT", "title_gratingmaint", "TitleGratingmaintBlock", false),
            expected("TITLE_GREENCIRCUIT", "title_greencircuit", "TitleGreencircuitBlock", false),
            expected("TITLE_GYM", "title_gym", "TitleGymBlock", false),
            expected("TITLE_HULLREINFORCED", "title_hullreinforced", "TitleHullreinforcedBlock", false),
            expected("TITLE_HULL", "title_hull", "TitleHullBlock", false),
            expected("TITLE_HYDRO", "title_hydro", "TitleHydroBlock", false),
            expected("TITLE_KITCHEN", "title_kitchen", "TitleKitchenBlock", false),
            expected("TITLE_LAUNDRY", "title_laundry", "TitleLaundryBlock", false),
            expected("TITLE_LIME", "title_lime", "TitleLimeBlock", false),
            expected("TITLE_LINO", "title_lino", "TitleLinoBlock", false),
            expected("TITLE_MEAT", "title_meat", "TitleMeatBlock", false),
            expected("TITLE_METALDIAMOND", "title_metaldiamond", "TitleMetaldiamondBlock", false),
            expected("TITLE_MIME", "title_mime", "TitleMimeBlock", false),
            expected("TITLE_MININGFLOORDARK", "title_miningfloordark", "TitleMiningfloordarkBlock", false),
            expected("TITLE_MININGFLOORLIGHT", "title_miningfloorlight", "TitleMiningfloorlightBlock", false),
            expected("TITLE_MININGFLOOR", "title_miningfloor", "TitleMiningfloorBlock", false),
            expected("TITLE_MONO", "title_mono", "TitleMonoBlock", false),
            expected("TITLE_REDCIRCUIT", "title_redcircuit", "TitleRedcircuitBlock", false),
            expected("TITLE_REINFORCED", "title_reinforced", "TitleReinforcedBlock", false),
            expected("TITLE_ROCKVAULT", "title_rockvault", "TitleRockvaultBlock", false),
            expected("TITLE_SHOWROOM", "title_showroom", "TitleShowroomBlock", false),
            expected("TITLE_SHUTTLEBLACK", "title_shuttleblack", "TitleShuttleblackBlock", false),
            expected("TITLE_SHUTTLEBLUE", "title_shuttleblue", "TitleShuttleblueBlock", false),
            expected("TITLE_SHUTTLEGREY", "title_shuttlegrey", "TitleShuttlegreyBlock", false),
            expected("TITLE_SHUTTLEORANGE", "title_shuttleorange", "TitleShuttleorangeBlock", false),
            expected("TITLE_SHUTTLEPURPLE", "title_shuttlepurple", "TitleShuttlepurpleBlock", false),
            expected("TITLE_SHUTTLERED", "title_shuttlered", "TitleShuttleredBlock", false),
            expected("TITLE_SHUTTLEWHITE", "title_shuttlewhite", "TitleShuttlewhiteBlock", false),
            expected("TITLE_SILVER", "title_silver", "TitleSilverBlock", false),
            expected("TITLE_STEELDAMAGED", "title_steeldamaged", "TitleSteeldamagedBlock", false),
            expected("TITLE_STEELDIRTY", "title_steeldirty", "TitleSteeldirtyBlock", false),
            expected("TITLE_STEELMAINT", "title_steelmaint", "TitleSteelmaintBlock", false),
            expected("TITLE_SUPERREINFORCED", "title_superreinforced", "TitleSuperreinforcedBlock", false),
            expected("TITLE_TECHMAINTDARK", "title_techmaintdark", "TitleTechmaintdarkBlock", false),
            expected("TITLE_TECHMAINT", "title_techmaint", "TitleTechmaintBlock", false),
            expected("TITLE_WOODLARGE", "title_woodlarge", "TitleWoodlargeBlock", false),
            expected("TITLE_WOOD", "title_wood", "TitleWoodBlock", false),
            expected("TITLE_WOODBROKEN", "title_woodbroken", "TitleWoodbrokenBlock", false),
            expected("TITLE_WOODTILE", "title_woodtile", "TitleWoodtileBlock", false),
            expected("TITLE_DARK_DIAGONAL_MINI", "title_dark_diagonal_mini", "TitleDarkDiagonalMiniBlock", false),
            expected("TITLE_DARK_DIAGONAL", "title_dark_diagonal", "TitleDarkDiagonalBlock", false),
            expected("TITLE_DARK_MINI", "title_dark_mini", "TitleDarkMiniBlock", false),
            expected("TITLE_DARK_MONO", "title_dark_mono", "TitleDarkMonoBlock", false),
            expected("TITLE_DARK_OFFSET", "title_dark_offset", "TitleDarkOffsetBlock", false),
            expected("TITLE_DARK_PAVEMENT", "title_dark_pavement", "TitleDarkPavementBlock", false),
            expected("TITLE_DARK_PAVEMENT_VERTICAL", "title_dark_pavement_vertical", "TitleDarkPavementVerticalBlock", false),
            expected("TITLE_DARK_HERRINGBONE", "title_dark_herringbone", "TitleDarkHerringboneBlock", false),
            expected("TITLE_DARK_PLASTIC", "title_dark_plastic", "TitleDarkPlasticBlock", false),
            expected("TITLE_PLASTIC", "title_plastic", "TitlePlasticBlock", false),
            expected("TITLE_STEEL_DIAGONAL_MINI", "title_steel_diagonal_mini", "TitleSteelDiagonalMiniBlock", false),
            expected("TITLE_STEEL_DIAGONAL", "title_steel_diagonal", "TitleSteelDiagonalBlock", false),
            expected("TITLE_STEEL_MINI", "title_steel_mini", "TitleSteelMiniBlock", false),
            expected("TITLE_STEEL_MONO", "title_steel_mono", "TitleSteelMonoBlock", false),
            expected("TITLE_STEEL_OFFSET", "title_steel_offset", "TitleSteelOffsetBlock", false),
            expected("TITLE_STEEL_PAVEMENT", "title_steel_pavement", "TitleSteelPavementBlock", false),
            expected("TITLE_STEEL_HERRINGBONE", "title_steel_herringbone", "TitleSteelHerringboneBlock", false),
            expected("TITLE_STEEL_PAVEMENT_VERTICAL", "title_steel_pavement_vertical", "TitleSteelPavementVerticalBlock", false),
            expected("TITLE_WHITE_DIAGONAL_MINI", "title_white_diagonal_mini", "TitleWhiteDiagonalMiniBlock", false),
            expected("TITLE_WHITE_DIAGONAL", "title_white_diagonal", "TitleWhiteDiagonalBlock", false),
            expected("TITLE_WHITE_HERRINGBONE", "title_white_herringbone", "TitleWhiteHerringboneBlock", false),
            expected("TITLE_WHITE_MINI", "title_white_mini", "TitleWhiteMiniBlock", false),
            expected("TITLE_WHITE_MONO", "title_white_mono", "TitleWhiteMonoBlock", false),
            expected("TITLE_WHITE_OFFSET", "title_white_offset", "TitleWhiteOffsetBlock", false),
            expected("TITLE_WHITE_PAVEMENT", "title_white_pavement", "TitleWhitePavementBlock", false),
            expected("TITLE_WHITE_PAVEMENT_VERTICAL", "title_white_pavement_vertical", "TitleWhitePavementVerticalBlock", false),
            expected("TITLE_WHITE_PLASTIC", "title_white_plastic", "TitleWhitePlasticBlock", false));

    @Test
    void canonicalRegistryOwnsAllIdsAndLegacyFacadeAliasesHolders() throws Exception {
        for (Expected expected : EXPECTED) {
            Object holder = Ssc14Blocks.class.getField(expected.field()).get(null);
            assertTrue(holder instanceof DeferredHolder<?, ?>, expected.field());
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) holder).getId().toString());
            assertSame(holder, Ssc14ModBlocks.class.getField(expected.field()).get(null));
        }
    }

    @Test
    void allLegacyTypesRemainConstructorCompatibleWrappers() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(AbstractFloorTileBlock.class.isAssignableFrom(legacy), expected.legacyClass());
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
    }

    @Test
    void canonicalPlacementPolicyRemainsAvailable() throws Exception {
        var canSupport = FloorTileSupportPolicy.class.getMethod("canSupport",
                net.minecraft.world.level.BlockGetter.class, BlockPos.class);
        assertSame(boolean.class, canSupport.getReturnType());
    }

    @Test
    void randomizedVariantsKeepExactStateRanges() throws Exception {
        assertRandomizedVariant("TITLE_MEAT", "TitleMeatBlock", 4);
        assertRandomizedVariant("TITLE_STEELDAMAGED", "TitleSteeldamagedBlock", 5);
        assertRandomizedVariant("TITLE_WOODBROKEN", "TitleWoodbrokenBlock", 5);
    }

    private static void assertRandomizedVariant(String field, String legacyClass,
            int maximumState) throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block." + legacyClass);
        assertTrue(RandomizedFloorTileBlock.class.isAssignableFrom(legacy), legacyClass);
        var property = legacy.getField("STATE").get(null);
        assertTrue(property instanceof net.minecraft.world.level.block.state.properties.IntegerProperty);
        assertEquals(maximumState,
                ((net.minecraft.world.level.block.state.properties.IntegerProperty) property)
                        .getPossibleValues().stream().mapToInt(Integer::intValue).max().orElseThrow());
        assertSame(Ssc14Blocks.class.getField(field).get(null),
                Ssc14ModBlocks.class.getField(field).get(null));
    }

    @Test
    void nonAccessorReaderUsesLegacySuperclassFallback() {
        LevelReader reader = (LevelReader) Proxy.newProxyInstance(
                LevelReader.class.getClassLoader(), new Class<?>[] {LevelReader.class},
                (proxy, method, arguments) -> {
                    throw new AssertionError("fallback must not query " + method.getName());
                });

        assertTrue(Ssc14Blocks.TITLE_STEEL.get().defaultBlockState()
                .canSurvive(reader, BlockPos.ZERO));
    }

    private static Expected expected(String field, String path, String legacyClass, boolean inset) {
        return new Expected(field, path, legacyClass, inset);
    }

    private record Expected(String field, String path, String legacyClass, boolean inset) { }
}
