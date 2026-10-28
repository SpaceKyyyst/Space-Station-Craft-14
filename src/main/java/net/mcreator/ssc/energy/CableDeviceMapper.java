package net.mcreator.ssc.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.energy.EnergyNetwork;

/** @deprecated Use {@link net.ssc14.content.energy.CableDeviceMapper}. */
@Deprecated
public final class CableDeviceMapper {
    private CableDeviceMapper() { }

    public static void attach(LevelAccessor level, EnergyNetwork network, BlockPos cablePos) {
        net.ssc14.content.energy.CableDeviceMapper.attach(level, network, cablePos);
    }
}
