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
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure;
import net.mcreator.ssc.block.entity.BaseAirlockD1BlockEntity;

import java.util.function.Function;

public class BaseAirlockD1Block extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty PANEL_OPEN = BooleanProperty.create("panel_open");
	public static final IntegerProperty ARL_VARIAT = IntegerProperty.create("arl_variat", 0, 25);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public BaseAirlockD1Block(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.NETHERITE_BLOCK).strength(30f, 15f).lightLevel(blockstate -> 4).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PANEL_OPEN, false).setValue(ARL_VARIAT, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 11) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 11) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1));
				};
			} else if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 12) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(11, 15, 5, 15, 32, 11), box(9, 0, 5, 15, 15, 11), box(1, 15, 5, 9, 32, 11), box(1, 17, 5, 7, 29, 11), box(1, 17, 6, 7, 29, 10), box(1, 0, 5, 7, 15, 11),
							box(1, 6, 5, 5, 17, 11), box(1, 6, 6, 5, 17, 10), box(11.25, 13.75, 5.25, 15.75, 18.25, 10.75), box(10, 1, 5, 15, 2, 11), box(1, 1, 5, 6, 2, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 15, 5, 5, 32, 11), box(1, 0, 5, 7, 15, 11), box(7, 15, 5, 15, 32, 11), box(9, 17, 5, 15, 29, 11), box(9, 17, 6, 15, 29, 10),
							box(9, 0, 5, 15, 15, 11), box(11, 6, 5, 15, 17, 11), box(11, 6, 6, 15, 17, 10), box(0.25, 13.75, 5.25, 4.75, 18.25, 10.75), box(1, 1, 5, 6, 2, 11), box(10, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 15, 1, 11, 32, 5), box(5, 0, 1, 11, 15, 7), box(5, 15, 7, 11, 32, 15), box(5, 17, 9, 11, 29, 15), box(6, 17, 9, 10, 29, 15),
							box(5, 0, 9, 11, 15, 15), box(5, 6, 11, 11, 17, 15), box(6, 6, 11, 10, 17, 15), box(5.25, 13.75, 0.25, 10.75, 18.25, 4.75), box(5, 1, 1, 11, 2, 6), box(5, 1, 10, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 15, 11, 11, 32, 15), box(5, 0, 9, 11, 15, 15), box(5, 15, 1, 11, 32, 9), box(5, 17, 1, 11, 29, 7), box(6, 17, 1, 10, 29, 7),
							box(5, 0, 1, 11, 15, 7), box(5, 6, 1, 11, 17, 5), box(6, 6, 1, 10, 17, 5), box(5.25, 13.75, 11.25, 10.75, 18.25, 15.75), box(5, 1, 10, 11, 2, 15), box(5, 1, 1, 11, 2, 6));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 12) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(11, 15, 5, 15, 32, 11), box(9, 0, 5, 15, 15, 11), box(1, 15, 5, 9, 32, 11), box(1, 17, 5, 7, 29, 11), box(1, 17, 6, 7, 29, 10), box(1, 0, 5, 7, 15, 11),
							box(1, 6, 5, 5, 17, 11), box(1, 6, 6, 5, 17, 10), box(11.25, 13.75, 5.25, 15.75, 18.25, 10.75), box(10, 1, 5, 15, 2, 11), box(1, 1, 5, 6, 2, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 15, 5, 5, 32, 11), box(1, 0, 5, 7, 15, 11), box(7, 15, 5, 15, 32, 11), box(9, 17, 5, 15, 29, 11), box(9, 17, 6, 15, 29, 10),
							box(9, 0, 5, 15, 15, 11), box(11, 6, 5, 15, 17, 11), box(11, 6, 6, 15, 17, 10), box(0.25, 13.75, 5.25, 4.75, 18.25, 10.75), box(1, 1, 5, 6, 2, 11), box(10, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 15, 1, 11, 32, 5), box(5, 0, 1, 11, 15, 7), box(5, 15, 7, 11, 32, 15), box(5, 17, 9, 11, 29, 15), box(6, 17, 9, 10, 29, 15),
							box(5, 0, 9, 11, 15, 15), box(5, 6, 11, 11, 17, 15), box(6, 6, 11, 10, 17, 15), box(5.25, 13.75, 0.25, 10.75, 18.25, 4.75), box(5, 1, 1, 11, 2, 6), box(5, 1, 10, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 15, 11, 11, 32, 15), box(5, 0, 9, 11, 15, 15), box(5, 15, 1, 11, 32, 9), box(5, 17, 1, 11, 29, 7), box(6, 17, 1, 10, 29, 7),
							box(5, 0, 1, 11, 15, 7), box(5, 6, 1, 11, 17, 5), box(6, 6, 1, 10, 17, 5), box(5.25, 13.75, 11.25, 10.75, 18.25, 15.75), box(5, 1, 10, 11, 2, 15), box(5, 1, 1, 11, 2, 6));
				};
			} else if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 13) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(13, 15, 5, 15, 32, 11), box(11, 0, 5, 15, 15, 11), box(1, 15, 5, 7, 32, 11), box(1, 17, 5, 5, 29, 11), box(1, 17, 6, 5, 29, 10), box(1, 0, 5, 5, 15, 11),
							box(1, 6, 5, 3, 17, 11), box(1, 6, 6, 3, 17, 10), box(13.25, 13.75, 5.25, 15.75, 18.25, 10.75), box(12, 1, 5, 15, 2, 11), box(1, 1, 5, 4, 2, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 15, 5, 3, 32, 11), box(1, 0, 5, 5, 15, 11), box(9, 15, 5, 15, 32, 11), box(11, 17, 5, 15, 29, 11), box(11, 17, 6, 15, 29, 10),
							box(11, 0, 5, 15, 15, 11), box(13, 6, 5, 15, 17, 11), box(13, 6, 6, 15, 17, 10), box(0.25, 13.75, 5.25, 2.75, 18.25, 10.75), box(1, 1, 5, 4, 2, 11), box(12, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 15, 1, 11, 32, 3), box(5, 0, 1, 11, 15, 5), box(5, 15, 9, 11, 32, 15), box(5, 17, 11, 11, 29, 15), box(6, 17, 11, 10, 29, 15),
							box(5, 0, 11, 11, 15, 15), box(5, 6, 13, 11, 17, 15), box(6, 6, 13, 10, 17, 15), box(5.25, 13.75, 0.25, 10.75, 18.25, 2.75), box(5, 1, 1, 11, 2, 4), box(5, 1, 12, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 15, 13, 11, 32, 15), box(5, 0, 11, 11, 15, 15), box(5, 15, 1, 11, 32, 7), box(5, 17, 1, 11, 29, 5), box(6, 17, 1, 10, 29, 5),
							box(5, 0, 1, 11, 15, 5), box(5, 6, 1, 11, 17, 3), box(6, 6, 1, 10, 17, 3), box(5.25, 13.75, 13.25, 10.75, 18.25, 15.75), box(5, 1, 12, 11, 2, 15), box(5, 1, 1, 11, 2, 4));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 13) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(13, 15, 5, 15, 32, 11), box(11, 0, 5, 15, 15, 11), box(1, 15, 5, 7, 32, 11), box(1, 17, 5, 5, 29, 11), box(1, 17, 6, 5, 29, 10), box(1, 0, 5, 5, 15, 11),
							box(1, 6, 5, 3, 17, 11), box(1, 6, 6, 3, 17, 10), box(13.25, 13.75, 5.25, 15.75, 18.25, 10.75), box(12, 1, 5, 15, 2, 11), box(1, 1, 5, 4, 2, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 15, 5, 3, 32, 11), box(1, 0, 5, 5, 15, 11), box(9, 15, 5, 15, 32, 11), box(11, 17, 5, 15, 29, 11), box(11, 17, 6, 15, 29, 10),
							box(11, 0, 5, 15, 15, 11), box(13, 6, 5, 15, 17, 11), box(13, 6, 6, 15, 17, 10), box(0.25, 13.75, 5.25, 2.75, 18.25, 10.75), box(1, 1, 5, 4, 2, 11), box(12, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 15, 1, 11, 32, 3), box(5, 0, 1, 11, 15, 5), box(5, 15, 9, 11, 32, 15), box(5, 17, 11, 11, 29, 15), box(6, 17, 11, 10, 29, 15),
							box(5, 0, 11, 11, 15, 15), box(5, 6, 13, 11, 17, 15), box(6, 6, 13, 10, 17, 15), box(5.25, 13.75, 0.25, 10.75, 18.25, 2.75), box(5, 1, 1, 11, 2, 4), box(5, 1, 12, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 15, 13, 11, 32, 15), box(5, 0, 11, 11, 15, 15), box(5, 15, 1, 11, 32, 7), box(5, 17, 1, 11, 29, 5), box(6, 17, 1, 10, 29, 5),
							box(5, 0, 1, 11, 15, 5), box(5, 6, 1, 11, 17, 3), box(6, 6, 1, 10, 17, 3), box(5.25, 13.75, 13.25, 10.75, 18.25, 15.75), box(5, 1, 12, 11, 2, 15), box(5, 1, 1, 11, 2, 4));
				};
			} else if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 14) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(13, 0, 5, 15, 15, 11), box(1, 15, 5, 5, 32, 11), box(1, 17, 5, 3, 29, 11), box(1, 17, 6, 3, 29, 10), box(1, 0, 5, 3, 15, 11), box(14, 1, 5, 15, 2, 11),
							box(1, 1, 5, 2, 2, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 3, 15, 11), box(11, 15, 5, 15, 32, 11), box(13, 17, 5, 15, 29, 11), box(13, 17, 6, 15, 29, 10), box(13, 0, 5, 15, 15, 11),
							box(1, 1, 5, 2, 2, 11), box(14, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 15, 3), box(5, 15, 11, 11, 32, 15), box(5, 17, 13, 11, 29, 15), box(6, 17, 13, 10, 29, 15), box(5, 0, 13, 11, 15, 15),
							box(5, 1, 1, 11, 2, 2), box(5, 1, 14, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 13, 11, 15, 15), box(5, 15, 1, 11, 32, 5), box(5, 17, 1, 11, 29, 3), box(6, 17, 1, 10, 29, 3), box(5, 0, 1, 11, 15, 3), box(5, 1, 14, 11, 2, 15),
							box(5, 1, 1, 11, 2, 2));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 14) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(13, 0, 5, 15, 15, 11), box(1, 15, 5, 5, 32, 11), box(1, 17, 5, 3, 29, 11), box(1, 17, 6, 3, 29, 10), box(1, 0, 5, 3, 15, 11), box(14, 1, 5, 15, 2, 11),
							box(1, 1, 5, 2, 2, 11));
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 3, 15, 11), box(11, 15, 5, 15, 32, 11), box(13, 17, 5, 15, 29, 11), box(13, 17, 6, 15, 29, 10), box(13, 0, 5, 15, 15, 11),
							box(1, 1, 5, 2, 2, 11), box(14, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 15, 3), box(5, 15, 11, 11, 32, 15), box(5, 17, 13, 11, 29, 15), box(6, 17, 13, 10, 29, 15), box(5, 0, 13, 11, 15, 15),
							box(5, 1, 1, 11, 2, 2), box(5, 1, 14, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 13, 11, 15, 15), box(5, 15, 1, 11, 32, 5), box(5, 17, 1, 11, 29, 3), box(6, 17, 1, 10, 29, 3), box(5, 0, 1, 11, 15, 3), box(5, 1, 14, 11, 2, 15),
							box(5, 1, 1, 11, 2, 2));
				};
			}
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, 5, 16, 16, 11);
				case NORTH -> box(0, 0, 5, 16, 16, 11);
				case EAST -> box(5, 0, 0, 11, 16, 16);
				case WEST -> box(5, 0, 0, 11, 16, 16);
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
		builder.add(FACING, PANEL_OPEN, ARL_VARIAT);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(PANEL_OPEN, false).setValue(ARL_VARIAT, 0);
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
		BaseAirlockD1PutProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
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
}