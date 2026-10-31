package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.structure.PlasteelWallCarcaseBlock;
import net.ssc14.content.block.structure.RodFloorBlock;
import net.ssc14.content.block.structure.RodUpFloorBlock;
import net.ssc14.content.block.structure.UperSheathingBlock;
import net.ssc14.content.block.structure.WallCarcaseBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksStructureCompatibilityTest {
    private static final Map<String, String> EXPECTED = Map.of(
            "ROD_FLOOR", "rod_floor", "ROD_UP_FLOOR", "rod_up_floor",
            "UPER_SHEATHING", "uper_sheathing", "WALL_CARCASE", "wall_carcase",
            "PLASTEEL_WALL_CARCASE", "plasteel_wall_carcase");

    @Test
    void canonicalRegistryOwnsExactIdsAndPreservesCanonicalIds() throws Exception {
        for (var entry : EXPECTED.entrySet()) {
            Field field = Ssc14Blocks.class.getField(entry.getKey());
            Object holder = field.get(null);
            assertTrue(holder instanceof DeferredHolder<?, ?>);
            assertEquals("ssc_14:" + entry.getValue(), ((DeferredHolder<?, ?>) holder).getId().toString());
        }
    }

    @Test
    void legacyTypesRemainConstructorAndPropertyCompatible() throws Exception {
        assertLegacy(net.mcreator.ssc.block.RodFloorBlock.class, RodFloorBlock.class);
        assertLegacy(net.mcreator.ssc.block.RodUpFloorBlock.class, RodUpFloorBlock.class);
        assertLegacy(net.mcreator.ssc.block.UperSheathingBlock.class, UperSheathingBlock.class);
        assertLegacy(net.mcreator.ssc.block.WallCarcaseBlock.class, WallCarcaseBlock.class);
        assertLegacy(net.mcreator.ssc.block.PlasteelWallCarcaseBlock.class, PlasteelWallCarcaseBlock.class);
        assertSame(RodFloorBlock.BLOCKSTATE, net.mcreator.ssc.block.RodFloorBlock.BLOCKSTATE);
    }

    private static void assertLegacy(Class<?> legacy, Class<?> canonical) throws Exception {
        assertTrue(canonical.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }
}
