package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.gravity.GravityGeneratorBlock;
import net.ssc14.content.block.gravity.GravityGeneratorBlockEntity;
import net.ssc14.content.block.gravity.GravityGeneratorLifecycleService;
import net.ssc14.content.block.gravity.Ssc14GravityBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksGravityGeneratorCompatibilityTest {
    @Test
    void canonicalRegistriesOwnGeneratorIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:gravity_generator", Ssc14Blocks.GRAVITY_GENERATOR.getId().toString());
        assertEquals("ssc_14:gravity_generator",
                Ssc14GravityBlockEntities.GRAVITY_GENERATOR.getId().toString());
        assertSame(Ssc14GravityBlockEntities.GRAVITY_GENERATOR,
                Ssc14GravityBlockEntities.GRAVITY_GENERATOR);
    }

    @Test
    void legacyGeneratorTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.GravityGeneratorBlock");
        assertTrue(GravityGeneratorBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.GravityGeneratorBlockEntity");
        assertTrue(GravityGeneratorBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalGeneratorKeepsLegacyStateAndPhysicalDefaults() {
        GravityGeneratorBlock block =
                (GravityGeneratorBlock) Ssc14Blocks.GRAVITY_GENERATOR.get();
        assertEquals(Direction.NORTH,
                block.defaultBlockState().getValue(GravityGeneratorBlock.FACING));
        assertEquals(false,
                block.defaultBlockState().getValue(GravityGeneratorBlock.GRAVITY));
        assertEquals(0,
                block.defaultBlockState().getValue(GravityGeneratorBlock.BLOCKSTATE));
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(3_600_000.0F, block.getExplosionResistance());
    }

    @Test
    void lifecycleServiceRoutesEachBoundaryExactlyOnce() {
        int[] calls = {0, 0, 0, 0};
        GravityGeneratorLifecycleService.install(new GravityGeneratorLifecycleService.Adapter() {
            @Override public boolean canSurvive(net.minecraft.world.level.LevelAccessor level,
                    BlockPos pos) {
                calls[0]++;
                return false;
            }

            @Override public void placed(net.minecraft.world.level.Level level, BlockPos pos,
                    net.minecraft.world.level.block.state.BlockState state) {
                calls[1]++;
            }

            @Override public void tick(net.minecraft.server.level.ServerLevel level, BlockPos pos,
                    net.minecraft.world.level.block.state.BlockState state) {
                calls[2]++;
            }

            @Override public void destroyed() {
                calls[3]++;
            }
        });

        assertEquals(false, GravityGeneratorLifecycleService.canSurvive(null, BlockPos.ZERO));
        GravityGeneratorLifecycleService.placed(null, BlockPos.ZERO, null);
        GravityGeneratorLifecycleService.tick(null, BlockPos.ZERO, null);
        GravityGeneratorLifecycleService.destroyed();

        assertEquals(1, calls[0]);
        assertEquals(1, calls[1]);
        assertEquals(1, calls[2]);
        assertEquals(1, calls[3]);
    }
}
