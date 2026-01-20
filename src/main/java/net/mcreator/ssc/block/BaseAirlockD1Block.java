package net.mcreator.ssc.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.mcreator.ssc.procedures.BaseAirlockD1_PutProcedure;
import net.mcreator.ssc.block.entity.BaseAirlockD1BlockEntity;

import javax.annotation.Nullable;

public class BaseAirlockD1Block extends Block implements EntityBlock {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 9);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty BOLTED = BooleanProperty.create("bolted");
	public static final BooleanProperty EMERGENCY_ACS = BooleanProperty.create("emergency_acs");
	public static final BooleanProperty PANEL_OPEN = BooleanProperty.create("panel_open");
	public static final BooleanProperty DIODS = BooleanProperty.create("diods");
	public static final IntegerProperty ENERGY_CABELS = IntegerProperty.create("energy_cabels", 0, 2);
	public static final BooleanProperty TIMER = BooleanProperty.create("timer");
	private static final VoxelShape SHAPE_1_NORTH = Shapes.or(box(10, 0, 6, 16, 8, 10), box(15, 8, 6, 16, 16, 10), box(9, 8, 7, 15, 16, 9), box(9, 0, 9, 11, 17, 11), box(0, 12, 7, 7, 16, 9), box(0.5, 13.5, 6, 3.5, 16.5, 10), box(0, 0, 6, 8, 12, 10),
			box(7, 0, 5, 9, 17, 7));
	private static final VoxelShape SHAPE_1_SOUTH = Shapes.or(box(0, 0, 6, 6, 8, 10), box(0, 8, 6, 1, 16, 10), box(1, 8, 7, 7, 16, 9), box(5, 0, 5, 7, 17, 7), box(9, 12, 7, 16, 16, 9), box(12.5, 13.5, 6, 15.5, 16.5, 10), box(8, 0, 6, 16, 12, 10),
			box(7, 0, 9, 9, 17, 11));
	private static final VoxelShape SHAPE_1_EAST = Shapes.or(box(6, 0, 10, 10, 8, 16), box(6, 8, 15, 10, 16, 16), box(7, 8, 9, 9, 16, 15), box(5, 0, 9, 7, 17, 11), box(7, 12, 0, 9, 16, 7), box(6, 13.5, 0.5, 10, 16.5, 3.5), box(6, 0, 0, 10, 12, 8),
			box(9, 0, 7, 11, 17, 9));
	private static final VoxelShape SHAPE_1_WEST = Shapes.or(box(6, 0, 0, 10, 8, 6), box(6, 8, 0, 10, 16, 1), box(7, 8, 1, 9, 16, 7), box(9, 0, 5, 11, 17, 7), box(7, 12, 9, 9, 16, 16), box(6, 13.5, 12.5, 10, 16.5, 15.5), box(6, 0, 8, 10, 12, 16),
			box(5, 0, 7, 7, 17, 9));
	private static final VoxelShape SHAPE_2_NORTH = Shapes.or(box(11, 0, 6, 16, 8, 10), box(10, 8, 7, 16, 16, 9), box(10, 0, 9, 12, 17, 11), box(0, 12, 7, 5, 16, 9), box(0, 13.5, 6, 1.5, 16.5, 10), box(0, 0, 6, 6, 12, 10), box(5, 0, 5, 7, 17, 7));
	private static final VoxelShape SHAPE_2_SOUTH = Shapes.or(box(0, 0, 6, 5, 8, 10), box(0, 8, 7, 6, 16, 9), box(4, 0, 5, 6, 17, 7), box(11, 12, 7, 16, 16, 9), box(14.5, 13.5, 6, 16, 16.5, 10), box(10, 0, 6, 16, 12, 10), box(9, 0, 9, 11, 17, 11));
	private static final VoxelShape SHAPE_2_EAST = Shapes.or(box(6, 0, 11, 10, 8, 16), box(7, 8, 10, 9, 16, 16), box(5, 0, 10, 7, 17, 12), box(7, 12, 0, 9, 16, 5), box(6, 13.5, 0, 10, 16.5, 1.5), box(6, 0, 0, 10, 12, 6), box(9, 0, 5, 11, 17, 7));
	private static final VoxelShape SHAPE_2_WEST = Shapes.or(box(6, 0, 0, 10, 8, 5), box(7, 8, 0, 9, 16, 6), box(9, 0, 4, 11, 17, 6), box(7, 12, 11, 9, 16, 16), box(6, 13.5, 14.5, 10, 16.5, 16), box(6, 0, 10, 10, 12, 16), box(5, 0, 9, 7, 17, 11));
	private static final VoxelShape SHAPE_3_NORTH = Shapes.or(box(13, 0, 6, 16, 8, 10), box(12, 8, 7, 16, 16, 9), box(12, 0, 9, 14, 17, 11), box(0, 12, 7, 3, 16, 9), box(0, 0, 6, 4, 12, 10), box(3, 0, 5, 5, 17, 7));
	private static final VoxelShape SHAPE_3_SOUTH = Shapes.or(box(0, 0, 6, 3, 8, 10), box(0, 8, 7, 4, 16, 9), box(2, 0, 5, 4, 17, 7), box(13, 12, 7, 16, 16, 9), box(12, 0, 6, 16, 12, 10), box(11, 0, 9, 13, 17, 11));
	private static final VoxelShape SHAPE_3_EAST = Shapes.or(box(6, 0, 13, 10, 8, 16), box(7, 8, 12, 9, 16, 16), box(5, 0, 12, 7, 17, 14), box(7, 12, 0, 9, 16, 3), box(6, 0, 0, 10, 12, 4), box(9, 0, 3, 11, 17, 5));
	private static final VoxelShape SHAPE_3_WEST = Shapes.or(box(6, 0, 0, 10, 8, 3), box(7, 8, 0, 9, 16, 4), box(9, 0, 2, 11, 17, 4), box(7, 12, 13, 9, 16, 16), box(6, 0, 12, 10, 12, 16), box(5, 0, 11, 7, 17, 13));
	private static final VoxelShape SHAPE_4_NORTH = Shapes.or(box(15, 0, 6, 16, 8, 10), box(14, 8, 7, 16, 16, 9), box(14, 0, 9, 16, 17, 11), box(0, 12, 7, 1, 16, 9), box(0, 0, 6, 2, 12, 10), box(1, 0, 5, 3, 17, 7));
	private static final VoxelShape SHAPE_4_SOUTH = Shapes.or(box(0, 0, 6, 1, 8, 10), box(0, 8, 7, 2, 16, 9), box(0, 0, 5, 2, 17, 7), box(15, 12, 7, 16, 16, 9), box(14, 0, 6, 16, 12, 10), box(13, 0, 9, 15, 17, 11));
	private static final VoxelShape SHAPE_4_EAST = Shapes.or(box(6, 0, 15, 10, 8, 16), box(7, 8, 14, 9, 16, 16), box(5, 0, 14, 7, 17, 16), box(7, 12, 0, 9, 16, 1), box(6, 0, 0, 10, 12, 2), box(9, 0, 1, 11, 17, 3));
	private static final VoxelShape SHAPE_4_WEST = Shapes.or(box(6, 0, 0, 10, 8, 1), box(7, 8, 0, 9, 16, 2), box(9, 0, 0, 11, 17, 2), box(7, 12, 15, 9, 16, 16), box(6, 0, 14, 10, 12, 16), box(5, 0, 13, 7, 17, 15));
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(9, 0, 6, 16, 8, 10), box(14, 8, 6, 16, 16, 10), box(8, 8, 7, 14, 16, 9), box(8, 0, 9, 10, 17, 11), box(0, 12, 7, 8, 16, 9), box(1.5, 13.5, 6, 4.5, 16.5, 10), box(0, 0, 6, 9, 12, 10),
			box(8, 0, 5, 10, 17, 7));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 6, 7, 8, 10), box(0, 8, 6, 2, 16, 10), box(2, 8, 7, 8, 16, 9), box(6, 0, 5, 8, 17, 7), box(8, 12, 7, 16, 16, 9), box(11.5, 13.5, 6, 14.5, 16.5, 10), box(7, 0, 6, 16, 12, 10),
			box(6, 0, 9, 8, 17, 11));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(6, 0, 9, 10, 8, 16), box(6, 8, 14, 10, 16, 16), box(7, 8, 8, 9, 16, 14), box(5, 0, 8, 7, 17, 10), box(7, 12, 0, 9, 16, 8), box(6, 13.5, 1.5, 10, 16.5, 4.5), box(6, 0, 0, 10, 12, 9),
			box(9, 0, 8, 11, 17, 10));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(6, 0, 0, 10, 8, 7), box(6, 8, 0, 10, 16, 2), box(7, 8, 2, 9, 16, 8), box(9, 0, 6, 11, 17, 8), box(7, 12, 8, 9, 16, 16), box(6, 13.5, 11.5, 10, 16.5, 14.5), box(6, 0, 7, 10, 12, 16),
			box(5, 0, 6, 7, 17, 8));

	public BaseAirlockD1Block(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.NETHERITE_BLOCK).strength(30f, 15f).lightLevel(s -> (new Object() {
			public int getLightLevel() {
				if (s.getValue(BLOCKSTATE) == 1)
					return 4;
				if (s.getValue(BLOCKSTATE) == 2)
					return 4;
				if (s.getValue(BLOCKSTATE) == 3)
					return 4;
				if (s.getValue(BLOCKSTATE) == 4)
					return 4;
				if (s.getValue(BLOCKSTATE) == 5)
					return 4;
				if (s.getValue(BLOCKSTATE) == 6)
					return 4;
				if (s.getValue(BLOCKSTATE) == 7)
					return 4;
				if (s.getValue(BLOCKSTATE) == 8)
					return 4;
				if (s.getValue(BLOCKSTATE) == 9)
					return 4;
				return 4;
			}
		}.getLightLevel())).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BOLTED, false).setValue(EMERGENCY_ACS, false).setValue(PANEL_OPEN, false).setValue(DIODS, true).setValue(ENERGY_CABELS, 2).setValue(TIMER, true));
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
		builder.add(FACING, BOLTED, EMERGENCY_ACS, PANEL_OPEN, DIODS, ENERGY_CABELS, TIMER, BLOCKSTATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BOLTED, false).setValue(EMERGENCY_ACS, false).setValue(PANEL_OPEN, false).setValue(DIODS, true).setValue(ENERGY_CABELS, 2)
				.setValue(TIMER, true);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		BaseAirlockD1_PutProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		BaseAirlockD1_PutProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
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
		BaseAirlockOpenCloseProcedure.execute(world, x, y, z, blockstate, entity);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BaseAirlockD1BlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof BaseAirlockD1BlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}