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

public class WhiteStaircaseBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 2, -1, 16, 4, 4), box(0, 6, 3, 16, 8, 8), box(0, 10, 7, 16, 12, 12), box(0, 14, 11, 16, 16, 16), box(0, 0, 0, 16, 2, 4), box(0, 0, 4, 16, 6, 8), box(0, 0, 8, 16, 10, 12),
			box(0, 0, 12, 16, 14, 16));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 2, 12, 16, 4, 17), box(0, 6, 8, 16, 8, 13), box(0, 10, 4, 16, 12, 9), box(0, 14, 0, 16, 16, 5), box(0, 0, 12, 16, 2, 16), box(0, 0, 8, 16, 6, 12), box(0, 0, 4, 16, 10, 8),
			box(0, 0, 0, 16, 14, 4));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(12, 2, 0, 17, 4, 16), box(8, 6, 0, 13, 8, 16), box(4, 10, 0, 9, 12, 16), box(0, 14, 0, 5, 16, 16), box(12, 0, 0, 16, 2, 16), box(8, 0, 0, 12, 6, 16), box(4, 0, 0, 8, 10, 16),
			box(0, 0, 0, 4, 14, 16));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(-1, 2, 0, 4, 4, 16), box(3, 6, 0, 8, 8, 16), box(7, 10, 0, 12, 12, 16), box(11, 14, 0, 16, 16, 16), box(0, 0, 0, 4, 2, 16), box(4, 0, 0, 8, 6, 16), box(8, 0, 0, 12, 10, 16),
			box(12, 0, 0, 16, 14, 16));

	public WhiteStaircaseBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.COPPER).strength(100f, 7.5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}