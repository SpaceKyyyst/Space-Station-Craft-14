package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14CanonicalBlockItemsCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            new Expected("ROD_FLOOR", "rod_floor"),
            new Expected("STEEL_WALL", "steel_wall"),
            new Expected("WALL_CARCASE", "wall_carcase"),
            new Expected("UPER_SHEATHING", "uper_sheathing"),
            new Expected("ROD_UP_FLOOR", "rod_up_floor"),
            new Expected("UP_TITLE", "up_title"),
            new Expected("BASE_WINDOW", "base_window"),
            new Expected("PLASTEEL_WALL", "plasteel_wall"),
            new Expected("PLASTEEL_WALL_CARCASE", "plasteel_wall_carcase"),
            new Expected("UP_TITLE_STEEL", "up_title_steel"),
            new Expected("UP_TITLE_DARK", "up_title_dark"),
            new Expected("ARMORED_WINDOW", "armored_window"),
            new Expected("GRILLE", "grille"),
            new Expected("BROKEN_GRILLE", "broken_grille"),
            new Expected("CAVE_FLOOR_1", "cave_floor_1"),
            new Expected("CATWALK_FLOOR", "catwalk_floor"),
            new Expected("STEEL_STAIRCASE", "steel_staircase"),
            new Expected("WHITE_STAIRCASE", "white_staircase"),
            new Expected("DARK_STAIRCASE", "dark_staircase"),
            new Expected("DIRT", "dirt"),
            new Expected("GRASS_LIGHT_BLOCK", "grass_light_block"),
            new Expected("LOW_PLANET_GRASS", "low_planet_grass"),
            new Expected("MED_PLANET_GRASS", "med_planet_grass"),
            new Expected("PLANETARY_TREE_LOG", "planetary_tree_log"),
            new Expected("PLANETARY_TREE_FOLIAGE", "planetary_tree_foliage"),
            new Expected("UP_STEEL_STAIRCASE", "up_steel_staircase"),
            new Expected("UP_WHITE_STAIRCASE", "up_white_staircase"),
            new Expected("UP_DARK_STAIRCASE", "up_dark_staircase"),
            new Expected("SHUTTLE_WALL", "shuttle_wall"),
            new Expected("ARMORED_SHUTTLE_WALL", "armored_shuttle_wall"),
            new Expected("DIRECTED_BASE_WINDOW", "directed_base_window"),
            new Expected("DIRECTED_ARMORED_WINDOW", "directed_armored_window"),
            new Expected("TITLEARCADEBLUE_2", "titlearcadeblue_2"),
            new Expected("TITLEEIGHTIES", "titleeighties"));

    @Test
    void canonicalRegistryOwnsRemainingCanonicalBlockItemsAndLegacyHoldersRemainAliases()
            throws ReflectiveOperationException {
        for (Expected expected : EXPECTED) {
            Field field = Ssc14Items.class.getField(expected.field());
            Object canonical = field.get(null);
            assertSame(canonical, Ssc14ModItems.class.getField(expected.field()).get(null));
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private record Expected(String field, String path) { }
}
