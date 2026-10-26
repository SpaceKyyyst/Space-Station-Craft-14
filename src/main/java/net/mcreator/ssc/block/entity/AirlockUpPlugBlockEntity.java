package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.airlock.ClosedAirlockUpperPlugBlockEntity}. */
@Deprecated(forRemoval = false)
public class AirlockUpPlugBlockEntity
        extends net.ssc14.content.block.airlock.ClosedAirlockUpperPlugBlockEntity {
    public AirlockUpPlugBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
