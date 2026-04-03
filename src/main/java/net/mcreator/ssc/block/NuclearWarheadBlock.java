package net.mcreator.ssc.block;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.NuclearWarhead_ExpProcedure;

public class NuclearWarheadBlock extends Block {
	public NuclearWarheadBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.ANVIL).strength(-1, 3600000));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos, Player entity, BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		NuclearWarhead_ExpProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}
}