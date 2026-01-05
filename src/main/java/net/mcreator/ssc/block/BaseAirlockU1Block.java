package net.mcreator.ssc.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.BaseAirlockU1_ChekProcedure;
import net.mcreator.ssc.block.entity.BaseAirlockU1BlockEntity;

import javax.annotation.Nullable;

public class BaseAirlockU1Block extends Block implements EntityBlock {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 5);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_1_NORTH = Shapes.or(box(15, 0, 6, 16, 14, 10), box(9, 0, 7, 15, 14, 9), box(7, -1, 9, 9, 16, 11), box(8, 14, 6, 16, 16, 10), box(0, 2, 6, 7, 16, 10), box(5, -1, 5, 7, 16, 7), box(0, 0, 7, 7, 2, 9));
	private static final VoxelShape SHAPE_1_SOUTH = Shapes.or(box(0, 0, 6, 1, 14, 10), box(1, 0, 7, 7, 14, 9), box(7, -1, 5, 9, 16, 7), box(0, 14, 6, 8, 16, 10), box(9, 2, 6, 16, 16, 10), box(9, -1, 9, 11, 16, 11), box(9, 0, 7, 16, 2, 9));
	private static final VoxelShape SHAPE_1_EAST = Shapes.or(box(6, 0, 15, 10, 14, 16), box(7, 0, 9, 9, 14, 15), box(5, -1, 7, 7, 16, 9), box(6, 14, 8, 10, 16, 16), box(6, 2, 0, 10, 16, 7), box(9, -1, 5, 11, 16, 7), box(7, 0, 0, 9, 2, 7));
	private static final VoxelShape SHAPE_1_WEST = Shapes.or(box(6, 0, 0, 10, 14, 1), box(7, 0, 1, 9, 14, 7), box(9, -1, 7, 11, 16, 9), box(6, 14, 0, 10, 16, 8), box(6, 2, 9, 10, 16, 16), box(5, -1, 9, 7, 16, 11), box(7, 0, 9, 9, 2, 16));
	private static final VoxelShape SHAPE_2_NORTH = Shapes.or(box(10, 0, 7, 16, 14, 9), box(8, -1, 9, 10, 16, 11), box(9, 14, 6, 16, 16, 10), box(0, 2, 6, 5, 16, 10), box(3, -1, 5, 5, 16, 7), box(0, 0, 7, 5, 2, 9));
	private static final VoxelShape SHAPE_2_SOUTH = Shapes.or(box(0, 0, 7, 6, 14, 9), box(6, -1, 5, 8, 16, 7), box(0, 14, 6, 7, 16, 10), box(11, 2, 6, 16, 16, 10), box(11, -1, 9, 13, 16, 11), box(11, 0, 7, 16, 2, 9));
	private static final VoxelShape SHAPE_2_EAST = Shapes.or(box(7, 0, 10, 9, 14, 16), box(5, -1, 8, 7, 16, 10), box(6, 14, 9, 10, 16, 16), box(6, 2, 0, 10, 16, 5), box(9, -1, 3, 11, 16, 5), box(7, 0, 0, 9, 2, 5));
	private static final VoxelShape SHAPE_2_WEST = Shapes.or(box(7, 0, 0, 9, 14, 6), box(9, -1, 6, 11, 16, 8), box(6, 14, 0, 10, 16, 7), box(6, 2, 11, 10, 16, 16), box(5, -1, 11, 7, 16, 13), box(7, 0, 11, 9, 2, 16));
	private static final VoxelShape SHAPE_3_NORTH = Shapes.or(box(12, 0, 7, 16, 14, 9), box(10, -1, 9, 12, 16, 11), box(11, 14, 6, 16, 16, 10), box(0, 2, 6, 3, 16, 10), box(1, -1, 5, 3, 16, 7), box(0, 0, 7, 3, 2, 9));
	private static final VoxelShape SHAPE_3_SOUTH = Shapes.or(box(0, 0, 7, 4, 14, 9), box(4, -1, 5, 6, 16, 7), box(0, 14, 6, 5, 16, 10), box(13, 2, 6, 16, 16, 10), box(13, -1, 9, 15, 16, 11), box(13, 0, 7, 16, 2, 9));
	private static final VoxelShape SHAPE_3_EAST = Shapes.or(box(7, 0, 12, 9, 14, 16), box(5, -1, 10, 7, 16, 12), box(6, 14, 11, 10, 16, 16), box(6, 2, 0, 10, 16, 3), box(9, -1, 1, 11, 16, 3), box(7, 0, 0, 9, 2, 3));
	private static final VoxelShape SHAPE_3_WEST = Shapes.or(box(7, 0, 0, 9, 14, 4), box(9, -1, 4, 11, 16, 6), box(6, 14, 0, 10, 16, 5), box(6, 2, 13, 10, 16, 16), box(5, -1, 13, 7, 16, 15), box(7, 0, 13, 9, 2, 16));
	private static final VoxelShape SHAPE_4_NORTH = Shapes.or(box(14, 0, 7, 16, 14, 9), box(12, -1, 9, 14, 16, 11), box(13, 14, 6, 16, 16, 10), box(0, 2, 6, 1, 16, 10), box(0, -1, 5, 1, 16, 7), box(0, 0, 7, 1, 2, 9));
	private static final VoxelShape SHAPE_4_SOUTH = Shapes.or(box(0, 0, 7, 2, 14, 9), box(2, -1, 5, 4, 16, 7), box(0, 14, 6, 3, 16, 10), box(15, 2, 6, 16, 16, 10), box(15, -1, 9, 16, 16, 11), box(15, 0, 7, 16, 2, 9));
	private static final VoxelShape SHAPE_4_EAST = Shapes.or(box(7, 0, 14, 9, 14, 16), box(5, -1, 12, 7, 16, 14), box(6, 14, 13, 10, 16, 16), box(6, 2, 0, 10, 16, 1), box(9, -1, 0, 11, 16, 1), box(7, 0, 0, 9, 2, 1));
	private static final VoxelShape SHAPE_4_WEST = Shapes.or(box(7, 0, 0, 9, 14, 2), box(9, -1, 2, 11, 16, 4), box(6, 14, 0, 10, 16, 3), box(6, 2, 15, 10, 16, 16), box(5, -1, 15, 7, 16, 16), box(7, 0, 15, 9, 2, 16));
	private static final VoxelShape SHAPE_5_NORTH = Shapes.or(box(15, 0, 7, 16, 14, 9), box(13, -1, 9, 15, 16, 11), box(14, 14, 6, 16, 16, 10));
	private static final VoxelShape SHAPE_5_SOUTH = Shapes.or(box(0, 0, 7, 1, 14, 9), box(1, -1, 5, 3, 16, 7), box(0, 14, 6, 2, 16, 10));
	private static final VoxelShape SHAPE_5_EAST = Shapes.or(box(7, 0, 15, 9, 14, 16), box(5, -1, 13, 7, 16, 15), box(6, 14, 14, 10, 16, 16));
	private static final VoxelShape SHAPE_5_WEST = Shapes.or(box(7, 0, 0, 9, 14, 1), box(9, -1, 1, 11, 16, 3), box(6, 14, 0, 10, 16, 2));
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(14, 0, 6, 16, 14, 10), box(8, 0, 7, 14, 14, 9), box(6, -1, 9, 8, 16, 11), box(7, 14, 6, 16, 16, 10), box(0, 2, 6, 8, 16, 10), box(6, -1, 5, 8, 16, 7), box(0, 0, 7, 8, 2, 9));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 6, 2, 14, 10), box(2, 0, 7, 8, 14, 9), box(8, -1, 5, 10, 16, 7), box(0, 14, 6, 9, 16, 10), box(8, 2, 6, 16, 16, 10), box(8, -1, 9, 10, 16, 11), box(8, 0, 7, 16, 2, 9));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(6, 0, 14, 10, 14, 16), box(7, 0, 8, 9, 14, 14), box(5, -1, 6, 7, 16, 8), box(6, 14, 7, 10, 16, 16), box(6, 2, 0, 10, 16, 8), box(9, -1, 6, 11, 16, 8), box(7, 0, 0, 9, 2, 8));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(6, 0, 0, 10, 14, 2), box(7, 0, 2, 9, 14, 8), box(9, -1, 8, 11, 16, 10), box(6, 14, 0, 10, 16, 9), box(6, 2, 8, 10, 16, 16), box(5, -1, 8, 7, 16, 10), box(7, 0, 8, 9, 2, 16));

	public BaseAirlockU1Block(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.NETHERITE_BLOCK).strength(-1, 3600000).lightLevel(s -> (new Object() {
			public int getLightLevel() {
				if (s.getValue(BLOCKSTATE) == 1)
					return 0;
				if (s.getValue(BLOCKSTATE) == 2)
					return 0;
				if (s.getValue(BLOCKSTATE) == 3)
					return 0;
				if (s.getValue(BLOCKSTATE) == 4)
					return 0;
				if (s.getValue(BLOCKSTATE) == 5)
					return 0;
				return 0;
			}
		}.getLightLevel())).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
		if (state.getValue(BLOCKSTATE) == 1) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_1_NORTH;
				case SOUTH -> SHAPE_1_SOUTH;
				case EAST -> SHAPE_1_EAST;
				case WEST -> SHAPE_1_WEST;
				default -> SHAPE_1_NORTH;
			});
		}
		if (state.getValue(BLOCKSTATE) == 2) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_2_NORTH;
				case SOUTH -> SHAPE_2_SOUTH;
				case EAST -> SHAPE_2_EAST;
				case WEST -> SHAPE_2_WEST;
				default -> SHAPE_2_NORTH;
			});
		}
		if (state.getValue(BLOCKSTATE) == 3) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_3_NORTH;
				case SOUTH -> SHAPE_3_SOUTH;
				case EAST -> SHAPE_3_EAST;
				case WEST -> SHAPE_3_WEST;
				default -> SHAPE_3_NORTH;
			});
		}
		if (state.getValue(BLOCKSTATE) == 4) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_4_NORTH;
				case SOUTH -> SHAPE_4_SOUTH;
				case EAST -> SHAPE_4_EAST;
				case WEST -> SHAPE_4_WEST;
				default -> SHAPE_4_NORTH;
			});
		}
		if (state.getValue(BLOCKSTATE) == 5) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_5_NORTH;
				case SOUTH -> SHAPE_5_SOUTH;
				case EAST -> SHAPE_5_EAST;
				case WEST -> SHAPE_5_WEST;
				default -> SHAPE_5_NORTH;
			});
		}
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
		builder.add(FACING, BLOCKSTATE);
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
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData, Player entity) {
		return ItemStack.EMPTY;
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		BaseAirlockU1_ChekProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		BaseAirlockU1_ChekProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BaseAirlockU1BlockEntity(pos, state);
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
		if (tileentity instanceof BaseAirlockU1BlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}