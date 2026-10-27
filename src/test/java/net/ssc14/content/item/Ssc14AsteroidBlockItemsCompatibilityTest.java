package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14AsteroidBlockItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED = expected();

    @Test
    void canonicalRegistryOwnsAsteroidItemsAndLegacyHoldersRemainAliases()
            throws ReflectiveOperationException {
        for (Map.Entry<String, String> entry : EXPECTED.entrySet()) {
            Field field = Ssc14Items.class.getField(entry.getKey());
            Object canonical = field.get(null);
            assertSame(canonical, Ssc14ModItems.class.getField(entry.getKey()).get(null));
            assertEquals("ssc_14:" + entry.getValue(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private static Map<String, String> expected() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("ASTEROID_STONE_BASE", "asteroid_stone_base");
        result.put("ASTEROID_STONE_IRON", "asteroid_stone_iron");
        result.put("ASTEROID_STONE_QUARTZ", "asteroid_stone_quartz");
        result.put("ASTEROID_STONE_COAL", "asteroid_stone_coal");
        result.put("ASTEROID_STONE_GOLD", "asteroid_stone_gold");
        result.put("ASTEROID_STONE_PLASMA", "asteroid_stone_plasma");
        result.put("ASTEROID_STONE_SILVER", "asteroid_stone_silver");
        result.put("ASTEROID_STONE_URAN", "asteroid_stone_uran");
        result.put("ASTEROID_STONE_DIAMOND", "asteroid_stone_diamond");
        return Map.copyOf(result);
    }
}
