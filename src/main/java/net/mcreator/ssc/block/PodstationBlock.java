package net.mcreator.ssc.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.block.entity.PodstationBlockEntity;

public class PodstationBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(7.5, 0, 1.5, 15.5, 16, 15.5), box(0.5, 2, 4.5, 7.5, 19, 13.5), box(1.5, 0, 5.5, 6.5, 2, 12.5), box(7.5, 16, 3.5, 15.5, 24, 14.5), box(2, 19, 7, 3, 22, 8), box(5, 19, 7, 6, 23, 8),
			box(2, 19, 10, 3, 22, 11), box(5, 19, 10, 6, 23, 11));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0.5, 0, 0.5, 8.5, 16, 14.5), box(8.5, 2, 2.5, 15.5, 19, 11.5), box(9.5, 0, 3.5, 14.5, 2, 10.5), box(0.5, 16, 1.5, 8.5, 24, 12.5), box(13, 19, 8, 14, 22, 9), box(10, 19, 8, 11, 23, 9),
			box(13, 19, 5, 14, 22, 6), box(10, 19, 5, 11, 23, 6));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0.5, 0, 7.5, 14.5, 16, 15.5), box(2.5, 2, 0.5, 11.5, 19, 7.5), box(3.5, 0, 1.5, 10.5, 2, 6.5), box(1.5, 16, 7.5, 12.5, 24, 15.5), box(8, 19, 2, 9, 22, 3), box(8, 19, 5, 9, 23, 6),
			box(5, 19, 2, 6, 22, 3), box(5, 19, 5, 6, 23, 6));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(1.5, 0, 0.5, 15.5, 16, 8.5), box(4.5, 2, 8.5, 13.5, 19, 15.5), box(5.5, 0, 9.5, 12.5, 2, 14.5), box(3.5, 16, 0.5, 14.5, 24, 8.5), box(7, 19, 13, 8, 22, 14), box(7, 19, 10, 8, 23, 11),
			box(10, 19, 13, 11, 22, 14), box(10, 19, 10, 11, 23, 11));

	public PodstationBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.ANVIL).strength(50f, 20f).lightLevel(s -> 3).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PodstationBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState blockstate, ServerLevel world, BlockPos blockpos, boolean flag) {
		Containers.updateNeighboursAfterDestroy(blockstate, world, blockpos);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof PodstationBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}