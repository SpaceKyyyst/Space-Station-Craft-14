package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.communication.CommunicationServerLifecycleService;
import net.ssc14.content.block.communication.CommunicationFrequencyStateService;

/** Compatibility facade for the generated communication-server tick hook. */
public final class CommunicationServers_TIC_PR_Procedure {
    private CommunicationServers_TIC_PR_Procedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        if (!(world instanceof net.minecraft.server.level.ServerLevel level)) return;
        BlockPos position = BlockPos.containing(x, y, z);
        CommunicationServerLifecycleService.activate(level, position, blockstate,
                (stateKey, enabled) -> CommunicationFrequencyStateService.set(level, stateKey, enabled));
    }
}
