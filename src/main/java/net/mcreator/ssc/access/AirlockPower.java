package net.mcreator.ssc.access;

import net.mcreator.ssc.procedures.CPlogLampPrProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.access.AirlockDoorState;

/** Network supply is distinct from the two internal power wires. */
public final class AirlockPower {
    private AirlockPower() { }
    public static boolean isDoor(BlockState state) {
        return AirlockDoorState.isDoor(state);
    }
    public static void supply(ServerLevel level, BlockPos pos, boolean powered) {
        var door = level.getBlockEntity(pos);
        if (door == null || !isDoor(door.getBlockState())) return;
        var data = door.getPersistentData();
        if (data.contains("_ssc14_supply_powered") && data.getBooleanOr("_ssc14_supply_powered", false) == powered) return;
        data.putBoolean("_ssc14_supply_powered", powered);
        door.setChanged();
        CPlogLampPrProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), door.getBlockState());
    }
}
