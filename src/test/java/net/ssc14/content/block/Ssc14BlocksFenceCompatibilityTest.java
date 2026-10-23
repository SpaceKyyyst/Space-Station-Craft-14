package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
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
    void canonicalRegistryOwnsExactFenceIdsAndLegacyFacadeAliasesHolders()
            throws ReflectiveOperationException {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Blocks.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertTrue(canonicalHolder instanceof DeferredHolder<?, ?>, expected.getKey());
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString());
            assertSame(canonicalHolder, Ssc14ModBlocks.class.getField(expected.getKey()).get(null));
        }
    }

    @Test
    void legacyFenceBlocksAndNestedItemsRemainConstructorCompatible()
            throws ReflectiveOperationException {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(DirectionalFenceBlock.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(BlockBehaviour.Properties.class);
            Class<?> itemType = Class.forName(legacyType.getName() + "$Item");
            itemType.getConstructor(Item.Properties.class);
        }
    }
}
