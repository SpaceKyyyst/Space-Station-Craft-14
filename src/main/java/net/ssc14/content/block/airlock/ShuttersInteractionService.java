package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.item.Ssc14Items;

/** Handles canonical player interaction with shutters. */
public final class ShuttersInteractionService {
    private ShuttersInteractionService() { }

    public static void use(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.isClientSide() || player == null
                || player.getMainHandItem().getItem() != Ssc14Items.DEBUGACTIVATOR.get()) {
            return;
        }
        ShuttersAnimationService.setOpen(level, pos, !state.getValue(ShuttersBlock.OPEN));
    }
}
