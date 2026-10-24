package net.ssc14.content.block.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Canonical secure closet with an explicit lock state. */
public class SecureClosetBlock extends StationClosetBlock {
    public static final BooleanProperty BLOCKED = BooleanProperty.create("blocked");

    public SecureClosetBlock(BlockBehaviour.Properties properties) {
        super(properties, (pos, state) -> new ClosetBlockEntity(
                "closet_secure", "Closet Secure", pos, state), 40.0F, 15.0F);
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
        SecureClosetInteractionService.interact(level, pos, state, player);
    }
}
