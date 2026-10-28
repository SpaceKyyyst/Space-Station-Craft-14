package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.window.ArmoredWindowBlock;
import net.ssc14.content.block.window.BaseWindowBlock;
import net.ssc14.content.block.window.DirectedArmoredWindowBlock;
import net.ssc14.content.block.window.DirectedBaseWindowBlock;
import net.ssc14.content.block.window.WindowKnockService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksWindowCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "BASE_WINDOW", "base_window",
            "ARMORED_WINDOW", "armored_window",
            "DIRECTED_BASE_WINDOW", "directed_base_window",
            "DIRECTED_ARMORED_WINDOW", "directed_armored_window");

    @Test
    void canonicalRegistryOwnsExactWindowIdsAndLegacyFacadeAliasesHolders()
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
    void legacyWindowBlocksRemainConstructorAndPropertyCompatible() throws ReflectiveOperationException {
        assertLegacy(net.mcreator.ssc.block.BaseWindowBlock.class, BaseWindowBlock.class);
        assertLegacy(net.mcreator.ssc.block.ArmoredWindowBlock.class, ArmoredWindowBlock.class);
        assertLegacy(net.mcreator.ssc.block.DirectedBaseWindowBlock.class, DirectedBaseWindowBlock.class);
        assertLegacy(net.mcreator.ssc.block.DirectedArmoredWindowBlock.class, DirectedArmoredWindowBlock.class);

        assertSame(BaseWindowBlock.WINDOW_DISASSEMBLY,
                net.mcreator.ssc.block.BaseWindowBlock.WINDOW_DISASSEMBLY);
        assertSame(ArmoredWindowBlock.BLOCKSTATE,
                net.mcreator.ssc.block.ArmoredWindowBlock.BLOCKSTATE);
        assertSame(DirectedBaseWindowBlock.FACING,
                net.mcreator.ssc.block.DirectedBaseWindowBlock.FACING);
        assertSame(DirectedBaseWindowBlock.WINDOW_DISASSEMBLY,
                net.mcreator.ssc.block.DirectedBaseWindowBlock.WINDOW_DISASSEMBLY);
        assertSame(DirectedBaseWindowBlock.BLOCKSTATE,
                net.mcreator.ssc.block.DirectedBaseWindowBlock.BLOCKSTATE);
        assertSame(DirectedArmoredWindowBlock.FACING,
                net.mcreator.ssc.block.DirectedArmoredWindowBlock.FACING);
        assertSame(DirectedArmoredWindowBlock.WINDOW_DISSASAMBLY,
                net.mcreator.ssc.block.DirectedArmoredWindowBlock.WINDOW_DISSASAMBLY);
    }

    @Test
    void canonicalKnockServiceKeepsExactAbi() throws ReflectiveOperationException {
        Method execute = WindowKnockService.class.getMethod(
                "knock", LevelAccessor.class, double.class, double.class, double.class, Entity.class);
        assertSame(InteractionResult.class, execute.getReturnType());
    }

    private static void assertLegacy(Class<?> legacyType, Class<?> canonicalType)
            throws ReflectiveOperationException {
        assertTrue(canonicalType.isAssignableFrom(legacyType), legacyType.getName());
        legacyType.getConstructor(BlockBehaviour.Properties.class);
    }
}
