package net.mcreator.ssc.block;

import net.ssc14.content.network.NetworkManager;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Server-side shutter animation shared by player interaction and signal actions. */
public final class ShutterController {
    private static final int ANIMATION_TICKS = 5;
    private ShutterController() { }

    public static void setOpen(LevelAccessor accessor, BlockPos position, boolean open) {
        if (!(accessor instanceof ServerLevel level)) return;
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
        return state.getBlock() instanceof net.ssc14.content.block.airlock.ShuttersBlock
                && state.getValue(net.ssc14.content.block.airlock.ShuttersBlock.OPEN) != open;
    }

    private static void animate(ServerLevel level, BlockPos pos, boolean open) {
        if (!needsChange(level, pos, open)) return;
        var state = level.getBlockState(pos);
        if (state.getValue(net.ssc14.content.block.airlock.ShuttersBlock.OPENING)) return;
        level.setBlock(pos, state.setValue(
                net.ssc14.content.block.airlock.ShuttersBlock.OPENING, true), 3);
        level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.getValue(
                Identifier.parse("ssc_14:machines_shutter")), SoundSource.BLOCKS, 1, 1);
        Ssc14Mod.queueServerWork(level, ANIMATION_TICKS, () -> {
            if (!needsChange(level, pos, open)) return;
            var current = level.getBlockState(pos);
            if (!current.getValue(net.ssc14.content.block.airlock.ShuttersBlock.OPENING)) return;
            level.setBlock(pos, current.setValue(
                    net.ssc14.content.block.airlock.ShuttersBlock.OPENING, false).setValue(
                            net.ssc14.content.block.airlock.ShuttersBlock.OPEN, open), 3);
            NetworkManager.trigger(level, pos, "toggle");
            Ssc14Mod.queueServerWork(level, ANIMATION_TICKS, () -> animate(level, open ? pos.above() : pos.below(), open)).orThrow();
        }).orThrow();
    }
}
