package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.procedures.CeilingPlacementConditionsProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.ceiling.CeilingTileBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksCeilingTileCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            new Expected("UP_TITLE", "up_title", "UpTitleBlock"),
            new Expected("UP_TITLE_STEEL", "up_title_steel", "UpTitleSteelBlock"),
            new Expected("UP_TITLE_DARK", "up_title_dark", "UpTitleDarkBlock"));

    @Test
    void canonicalRegistryOwnsIdsAndLegacyFacadeAliasesHolders() throws Exception {
        for (Expected expected : EXPECTED) {
            Object holder = Ssc14Blocks.class.getField(expected.field()).get(null);
            assertTrue(holder instanceof DeferredHolder<?, ?>, expected.field());
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) holder).getId().toString());
            assertSame(holder, Ssc14ModBlocks.class.getField(expected.field()).get(null));
        }
    }

    @Test
    void legacyTypesAndProcedureKeepTheirAbi() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(CeilingTileBlock.class.isAssignableFrom(legacy), expected.legacyClass());
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
        var execute = CeilingPlacementConditionsProcedure.class.getMethod("execute",
                LevelAccessor.class, double.class, double.class, double.class);
        assertSame(boolean.class, execute.getReturnType());
    }

    @Test
    void nonAccessorReaderUsesLegacySuperclassFallback() {
        LevelReader reader = (LevelReader) Proxy.newProxyInstance(
                LevelReader.class.getClassLoader(), new Class<?>[] {LevelReader.class},
                (proxy, method, arguments) -> {
                    throw new AssertionError("fallback must not query " + method.getName());
                });

        assertTrue(Ssc14Blocks.UP_TITLE.get().defaultBlockState()
                .canSurvive(reader, BlockPos.ZERO));
    }

    @Test
    void legacyProcedurePreservesDoubleRoundingBeforeBlockPositionConversion() {
        AtomicReference<BlockPos> queriedPos = new AtomicReference<>();
        LevelAccessor world = (LevelAccessor) Proxy.newProxyInstance(
                LevelAccessor.class.getClassLoader(), new Class<?>[] {LevelAccessor.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("getBlockState")) {
                        queriedPos.set((BlockPos) arguments[0]);
                        return Blocks.AIR.defaultBlockState();
                    }
                    throw new AssertionError("adapter must not call " + method.getName());
                });

        assertFalse(CeilingPlacementConditionsProcedure.execute(
                world, 0.0, Math.nextDown(1.0), 0.0));
        assertEquals(new BlockPos(0, 2, 0), queriedPos.get());
    }

    private record Expected(String field, String path, String legacyClass) { }
}
