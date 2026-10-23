package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.planetary.PlanetDirtBlock;
import net.ssc14.content.block.planetary.PlanetGrassPlantBlock;
import net.ssc14.content.block.planetary.PlanetGrassSoilBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeFoliageBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeLogBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksPlanetaryCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "DIRT", "dirt",
            "GRASS_LIGHT_BLOCK", "grass_light_block",
            "LOW_PLANET_GRASS", "low_planet_grass",
            "MED_PLANET_GRASS", "med_planet_grass",
            "PLANETARY_TREE_LOG", "planetary_tree_log",
            "PLANETARY_TREE_FOLIAGE", "planetary_tree_foliage");

    private static final Class<?>[] LEGACY_TYPES = {
        net.mcreator.ssc.block.DirtBlock.class,
        net.mcreator.ssc.block.GrassLightBlockBlock.class,
        net.mcreator.ssc.block.LowPlanetGrassBlock.class,
        net.mcreator.ssc.block.MedPlanetGrassBlock.class,
        net.mcreator.ssc.block.PlanetaryTreeLogBlock.class,
        net.mcreator.ssc.block.PlanetaryTreeFoliageBlock.class
    };

    @Test
    void canonicalRegistryOwnsExactPlanetaryIdsAndLegacyFacadeAliasesHolders()
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
    void canonicalTypesAreServerSafeAndLegacyBlocksRemainConstructorCompatible()
            throws ReflectiveOperationException {
        assertTrue(PlanetDirtBlock.class.isAssignableFrom(LEGACY_TYPES[0]));
        assertTrue(PlanetGrassSoilBlock.class.isAssignableFrom(LEGACY_TYPES[1]));
        assertTrue(PlanetGrassPlantBlock.class.isAssignableFrom(LEGACY_TYPES[2]));
        assertTrue(PlanetGrassPlantBlock.class.isAssignableFrom(LEGACY_TYPES[3]));
        assertTrue(PlanetaryTreeLogBlock.class.isAssignableFrom(LEGACY_TYPES[4]));
        assertTrue(PlanetaryTreeFoliageBlock.class.isAssignableFrom(LEGACY_TYPES[5]));

        for (Class<?> legacyType : LEGACY_TYPES) {
            legacyType.getConstructor(BlockBehaviour.Properties.class);
        }
    }

    @Test
    void legacyPlanetaryProceduresRetainGeneratedSignatures() throws ReflectiveOperationException {
        assertProcedureSignature(net.mcreator.ssc.procedures.Dirt_GenerationProcedure.class, void.class);
        assertProcedureSignature(
                net.mcreator.ssc.procedures.GrassLightBlock_GenerationProcedure.class, void.class);
        assertProcedureSignature(
                net.mcreator.ssc.procedures.LowPlanetGrass_InstallationProcedure.class, boolean.class);
    }

    private static void assertProcedureSignature(Class<?> type, Class<?> returnType)
            throws ReflectiveOperationException {
        Method execute = type.getMethod(
                "execute", LevelAccessor.class, double.class, double.class, double.class);
        assertTrue(Modifier.isPublic(execute.getModifiers()), type.getName());
        assertTrue(Modifier.isStatic(execute.getModifiers()), type.getName());
        assertEquals(returnType, execute.getReturnType(), type.getName());
    }
}
