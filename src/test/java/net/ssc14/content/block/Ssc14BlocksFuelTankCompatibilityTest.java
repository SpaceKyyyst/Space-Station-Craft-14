package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.fuel.FuelTankBlock;
import net.ssc14.content.block.fuel.FuelTankBlockEntity;
import net.ssc14.content.block.fuel.Ssc14FuelBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksFuelTankCompatibilityTest {
    @Test
    void canonicalRegistryOwnsFuelTankIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:fuel_tank", Ssc14Blocks.FUEL_TANK.getId().toString());
        assertSame(Ssc14Blocks.FUEL_TANK, Ssc14ModBlocks.FUEL_TANK);
    }

    @Test
    void legacyFuelTankTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.FuelTankBlock");
        assertTrue(FuelTankBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(FuelTankBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.FuelTankBlockEntity")));
    }

    @Test
    void canonicalFuelTankKeepsDirectionalAndPhysicalDefaults() {
        FuelTankBlock tank = (FuelTankBlock) Ssc14Blocks.FUEL_TANK.get();
        assertEquals(Direction.NORTH, tank.defaultBlockState().getValue(FuelTankBlock.FACING));
        assertEquals(10.0F, tank.defaultDestroyTime());
        assertEquals(0, tank.getLightDampening(tank.defaultBlockState()));
    }
}
