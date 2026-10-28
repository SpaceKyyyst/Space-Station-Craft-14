package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.surface.StaircaseBlock;
import net.ssc14.content.block.surface.UpperStaircaseBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksStaircaseCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "STEEL_STAIRCASE", "steel_staircase",
            "WHITE_STAIRCASE", "white_staircase",
            "DARK_STAIRCASE", "dark_staircase",
            "UP_STEEL_STAIRCASE", "up_steel_staircase",
            "UP_WHITE_STAIRCASE", "up_white_staircase",
            "UP_DARK_STAIRCASE", "up_dark_staircase");

    private static final Map<Class<?>, Class<?>> LEGACY_TYPES = Map.of(
            net.mcreator.ssc.block.SteelStaircaseBlock.class, StaircaseBlock.class,
            net.mcreator.ssc.block.WhiteStaircaseBlock.class, StaircaseBlock.class,
            net.mcreator.ssc.block.DarkStaircaseBlock.class, StaircaseBlock.class,
            net.mcreator.ssc.block.UpSteelStaircaseBlock.class, UpperStaircaseBlock.class,
            net.mcreator.ssc.block.UpWhiteStaircaseBlock.class, UpperStaircaseBlock.class,
            net.mcreator.ssc.block.UpDarkStaircaseBlock.class, UpperStaircaseBlock.class);

    @Test
    void canonicalRegistryOwnsExactStaircaseIdsAndLegacyFacadeAliasesHolders()
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
    void legacyStaircaseBlocksRemainConstructorCompatibleSubtypes()
            throws ReflectiveOperationException {
        for (Map.Entry<Class<?>, Class<?>> pair : LEGACY_TYPES.entrySet()) {
            assertTrue(pair.getValue().isAssignableFrom(pair.getKey()), pair.getKey().getName());
            pair.getKey().getConstructor(BlockBehaviour.Properties.class);
        }
    }
}
