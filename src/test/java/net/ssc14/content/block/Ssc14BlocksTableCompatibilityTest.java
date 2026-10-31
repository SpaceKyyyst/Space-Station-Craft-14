package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.furniture.ConnectedTableBlock;
import net.ssc14.content.block.furniture.TableConnectionService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksTableCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "STEEL_TABLE", "steel_table",
            "WOOD_TABLE", "wood_table",
            "GLASS_TABLE", "glass_table");

    private static final Class<?>[] LEGACY_TYPES = {
        net.mcreator.ssc.block.SteelTableBlock.class,
        net.mcreator.ssc.block.WoodTableBlock.class,
        net.mcreator.ssc.block.GlassTableBlock.class
    };

    @Test
    void canonicalRegistryOwnsExactTableIdsAndPreservesCanonicalIds()
            throws ReflectiveOperationException {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Blocks.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertTrue(canonicalHolder instanceof DeferredHolder<?, ?>, expected.getKey());
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString());
        }
    }

    @Test
    void legacyTableBlocksRemainConstructorCompatible() throws ReflectiveOperationException {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(ConnectedTableBlock.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(BlockBehaviour.Properties.class);
        }
    }

    @Test
    void connectionMaskKeepsLegacyStateMapping() {
        int[] expected = {0, 1, 2, 5, 3, 9, 6, 12, 4, 8, 10, 13, 7, 11, 14, 15};
        for (int mask = 0; mask < expected.length; mask++) {
            assertEquals(expected[mask], TableConnectionService.connectionState(
                    (mask & 1) != 0, (mask & 2) != 0, (mask & 4) != 0, (mask & 8) != 0));
        }
    }
}
