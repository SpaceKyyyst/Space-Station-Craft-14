package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.lighting.DebugLightBlock;
import net.ssc14.content.block.lighting.EmergencyLightBlock;
import net.ssc14.content.block.lighting.EmergencyLightingService;
import net.ssc14.content.block.lighting.ServiceableLightBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksLightingCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            expected("LAMP", "lamp", "LampBlock", ServiceableLightBlock.class),
            expected("TECH_LAMP", "tech_lamp", "TechLampBlock", ServiceableLightBlock.class),
            expected("DEBUG_LAMP", "debug_lamp", "DEBUGLampBlock", DebugLightBlock.class),
            expected("DEBUG_TECH_LAMP", "debug_tech_lamp", "DEBUGTechLampBlock",
                    DebugLightBlock.class),
            expected("EMERGENCY_LIGHT", "emergency_light", "EmergencyLightBlock",
                    EmergencyLightBlock.class));

    @Test
    void canonicalRegistryOwnsLightingIdsAndPreservesCanonicalIds() throws Exception {
        for (Expected expected : EXPECTED) {
            Object canonical = Ssc14Blocks.class.getField(expected.field()).get(null);
            assertTrue(canonical instanceof DeferredHolder<?, ?>, expected.field());
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    @Test
    void legacyLightingBlocksRemainConstructorCompatibleWrappers() throws Exception {
        for (Expected expected : EXPECTED) {
            Class<?> legacy = Class.forName("net.mcreator.ssc.block." + expected.legacyClass());
            assertTrue(expected.canonicalClass().isAssignableFrom(legacy),
                    expected.legacyClass());
            legacy.getConstructor(BlockBehaviour.Properties.class);
        }
    }

    @Test
    void canonicalLightsKeepLegacyStateDefaults() {
        for (String field : List.of("LAMP", "TECH_LAMP")) {
            ServiceableLightBlock light = (ServiceableLightBlock) holder(field).get();
            assertEquals(Direction.NORTH,
                    light.defaultBlockState().getValue(ServiceableLightBlock.FACING));
            assertTrue(light.defaultBlockState().getValue(ServiceableLightBlock.HAVE_LAMP));
            assertFalse(light.defaultBlockState().getValue(ServiceableLightBlock.BROKEN));
            assertFalse(light.defaultBlockState().getValue(ServiceableLightBlock.ACTIVE));
        }

        for (String field : List.of("DEBUG_LAMP", "DEBUG_TECH_LAMP")) {
            DebugLightBlock light = (DebugLightBlock) holder(field).get();
            assertEquals(Direction.NORTH,
                    light.defaultBlockState().getValue(DebugLightBlock.FACING));
            assertEquals(15, light.defaultBlockState().getLightEmission());
        }

        EmergencyLightBlock emergency = (EmergencyLightBlock) holder("EMERGENCY_LIGHT").get();
        assertEquals(Direction.NORTH,
                emergency.defaultBlockState().getValue(EmergencyLightBlock.FACING));
        assertEquals(0, emergency.defaultBlockState().getValue(EmergencyLightBlock.ST_CODE));
    }

    @Test
    void emergencyStationCodeIsConvertedToBoundedBlockState() {
        assertEquals(0, EmergencyLightingService.toBlockStateCode(Integer.MIN_VALUE));
        assertEquals(0, EmergencyLightingService.toBlockStateCode(-10));
        assertEquals(0, EmergencyLightingService.toBlockStateCode(0));
        assertEquals(0, EmergencyLightingService.toBlockStateCode(1));
        assertEquals(1, EmergencyLightingService.toBlockStateCode(2));
        assertEquals(7, EmergencyLightingService.toBlockStateCode(8));
        assertEquals(7, EmergencyLightingService.toBlockStateCode(99));
    }

    @SuppressWarnings("unchecked")
    private static DeferredHolder<Block, Block> holder(String field) {
        try {
            return (DeferredHolder<Block, Block>) Ssc14Blocks.class.getField(field).get(null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }

    private static Expected expected(String field, String path, String legacyClass,
            Class<?> canonicalClass) {
        return new Expected(field, path, legacyClass, canonicalClass);
    }

    private record Expected(String field, String path, String legacyClass,
            Class<?> canonicalClass) { }
}
