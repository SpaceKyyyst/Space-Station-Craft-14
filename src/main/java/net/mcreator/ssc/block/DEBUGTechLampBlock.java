package net.mcreator.ssc.block;

import org.checkerframework.checker.units.qual.s;

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

public class DEBUGTechLampBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(6.5, 10.5, 14, 9.5, 13.5, 16), box(7, 11, 13, 9, 13, 14), box(6, 10, 9, 10, 14, 13), box(6, 10, 9, 10, 14, 13));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(6.5, 10.5, 0, 9.5, 13.5, 2), box(7, 11, 2, 9, 13, 3), box(6, 10, 3, 10, 14, 7), box(6, 10, 3, 10, 14, 7));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 10.5, 6.5, 2, 13.5, 9.5), box(2, 11, 7, 3, 13, 9), box(3, 10, 6, 7, 14, 10), box(3, 10, 6, 7, 14, 10));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(14, 10.5, 6.5, 16, 13.5, 9.5), box(13, 11, 7, 14, 13, 9), box(9, 10, 6, 13, 14, 10), box(9, 10, 6, 13, 14, 10));

	public DEBUGTechLampBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.GLASS).strength(10f).lightLevel(s -> 15).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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