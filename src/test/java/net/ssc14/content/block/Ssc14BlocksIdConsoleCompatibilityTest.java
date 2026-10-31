package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.ssc14.content.block.access.IdConsoleBlock;
import net.ssc14.content.block.access.IdConsoleBlockEntity;
import net.ssc14.content.block.access.IdConsoleIntegrationService;
import net.ssc14.content.block.access.Ssc14AccessBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksIdConsoleCompatibilityTest {
    @Test
    void canonicalRegistriesOwnIdsAndLegacyHoldersAliasThem() {
        assertEquals("ssc_14:console_of_id", Ssc14Blocks.CONSOLE_OF_ID.getId().toString());
        assertEquals("ssc_14:console_of_id",
                Ssc14AccessBlockEntities.CONSOLE_OF_ID.getId().toString());
        assertSame(Ssc14Blocks.CONSOLE_OF_ID, Ssc14ModBlocks.CONSOLE_OF_ID);
        assertSame(Ssc14AccessBlockEntities.CONSOLE_OF_ID,
                Ssc14AccessBlockEntities.CONSOLE_OF_ID);
    }

    @Test
    void legacyTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.ConsoleOfIDBlock");
        assertTrue(IdConsoleBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(IdConsoleBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.ConsoleOfIDBlockEntity")));
    }

    @Test
    void consoleKeepsPhysicalContainerAndTransformContracts() {
        IdConsoleBlock block = (IdConsoleBlock) Ssc14Blocks.CONSOLE_OF_ID.get();
        var north = block.defaultBlockState();
        assertEquals(Direction.NORTH, north.getValue(IdConsoleBlock.FACING));
        assertEquals(20.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
        assertEquals(2, IdConsoleBlockEntity.SLOT_COUNT);
        assertEquals(1, IdConsoleBlockEntity.MAX_STACK_SIZE);
        assertEquals(Direction.EAST,
                block.rotate(north, Rotation.CLOCKWISE_90).getValue(IdConsoleBlock.FACING));
        assertEquals(Direction.SOUTH,
                block.mirror(north, Mirror.LEFT_RIGHT).getValue(IdConsoleBlock.FACING));
        assertBounds(block, Direction.NORTH, 0.5, 15.5, 0, 24.77111, 1, 16);
        assertBounds(block, Direction.SOUTH, 0.5, 15.5, 0, 24.77111, 0, 15);
        assertBounds(block, Direction.EAST, 0, 15, 0, 24.77111, 0.5, 15.5);
        assertBounds(block, Direction.WEST, 1, 16, 0, 24.77111, 0.5, 15.5);
        assertShape(block, Direction.NORTH, Shapes.or(box(2, 0, 12, 14, 18, 16),
                box(1, 0, 4, 15, 13, 11), box(1, 13, 1, 15, 16, 13),
                box(0.5, 13.77111, 7.39283, 15.5, 24.77111, 15)));
        assertShape(block, Direction.EAST, Shapes.or(box(0, 0, 2, 4, 18, 14),
                box(5, 0, 1, 12, 13, 15), box(3, 13, 1, 15, 16, 15),
                box(1, 13.77111, 0.5, 8.60717, 24.77111, 15.5)));
        assertShape(block, Direction.WEST, Shapes.or(box(12, 0, 2, 16, 18, 14),
                box(4, 0, 1, 11, 13, 15), box(1, 13, 1, 13, 16, 15),
                box(7.39283, 13.77111, 0.5, 15, 24.77111, 15.5)));
        assertShape(block, Direction.SOUTH, Shapes.or(box(2, 0, 0, 14, 18, 4),
                box(1, 0, 5, 15, 13, 12), box(1, 13, 3, 15, 16, 15),
                box(0.5, 13.77111, 1, 15.5, 24.77111, 8.60717)));
    }

    private static void assertShape(IdConsoleBlock block, Direction direction,
            VoxelShape expected) {
        VoxelShape actual = block.defaultBlockState().setValue(IdConsoleBlock.FACING, direction)
                .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
        assertTrue(!Shapes.joinIsNotEmpty(actual, expected, BooleanOp.ONLY_FIRST));
        assertTrue(!Shapes.joinIsNotEmpty(expected, actual, BooleanOp.ONLY_FIRST));
    }

    private static VoxelShape box(double minX, double minY, double minZ,
            double maxX, double maxY, double maxZ) {
        return Shapes.box(minX / 16.0, minY / 16.0, minZ / 16.0,
                maxX / 16.0, maxY / 16.0, maxZ / 16.0);
    }

    private static void assertBounds(IdConsoleBlock block, Direction direction,
            double minX, double maxX, double minY, double maxY,
            double minZ, double maxZ) {
        var shape = block.defaultBlockState().setValue(IdConsoleBlock.FACING, direction)
                .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
        assertEquals(minX / 16.0, shape.min(Direction.Axis.X), 0.00001);
        assertEquals(maxX / 16.0, shape.max(Direction.Axis.X), 0.00001);
        assertEquals(minY / 16.0, shape.min(Direction.Axis.Y), 0.00001);
        assertEquals(maxY / 16.0, shape.max(Direction.Axis.Y), 0.00001);
        assertEquals(minZ / 16.0, shape.min(Direction.Axis.Z), 0.00001);
        assertEquals(maxZ / 16.0, shape.max(Direction.Axis.Z), 0.00001);
    }

    @Test
    void integrationServiceRoutesUseAndMenuAndRestoresAdapter() {
        int[] calls = {0, 0};
        IdConsoleIntegrationService.Adapter previous = IdConsoleIntegrationService.install(
                new IdConsoleIntegrationService.Adapter() {
                    @Override public void use(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.entity.player.Player player) {
                        calls[0]++;
                    }
                    @Override public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
                            int id, net.minecraft.world.entity.player.Inventory inventory,
                            BlockPos pos) {
                        calls[1]++;
                        return null;
                    }
                });
        try {
            IdConsoleBlock block = (IdConsoleBlock) Ssc14Blocks.CONSOLE_OF_ID.get();
            InteractionResult result = block.useWithoutItem(block.defaultBlockState(), null,
                    BlockPos.ZERO, null, new BlockHitResult(Vec3.ZERO, Direction.UP,
                            BlockPos.ZERO, false));
            IdConsoleIntegrationService.createMenu(1, null, BlockPos.ZERO);
            assertSame(InteractionResult.SUCCESS, result);
            assertEquals(1, calls[0]);
            assertEquals(1, calls[1]);
        } finally {
            IdConsoleIntegrationService.install(previous);
        }
    }
}
