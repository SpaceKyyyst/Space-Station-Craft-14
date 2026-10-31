package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.ssc14.content.item.Ssc14Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.decoration.DirectionalFenceBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksFenceCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "FENCE_POINT", "fence_point",
            "FENCE_LINE", "fence_line",
            "FENCE_G", "fence_g",
            "FENCE_P", "fence_p");

    private static final Class<?>[] LEGACY_TYPES = {
        net.mcreator.ssc.block.FencePointBlock.class,
        net.mcreator.ssc.block.FenceLineBlock.class,
        net.mcreator.ssc.block.FenceGBlock.class,
        net.mcreator.ssc.block.FencePBlock.class
    };

    @Test
    void canonicalRegistryOwnsExactFenceIdsAndPreservesCanonicalIds()
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
    void legacyFenceBlocksAndNestedItemsRemainConstructorCompatible()
            throws ReflectiveOperationException {
        Class<?> canonicalItemType = Class.forName(
                "net.ssc14.content.item.block.FenceBlockItem");
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(DirectionalFenceBlock.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(BlockBehaviour.Properties.class);
            Class<?> itemType = Class.forName(legacyType.getName() + "$Item");
            assertTrue(canonicalItemType.isAssignableFrom(itemType), itemType.getName());
            itemType.getConstructor(Item.Properties.class);
        }
    }

    @Test
    void canonicalItemRegistryOwnsFenceItemsAndPreservesCanonicalIds()
            throws ReflectiveOperationException {
        Class<?> canonicalItems = Class.forName("net.ssc14.content.item.Ssc14Items");
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Object canonicalHolder = canonicalItems.getField(expected.getKey()).get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString());
        }
    }
}
