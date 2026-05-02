package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.mcreator.ssc.procedures.BaseAirlockDEBUGCHECKProcedure;
import net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure;
import net.mcreator.ssc.block.entity.TechAirlockD1BlockEntity;

import java.util.function.Function;

public class TechAirlockD1Block extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty BOLTED = BooleanProperty.create("bolted");
	public static final BooleanProperty EMERGENCY_ACS = BooleanProperty.create("emergency_acs");
	public static final BooleanProperty PANEL_OPEN = BooleanProperty.create("panel_open");
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 9);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public TechAirlockD1Block(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.NETHERITE_BLOCK).strength(30f, 15f).lightLevel(blockstate -> 4).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BOLTED, false).setValue(EMERGENCY_ACS, false).setValue(PANEL_OPEN, false).setValue(BLOCKSTATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(BLOCKSTATE) == 1) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 8, 6, 1, 30, 10), box(1, 8, 7, 7, 30, 9), box(7, 15, 5, 9, 32, 7), box(0, 30, 6, 8, 32, 10), box(5, 0, 5, 7, 17, 7), box(0, 0, 6, 6, 8, 10), box(1, 0, 6, 4, 1, 10), box(12.5, 13.5, 6, 15.5, 16.5, 10),
							box(8, 0, 6, 16, 12, 10), box(7, 0, 9, 9, 17, 11), box(9, 18, 6, 16, 32, 10), box(9, 15, 9, 11, 32, 11), box(12, 13, 7, 16, 17, 9), box(9, 12, 7, 16, 18, 9), box(11, 0, 6, 15, 1, 10));
					case NORTH -> Shapes.or(box(15, 8, 6, 16, 30, 10), box(9, 8, 7, 15, 30, 9), box(7, 15, 9, 9, 32, 11), box(8, 30, 6, 16, 32, 10), box(9, 0, 9, 11, 17, 11), box(10, 0, 6, 16, 8, 10), box(12, 0, 6, 15, 1, 10),
							box(0.5, 13.5, 6, 3.5, 16.5, 10), box(0, 0, 6, 8, 12, 10), box(7, 0, 5, 9, 17, 7), box(0, 18, 6, 7, 32, 10), box(5, 15, 5, 7, 32, 7), box(0, 13, 7, 4, 17, 9), box(0, 12, 7, 7, 18, 9), box(1, 0, 6, 5, 1, 10));
					case EAST -> Shapes.or(box(6, 8, 15, 10, 30, 16), box(7, 8, 9, 9, 30, 15), box(5, 15, 7, 7, 32, 9), box(6, 30, 8, 10, 32, 16), box(5, 0, 9, 7, 17, 11), box(6, 0, 10, 10, 8, 16), box(6, 0, 12, 10, 1, 15),
							box(6, 13.5, 0.5, 10, 16.5, 3.5), box(6, 0, 0, 10, 12, 8), box(9, 0, 7, 11, 17, 9), box(6, 18, 0, 10, 32, 7), box(9, 15, 5, 11, 32, 7), box(7, 13, 0, 9, 17, 4), box(7, 12, 0, 9, 18, 7), box(6, 0, 1, 10, 1, 5));
					case WEST -> Shapes.or(box(6, 8, 0, 10, 30, 1), box(7, 8, 1, 9, 30, 7), box(9, 15, 7, 11, 32, 9), box(6, 30, 0, 10, 32, 8), box(9, 0, 5, 11, 17, 7), box(6, 0, 0, 10, 8, 6), box(6, 0, 1, 10, 1, 4),
							box(6, 13.5, 12.5, 10, 16.5, 15.5), box(6, 0, 8, 10, 12, 16), box(5, 0, 7, 7, 17, 9), box(6, 18, 9, 10, 32, 16), box(5, 15, 9, 7, 32, 11), box(7, 13, 12, 9, 17, 16), box(7, 12, 9, 9, 18, 16), box(6, 0, 11, 10, 1, 15));
				};
			} else if (state.getValue(BLOCKSTATE) == 2) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 8, 7, 6, 30, 9), box(6, 15, 5, 8, 32, 7), box(0, 30, 6, 7, 32, 10), box(4, 0, 5, 6, 17, 7), box(0, 0, 6, 5, 8, 10), box(0, 0, 6, 3, 1, 10), box(14.5, 13.5, 6, 16, 16.5, 10), box(10, 0, 6, 16, 12, 10),
							box(9, 0, 9, 11, 17, 11), box(11, 18, 6, 16, 32, 10), box(11, 15, 9, 13, 32, 11), box(14, 13, 7, 16, 17, 9), box(11, 12, 7, 16, 18, 9), box(13, 0, 6, 16, 1, 10));
					case NORTH -> Shapes.or(box(10, 8, 7, 16, 30, 9), box(8, 15, 9, 10, 32, 11), box(9, 30, 6, 16, 32, 10), box(10, 0, 9, 12, 17, 11), box(11, 0, 6, 16, 8, 10), box(13, 0, 6, 16, 1, 10), box(0, 13.5, 6, 1.5, 16.5, 10),
							box(0, 0, 6, 6, 12, 10), box(5, 0, 5, 7, 17, 7), box(0, 18, 6, 5, 32, 10), box(3, 15, 5, 5, 32, 7), box(0, 13, 7, 2, 17, 9), box(0, 12, 7, 5, 18, 9), box(0, 0, 6, 3, 1, 10));
					case EAST -> Shapes.or(box(7, 8, 10, 9, 30, 16), box(5, 15, 8, 7, 32, 10), box(6, 30, 9, 10, 32, 16), box(5, 0, 10, 7, 17, 12), box(6, 0, 11, 10, 8, 16), box(6, 0, 13, 10, 1, 16), box(6, 13.5, 0, 10, 16.5, 1.5),
							box(6, 0, 0, 10, 12, 6), box(9, 0, 5, 11, 17, 7), box(6, 18, 0, 10, 32, 5), box(9, 15, 3, 11, 32, 5), box(7, 13, 0, 9, 17, 2), box(7, 12, 0, 9, 18, 5), box(6, 0, 0, 10, 1, 3));
					case WEST -> Shapes.or(box(7, 8, 0, 9, 30, 6), box(9, 15, 6, 11, 32, 8), box(6, 30, 0, 10, 32, 7), box(9, 0, 4, 11, 17, 6), box(6, 0, 0, 10, 8, 5), box(6, 0, 0, 10, 1, 3), box(6, 13.5, 14.5, 10, 16.5, 16),
							box(6, 0, 10, 10, 12, 16), box(5, 0, 9, 7, 17, 11), box(6, 18, 11, 10, 32, 16), box(5, 15, 11, 7, 32, 13), box(7, 13, 14, 9, 17, 16), box(7, 12, 11, 9, 18, 16), box(6, 0, 13, 10, 1, 16));
				};
			} else if (state.getValue(BLOCKSTATE) == 3) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 8, 7, 4, 30, 9), box(4, 15, 5, 6, 32, 7), box(0, 30, 6, 5, 32, 10), box(2, 0, 5, 4, 17, 7), box(0, 0, 6, 3, 8, 10), box(0, 0, 6, 1, 1, 10), box(12, 0, 6, 16, 12, 10), box(11, 0, 9, 13, 17, 11),
							box(13, 18, 6, 16, 32, 10), box(13, 15, 9, 15, 32, 11), box(13, 12, 7, 16, 18, 9), box(15, 0, 6, 16, 1, 10));
					case NORTH -> Shapes.or(box(12, 8, 7, 16, 30, 9), box(10, 15, 9, 12, 32, 11), box(11, 30, 6, 16, 32, 10), box(12, 0, 9, 14, 17, 11), box(13, 0, 6, 16, 8, 10), box(15, 0, 6, 16, 1, 10), box(0, 0, 6, 4, 12, 10),
							box(3, 0, 5, 5, 17, 7), box(0, 18, 6, 3, 32, 10), box(1, 15, 5, 3, 32, 7), box(0, 12, 7, 3, 18, 9), box(0, 0, 6, 1, 1, 10));
					case EAST -> Shapes.or(box(7, 8, 12, 9, 30, 16), box(5, 15, 10, 7, 32, 12), box(6, 30, 11, 10, 32, 16), box(5, 0, 12, 7, 17, 14), box(6, 0, 13, 10, 8, 16), box(6, 0, 15, 10, 1, 16), box(6, 0, 0, 10, 12, 4),
							box(9, 0, 3, 11, 17, 5), box(6, 18, 0, 10, 32, 3), box(9, 15, 1, 11, 32, 3), box(7, 12, 0, 9, 18, 3), box(6, 0, 0, 10, 1, 1));
					case WEST -> Shapes.or(box(7, 8, 0, 9, 30, 4), box(9, 15, 4, 11, 32, 6), box(6, 30, 0, 10, 32, 5), box(9, 0, 2, 11, 17, 4), box(6, 0, 0, 10, 8, 3), box(6, 0, 0, 10, 1, 1), box(6, 0, 12, 10, 12, 16), box(5, 0, 11, 7, 17, 13),
							box(6, 18, 13, 10, 32, 16), box(5, 15, 13, 7, 32, 15), box(7, 12, 13, 9, 18, 16), box(6, 0, 15, 10, 1, 16));
				};
			} else if (state.getValue(BLOCKSTATE) == 4) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 8, 7, 2, 30, 9), box(2, 15, 5, 4, 32, 7), box(0, 30, 6, 3, 32, 10), box(0, 0, 5, 2, 17, 7), box(0, 0, 6, 1, 8, 10), box(14, 0, 6, 16, 12, 10), box(13, 0, 9, 15, 17, 11), box(15, 18, 6, 16, 32, 9),
							box(15, 15, 9, 16, 32, 11), box(15, 12, 7, 16, 18, 9));
					case NORTH -> Shapes.or(box(14, 8, 7, 16, 30, 9), box(12, 15, 9, 14, 32, 11), box(13, 30, 6, 16, 32, 10), box(14, 0, 9, 16, 17, 11), box(15, 0, 6, 16, 8, 10), box(0, 0, 6, 2, 12, 10), box(1, 0, 5, 3, 17, 7),
							box(0, 18, 7, 1, 32, 10), box(0, 15, 5, 1, 32, 7), box(0, 12, 7, 1, 18, 9));
					case EAST -> Shapes.or(box(7, 8, 14, 9, 30, 16), box(5, 15, 12, 7, 32, 14), box(6, 30, 13, 10, 32, 16), box(5, 0, 14, 7, 17, 16), box(6, 0, 15, 10, 8, 16), box(6, 0, 0, 10, 12, 2), box(9, 0, 1, 11, 17, 3), box(6, 18, 0, 9, 32, 1),
							box(9, 15, 0, 11, 32, 1), box(7, 12, 0, 9, 18, 1));
					case WEST -> Shapes.or(box(7, 8, 0, 9, 30, 2), box(9, 15, 2, 11, 32, 4), box(6, 30, 0, 10, 32, 3), box(9, 0, 0, 11, 17, 2), box(6, 0, 0, 10, 8, 1), box(6, 0, 14, 10, 12, 16), box(5, 0, 13, 7, 17, 15), box(7, 18, 15, 10, 32, 16),
							box(5, 15, 15, 7, 32, 16), box(7, 12, 15, 9, 18, 16));
				};
			}
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, 6, 16, 32, 10);
				case NORTH -> box(0, 0, 6, 16, 32, 10);
				case EAST -> box(6, 0, 0, 10, 32, 16);
				case WEST -> box(6, 0, 0, 10, 32, 16);
			};
		}, BOLTED, EMERGENCY_ACS, PANEL_OPEN);
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
		builder.add(FACING, BOLTED, EMERGENCY_ACS, PANEL_OPEN, BLOCKSTATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BOLTED, false).setValue(EMERGENCY_ACS, false).setValue(PANEL_OPEN, false).setValue(BLOCKSTATE, 0);
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
		world.scheduleTick(pos, this, 10);
		BaseAirlockD1PutProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		BaseAirlockDEBUGCHECKProcedure.execute();
		world.scheduleTick(pos, this, 10);
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
		return new TechAirlockD1BlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}
}