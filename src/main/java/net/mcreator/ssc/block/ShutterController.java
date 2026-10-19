package net.mcreator.ssc.block;

import net.mcreator.ssc.NetworkManager;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Server-side shutter animation shared by player interaction and signal actions. */
public final class ShutterController {
    private static final int ANIMATION_TICKS = 5;
    private ShutterController() { }

    public static void setOpen(LevelAccessor accessor, BlockPos position, boolean open) {
        if (!(accessor instanceof Level level) || level.isClientSide()) return;
        BlockPos first = position;
        if (!needsChange(level, first, open)) return;
        BlockPos previous = open ? first.below() : first.above();
        while (needsChange(level, previous, open)) {
            first = previous;
            previous = open ? first.below() : first.above();
        }
        animate(level, first, open);
    }

    private static boolean needsChange(Level level, BlockPos pos, boolean open) {
        if (!level.isLoaded(pos)) return false;
        var state = level.getBlockState(pos);
        return state.getBlock() instanceof ShuttersBlock && state.getValue(ShuttersBlock.OPEN) != open;
    }

    private static void animate(Level level, BlockPos pos, boolean open) {
        if (!needsChange(level, pos, open)) return;
        var state = level.getBlockState(pos);
        if (state.getValue(ShuttersBlock.OPENING)) return;
        level.setBlock(pos, state.setValue(ShuttersBlock.OPENING, true), 3);
        level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.getValue(
                Identifier.parse("ssc_14:machines_shutter")), SoundSource.BLOCKS, 1, 1);
        Ssc14Mod.queueServerWork(ANIMATION_TICKS, () -> {
            if (!needsChange(level, pos, open)) return;
            var current = level.getBlockState(pos);
            if (!current.getValue(ShuttersBlock.OPENING)) return;
            level.setBlock(pos, current.setValue(ShuttersBlock.OPENING, false).setValue(ShuttersBlock.OPEN, open), 3);
            NetworkManager.trigger(level, pos, "toggle");
            Ssc14Mod.queueServerWork(ANIMATION_TICKS, () -> animate(level, open ? pos.above() : pos.below(), open));
        });
    }
}
