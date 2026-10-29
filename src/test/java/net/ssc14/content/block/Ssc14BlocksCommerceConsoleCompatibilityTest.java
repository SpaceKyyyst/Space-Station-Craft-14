package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlockEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.ssc14.content.block.commerce.LegacyCommerceConsoleIntegrationAdapter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.ssc14.content.block.commerce.CommerceConsoleBlock;
import net.ssc14.content.block.commerce.CommerceConsoleIntegrationService;
import net.ssc14.content.block.commerce.OrderConsoleBlock;
import net.ssc14.content.block.commerce.OrderConsoleBlockEntity;
import net.ssc14.content.block.commerce.SaleConsoleBlock;
import net.ssc14.content.block.commerce.SaleConsoleBlockEntity;
import net.ssc14.content.block.commerce.Ssc14CommerceBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksCommerceConsoleCompatibilityTest {
    @Test
    void canonicalRegistriesOwnConsoleIdsAndLegacyHoldersAliasThem() {
        assertEquals("ssc_14:order_console", Ssc14Blocks.ORDER_CONSOLE.getId().toString());
        assertEquals("ssc_14:sale_console", Ssc14Blocks.SALE_CONSOLE.getId().toString());
        assertEquals("ssc_14:order_console",
                Ssc14CommerceBlockEntities.ORDER_CONSOLE.getId().toString());
        assertEquals("ssc_14:sale_console",
                Ssc14CommerceBlockEntities.SALE_CONSOLE.getId().toString());
        assertSame(Ssc14Blocks.ORDER_CONSOLE, Ssc14ModBlocks.ORDER_CONSOLE);
        assertSame(Ssc14Blocks.SALE_CONSOLE, Ssc14ModBlocks.SALE_CONSOLE);
        assertSame(Ssc14CommerceBlockEntities.ORDER_CONSOLE,
                Ssc14ModBlockEntities.ORDER_CONSOLE);
        assertSame(Ssc14CommerceBlockEntities.SALE_CONSOLE,
                Ssc14ModBlockEntities.SALE_CONSOLE);
    }

    @Test
    void legacyConsoleTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyOrder = Class.forName("net.mcreator.ssc.block.OrderConsoleBlock");
        assertTrue(OrderConsoleBlock.class.isAssignableFrom(legacyOrder));
        legacyOrder.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacySale = Class.forName("net.mcreator.ssc.block.SaleConsoleBlock");
        assertTrue(SaleConsoleBlock.class.isAssignableFrom(legacySale));
        legacySale.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(OrderConsoleBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.OrderConsoleBlockEntity")));
        assertTrue(SaleConsoleBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.SaleConsoleBlockEntity")));
    }

    @Test
    void canonicalConsolesKeepPhysicalAndInventoryDefaults() {
        CommerceConsoleBlock order = (CommerceConsoleBlock) Ssc14Blocks.ORDER_CONSOLE.get();
        CommerceConsoleBlock sale = (CommerceConsoleBlock) Ssc14Blocks.SALE_CONSOLE.get();
        assertEquals(Direction.NORTH,
                order.defaultBlockState().getValue(CommerceConsoleBlock.FACING));
        assertEquals(Direction.NORTH,
                sale.defaultBlockState().getValue(CommerceConsoleBlock.FACING));
        assertEquals(20.0F, order.defaultDestroyTime());
        assertEquals(10.0F, order.getExplosionResistance());
        assertEquals(20.0F, sale.defaultDestroyTime());
        assertEquals(10.0F, sale.getExplosionResistance());
        assertEquals(21, OrderConsoleBlockEntity.SLOT_COUNT);
        assertEquals(64, OrderConsoleBlockEntity.MAX_STACK_SIZE);
        assertEquals(0, SaleConsoleBlockEntity.SLOT_COUNT);
        assertEquals(1, SaleConsoleBlockEntity.MAX_STACK_SIZE);
    }

    @Test
    void consolesKeepRotationAndMirrorRules() {
        CommerceConsoleBlock block = (CommerceConsoleBlock) Ssc14Blocks.ORDER_CONSOLE.get();
        var north = block.defaultBlockState().setValue(
                CommerceConsoleBlock.FACING, Direction.NORTH);
        var east = north.setValue(CommerceConsoleBlock.FACING, Direction.EAST);
        assertEquals(Direction.EAST,
                block.rotate(north, Rotation.CLOCKWISE_90).getValue(CommerceConsoleBlock.FACING));
        assertEquals(Direction.SOUTH,
                block.mirror(north, Mirror.LEFT_RIGHT).getValue(CommerceConsoleBlock.FACING));
        assertEquals(Direction.WEST,
                block.mirror(east, Mirror.FRONT_BACK).getValue(CommerceConsoleBlock.FACING));
    }

    @Test
    void consoleShapesKeepDirectionalBounds() {
        CommerceConsoleBlock order = (CommerceConsoleBlock) Ssc14Blocks.ORDER_CONSOLE.get();
        CommerceConsoleBlock sale = (CommerceConsoleBlock) Ssc14Blocks.SALE_CONSOLE.get();
        assertBounds(order, Direction.NORTH,
                0.5 / 16.0, 15.5 / 16.0, 1.0 / 16.0, 1.0);
        assertBounds(order, Direction.SOUTH,
                0.5 / 16.0, 15.5 / 16.0, 0.0, 15.0 / 16.0);
        assertBounds(order, Direction.EAST,
                0.0, 15.0 / 16.0, 0.5 / 16.0, 15.5 / 16.0);
        assertBounds(order, Direction.WEST,
                1.0 / 16.0, 1.0, 0.5 / 16.0, 15.5 / 16.0);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            var orderShape = order.defaultBlockState()
                    .setValue(CommerceConsoleBlock.FACING, direction)
                    .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
            var saleShape = sale.defaultBlockState()
                    .setValue(CommerceConsoleBlock.FACING, direction)
                    .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
            for (Direction.Axis axis : Direction.Axis.values()) {
                assertEquals(orderShape.min(axis), saleShape.min(axis), 0.00001);
                assertEquals(orderShape.max(axis), saleShape.max(axis), 0.00001);
            }
        }
    }

    private static void assertBounds(CommerceConsoleBlock block, Direction direction,
            double minX, double maxX, double minZ, double maxZ) {
        var shape = block.defaultBlockState().setValue(CommerceConsoleBlock.FACING, direction)
                .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
        assertEquals(minX, shape.min(Direction.Axis.X), 0.00001);
        assertEquals(maxX, shape.max(Direction.Axis.X), 0.00001);
        assertEquals(0.0, shape.min(Direction.Axis.Y), 0.00001);
        assertEquals(24.77111 / 16.0, shape.max(Direction.Axis.Y), 0.00001);
        assertEquals(minZ, shape.min(Direction.Axis.Z), 0.00001);
        assertEquals(maxZ, shape.max(Direction.Axis.Z), 0.00001);
    }

    @Test
    void integrationServiceRoutesEachConsoleKindExactlyOnce() {
        int[] uses = {0};
        int[] menus = {0};
        CommerceConsoleIntegrationService.ConsoleKind[] lastKind = {null};
        CommerceConsoleIntegrationService.Adapter previous =
                CommerceConsoleIntegrationService.install(
                        new CommerceConsoleIntegrationService.Adapter() {
                    @Override
                    public void use(CommerceConsoleIntegrationService.ConsoleKind kind,
                            net.minecraft.world.level.Level level, BlockPos pos,
                            net.minecraft.world.entity.player.Player player) {
                        uses[0]++;
                        lastKind[0] = kind;
                    }

                    @Override
                    public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
                            CommerceConsoleIntegrationService.ConsoleKind kind, int id,
                            net.minecraft.world.entity.player.Inventory inventory, BlockPos pos) {
                        menus[0]++;
                        lastKind[0] = kind;
                        return null;
                    }
                        });
        try {
            CommerceConsoleIntegrationService.use(
                    CommerceConsoleIntegrationService.ConsoleKind.ORDER,
                    null, BlockPos.ZERO, null);
            assertEquals(1, uses[0]);
            assertSame(CommerceConsoleIntegrationService.ConsoleKind.ORDER, lastKind[0]);
            CommerceConsoleIntegrationService.createMenu(
                    CommerceConsoleIntegrationService.ConsoleKind.SALE,
                    0, null, BlockPos.ZERO);
            assertEquals(1, menus[0]);
            assertSame(CommerceConsoleIntegrationService.ConsoleKind.SALE, lastKind[0]);
        } finally {
            CommerceConsoleIntegrationService.install(previous);
        }
    }

}
