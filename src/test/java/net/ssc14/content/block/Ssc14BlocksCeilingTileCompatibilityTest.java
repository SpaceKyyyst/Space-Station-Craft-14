package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
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
    void canonicalRegistryOwnsIdsAndPreservesCanonicalIds() throws Exception {
        for (Expected expected : EXPECTED) {
            Object holder = Ssc14Blocks.class.getField(expected.field()).get(null);
            assertTrue(holder instanceof DeferredHolder<?, ?>, expected.field());
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) holder).getId().toString());
        }
    }

    @Test
    void legacyTypesKeepTheirAbi() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(CeilingTileBlock.class.isAssignableFrom(legacy), expected.legacyClass());
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
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

    private record Expected(String field, String path, String legacyClass) { }
}
