package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.mcreator.ssc.init.Ssc14ModBlockEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.ssc14.common.network.NetworkTrigger;
import net.ssc14.content.block.control.NetworkButtonBlock;
import net.ssc14.content.block.control.NetworkButtonBlockEntity;
import net.ssc14.content.block.control.NetworkButtonInteractionService;
import net.ssc14.content.block.control.Ssc14ControlBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksNetworkButtonCompatibilityTest {
    @Test
    void canonicalRegistriesOwnButtonIdsAndLegacyHoldersAliasThem() {
        assertEquals("ssc_14:button", Ssc14Blocks.BUTTON.getId().toString());
        assertEquals("ssc_14:button", Ssc14ControlBlockEntities.BUTTON.getId().toString());
        assertSame(Ssc14Blocks.BUTTON, Ssc14ModBlocks.BUTTON);
        assertSame(Ssc14ControlBlockEntities.BUTTON, Ssc14ModBlockEntities.BUTTON);
    }

    @Test
    void legacyButtonTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.ButtonBlock");
        assertTrue(NetworkButtonBlock.class.isAssignableFrom(legacyBlock));
        assertTrue(NetworkTrigger.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(NetworkButtonBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.ButtonBlockEntity")));
    }

    @Test
    void buttonKeepsStateGeometryTransformsAndTriggerContract() {
        NetworkButtonBlock block = (NetworkButtonBlock) Ssc14Blocks.BUTTON.get();
        var north = block.defaultBlockState();
        assertEquals(Direction.NORTH, north.getValue(NetworkButtonBlock.FACING));
        assertEquals(false, north.getValue(NetworkButtonBlock.ACTIVE));
        assertEquals(10.0F, block.defaultDestroyTime());
        assertEquals(5.0F, block.getExplosionResistance());
        assertTrue(block instanceof NetworkTrigger);
        NetworkTrigger trigger = block;
        assertEquals(List.of("activate"), trigger.getAvailableTriggers());
        assertEquals("При нажатии", trigger.getTriggerName("activate"));
        assertEquals("other", trigger.getTriggerName("other"));
        assertEquals(Direction.EAST,
                block.rotate(north, Rotation.CLOCKWISE_90)
                        .getValue(NetworkButtonBlock.FACING));
        assertEquals(Direction.SOUTH,
                block.mirror(north, Mirror.LEFT_RIGHT)
                        .getValue(NetworkButtonBlock.FACING));
        assertBounds(block, Direction.NORTH, 6, 10, 6, 10, 14, 16);
        assertBounds(block, Direction.SOUTH, 6, 10, 6, 10, 0, 2);
        assertBounds(block, Direction.EAST, 0, 2, 6, 10, 6, 10);
        assertBounds(block, Direction.WEST, 14, 16, 6, 10, 6, 10);
        assertBounds(block, Direction.UP, 6, 10, 0, 2, 6, 10);
        assertBounds(block, Direction.DOWN, 6, 10, 14, 16, 6, 10);
    }

    private static void assertBounds(NetworkButtonBlock block, Direction direction,
            double minX, double maxX, double minY, double maxY,
            double minZ, double maxZ) {
        var shape = block.defaultBlockState().setValue(NetworkButtonBlock.FACING, direction)
                .getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
        assertEquals(minX / 16.0, shape.min(Direction.Axis.X), 0.00001);
        assertEquals(maxX / 16.0, shape.max(Direction.Axis.X), 0.00001);
        assertEquals(minY / 16.0, shape.min(Direction.Axis.Y), 0.00001);
        assertEquals(maxY / 16.0, shape.max(Direction.Axis.Y), 0.00001);
        assertEquals(minZ / 16.0, shape.min(Direction.Axis.Z), 0.00001);
        assertEquals(maxZ / 16.0, shape.max(Direction.Axis.Z), 0.00001);
    }

}
