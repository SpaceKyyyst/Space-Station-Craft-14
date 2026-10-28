package net.ssc14.content.energy;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.ssc.access.AirlockPower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

/** Both supported LV loads draw 20 W; shared consumers receive and pay once per tick. */
public final class LowVoltageTickBatch {
    private final ServerLevel level;
    private final LampTickBatch lamps;
    private final Map<BlockPos, Boolean> doors = new HashMap<>();
    public LowVoltageTickBatch(ServerLevel level) { this.level = level; lamps = new LampTickBatch(level); }
    public boolean track(BlockPos pos) {
        if (doors.containsKey(pos)) return true;
        if (level.isLoaded(pos) && AirlockPower.isDoor(level.getBlockState(pos))) {
            doors.put(pos, false);
            return true;
        }
        return lamps.track(pos);
    }
    public boolean needsPower(BlockPos pos) {
        track(pos);
        return doors.containsKey(pos) ? !doors.get(pos) : lamps.needsPower(pos);
    }
    public void power(BlockPos pos) {
        if (doors.containsKey(pos)) doors.put(pos, true);
        else lamps.power(pos);
    }
    public void apply() {
        lamps.apply();
        doors.forEach((pos, powered) -> { if (level.isLoaded(pos)) AirlockPower.supply(level, pos, powered); });
    }
}
