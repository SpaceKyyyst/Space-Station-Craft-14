package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.common.network.NetworkAction;
import net.ssc14.common.network.NetworkTrigger;
import net.ssc14.content.block.airlock.ShuttersBlock;
import net.ssc14.content.block.airlock.ShuttersBlockEntity;
import net.ssc14.content.block.airlock.ShuttersIntegrationService;
import net.ssc14.content.block.airlock.Ssc14AirlockBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksShuttersCompatibilityTest {
    @Test
    void canonicalRegistriesOwnIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:shutters", Ssc14Blocks.SHUTTERS.getId().toString());
        assertEquals("ssc_14:shutters", Ssc14AirlockBlockEntities.SHUTTERS.getId().toString());
    }

    @Test
    void legacyTypesRemainCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.ShuttersBlock");
        assertTrue(ShuttersBlock.class.isAssignableFrom(legacyBlock));
        assertTrue(NetworkAction.class.isAssignableFrom(legacyBlock));
        assertTrue(NetworkTrigger.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(ShuttersBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.ShuttersBlockEntity")));
    }

    @Test
    void shuttersKeepStateGeometryAndNetworkContracts() {
        ShuttersBlock block = (ShuttersBlock) Ssc14Blocks.SHUTTERS.get();
        var state = block.defaultBlockState();
        assertEquals(Direction.NORTH, state.getValue(ShuttersBlock.FACING));
        assertEquals(false, state.getValue(ShuttersBlock.UP));
        assertEquals(false, state.getValue(ShuttersBlock.DOWN));
        assertEquals(false, state.getValue(ShuttersBlock.OPENING));
        assertEquals(false, state.getValue(ShuttersBlock.OPEN));
        assertEquals(30.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
        assertTrue(block instanceof NetworkAction);
        assertTrue(block instanceof NetworkTrigger);
        assertEquals(List.of("open", "close", "toggle"), block.getAvailableActions());
        assertEquals(List.of("toggle"), block.getAvailableTriggers());
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (boolean up : List.of(false, true)) {
                for (boolean down : List.of(false, true)) {
                    for (boolean opening : List.of(false, true)) {
                        for (boolean open : List.of(false, true)) {
                            BlockState variant = state.setValue(ShuttersBlock.FACING, facing)
                                    .setValue(ShuttersBlock.UP, up)
                                    .setValue(ShuttersBlock.DOWN, down)
                                    .setValue(ShuttersBlock.OPENING, opening)
                                    .setValue(ShuttersBlock.OPEN, open);
                            assertEquals(expectedShape(facing, up, down, opening, open).toAabbs(),
                                    block.getShape(variant, EmptyBlockGetter.INSTANCE,
                                            BlockPos.ZERO, CollisionContext.empty()).toAabbs(),
                                    () -> "shape changed for " + variant);
                        }
                    }
                }
            }
        }
    }

    private static VoxelShape expectedShape(Direction facing, boolean up, boolean down,
            boolean opening, boolean open) {
        boolean eastWest = facing == Direction.EAST || facing == Direction.WEST;
        if (opening) {
            VoxelShape lower = eastWest ? box(7, 8, 0, 9, 16, 16)
                    : box(0, 8, 7, 16, 16, 9);
            if (!open && !up && !down) return lower;
            VoxelShape top = eastWest ? box(6, 14, 0, 10, 16, 16)
                    : box(0, 14, 6, 16, 16, 10);
            return Shapes.or(top, lower);
        }
        if (!open) {
            return eastWest ? box(7, 0, 0, 9, 16, 16)
                    : box(0, 0, 7, 16, 16, 9);
        }
        if (!up) {
            return eastWest ? box(6, 14, 0, 10, 16, 16)
                    : box(0, 14, 6, 16, 16, 10);
        }
        return switch (facing) {
            case NORTH -> Shapes.or(box(0.5, 0, 7, 0.501, 16, 9),
                    box(15.5, 0, 7, 15.501, 16, 9));
            case EAST -> Shapes.or(box(7, 0, 0.5, 9, 16, 0.501),
                    box(7, 0, 15.5, 9, 16, 15.501));
            case WEST -> Shapes.or(box(7, 0, 15.499, 9, 16, 15.5),
                    box(7, 0, 0.499, 9, 16, 0.5));
            default -> Shapes.or(box(15.499, 0, 7, 15.5, 16, 9),
                    box(0.499, 0, 7, 0.5, 16, 9));
        };
    }

    private static VoxelShape box(double minX, double minY, double minZ,
            double maxX, double maxY, double maxZ) {
        return Shapes.box(minX / 16.0, minY / 16.0, minZ / 16.0,
                maxX / 16.0, maxY / 16.0, maxZ / 16.0);
    }

    @Test
    void integrationServiceRoutesEveryBoundaryAndRestoresAdapter() {
        int[] calls = new int[3];
        ShuttersIntegrationService.Adapter previous = ShuttersIntegrationService.install(
                new ShuttersIntegrationService.Adapter() {
                    @Override public void update(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
                        calls[0]++;
                    }
                    @Override public void use(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.level.block.state.BlockState state,
                            net.minecraft.world.entity.player.Player player) { calls[1]++; }
                    @Override public void setOpen(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.level.block.state.BlockState state,
                            boolean open) { calls[2] += open ? 1 : 10; }
                });
        try {
            ShuttersIntegrationService.update(null, BlockPos.ZERO, null);
            ShuttersIntegrationService.use(null, BlockPos.ZERO, null, null);
            ShuttersIntegrationService.setOpen(null, BlockPos.ZERO, null, true);
            ShuttersIntegrationService.setOpen(null, BlockPos.ZERO, null, false);
            assertEquals(1, calls[0]);
            assertEquals(1, calls[1]);
            assertEquals(11, calls[2]);
        } finally {
            ShuttersIntegrationService.install(previous);
        }
    }
}
