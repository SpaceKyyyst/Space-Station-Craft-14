package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical communication content type. */
@Deprecated(forRemoval = false)
public class CommunicationConsoleBlockEntity
        extends net.ssc14.content.block.communication.CommunicationConsoleBlockEntity {
    public CommunicationConsoleBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
