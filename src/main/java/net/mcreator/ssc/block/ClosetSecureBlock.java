package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetSecureBlockEntity;
import net.mcreator.ssc.procedures.Closet_OpenClose_PrProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ClosetSecureBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public static final BooleanProperty BLOCKED = BooleanProperty.create("blocked");

    public ClosetSecureBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetSecureBlockEntity::new, 40.0F, 15.0F);
        registerDefaultState(defaultBlockState().setValue(BLOCKED, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCKED);
    }

    @Override
    protected boolean isDoorOpen(BlockState state) {
        return state.getValue(OPEN) && !state.getValue(BLOCKED);
    }

    @Override
    protected void interact(Level level, BlockPos pos, BlockState state, Player player) {
        Closet_OpenClose_PrProcedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), state, player);
    }
}
