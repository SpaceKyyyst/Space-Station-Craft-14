package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.grille.BrokenGrilleBlock;
import net.ssc14.content.block.grille.GrilleBlock;
import net.ssc14.content.block.grille.GrilleBreakService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksGrilleCompatibilityTest {
    private static final Map<String, String> EXPECTED = Map.of(
            "GRILLE", "grille",
            "BROKEN_GRILLE", "broken_grille");

    @Test
    void canonicalRegistryOwnsExactIdsAndLegacyFacadeAliasesHolders() throws Exception {
        for (var entry : EXPECTED.entrySet()) {
            Field field = Ssc14Blocks.class.getField(entry.getKey());
            Object holder = field.get(null);
            assertTrue(holder instanceof DeferredHolder<?, ?>);
            assertEquals("ssc_14:" + entry.getValue(), ((DeferredHolder<?, ?>) holder).getId().toString());
            assertSame(holder, Ssc14ModBlocks.class.getField(entry.getKey()).get(null));
        }
    }

    @Test
    void legacyTypesRemainConstructorAndFacingCompatible() throws Exception {
        assertLegacy(net.mcreator.ssc.block.GrilleBlock.class, GrilleBlock.class);
        assertLegacy(net.mcreator.ssc.block.BrokenGrilleBlock.class, BrokenGrilleBlock.class);
        assertSame(BrokenGrilleBlock.FACING, net.mcreator.ssc.block.BrokenGrilleBlock.FACING);
    }

    @Test
    void canonicalBreakServiceKeepsExactExecuteAbi() throws Exception {
        var execute = GrilleBreakService.class.getMethod(
                "replaceWithBroken", LevelAccessor.class, BlockPos.class, Entity.class);

        assertSame(void.class, execute.getReturnType());
    }

    private static void assertLegacy(Class<?> legacy, Class<?> canonical) throws Exception {
        assertTrue(canonical.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }
}
