package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.furniture.ChairInteractionService;
import net.ssc14.content.block.furniture.DirectionalChairBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksChairCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "STEEL_CHAIR", "steel_chair",
            "WOOD_CHAIR", "wood_chair",
            "OFICE_BLACK_CHAIR", "ofice_black_chair",
            "OFICE_WHITE_CHAIR", "ofice_white_chair",
            "BAR_CHAIR", "bar_chair");

    private static final Class<?>[] LEGACY_TYPES = {
        net.mcreator.ssc.block.SteelChairBlock.class,
        net.mcreator.ssc.block.WoodChairBlock.class,
        net.mcreator.ssc.block.OficeBlackChairBlock.class,
        net.mcreator.ssc.block.OficeWhiteChairBlock.class,
        net.mcreator.ssc.block.BarChairBlock.class
    };

    @Test
    void canonicalRegistryOwnsExactChairIdsAndLegacyFacadeAliasesHolders()
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
    void legacyChairBlocksRemainConstructorCompatible() throws ReflectiveOperationException {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(DirectionalChairBlock.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(BlockBehaviour.Properties.class);
        }
    }

    @Test
    void autorotationKeepsLegacyYawBoundaries() {
        assertEquals(Direction.SOUTH, ChairInteractionService.directionForYaw(0.0f));
        assertEquals(Direction.SOUTH, ChairInteractionService.directionForYaw(44.999f));
        assertEquals(Direction.WEST, ChairInteractionService.directionForYaw(45.0f));
        assertEquals(Direction.WEST, ChairInteractionService.directionForYaw(134.999f));
        assertEquals(Direction.NORTH, ChairInteractionService.directionForYaw(135.0f));
        assertEquals(Direction.NORTH, ChairInteractionService.directionForYaw(-135.0f));
        assertEquals(Direction.EAST, ChairInteractionService.directionForYaw(-134.999f));
        assertEquals(Direction.EAST, ChairInteractionService.directionForYaw(-45.0f));
        assertEquals(Direction.SOUTH, ChairInteractionService.directionForYaw(-44.999f));
    }
}
