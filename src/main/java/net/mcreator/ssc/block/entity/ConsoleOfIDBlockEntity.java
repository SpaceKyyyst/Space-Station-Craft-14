package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.access.IdConsoleBlockEntity;

/** @deprecated canonical implementation lives in {@code net.ssc14}. */
@Deprecated
public class ConsoleOfIDBlockEntity extends IdConsoleBlockEntity {
    public ConsoleOfIDBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
