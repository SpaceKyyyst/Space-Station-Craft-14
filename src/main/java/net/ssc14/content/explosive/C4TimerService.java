package net.ssc14.content.explosive;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;
import net.ssc14.content.block.Ssc14Blocks;

/** Owns the server-side C4 countdown and detonation scheduling. */
public final class C4TimerService {
    private static final int TICK_DELAY = 1;
    private static final int PIP_INTERVAL = 20;
    private C4TimerService() { }

    public static void start(ServerLevel level, BlockPos pos) {
        tick(level, pos);
    }

    private static void tick(ServerLevel level, BlockPos pos) {
        var data = level.getBlockEntity(pos) == null ? null : level.getBlockEntity(pos).getPersistentData();
        if (data == null) return;
        if (data.getDoubleOr("c4timer", 0) >= data.getDoubleOr("c4setting", 0) * 20) {
            C4DetonationService.execute(level, pos.getX(), pos.getY(), pos.getZ());
            return;
        }
        if (level.getBlockState(pos).is(Ssc14Blocks.C_4.get())) {
            data.putDouble("c4timer", data.getDoubleOr("c4timer", 0) + 1);
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            if (data.getDoubleOr("c4timer", 0) % PIP_INTERVAL == 0) {
                level.playSound(null, pos,
                        BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:c4_pip")),
                        SoundSource.NEUTRAL, 1, 1);
            }
            ServerWorkScheduler.schedule(level, TICK_DELAY, () -> tick(level, pos));
        } else {
            data.putDouble("c4timer", 0);
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
        }
    }
}
