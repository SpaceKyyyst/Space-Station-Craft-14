package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.wall.ArmoredShuttleWallBlock;
import net.ssc14.content.block.wall.PlasteelWallBlock;
import net.ssc14.content.block.wall.ShuttleWallBlock;
import net.ssc14.content.block.wall.SteelWallBlock;
import net.ssc14.content.block.wall.WallDamageService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksStationWallCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            new Expected("STEEL_WALL", "steel_wall", "SteelWallBlock", SteelWallBlock.class),
            new Expected("PLASTEEL_WALL", "plasteel_wall", "PlasteelWallBlock",
                    PlasteelWallBlock.class),
            new Expected("SHUTTLE_WALL", "shuttle_wall", "ShuttleWallBlock",
                    ShuttleWallBlock.class),
            new Expected("ARMORED_SHUTTLE_WALL", "armored_shuttle_wall",
                    "ArmoredShuttleWallBlock", ArmoredShuttleWallBlock.class));

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
    void legacyTypesAndCanonicalDamageServiceKeepTheirAbi() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(expected.canonicalClass().isAssignableFrom(legacy));
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
        assertSame(void.class, WallDamageService.class.getMethod("breakByPlayer",
                LevelAccessor.class, double.class, double.class, double.class, Entity.class)
                .getReturnType());
        assertSame(void.class, WallDamageService.class.getMethod("scheduleSteelExplosion",
                ServerLevel.class, double.class, double.class, double.class).getReturnType());
        assertSame(void.class, WallDamageService.class.getMethod("schedulePlasteelExplosion",
                ServerLevel.class, double.class, double.class, double.class).getReturnType());
        assertSame(PlasteelWallBlock.BLOCKSTATE,
                net.mcreator.ssc.block.PlasteelWallBlock.BLOCKSTATE);
    }

    private record Expected(String field, String path, String legacyClass,
            Class<?> canonicalClass) { }
}
