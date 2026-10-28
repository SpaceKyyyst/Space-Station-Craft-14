package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MedicalAirlockD1Block extends BaseAirlockD1Block {
    public MedicalAirlockD1Block(BlockBehaviour.Properties properties) { super(properties); }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MedicalAirlockD1BlockEntity(pos, state);
    }
}
