package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.procedures.Title_Placement_ConditionsProcedure;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.floor.AbstractFloorTileBlock;
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
    void legacyPlacementProcedureKeepsExactAbi() throws Exception {
        var execute = Title_Placement_ConditionsProcedure.class.getMethod("execute",
                LevelAccessor.class, double.class, double.class, double.class);
        assertSame(boolean.class, execute.getReturnType());
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
