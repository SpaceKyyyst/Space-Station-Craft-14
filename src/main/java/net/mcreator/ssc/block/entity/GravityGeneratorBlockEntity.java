package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.gravity.GravityGeneratorBlockEntity}. */
@Deprecated(forRemoval = false)
public class GravityGeneratorBlockEntity
        extends net.ssc14.content.block.gravity.GravityGeneratorBlockEntity {
    public GravityGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
