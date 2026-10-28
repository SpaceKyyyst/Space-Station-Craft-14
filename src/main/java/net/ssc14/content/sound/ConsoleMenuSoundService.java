package net.ssc14.content.sound;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

/** Plays one of the three console-use variants on the correct logical side. */
public final class ConsoleMenuSoundService {
    private ConsoleMenuSoundService() { }

    public static void play(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level)) {
            return;
        }
        var sound = switch (Mth.nextInt(RandomSource.create(), 1, 3)) {
            case 1 -> Ssc14SoundEvents.CONSOLE_USE_1.get();
            case 2 -> Ssc14SoundEvents.CONSOLE_USE_2.get();
            default -> Ssc14SoundEvents.CONSOLE_USE_3.get();
        };
        float pitch = (float) Mth.nextDouble(RandomSource.create(), 0.85D, 1.15D);
        if (level.isClientSide()) {
            level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1.0F, pitch, false);
        } else {
            level.playSound(null, BlockPos.containing(x, y, z), sound,
                    SoundSource.NEUTRAL, 1.0F, pitch);
        }
    }
}
