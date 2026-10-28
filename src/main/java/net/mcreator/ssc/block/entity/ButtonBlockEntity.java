package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.control.NetworkButtonBlockEntity;

/** @deprecated canonical implementation lives in {@code net.ssc14}. */
@Deprecated
public class ButtonBlockEntity extends NetworkButtonBlockEntity {
    public ButtonBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
