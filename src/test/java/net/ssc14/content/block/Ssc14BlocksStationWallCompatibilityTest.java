package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.wall.ArmoredShuttleWallBlock;
import net.ssc14.content.block.wall.PlasteelWallBlock;
import net.ssc14.content.block.wall.ShuttleWallBlock;
import net.ssc14.content.block.wall.SteelWallBlock;
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
    void legacyTypesAndProceduresKeepTheirAbi() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(expected.canonicalClass().isAssignableFrom(legacy));
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
        assertProcedure("SteelWall_DestroyProcedure", Entity.class);
        assertProcedure("Wall_Explosion_BreakdownProcedure");
        assertProcedure("PlstWallExplosionBreakdownProcedure");
        assertSame(PlasteelWallBlock.BLOCKSTATE,
                net.mcreator.ssc.block.PlasteelWallBlock.BLOCKSTATE);
    }

    private static void assertProcedure(String simpleName, Class<?>... trailingTypes)
            throws Exception {
        Class<?>[] parameters = new Class<?>[4 + trailingTypes.length];
        parameters[0] = LevelAccessor.class;
        parameters[1] = double.class;
        parameters[2] = double.class;
        parameters[3] = double.class;
        System.arraycopy(trailingTypes, 0, parameters, 4, trailingTypes.length);
        Class<?> procedure = Class.forName("net.mcreator.ssc.procedures." + simpleName);
        procedure.getConstructor();
        assertSame(void.class, procedure.getMethod("execute", parameters).getReturnType());
    }

    private record Expected(String field, String path, String legacyClass,
            Class<?> canonicalClass) { }
}
