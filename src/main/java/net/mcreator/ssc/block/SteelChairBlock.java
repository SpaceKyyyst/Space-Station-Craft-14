package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.Sit_On_Chair_PRProcedure;
import net.mcreator.ssc.procedures.Chair_Ent_GenerationProcedure;

public class SteelChairBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(2, 0, 2, 4, 7, 4), box(3, 0, 12, 5, 7, 14), box(2, 7, 2, 14, 9, 13), box(3, 7, 13, 5, 19, 15), box(2, 14, 12, 14, 20, 14), box(11, 7, 13, 13, 19, 15), box(12, 0, 2, 14, 7, 4),
			box(11, 0, 12, 13, 7, 14));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(12, 0, 12, 14, 7, 14), box(11, 0, 2, 13, 7, 4), box(2, 7, 3, 14, 9, 14), box(11, 7, 1, 13, 19, 3), box(2, 14, 2, 14, 20, 4), box(3, 7, 1, 5, 19, 3), box(2, 0, 12, 4, 7, 14),
			box(3, 0, 2, 5, 7, 4));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(12, 0, 2, 14, 7, 4), box(2, 0, 3, 4, 7, 5), box(3, 7, 2, 14, 9, 14), box(1, 7, 3, 3, 19, 5), box(2, 14, 2, 4, 20, 14), box(1, 7, 11, 3, 19, 13), box(12, 0, 12, 14, 7, 14),
			box(2, 0, 11, 4, 7, 13));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(2, 0, 12, 4, 7, 14), box(12, 0, 11, 14, 7, 13), box(2, 7, 2, 13, 9, 14), box(13, 7, 11, 15, 19, 13), box(12, 14, 2, 14, 20, 14), box(13, 7, 3, 15, 19, 5), box(2, 0, 2, 4, 7, 4),
			box(12, 0, 3, 14, 7, 5));

	public SteelChairBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.IRON).strength(5f).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		Chair_Ent_GenerationProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
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
		Sit_On_Chair_PRProcedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}
}