package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.storage.StationClosetBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksClosetCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            expected("CLOSET", "closet", "ClosetBlock"),
            expected("CLOSET_EMERG", "closet_emerg", "ClosetEmergBlock"),
            expected("CLOSET_FIRE", "closet_fire", "ClosetFireBlock"),
            expected("CLOSET_BLACK", "closet_black", "ClosetBlackBlock"),
            expected("CLOSET_BLUE", "closet_blue", "ClosetBlueBlock"),
            expected("CLOSET_GRAY", "closet_gray", "ClosetGrayBlock"),
            expected("CLOSET_GREEN", "closet_green", "ClosetGreenBlock"),
            expected("CLOSET_MIXED", "closet_mixed", "ClosetMixedBlock"),
            expected("CLOSET_N_2", "closet_n_2", "ClosetN2Block"),
            expected("CLOSET_ORANGE", "closet_orange", "ClosetOrangeBlock"),
            expected("CLOSET_PINK", "closet_pink", "ClosetPinkBlock"));

    @Test
    void canonicalRegistryOwnsClosetIdsAndPreservesCanonicalIds() throws Exception {
        for (Expected expected : EXPECTED) {
            Object canonical = Ssc14Blocks.class.getField(expected.field()).get(null);
            assertTrue(canonical instanceof DeferredHolder<?, ?>, expected.field());
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    @Test
    void legacyClosetBlocksRemainConstructorCompatibleWrappers() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(StationClosetBlock.class.isAssignableFrom(legacy), expected.legacyClass());
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
    }

    @Test
    void canonicalClosetsKeepLegacyStateDefaults() {
        for (Expected expected : EXPECTED) {
            StationClosetBlock closet = (StationClosetBlock) holder(expected).get();
            assertFalse(closet.defaultBlockState().getValue(StationClosetBlock.OPEN));
            assertEquals(net.minecraft.core.Direction.NORTH,
                    closet.defaultBlockState().getValue(StationClosetBlock.FACING));
        }
    }

    private static DeferredHolder<?, ?> holder(Expected expected) {
        try {
            return (DeferredHolder<?, ?>) Ssc14Blocks.class.getField(expected.field()).get(null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }

    private static Expected expected(String field, String path, String legacyClass) {
        return new Expected(field, path, legacyClass);
    }

    private record Expected(String field, String path, String legacyClass) { }
}
