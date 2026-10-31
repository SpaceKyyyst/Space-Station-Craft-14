package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.airlock.AirlockDoorIntegrationService;
import net.ssc14.content.block.airlock.BaseAirlockD1Block;
import net.ssc14.content.block.airlock.BaseAirlockD1BlockEntity;
import net.ssc14.content.block.airlock.Ssc14AirlockBlockEntities;
import org.junit.jupiter.api.Test;

class BaseAirlockDoorShapeContractTest {
    @Test
    void canonicalRegistriesOwnBaseDoorAndPreservesCanonicalIds() {
        assertEquals("ssc_14:base_airlock_d_1", Ssc14Blocks.BASE_AIRLOCK_D_1.getId().toString());
        assertEquals("ssc_14:base_airlock_d_1",
                Ssc14AirlockBlockEntities.BASE_AIRLOCK_D_1.getId().toString());
    }

    @Test
    void legacyTypesRemainCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.BaseAirlockD1Block");
        assertTrue(BaseAirlockD1Block.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(BaseAirlockD1BlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.BaseAirlockD1BlockEntity")));
        assertTrue(net.ssc14.content.block.airlock.PoweredAirlockBlockEntity.class
                .isAssignableFrom(Class.forName(
                        "net.mcreator.ssc.block.entity.PoweredAirlockBlockEntity")));
    }

    @Test
    void integrationServiceRoutesDoorBoundaries() {
        int[] calls = new int[5];
        var previous = AirlockDoorIntegrationService.install(
                new AirlockDoorIntegrationService.Adapter() {
                    @Override public void place(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
                        calls[0]++;
                    }
                    @Override public void use(net.minecraft.world.level.Level level,
                            BlockPos pos, net.minecraft.world.level.block.state.BlockState state,
                            net.minecraft.world.entity.player.Player player) { calls[1]++; }
                    @Override public void tick(net.minecraft.server.level.ServerLevel level,
                            BlockPos pos) { calls[2]++; }
                    @Override public void neighborChanged(net.minecraft.world.level.Level level,
                            BlockPos pos) { calls[3]++; }
                    @Override public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
                            int id, net.minecraft.world.entity.player.Inventory inventory,
                            BlockPos pos) { calls[4]++; return null; }
                });
        try {
            AirlockDoorIntegrationService.place(null, BlockPos.ZERO, null);
            AirlockDoorIntegrationService.use(null, BlockPos.ZERO, null, null);
            AirlockDoorIntegrationService.tick(null, BlockPos.ZERO);
            AirlockDoorIntegrationService.neighborChanged(null, BlockPos.ZERO);
            AirlockDoorIntegrationService.createMenu(1, null, BlockPos.ZERO);
            assertEquals(1, calls[0]);
            assertEquals(1, calls[1]);
            assertEquals(1, calls[2]);
            assertEquals(1, calls[3]);
            assertEquals(1, calls[4]);
        } finally {
            AirlockDoorIntegrationService.install(previous);
        }
    }

    @Test
    void everyDirectionalDoorStateMatchesTheFrozenShapeDigest()
            throws NoSuchAlgorithmException {
        BaseAirlockD1Block block = (BaseAirlockD1Block) Ssc14Blocks.BASE_AIRLOCK_D_1.get();
        StringBuilder signature = new StringBuilder();
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (boolean panelOpen : new boolean[] {false, true}) {
                for (int variant = 0; variant <= 25; variant++) {
                    var state = block.defaultBlockState()
                            .setValue(BaseAirlockD1Block.FACING, facing)
                            .setValue(BaseAirlockD1Block.PANEL_OPEN, panelOpen)
                            .setValue(BaseAirlockD1Block.ARL_VARIAT, variant);
                    signature.append(facing).append('|').append(panelOpen).append('|')
                            .append(variant).append('|');
                    state.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO).toAabbs()
                            .forEach(box -> signature.append(box).append(';'));
                    signature.append('\n');
                }
            }
        }
        String digest = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(signature.toString().getBytes(StandardCharsets.UTF_8)));
        assertEquals("5f355098b6686e6292808bbc1eb3a8562427b238af5d284f781a4dfb56c83b88",
                digest);
    }
}
