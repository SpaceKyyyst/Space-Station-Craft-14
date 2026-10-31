package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14TileBlockItemsCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            new Expected("TITLE_STEEL", "title_steel"),
            new Expected("TITLE_DARK", "title_dark"),
            new Expected("TITLE_STEEL_DIAGONAL_MINI", "title_steel_diagonal_mini"),
            new Expected("TITLE_STEEL_DIAGONAL", "title_steel_diagonal"),
            new Expected("TITLE_STEEL_MINI", "title_steel_mini"),
            new Expected("TITLE_STEEL_MONO", "title_steel_mono"),
            new Expected("TITLE_STEEL_OFFSET", "title_steel_offset"),
            new Expected("TITLE_STEEL_PAVEMENT", "title_steel_pavement"),
            new Expected("TITLE_STEEL_HERRINGBONE", "title_steel_herringbone"),
            new Expected("TITLE_PLASTIC", "title_plastic"),
            new Expected("TITLE_DARK_DIAGONAL_MINI", "title_dark_diagonal_mini"),
            new Expected("TITLE_DARK_DIAGONAL", "title_dark_diagonal"),
            new Expected("TITLE_DARK_MINI", "title_dark_mini"),
            new Expected("TITLE_DARK_MONO", "title_dark_mono"),
            new Expected("TITLE_DARK_OFFSET", "title_dark_offset"),
            new Expected("TITLE_DARK_PAVEMENT", "title_dark_pavement"),
            new Expected("TITLE_DARK_PAVEMENT_VERTICAL", "title_dark_pavement_vertical"),
            new Expected("TITLE_DARK_HERRINGBONE", "title_dark_herringbone"),
            new Expected("TITLE_DARK_PLASTIC", "title_dark_plastic"),
            new Expected("TITLE_WHITE", "title_white"),
            new Expected("TITLE_WHITE_DIAGONAL_MINI", "title_white_diagonal_mini"),
            new Expected("TITLE_WHITE_DIAGONAL", "title_white_diagonal"),
            new Expected("TITLE_WHITE_HERRINGBONE", "title_white_herringbone"),
            new Expected("TITLE_WHITE_MINI", "title_white_mini"),
            new Expected("TITLE_WHITE_MONO", "title_white_mono"),
            new Expected("TITLE_WHITE_OFFSET", "title_white_offset"),
            new Expected("TITLE_WHITE_PAVEMENT", "title_white_pavement"),
            new Expected("TITLE_WHITE_PAVEMENT_VERTICAL", "title_white_pavement_vertical"),
            new Expected("TITLE_WHITE_PLASTIC", "title_white_plastic"),
            new Expected("TITLE_STEEL_PAVEMENT_VERTICAL", "title_steel_pavement_vertical"),
            new Expected("TITLE_ARCADEBLUE", "title_arcadeblue"),
            new Expected("TITLE_ARCADERED", "title_arcadered"),
            new Expected("TITLE_BAR", "title_bar"),
            new Expected("TITLE_BLUE_CIRCUIT", "title_blue_circuit"),
            new Expected("TITLE_BLUE", "title_blue"),
            new Expected("TITLE_BOXING", "title_boxing"),
            new Expected("TITLE_CAFETERIA", "title_cafeteria"),
            new Expected("TITLE_CARPETCLOWN", "title_carpetclown"),
            new Expected("TITLE_CARPETOFFICE", "title_carpetoffice"),
            new Expected("TITLE_CHECKERDARK", "title_checkerdark"),
            new Expected("TITLE_CLOWN", "title_clown"),
            new Expected("TITLE_DARKTEST", "title_darktest"),
            new Expected("TITLE_ELEVATORSHAFT", "title_elevatorshaft"),
            new Expected("TITLE_EXOBORG", "title_exoborg"),
            new Expected("TITLE_FREEZER", "title_freezer"),
            new Expected("TITLE_GOLD", "title_gold"),
            new Expected("TITLE_GRATINGMAINT", "title_gratingmaint"),
            new Expected("TITLE_GREENCIRCUIT", "title_greencircuit"),
            new Expected("TITLE_GYM", "title_gym"),
            new Expected("TITLE_HULLREINFORCED", "title_hullreinforced"),
            new Expected("TITLE_HULL", "title_hull"),
            new Expected("TITLE_HYDRO", "title_hydro"),
            new Expected("TITLE_KITCHEN", "title_kitchen"),
            new Expected("TITLE_LAUNDRY", "title_laundry"),
            new Expected("TITLE_LIME", "title_lime"),
            new Expected("TITLE_LINO", "title_lino"),
            new Expected("TITLE_MEAT", "title_meat"),
            new Expected("TITLE_METALDIAMOND", "title_metaldiamond"),
            new Expected("TITLE_MIME", "title_mime"),
            new Expected("TITLE_MININGFLOORDARK", "title_miningfloordark"),
            new Expected("TITLE_MININGFLOORLIGHT", "title_miningfloorlight"),
            new Expected("TITLE_MININGFLOOR", "title_miningfloor"),
            new Expected("TITLE_MONO", "title_mono"),
            new Expected("TITLE_REDCIRCUIT", "title_redcircuit"),
            new Expected("TITLE_REINFORCED", "title_reinforced"),
            new Expected("TITLE_ROCKVAULT", "title_rockvault"),
            new Expected("TITLE_SHOWROOM", "title_showroom"),
            new Expected("TITLE_SHUTTLEBLACK", "title_shuttleblack"),
            new Expected("TITLE_SHUTTLEBLUE", "title_shuttleblue"),
            new Expected("TITLE_SHUTTLEGREY", "title_shuttlegrey"),
            new Expected("TITLE_SHUTTLEORANGE", "title_shuttleorange"),
            new Expected("TITLE_SHUTTLEPURPLE", "title_shuttlepurple"),
            new Expected("TITLE_SHUTTLERED", "title_shuttlered"),
            new Expected("TITLE_SHUTTLEWHITE", "title_shuttlewhite"),
            new Expected("TITLE_SILVER", "title_silver"),
            new Expected("TITLE_STEELDAMAGED", "title_steeldamaged"),
            new Expected("TITLE_STEELDIRTY", "title_steeldirty"),
            new Expected("TITLE_STEELMAINT", "title_steelmaint"),
            new Expected("TITLE_SUPERREINFORCED", "title_superreinforced"),
            new Expected("TITLE_TECHMAINTDARK", "title_techmaintdark"),
            new Expected("TITLE_TECHMAINT", "title_techmaint"),
            new Expected("TITLE_WOODBROKEN", "title_woodbroken"),
            new Expected("TITLE_WOODLARGE", "title_woodlarge"),
            new Expected("TITLE_WOOD", "title_wood"),
            new Expected("TITLE_WOODTILE", "title_woodtile"));

    @Test
    void canonicalRegistryOwnsTileItemsAndPreservesCanonicalIds()
            throws ReflectiveOperationException {
        for (Expected expected : EXPECTED) {
            Field field = Ssc14Items.class.getField(expected.field());
            Object canonical = field.get(null);
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private record Expected(String field, String path) { }
}
