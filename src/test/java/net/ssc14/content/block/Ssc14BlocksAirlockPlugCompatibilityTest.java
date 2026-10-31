package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.ssc14.content.block.airlock.AirlockUpperPlugBlock;
import net.ssc14.content.block.airlock.AirlockUpperPlugIntegrationService;
import net.ssc14.content.block.airlock.ClosedAirlockUpperPlugBlockEntity;
import net.ssc14.content.block.airlock.OpenAirlockUpperPlugBlockEntity;
import net.ssc14.content.block.airlock.Ssc14AirlockBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksAirlockPlugCompatibilityTest {
    @Test
    void canonicalRegistriesOwnBothIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:airlock_up_plug", Ssc14Blocks.AIRLOCK_UP_PLUG.getId().toString());
        assertEquals("ssc_14:airlock_up_plug_open",
                Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.getId().toString());
        assertEquals("ssc_14:airlock_up_plug",
                Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG.getId().toString());
        assertEquals("ssc_14:airlock_up_plug_open",
                Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG_OPEN.getId().toString());
        assertSame(Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG,
                Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG);
        assertSame(Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG_OPEN,
                Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG_OPEN);
    }

    @Test
    void blocksKeepDirectionalShapeAndCollisionContracts() {
        AirlockUpperPlugBlock closed = (AirlockUpperPlugBlock) Ssc14Blocks.AIRLOCK_UP_PLUG.get();
        AirlockUpperPlugBlock open = (AirlockUpperPlugBlock) Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get();
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            var closedState = closed.defaultBlockState()
                    .setValue(AirlockUpperPlugBlock.FACING, facing);
            var openState = open.defaultBlockState()
                    .setValue(AirlockUpperPlugBlock.FACING, facing);
            var closedShape = closed.getShape(closedState, EmptyBlockGetter.INSTANCE,
                    BlockPos.ZERO, CollisionContext.empty());
            var openShape = open.getShape(openState, EmptyBlockGetter.INSTANCE,
                    BlockPos.ZERO, CollisionContext.empty());
            boolean eastWest = facing.getAxis() == Direction.Axis.X;
            assertEquals(eastWest ? 5.0 / 16.0 : 0.0,
                    closedShape.min(Direction.Axis.X), 0.00001);
            assertEquals(eastWest ? 11.0 / 16.0 : 1.0,
                    closedShape.max(Direction.Axis.X), 0.00001);
            assertEquals(eastWest ? 0.0 : 5.0 / 16.0,
                    closedShape.min(Direction.Axis.Z), 0.00001);
            assertEquals(eastWest ? 1.0 : 11.0 / 16.0,
                    closedShape.max(Direction.Axis.Z), 0.00001);
            assertEquals(2, openShape.toAabbs().size(), "open frame changed for " + facing);
            assertTrue(openState.getCollisionShape(EmptyBlockGetter.INSTANCE,
                    BlockPos.ZERO, CollisionContext.empty()).isEmpty());
        }
        assertEquals(-1.0F, closed.defaultDestroyTime());
        assertEquals(20.0F, closed.getExplosionResistance());
        assertEquals(-1.0F, open.defaultDestroyTime());
        assertEquals(20.0F, open.getExplosionResistance());
    }

    @Test
    void legacyTypesRemainCompatibleWrappers() throws Exception {
        Class<?> closed = Class.forName("net.mcreator.ssc.block.AirlockUpPlugBlock");
        Class<?> open = Class.forName("net.mcreator.ssc.block.AirlockUpPlugOPENBlock");
        assertTrue(AirlockUpperPlugBlock.class.isAssignableFrom(closed));
        assertTrue(AirlockUpperPlugBlock.class.isAssignableFrom(open));
        closed.getConstructor(BlockBehaviour.Properties.class);
        open.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(ClosedAirlockUpperPlugBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.AirlockUpPlugBlockEntity")));
        assertTrue(OpenAirlockUpperPlugBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.AirlockUpPlugOPENBlockEntity")));
    }

    @Test
    void integrationServiceRoutesLifecycleBoundaries() {
        int[] calls = new int[1];
        AirlockUpperPlugIntegrationService.installUseHandler(
                (level, pos, player) -> calls[0]++);
        try {
            AirlockUpperPlugIntegrationService.use(null, BlockPos.ZERO, null);
            assertEquals(1, calls[0]);
        } finally {
            AirlockUpperPlugIntegrationService.installUseHandler(
                    net.ssc14.content.airlock.AirlockUpperPlugInteractionService::execute);
        }
    }
}
