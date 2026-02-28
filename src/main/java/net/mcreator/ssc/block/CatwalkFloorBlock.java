package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.CatwalkFloor_BrokeProcedure;

public class CatwalkFloorBlock extends Block {
	private static final VoxelShape SHAPE = Shapes.or(box(0, 12, 0, 16, 16, 16), box(0, 0, 0, 1, 16, 1), box(0, 0, 15, 1, 16, 16), box(15, 0, 0, 16, 16, 1), box(15, 0, 15, 16, 16, 16));

	public CatwalkFloorBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.CHAIN).strength(50f, 2.5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return (SHAPE);
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		CatwalkFloor_BrokeProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}