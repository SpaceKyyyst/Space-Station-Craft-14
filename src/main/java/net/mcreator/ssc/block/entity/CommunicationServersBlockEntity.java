package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical communication server block entity. */
@Deprecated(forRemoval = false)
public class CommunicationServersBlockEntity
        extends net.ssc14.content.block.communication.CommunicationServersBlockEntity {
    public CommunicationServersBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
