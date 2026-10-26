package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.airlock.PoweredAirlockBlockEntity}. */
@Deprecated(forRemoval = false)
public abstract class PoweredAirlockBlockEntity
        extends net.ssc14.content.block.airlock.PoweredAirlockBlockEntity {
    protected PoweredAirlockBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
