
package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.redstone.Orientation;
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

import net.mcreator.ssc.procedures.Shutters_UPDATE_Procedure;
import net.mcreator.ssc.procedures.Shutters_DEBUD_AKTIVATOR_PR_Procedure;
import net.mcreator.ssc.block.entity.ShuttersBlockEntity;
import net.mcreator.ssc.INetworkAction;

import javax.annotation.Nullable;

import java.util.function.Function;

public class ShuttersBlock extends Block implements EntityBlock, net.mcreator.ssc.INetworkAction, net.mcreator.ssc.INetworkTrigger {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty DOWN = BooleanProperty.create("down");
	public static final BooleanProperty OPENING = BooleanProperty.create("opening");
	public static final BooleanProperty OPEN = BooleanProperty.create("open");
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ShuttersBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.TRIAL_SPAWNER).strength(30f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(UP, false).setValue(DOWN, false).setValue(OPENING, false).setValue(OPEN, false));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(UP) == false && state.getValue(DOWN) == false && state.getValue(OPENING) == true && state.getValue(OPEN) == false) {
				return switch (state.getValue(FACING)) {
					default -> box(0, 8, 7, 16, 16, 9);
					case NORTH -> box(0, 8, 7, 16, 16, 9);
					case EAST -> box(7, 8, 0, 9, 16, 16);
					case WEST -> box(7, 8, 0, 9, 16, 16);
				};
			} else if (state.getValue(UP) == true && state.getValue(DOWN) == false && state.getValue(OPENING) == true && state.getValue(OPEN) == false) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			} else if (state.getValue(UP) == true && state.getValue(DOWN) == true && state.getValue(OPENING) == true && state.getValue(OPEN) == false) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			} else if (state.getValue(UP) == false && state.getValue(DOWN) == true && state.getValue(OPENING) == true && state.getValue(OPEN) == false) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			} else if (state.getValue(UP) == false && state.getValue(DOWN) == false && state.getValue(OPENING) == false && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> box(0, 14, 6, 16, 16, 10);
					case NORTH -> box(0, 14, 6, 16, 16, 10);
					case EAST -> box(6, 14, 0, 10, 16, 16);
					case WEST -> box(6, 14, 0, 10, 16, 16);
				};
			} else if (state.getValue(UP) == true && state.getValue(DOWN) == false && state.getValue(OPENING) == false && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15.499, 0, 7, 15.5, 16, 9), box(0.499, 0, 7, 0.5, 16, 9));
					case NORTH -> Shapes.or(box(0.5, 0, 7, 0.501, 16, 9), box(15.5, 0, 7, 15.501, 16, 9));
					case EAST -> Shapes.or(box(7, 0, 0.5, 9, 16, 0.501), box(7, 0, 15.5, 9, 16, 15.501));
					case WEST -> Shapes.or(box(7, 0, 15.499, 9, 16, 15.5), box(7, 0, 0.499, 9, 16, 0.5));
				};
			} else if (state.getValue(UP) == true && state.getValue(DOWN) == true && state.getValue(OPENING) == false && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(15.499, 0, 7, 15.5, 16, 9), box(0.499, 0, 7, 0.5, 16, 9));
					case NORTH -> Shapes.or(box(0.5, 0, 7, 0.501, 16, 9), box(15.5, 0, 7, 15.501, 16, 9));
					case EAST -> Shapes.or(box(7, 0, 0.5, 9, 16, 0.501), box(7, 0, 15.5, 9, 16, 15.501));
					case WEST -> Shapes.or(box(7, 0, 15.499, 9, 16, 15.5), box(7, 0, 0.499, 9, 16, 0.5));
				};
			} else if (state.getValue(UP) == false && state.getValue(DOWN) == true && state.getValue(OPENING) == false && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> box(0, 14, 6, 16, 16, 10);
					case NORTH -> box(0, 14, 6, 16, 16, 10);
					case EAST -> box(6, 14, 0, 10, 16, 16);
					case WEST -> box(6, 14, 0, 10, 16, 16);
				};
			} else if (state.getValue(UP) == false && state.getValue(DOWN) == false && state.getValue(OPENING) == true && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			} else if (state.getValue(UP) == true && state.getValue(DOWN) == false && state.getValue(OPENING) == true && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			} else if (state.getValue(UP) == true && state.getValue(DOWN) == true && state.getValue(OPENING) == true && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			} else if (state.getValue(UP) == false && state.getValue(DOWN) == true && state.getValue(OPENING) == true && state.getValue(OPEN) == true) {
				return switch (state.getValue(FACING)) {
					default -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case NORTH -> Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
					case EAST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
					case WEST -> Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16));
				};
			}
			return switch (state.getValue(FACING)) {
				default -> box(0, 0, 7, 16, 16, 9);
				case NORTH -> box(0, 0, 7, 16, 16, 9);
				case EAST -> box(7, 0, 0, 9, 16, 16);
				case WEST -> box(7, 0, 0, 9, 16, 16);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, UP, DOWN, OPENING, OPEN);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(UP, false).setValue(DOWN, false).setValue(OPENING, false).setValue(OPEN, false);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		Shutters_UPDATE_Procedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
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
		Shutters_DEBUD_AKTIVATOR_PR_Procedure.execute(world, x, y, z, blockstate, entity);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ShuttersBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}
	
	@Override
	public java.util.List<String> getAvailableActions() {
		return java.util.List.of("open", "close", "toggle");
	}

	@Override
	public String getActionName(String actionId) {
		if ("open".equals(actionId)) return "Открыть";
		if ("close".equals(actionId)) return "Закрыть";
		if ("toggle".equals(actionId)) return "Переключить";
		return actionId;
	}

	@Override
	public java.util.List<String> getAvailableTriggers() {
	    return java.util.List.of("toggle");
	}
	
	@Override
	public String getTriggerName(String triggerId) {
	    if ("toggle".equals(triggerId)) return "При активации";
	    return triggerId;
	}
	
	@Override
	public void executeNetworkAction(String actionId, net.minecraft.world.level.Level world, net.minecraft.core.BlockPos pos) {
	    net.minecraft.world.level.block.state.BlockState state = world.getBlockState(pos);
	    
	    // ИСПРАВЛЕНО: Находим ближайшего игрока вместо null
	    net.minecraft.world.entity.player.Player nearestPlayer = null;
	    if (world instanceof net.minecraft.server.level.ServerLevel serverLevel) {
	        nearestPlayer = serverLevel.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 64.0, false);
	    }
	    
	    if ("open".equals(actionId)) {
	        net.mcreator.ssc.procedures.ShuttersOpeningPRProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), state, nearestPlayer);
	    } else if ("close".equals(actionId)) {
	        net.mcreator.ssc.procedures.ShuttersClosingPRProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), state, nearestPlayer);
	    } else if ("toggle".equals(actionId)) {
	        if (state.getBlock().getStateDefinition().getProperty("open") instanceof net.minecraft.world.level.block.state.properties.BooleanProperty _prop) {
	            if (!state.getValue(_prop)) {
	                net.mcreator.ssc.procedures.ShuttersOpeningPRProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), state, nearestPlayer);
	            } else {
	                net.mcreator.ssc.procedures.ShuttersClosingPRProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), state, nearestPlayer);
	            }
	        }
	    }
	}
}
