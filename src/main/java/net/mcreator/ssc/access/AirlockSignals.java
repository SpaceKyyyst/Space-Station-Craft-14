package net.mcreator.ssc.access;

import net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure;
import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

/** Physical redstone adapter: left=open, right=close, rear=toggle, below=hold, front=bolts. */
public final class AirlockSignals {
    public static final String HOLD = "_ssc14_signal_hold";
    private static final String INPUTS = "_ssc14_signal_inputs";
    private AirlockSignals() { }

    public static void changed(ServerLevel level, BlockPos pos) {
        if (!level.hasChunkAt(pos)) return;
        var door = level.getBlockEntity(pos);
        if (door == null || !AirlockPower.isDoor(door.getBlockState())) return;
        var state = door.getBlockState();
        var facing = state.getValue(HorizontalDirectionalBlock.FACING);
        Direction[] sides = {facing.getCounterClockWise(), facing.getClockWise(), facing.getOpposite(), Direction.DOWN, facing};
        int inputs = 0;
        for (int i = 0; i < sides.length; i++) {
            var source = pos.relative(sides[i]);
            if (level.hasChunkAt(source) && level.getSignal(source, sides[i]) > 0) inputs |= 1 << i;
        }
        var data = door.getPersistentData();
        int old = data.getIntOr(INPUTS, 0);
        if (old == inputs) return;
        // Store before state updates notify neighbors, avoiding recursive signal processing.
        data.putInt(INPUTS, inputs);
        door.setChanged();
        int rising = inputs & ~old;
        if ((rising & 8) != 0) {
            data.putBoolean(HOLD, true);
            BaseAirlockOpenCloseProcedure.refreshAutoClose(level, pos);
        }
        if (((old ^ inputs) & 16) != 0 && data.getBooleanOr("powered", false)) {
            boolean bolted = (inputs & 16) != 0;
            if (bolted || !data.getBooleanOr("_ssc14_bolt_wire_cut", false)) {
                data.putBoolean("bolted", bolted);
                AirlockEventLog.append(door, bolted ? "SIGNAL_BOLTS_DOWN" : "SIGNAL_BOLTS_UP");
                BaseAirlockD1PutProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
                BaseAirlockOpenCloseProcedure.refreshAutoClose(level, pos);
            }
        }
        // Conflicting edges resolve deterministically: close takes priority over open/toggle.
        if ((rising & 2) != 0) BaseAirlockOpenCloseProcedure.requestSignal(level, pos, false, false);
        else if ((rising & 1) != 0) BaseAirlockOpenCloseProcedure.requestSignal(level, pos, true, false);
        else if ((rising & 4) != 0) BaseAirlockOpenCloseProcedure.requestSignal(level, pos, false, true);
    }
}
