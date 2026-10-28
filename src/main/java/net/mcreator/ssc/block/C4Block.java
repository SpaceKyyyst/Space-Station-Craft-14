package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mcreator.ssc.block.entity.C4BlockEntity;
import net.ssc14.content.block.explosives.C4ChargeBlock;

/** @deprecated Use {@link C4ChargeBlock}. */
@Deprecated(forRemoval = false)
public class C4Block extends C4ChargeBlock {
    public C4Block(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new C4BlockEntity(pos, state);
    }
}
