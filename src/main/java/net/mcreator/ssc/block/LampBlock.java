package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.LampBlockEntity;
import net.mcreator.ssc.procedures.InsertingLampProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical lighting implementation from {@code net.ssc14}. */
@Deprecated(forRemoval = false)
public class LampBlock extends net.ssc14.content.block.lighting.EnergyLampBlock {
    public LampBlock(BlockBehaviour.Properties properties) {
        super(properties, LampBlockEntity::new);
    }

    @Override
    protected void interact(Level level, BlockPos pos, BlockState state, Player player) {
        InsertingLampProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), state, player);
    }
}
