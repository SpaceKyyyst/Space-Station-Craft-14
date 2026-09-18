
package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
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

import net.mcreator.ssc.procedures.Button_CLICK_Procedure;
import net.mcreator.ssc.block.entity.ButtonBlockEntity;
import net.mcreator.ssc.INetworkTrigger; // Наш кастомный интерфейс сетей мода

import java.util.function.Function;

public class ButtonBlock extends Block implements EntityBlock, INetworkTrigger {
	public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public ButtonBlock(BlockBehaviour.Properties properties) {
		// ИСПРАВЛЕНО: noCollission() заменен на новейший noCollision()
		super(properties.sound(SoundType.LANTERN).strength(10f, 5f).noCollision().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ACTIVE, false));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> box(6, 6, 14, 10, 10, 16);
				case EAST -> box(0, 6, 6, 2, 10, 10);
				case WEST -> box(14, 6, 6, 16, 10, 10);
				case UP -> box(6, 0, 6, 10, 2, 10);
				case DOWN -> box(6, 14, 6, 10, 16, 10);
				default -> box(6, 6, 0, 10, 10, 2);
			};
		});
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, ACTIVE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getClickedFace()).setValue(ACTIVE, false);
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos, Player entity, BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		Button_CLICK_Procedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ButtonBlockEntity(pos, state);
	}

	// ==========================================
	// КАСТОМНАЯ СИГНАЛЬНАЯ СИСТЕМА ДЛЯ INetworkTrigger
	// ==========================================
	@Override
	public java.util.List<String> getAvailableTriggers() {
		return java.util.List.of("activate");
	}

	@Override
	public String getTriggerName(String triggerId) {
		if ("activate".equals(triggerId)) return "При нажатии";
		return triggerId;
	}
}
