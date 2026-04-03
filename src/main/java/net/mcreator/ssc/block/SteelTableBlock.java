package net.mcreator.ssc.block;

import org.checkerframework.checker.units.qual.s;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.SteelTableStateUpdateProcedure;

public class SteelTableBlock extends Block {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 15);
	private static final VoxelShape SHAPE_1 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_2 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_3 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_4 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_5 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_6 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_7 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_8 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_9 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_10 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_11 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_12 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_13 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_14 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_15 = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape SHAPE = box(0, 14, 0, 16, 16, 16);

	public SteelTableBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.LANTERN).strength(5f).lightLevel(s -> (new Object() {
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
				if (s.getValue(BLOCKSTATE) == 6)
					return 0;
				if (s.getValue(BLOCKSTATE) == 7)
					return 0;
				if (s.getValue(BLOCKSTATE) == 8)
					return 0;
				if (s.getValue(BLOCKSTATE) == 9)
					return 0;
				if (s.getValue(BLOCKSTATE) == 10)
					return 0;
				if (s.getValue(BLOCKSTATE) == 11)
					return 0;
				if (s.getValue(BLOCKSTATE) == 12)
					return 0;
				if (s.getValue(BLOCKSTATE) == 13)
					return 0;
				if (s.getValue(BLOCKSTATE) == 14)
					return 0;
				if (s.getValue(BLOCKSTATE) == 15)
					return 0;
				return 0;
			}
		}.getLightLevel())).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
			return (SHAPE_1);
		}
		if (state.getValue(BLOCKSTATE) == 2) {
			return (SHAPE_2);
		}
		if (state.getValue(BLOCKSTATE) == 3) {
			return (SHAPE_3);
		}
		if (state.getValue(BLOCKSTATE) == 4) {
			return (SHAPE_4);
		}
		if (state.getValue(BLOCKSTATE) == 5) {
			return (SHAPE_5);
		}
		if (state.getValue(BLOCKSTATE) == 6) {
			return (SHAPE_6);
		}
		if (state.getValue(BLOCKSTATE) == 7) {
			return (SHAPE_7);
		}
		if (state.getValue(BLOCKSTATE) == 8) {
			return (SHAPE_8);
		}
		if (state.getValue(BLOCKSTATE) == 9) {
			return (SHAPE_9);
		}
		if (state.getValue(BLOCKSTATE) == 10) {
			return (SHAPE_10);
		}
		if (state.getValue(BLOCKSTATE) == 11) {
			return (SHAPE_11);
		}
		if (state.getValue(BLOCKSTATE) == 12) {
			return (SHAPE_12);
		}
		if (state.getValue(BLOCKSTATE) == 13) {
			return (SHAPE_13);
		}
		if (state.getValue(BLOCKSTATE) == 14) {
			return (SHAPE_14);
		}
		if (state.getValue(BLOCKSTATE) == 15) {
			return (SHAPE_15);
		}
		return (SHAPE);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BLOCKSTATE);
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