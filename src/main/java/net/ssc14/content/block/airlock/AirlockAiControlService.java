package net.ssc14.content.block.airlock;

import net.mcreator.ssc.access.AirlockEventLog;
import net.mcreator.ssc.access.AirlockMaintenance;
import net.mcreator.ssc.access.AirlockPower;
import net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure;
import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

/** Server integration boundary. The caller must authenticate its AI and authorize this station/door. */
public final class AirlockAiControlService {
    @FunctionalInterface
    public interface Authority { boolean mayControl(ServerLevel level, BlockPos door); }
    public enum Action { OPEN, CLOSE, BOLTS_DOWN, BOLTS_UP, EMERGENCY_ON, EMERGENCY_OFF }
    private AirlockAiControlService() { }

    /** No network packet invokes this API; callers must run on the server thread. */
    public static boolean request(ServerLevel level, BlockPos clicked, Authority authority, Action action) {
        if (!level.getServer().isSameThread()) throw new IllegalStateException("Airlock AI control requires the server thread");
        if (authority == null || action == null || !level.hasChunkAt(clicked)) return false;
        var pos = AirlockMaintenance.root(level, clicked);
        if (!level.hasChunkAt(pos) || !authority.mayControl(level, pos)) return false;
        var door = level.getBlockEntity(pos);
        if (door == null || !AirlockPower.isDoor(door.getBlockState())) return false;
        var data = door.getPersistentData();
        if (!data.getBooleanOr("powered", false) || !data.getBooleanOr("ai_access", false)) return false;
        switch (action) {
            case OPEN, CLOSE -> {
                if (data.getBooleanOr("bolted", false) || data.getBooleanOr("welded", false)) return false;
                BaseAirlockOpenCloseProcedure.requestSignal(level, pos, action == Action.OPEN, false);
            }
            case BOLTS_DOWN -> data.putBoolean("bolted", true);
            case BOLTS_UP -> {
                if (data.getBooleanOr("_ssc14_bolt_wire_cut", false)) return false;
                data.putBoolean("bolted", false);
            }
            case EMERGENCY_ON, EMERGENCY_OFF -> data.putBoolean("emergency_acs", action == Action.EMERGENCY_ON);
        }
        AirlockEventLogService.append(door, "AI_" + action.name());
        door.setChanged();
        BaseAirlockD1PutProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), door.getBlockState());
        BaseAirlockOpenCloseProcedure.refreshAutoClose(level, pos);
        level.sendBlockUpdated(pos, door.getBlockState(), door.getBlockState(), 3);
        return true;
    }
}
