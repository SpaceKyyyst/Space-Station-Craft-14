package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlockEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.ssc14.content.block.commerce.Ssc14CommerceBlockEntities;
import net.ssc14.content.block.commerce.VendingMachineBlock;
import net.ssc14.content.block.commerce.VendingMachineBlockEntity;
import net.ssc14.content.block.commerce.VendingMachineIntegrationService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksVendingMachineCompatibilityTest {
    @Test
    void canonicalRegistriesOwnVendingMachineIdsAndLegacyHoldersAliasThem() {
        assertEquals("ssc_14:vending_aut_1", Ssc14Blocks.VENDING_AUT_1.getId().toString());
        assertEquals("ssc_14:vending_aut_1",
                Ssc14CommerceBlockEntities.VENDING_AUT_1.getId().toString());
        assertSame(Ssc14Blocks.VENDING_AUT_1, Ssc14ModBlocks.VENDING_AUT_1);
        assertSame(Ssc14CommerceBlockEntities.VENDING_AUT_1,
                Ssc14ModBlockEntities.VENDING_AUT_1);
    }

    @Test
    void legacyVendingMachineTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.VendingAut1Block");
        assertTrue(VendingMachineBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(VendingMachineBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.VendingAut1BlockEntity")));
    }

    @Test
    void canonicalVendingMachineKeepsPhysicalInventoryAndTransformDefaults() {
        VendingMachineBlock block = (VendingMachineBlock) Ssc14Blocks.VENDING_AUT_1.get();
        var north = block.defaultBlockState();
        assertEquals(Direction.NORTH, north.getValue(VendingMachineBlock.FACING));
        assertEquals(20.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
        assertTrue(block.propagatesSkylightDown(north));
        assertEquals(0, block.getLightDampening(north));
        assertEquals(26, VendingMachineBlockEntity.SLOT_COUNT);
        assertEquals(50, VendingMachineBlockEntity.MAX_STACK_SIZE);
        assertEquals(Direction.EAST,
                block.rotate(north, Rotation.CLOCKWISE_90)
                        .getValue(VendingMachineBlock.FACING));
        assertEquals(Direction.SOUTH,
                block.mirror(north, Mirror.LEFT_RIGHT)
                        .getValue(VendingMachineBlock.FACING));
        assertBounds(block, Direction.NORTH, 1, 15, 4, 15);
        assertBounds(block, Direction.SOUTH, 1, 15, 1, 12);
        assertBounds(block, Direction.EAST, 1, 12, 1, 15);
        assertBounds(block, Direction.WEST, 4, 15, 1, 15);
    }

    private static void assertBounds(VendingMachineBlock block, Direction direction,
            double minX, double maxX, double minZ, double maxZ) {
        var shape = block.defaultBlockState().setValue(VendingMachineBlock.FACING, direction)
                .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
        assertEquals(minX / 16.0, shape.min(Direction.Axis.X), 0.00001);
        assertEquals(maxX / 16.0, shape.max(Direction.Axis.X), 0.00001);
        assertEquals(0.0, shape.min(Direction.Axis.Y), 0.00001);
        assertEquals(1.0, shape.max(Direction.Axis.Y), 0.00001);
        assertEquals(minZ / 16.0, shape.min(Direction.Axis.Z), 0.00001);
        assertEquals(maxZ / 16.0, shape.max(Direction.Axis.Z), 0.00001);
    }

    @Test
    void integrationServiceRoutesEachLifecycleBoundaryAndRestoresAdapter() {
        int[] calls = {0, 0, 0, 0, 0};
        VendingMachineIntegrationService.Adapter previous =
                VendingMachineIntegrationService.install(
                        new VendingMachineIntegrationService.Adapter() {
                    @Override public void onPlaced(
                            net.minecraft.world.level.Level level, BlockPos pos) { calls[0]++; }
                    @Override public void onNeighborChanged(
                            net.minecraft.world.level.Level level, BlockPos pos) { calls[1]++; }
                    @Override public void onAttack(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.entity.player.Player player) {
                        calls[2]++;
                    }
                    @Override public void open(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.entity.player.Player player) {
                        calls[3]++;
                    }
                    @Override public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
                            int id, net.minecraft.world.entity.player.Inventory inventory,
                            BlockPos pos) {
                        calls[4]++;
                        return null;
                    }
                });
        try {
            VendingMachineIntegrationService.onPlaced(null, BlockPos.ZERO);
            VendingMachineIntegrationService.onNeighborChanged(null, BlockPos.ZERO);
            VendingMachineIntegrationService.onAttack(null, BlockPos.ZERO, null);
            VendingMachineIntegrationService.open(null, BlockPos.ZERO, null);
            VendingMachineIntegrationService.createMenu(0, null, BlockPos.ZERO);
            for (int call : calls) assertEquals(1, call);
        } finally {
            VendingMachineIntegrationService.install(previous);
        }
    }
}
