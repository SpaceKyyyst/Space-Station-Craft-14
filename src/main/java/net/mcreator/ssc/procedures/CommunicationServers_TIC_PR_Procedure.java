package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.communication.CommunicationServerLifecycleService;
import net.mcreator.ssc.network.Ssc14ModVariables;

/** Compatibility facade for the generated communication-server tick hook. */
public final class CommunicationServers_TIC_PR_Procedure {
    private CommunicationServers_TIC_PR_Procedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        if (!(world instanceof net.minecraft.server.level.ServerLevel level)) return;
        BlockPos position = BlockPos.containing(x, y, z);
        CommunicationServerLifecycleService.activate(level, position, blockstate,
                (stateKey, enabled) -> {
                    var state = Ssc14ModVariables.MapVariables.get(level);
                    switch (stateKey) {
                        case "Base_Radio_Frequency" -> state.Base_Radio_Frequency = enabled;
                        case "Command_Radio_Frequency" -> state.Command_Radio_Frequency = enabled;
                        case "Engeneer_Radio_Frequency" -> state.Engeneer_Radio_Frequency = enabled;
                        case "Medical_Radio_Frequency" -> state.Medical_Radio_Frequency = enabled;
                        case "RND_Radio_Frequency" -> state.RND_Radio_Frequency = enabled;
                        case "Sec_Radio_Frequency" -> state.Sec_Radio_Frequency = enabled;
                        case "Serv_Radio_Frequency" -> state.Serv_Radio_Frequency = enabled;
                        case "Cargo_Radio_Frequency" -> state.Cargo_Radio_Frequency = enabled;
                        default -> { return; }
                    }
                    state.markSyncDirty();
                });
    }
}
