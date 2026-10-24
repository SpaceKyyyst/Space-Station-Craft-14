package net.ssc14.content.block.lighting;

import java.util.Objects;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/** Synchronizes emergency-light state through a legacy-neutral station-code port. */
public final class EmergencyLightingService {
    private static final int MIN_BLOCK_CODE = 0;
    private static final int MAX_BLOCK_CODE = 7;
    private static volatile ToIntFunction<LevelAccessor> stationCodeProvider = level -> 0;

    private EmergencyLightingService() { }

    public static void install(ToIntFunction<LevelAccessor> provider) {
        stationCodeProvider = Objects.requireNonNull(provider, "provider");
    }

    public static int toBlockStateCode(int stationCode) {
        long zeroBasedCode = (long) stationCode - 1L;
        return (int) Math.clamp(zeroBasedCode, MIN_BLOCK_CODE, MAX_BLOCK_CODE);
    }

    public static void synchronize(LevelAccessor level, BlockPos pos, BlockState state) {
        int target = toBlockStateCode(stationCodeProvider.applyAsInt(level));
        if (state.getValue(EmergencyLightBlock.ST_CODE) != target) {
            level.setBlock(pos, state.setValue(EmergencyLightBlock.ST_CODE, target), 3);
        }
    }
}
