package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.communication.CommunicationConsoleBlock;
import net.ssc14.content.block.communication.CommunicationConsoleBlockEntity;
import net.ssc14.content.block.communication.CommunicationConsoleIntegrationService;
import net.ssc14.content.block.communication.Ssc14CommunicationBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksCommunicationConsoleCompatibilityTest {
    @Test
    void canonicalRegistriesOwnConsoleIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:communication_console",
                Ssc14Blocks.COMMUNICATION_CONSOLE.getId().toString());
        assertEquals("ssc_14:communication_console",
                Ssc14CommunicationBlockEntities.COMMUNICATION_CONSOLE.getId().toString());
        assertSame(Ssc14Blocks.COMMUNICATION_CONSOLE, Ssc14ModBlocks.COMMUNICATION_CONSOLE);
        assertSame(Ssc14CommunicationBlockEntities.COMMUNICATION_CONSOLE,
                Ssc14CommunicationBlockEntities.COMMUNICATION_CONSOLE);
    }

    @Test
    void legacyConsoleTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.CommunicationConsoleBlock");
        assertTrue(CommunicationConsoleBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.CommunicationConsoleBlockEntity");
        assertTrue(CommunicationConsoleBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalConsoleKeepsStatePhysicalAndInventoryDefaults() {
        CommunicationConsoleBlock block =
                (CommunicationConsoleBlock) Ssc14Blocks.COMMUNICATION_CONSOLE.get();
        assertEquals(Direction.NORTH,
                block.defaultBlockState().getValue(CommunicationConsoleBlock.FACING));
        assertEquals(20.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
        assertEquals(1, CommunicationConsoleBlockEntity.SLOT_COUNT);
    }

    @Test
    void integrationServiceRoutesUseAndMenuCreationExactlyOnce() {
        int[] calls = {0, 0};
        CommunicationConsoleIntegrationService.install(
                new CommunicationConsoleIntegrationService.Adapter() {
                    @Override
                    public void use(net.minecraft.world.level.Level level, BlockPos pos,
                            net.minecraft.world.entity.player.Player player) {
                        calls[0]++;
                    }

                    @Override
                    public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
                            int id, net.minecraft.world.entity.player.Inventory inventory,
                            BlockPos pos) {
                        calls[1]++;
                        return null;
                    }
                });

        CommunicationConsoleIntegrationService.use(null, BlockPos.ZERO, null);
        CommunicationConsoleIntegrationService.createMenu(0, null, BlockPos.ZERO);

        assertEquals(1, calls[0]);
        assertEquals(1, calls[1]);
    }
}
