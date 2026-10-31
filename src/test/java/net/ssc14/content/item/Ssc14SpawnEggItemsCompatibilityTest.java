package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.Map;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14SpawnEggItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("WALL_CARCASE_ENTIT_SPAWN_EGG", "wall_carcase_entit_spawn_egg"),
            Map.entry("ID_CONSOLE_ENTITY_SPAWN_EGG", "id_console_entity_spawn_egg"),
            Map.entry("PLASSTEEL_WALL_CARCASE_ENTIT_SPAWN_EGG", "plassteel_wall_carcase_entit_spawn_egg"),
            Map.entry("MOUSE_BROWN_SPAWN_EGG", "mouse_brown_spawn_egg"),
            Map.entry("MOUSE_GRAY_SPAWN_EGG", "mouse_gray_spawn_egg"),
            Map.entry("MOUSE_WHITE_SPAWN_EGG", "mouse_white_spawn_egg"),
            Map.entry("CARP_SPAWN_EGG", "carp_spawn_egg"));

    @Test
    void canonicalRegistryOwnsSpawnEggIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }
}
