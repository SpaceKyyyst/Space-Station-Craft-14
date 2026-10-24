package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.procedures.CatwalkPlacementConditionsProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.catwalk.CatwalkFloorBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksCatwalkCompatibilityTest {
    @Test
    void canonicalRegistryOwnsExactIdAndLegacyFacadeAliasesHolder() throws Exception {
        Object holder = Ssc14Blocks.class.getField("CATWALK_FLOOR").get(null);

        assertTrue(holder instanceof DeferredHolder<?, ?>);
        assertEquals("ssc_14:catwalk_floor", ((DeferredHolder<?, ?>) holder).getId().toString());
        assertSame(holder, Ssc14ModBlocks.CATWALK_FLOOR);
    }

    @Test
    void legacyBlockAndProcedureKeepTheirAbi() throws Exception {
        assertTrue(CatwalkFloorBlock.class.isAssignableFrom(
                net.mcreator.ssc.block.CatwalkFloorBlock.class));
        net.mcreator.ssc.block.CatwalkFloorBlock.class
                .getConstructor(BlockBehaviour.Properties.class);
        var execute = CatwalkPlacementConditionsProcedure.class.getMethod("execute",
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

        assertTrue(Ssc14Blocks.CATWALK_FLOOR.get().defaultBlockState()
                .canSurvive(reader, BlockPos.ZERO));
    }
}
