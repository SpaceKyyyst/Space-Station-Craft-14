package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class ComputerBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ComputerBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.ANVIL).strength(20f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				default -> Shapes.or(box(2, 0, 0, 14, 18, 4), box(1, 0, 5, 15, 13, 12), box(1, 13, 3, 15, 16, 15), box(0.5, 13.77111, 1, 15.5, 24.77111, 8.60717));
				case NORTH -> Shapes.or(box(2, 0, 12, 14, 18, 16), box(1, 0, 4, 15, 13, 11), box(1, 13, 1, 15, 16, 13), box(0.5, 13.77111, 7.39283, 15.5, 24.77111, 15));
				case EAST -> Shapes.or(box(0, 0, 2, 4, 18, 14), box(5, 0, 1, 12, 13, 15), box(3, 13, 1, 15, 16, 15), box(1, 13.77111, 0.5, 8.60717, 24.77111, 15.5));
				case WEST -> Shapes.or(box(12, 0, 2, 16, 18, 14), box(4, 0, 1, 11, 13, 15), box(1, 13, 1, 13, 16, 15), box(7.39283, 13.77111, 0.5, 15, 24.77111, 15.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}