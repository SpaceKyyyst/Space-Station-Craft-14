package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class BrokenGrilleBlock extends Block {
	public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.join(box(0, 0, 11, 16, 16, 16), box(2, 2, 0, 14, 14, 16), BooleanOp.ONLY_FIRST);
	private static final VoxelShape SHAPE_SOUTH = Shapes.join(box(0, 0, 0, 16, 16, 5), box(2, 2, 0, 14, 14, 16), BooleanOp.ONLY_FIRST);
	private static final VoxelShape SHAPE_EAST = Shapes.join(box(0, 0, 0, 5, 16, 16), box(0, 2, 2, 16, 14, 14), BooleanOp.ONLY_FIRST);
	private static final VoxelShape SHAPE_WEST = Shapes.join(box(11, 0, 0, 16, 16, 16), box(0, 2, 2, 16, 14, 14), BooleanOp.ONLY_FIRST);
	private static final VoxelShape SHAPE_UP = Shapes.join(box(0, 0, 0, 16, 5, 16), box(2, 0, 2, 14, 16, 14), BooleanOp.ONLY_FIRST);
	private static final VoxelShape SHAPE_DOWN = Shapes.join(box(0, 11, 0, 16, 16, 16), box(2, 0, 2, 14, 16, 14), BooleanOp.ONLY_FIRST);

	public BrokenGrilleBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.CHAIN).strength(2f).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
		return (switch (state.getValue(FACING)) {
			case NORTH -> SHAPE_NORTH;
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			case UP -> SHAPE_UP;
			case DOWN -> SHAPE_DOWN;
			default -> SHAPE_NORTH;
		});
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}