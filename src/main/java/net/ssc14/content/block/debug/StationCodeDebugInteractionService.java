package net.ssc14.content.block.debug;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.ssc14.common.station.StationCodeService;

/** Migration boundary for the debug block's station-code interaction. */
public final class StationCodeDebugInteractionService {
    private StationCodeDebugInteractionService() { }

    public static void interact(Level level, BlockPos pos, Player player) {
        if (level == null || pos == null || player == null || level.isClientSide()) return;
        level.playSound(null, pos,
                BuiltInRegistries.SOUND_EVENT.getValue(
                        Identifier.parse("block.note_block.xylophone")),
                SoundSource.BLOCKS, 1.0F, 1.0F);
        StationCodeService.advanceOrReset(level, player.isShiftKeyDown());
    }
}
