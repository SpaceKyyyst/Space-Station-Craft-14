package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.CaveFloorRandomGenerationProcedure;

public class CaveFloor1Block extends Block {
	public CaveFloor1Block(BlockBehaviour.Properties properties) {
		super(properties.strength(-1, 3600000));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		CaveFloorRandomGenerationProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}