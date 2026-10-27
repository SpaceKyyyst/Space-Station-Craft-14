package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.atmosphere.AtmosphericBlock;
import net.ssc14.content.block.atmosphere.GasProducerO2Block;
import net.ssc14.content.block.atmosphere.GasProducerO2TickService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksGasProducerO2CompatibilityTest {
    @Test
    void canonicalRegistryOwnsGasProducerIdAndLegacyFacadeAliasesHolder() {
        assertEquals("ssc_14:gas_producer_o_2",
                Ssc14Blocks.GAS_PRODUCER_O_2.getId().toString());
        assertSame(Ssc14Blocks.GAS_PRODUCER_O_2, Ssc14ModBlocks.GAS_PRODUCER_O_2);
    }

    @Test
    void legacyAtmosphereTypesRemainCompatibleWithCanonicalContracts() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.GasProducerO2Block");
        assertTrue(GasProducerO2Block.class.isAssignableFrom(legacy));
        assertTrue(AtmosphericBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalGasProducerKeepsPhysicalAndAtmosphereDefaults() {
        GasProducerO2Block block =
                (GasProducerO2Block) Ssc14Blocks.GAS_PRODUCER_O_2.get();
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(3_600_000.0F, block.getExplosionResistance());
        assertEquals(1.0F, block.getPermeability(block.defaultBlockState()));
    }

    @Test
    void tickServiceRoutesProductionExactlyOnce() {
        int[] calls = {0};
        GasProducerO2TickService.install((level, pos) -> calls[0]++);

        GasProducerO2TickService.tick(null, BlockPos.ZERO);

        assertEquals(1, calls[0]);
    }
}
