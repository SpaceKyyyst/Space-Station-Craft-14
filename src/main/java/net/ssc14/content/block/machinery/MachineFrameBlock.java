package net.ssc14.content.block.machinery;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** Initial machine-frame construction stage. */
public class MachineFrameBlock extends DirectionalMachineFrameBlock {
    public MachineFrameBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        MachineFrameAssemblyService.assemble(level, pos, player);
        return InteractionResult.SUCCESS;
    }
}
