package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.communication.CommunicationServerLifecycleService;
import net.ssc14.content.block.communication.CommunicationServersBlock;
import net.ssc14.content.block.communication.CommunicationServersBlockEntity;
import net.ssc14.content.block.communication.Ssc14CommunicationBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksCommunicationServersCompatibilityTest {
    @Test
    void canonicalRegistriesOwnServerIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:communication_servers",
                Ssc14Blocks.COMMUNICATION_SERVERS.getId().toString());
        assertEquals("ssc_14:communication_servers",
                Ssc14CommunicationBlockEntities.COMMUNICATION_SERVERS.getId().toString());
        assertSame(Ssc14CommunicationBlockEntities.COMMUNICATION_SERVERS,
                Ssc14CommunicationBlockEntities.COMMUNICATION_SERVERS);
    }

    @Test
    void legacyServerTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.CommunicationServersBlock");
        assertTrue(CommunicationServersBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.CommunicationServersBlockEntity");
        assertTrue(CommunicationServersBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalServerKeepsStatePhysicalAndInventoryDefaults() {
        CommunicationServersBlock block =
                (CommunicationServersBlock) Ssc14Blocks.COMMUNICATION_SERVERS.get();
        assertEquals(Direction.NORTH,
                block.defaultBlockState().getValue(CommunicationServersBlock.FACING));
        assertEquals(0, block.defaultBlockState().getValue(CommunicationServersBlock.TYPE));
        assertEquals(60.0F, block.defaultDestroyTime());
        assertEquals(30.0F, block.getExplosionResistance());
        assertEquals(1, CommunicationServersBlockEntity.SLOT_COUNT);
    }

    @Test
    void lifecycleServiceRoutesTickAndUseExactlyOnce() {
        int[] calls = {0, 0};
        CommunicationServerLifecycleService.install(
                new CommunicationServerLifecycleService.Adapter() {
                    @Override
                    public void tick(net.minecraft.server.level.ServerLevel level, BlockPos pos,
                            net.minecraft.world.level.block.state.BlockState state) {
                        calls[0]++;
                    }

                    @Override
                    public void use(net.minecraft.world.level.Level level, BlockPos pos,
                            net.minecraft.world.level.block.state.BlockState state,
                            net.minecraft.world.entity.player.Player player) {
                        calls[1]++;
                    }
                });

        CommunicationServerLifecycleService.tick(null, BlockPos.ZERO, null);
        CommunicationServerLifecycleService.use(null, BlockPos.ZERO, null, null);

        assertEquals(1, calls[0]);
        assertEquals(1, calls[1]);
    }
}
