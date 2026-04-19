package net.mcreator.ssc.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.SteelTableStateUpdateProcedure;

import java.util.function.Function;

public class SteelTableBlock extends Block {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 15);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public SteelTableBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.LANTERN).strength(5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(BLOCKSTATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(BLOCKSTATE) == 1) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 2) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 3) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 4) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 5) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 6) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 7) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 8) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 9) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 10) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 11) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 12) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 13) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 14) {
				return box(0, 14, 0, 16, 16, 16);
			} else if (state.getValue(BLOCKSTATE) == 15) {
				return box(0, 14, 0, 16, 16, 16);
			}
			return box(0, 14, 0, 16, 16, 16);
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
		builder.add(BLOCKSTATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(BLOCKSTATE, 0);
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		SteelTableStateUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
		SteelTableStateUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		return retval;
	}
}