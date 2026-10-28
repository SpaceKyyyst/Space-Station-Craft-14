package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.furniture.ArmChairBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksArmChairCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "ARM_CHAIR_BASE", "arm_chair_base",
            "ARM_CHAIR_BROWN", "arm_chair_brown",
            "ARM_CHAIR_CARP", "arm_chair_carp");

    private static final Class<?>[] LEGACY_TYPES = {
        net.mcreator.ssc.block.ArmChairBaseBlock.class,
        net.mcreator.ssc.block.ArmChairBrownBlock.class,
        net.mcreator.ssc.block.ArmChairCarpBlock.class
    };

    @Test
    void canonicalRegistryOwnsExactArmChairIdsAndLegacyFacadeAliasesHolders()
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
    void legacyArmChairBlocksRemainConstructorCompatible() throws ReflectiveOperationException {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(ArmChairBlock.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(BlockBehaviour.Properties.class);
        }
    }
}
