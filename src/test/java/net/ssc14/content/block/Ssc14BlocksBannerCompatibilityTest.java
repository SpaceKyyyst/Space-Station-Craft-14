package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.decoration.DirectionalBannerBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksBannerCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "BANNER_NT", "banner_nt",
            "BANNER_MED", "banner_med",
            "BANNER_RN_D", "banner_rn_d",
            "BANNER_SEC", "banner_sec",
            "BANNER_CARGO", "banner_cargo",
            "BANNER_ENG", "banner_eng",
            "BANNER_SYND", "banner_synd");

    private static final Class<?>[] LEGACY_TYPES = {
        net.mcreator.ssc.block.BannerNTBlock.class,
        net.mcreator.ssc.block.BannerMedBlock.class,
        net.mcreator.ssc.block.BannerRnDBlock.class,
        net.mcreator.ssc.block.BannerSecBlock.class,
        net.mcreator.ssc.block.BannerCargoBlock.class,
        net.mcreator.ssc.block.BannerEngBlock.class,
        net.mcreator.ssc.block.BannerSyndBlock.class
    };

    @Test
    void canonicalRegistryOwnsExactBannerIdsAndPreservesCanonicalIds()
            throws ReflectiveOperationException {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Blocks.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertTrue(canonicalHolder instanceof DeferredHolder<?, ?>, expected.getKey());
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString());
        }
    }

    @Test
    void legacyBannerBlocksRemainConstructorCompatibleSubtypes()
            throws ReflectiveOperationException {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(DirectionalBannerBlock.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(BlockBehaviour.Properties.class);
        }
    }
}
