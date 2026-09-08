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
import net.mcreator.ssc.block.entity.GlassAirlockD1BlockEntity;

import java.util.function.Function;

public class GlassAirlockD1Block extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty PANEL_OPEN = BooleanProperty.create("panel_open");
	public static final IntegerProperty ARL_VARIAT = IntegerProperty.create("arl_variat", 0, 25);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public GlassAirlockD1Block(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.NETHERITE_BLOCK).strength(30f, 15f).lightLevel(blockstate -> 4).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PANEL_OPEN, false).setValue(ARL_VARIAT, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 11) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 11) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 12) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 7, 32, 11), box(4, 12, 5, 9, 20, 11), box(1, 6, 5, 6, 29, 11), box(1, 6, 6, 6, 29, 10), box(9, 0, 5, 15, 32, 11), box(10, 11, 5, 12, 21, 11),
							box(9, 12, 5, 11, 20, 11), box(10, 6, 5, 15, 29, 11), box(10, 6, 6, 15, 29, 10), box(4.25, 13.75, 5.25, 8.75, 18.25, 10.75), box(1, 1, 5, 6, 2, 11), box(10, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 32, 7), box(5, 12, 4, 11, 20, 9), box(5, 6, 1, 11, 29, 6), box(6, 6, 1, 10, 29, 6), box(5, 0, 9, 11, 32, 15), box(5, 11, 10, 11, 21, 12),
							box(5, 12, 9, 11, 20, 11), box(5, 6, 10, 11, 29, 15), box(6, 6, 10, 10, 29, 15), box(5.25, 13.75, 4.25, 10.75, 18.25, 8.75), box(5, 1, 1, 11, 2, 6), box(5, 1, 10, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 9, 11, 32, 15), box(5, 12, 7, 11, 20, 12), box(5, 6, 10, 11, 29, 15), box(6, 6, 10, 10, 29, 15), box(5, 0, 1, 11, 32, 7),
							box(5, 11, 4, 11, 21, 6), box(5, 12, 5, 11, 20, 7), box(5, 6, 1, 11, 29, 6), box(6, 6, 1, 10, 29, 6), box(5.25, 13.75, 7.25, 10.75, 18.25, 11.75), box(5, 1, 10, 11, 2, 15), box(5, 1, 1, 11, 2, 6));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(9, 0, 5, 15, 32, 11), box(7, 12, 5, 12, 20, 11), box(10, 6, 5, 15, 29, 11), box(10, 6, 6, 15, 29, 10), box(1, 0, 5, 7, 32, 11), box(4, 11, 5, 6, 21, 11),
							box(5, 12, 5, 7, 20, 11), box(1, 6, 5, 6, 29, 11), box(1, 6, 6, 6, 29, 10), box(7.25, 13.75, 5.25, 11.75, 18.25, 10.75), box(10, 1, 5, 15, 2, 11), box(1, 1, 5, 6, 2, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 12) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 7, 32, 11), box(4, 12, 5, 9, 20, 11), box(1, 6, 5, 6, 29, 11), box(1, 6, 6, 6, 29, 10), box(9, 0, 5, 15, 32, 11), box(10, 11, 5, 12, 21, 11),
							box(9, 12, 5, 11, 20, 11), box(10, 6, 5, 15, 29, 11), box(10, 6, 6, 15, 29, 10), box(4.25, 13.75, 5.25, 8.75, 18.25, 10.75), box(1, 1, 5, 6, 2, 11), box(10, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 32, 7), box(5, 12, 4, 11, 20, 9), box(5, 6, 1, 11, 29, 6), box(6, 6, 1, 10, 29, 6), box(5, 0, 9, 11, 32, 15), box(5, 11, 10, 11, 21, 12),
							box(5, 12, 9, 11, 20, 11), box(5, 6, 10, 11, 29, 15), box(6, 6, 10, 10, 29, 15), box(5.25, 13.75, 4.25, 10.75, 18.25, 8.75), box(5, 1, 1, 11, 2, 6), box(5, 1, 10, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 9, 11, 32, 15), box(5, 12, 7, 11, 20, 12), box(5, 6, 10, 11, 29, 15), box(6, 6, 10, 10, 29, 15), box(5, 0, 1, 11, 32, 7),
							box(5, 11, 4, 11, 21, 6), box(5, 12, 5, 11, 20, 7), box(5, 6, 1, 11, 29, 6), box(6, 6, 1, 10, 29, 6), box(5.25, 13.75, 7.25, 10.75, 18.25, 11.75), box(5, 1, 10, 11, 2, 15), box(5, 1, 1, 11, 2, 6));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(9, 0, 5, 15, 32, 11), box(7, 12, 5, 12, 20, 11), box(10, 6, 5, 15, 29, 11), box(10, 6, 6, 15, 29, 10), box(1, 0, 5, 7, 32, 11), box(4, 11, 5, 6, 21, 11),
							box(5, 12, 5, 7, 20, 11), box(1, 6, 5, 6, 29, 11), box(1, 6, 6, 6, 29, 10), box(7.25, 13.75, 5.25, 11.75, 18.25, 10.75), box(10, 1, 5, 15, 2, 11), box(1, 1, 5, 6, 2, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 13) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 5, 32, 11), box(1, 6, 5, 4, 29, 11), box(1, 6, 6, 4, 29, 10), box(2, 12, 5, 7, 20, 11), box(11, 0, 5, 15, 32, 11),
							box(12, 11, 5, 14, 21, 11), box(11, 12, 5, 13, 20, 11), box(12, 6, 5, 15, 29, 11), box(12, 6, 6, 15, 29, 10), box(2.25, 13.75, 5.25, 6.75, 18.25, 10.75), box(1, 1, 5, 4, 2, 11), box(12, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 32, 5), box(5, 6, 1, 11, 29, 4), box(6, 6, 1, 10, 29, 4), box(5, 12, 2, 11, 20, 7), box(5, 0, 11, 11, 32, 15), box(5, 11, 12, 11, 21, 14),
							box(5, 12, 11, 11, 20, 13), box(5, 6, 12, 11, 29, 15), box(6, 6, 12, 10, 29, 15), box(5.25, 13.75, 2.25, 10.75, 18.25, 6.75), box(5, 1, 1, 11, 2, 4), box(5, 1, 12, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 11, 11, 32, 15), box(5, 6, 12, 11, 29, 15), box(6, 6, 12, 10, 29, 15), box(5, 12, 9, 11, 20, 14), box(5, 0, 1, 11, 32, 5),
							box(5, 11, 2, 11, 21, 4), box(5, 12, 3, 11, 20, 5), box(5, 6, 1, 11, 29, 4), box(6, 6, 1, 10, 29, 4), box(5.25, 13.75, 9.25, 10.75, 18.25, 13.75), box(5, 1, 12, 11, 2, 15), box(5, 1, 1, 11, 2, 4));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(11, 0, 5, 15, 32, 11), box(12, 6, 5, 15, 29, 11), box(12, 6, 6, 15, 29, 10), box(9, 12, 5, 14, 20, 11), box(1, 0, 5, 5, 32, 11),
							box(2, 11, 5, 4, 21, 11), box(3, 12, 5, 5, 20, 11), box(1, 6, 5, 4, 29, 11), box(1, 6, 6, 4, 29, 10), box(9.25, 13.75, 5.25, 13.75, 18.25, 10.75), box(12, 1, 5, 15, 2, 11), box(1, 1, 5, 4, 2, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 13) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 5, 32, 11), box(1, 6, 5, 4, 29, 11), box(1, 6, 6, 4, 29, 10), box(2, 12, 5, 7, 20, 11), box(11, 0, 5, 15, 32, 11),
							box(12, 11, 5, 14, 21, 11), box(11, 12, 5, 13, 20, 11), box(12, 6, 5, 15, 29, 11), box(12, 6, 6, 15, 29, 10), box(2.25, 13.75, 5.25, 6.75, 18.25, 10.75), box(1, 1, 5, 4, 2, 11), box(12, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 32, 5), box(5, 6, 1, 11, 29, 4), box(6, 6, 1, 10, 29, 4), box(5, 12, 2, 11, 20, 7), box(5, 0, 11, 11, 32, 15), box(5, 11, 12, 11, 21, 14),
							box(5, 12, 11, 11, 20, 13), box(5, 6, 12, 11, 29, 15), box(6, 6, 12, 10, 29, 15), box(5.25, 13.75, 2.25, 10.75, 18.25, 6.75), box(5, 1, 1, 11, 2, 4), box(5, 1, 12, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 11, 11, 32, 15), box(5, 6, 12, 11, 29, 15), box(6, 6, 12, 10, 29, 15), box(5, 12, 9, 11, 20, 14), box(5, 0, 1, 11, 32, 5),
							box(5, 11, 2, 11, 21, 4), box(5, 12, 3, 11, 20, 5), box(5, 6, 1, 11, 29, 4), box(6, 6, 1, 10, 29, 4), box(5.25, 13.75, 9.25, 10.75, 18.25, 13.75), box(5, 1, 12, 11, 2, 15), box(5, 1, 1, 11, 2, 4));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(11, 0, 5, 15, 32, 11), box(12, 6, 5, 15, 29, 11), box(12, 6, 6, 15, 29, 10), box(9, 12, 5, 14, 20, 11), box(1, 0, 5, 5, 32, 11),
							box(2, 11, 5, 4, 21, 11), box(3, 12, 5, 5, 20, 11), box(1, 6, 5, 4, 29, 11), box(1, 6, 6, 4, 29, 10), box(9.25, 13.75, 5.25, 13.75, 18.25, 10.75), box(12, 1, 5, 15, 2, 11), box(1, 1, 5, 4, 2, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == false && state.getValue(ARL_VARIAT) == 14) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 3, 32, 11), box(1, 12, 5, 5, 20, 11), box(1, 6, 5, 2, 29, 11), box(1, 6, 6, 2, 29, 10), box(13, 0, 5, 15, 32, 11), box(14, 6, 5, 15, 29, 11),
							box(14, 6, 6, 15, 29, 10), box(14, 11, 5, 15, 21, 11), box(13, 12, 5, 15, 20, 11), box(0.25, 13.75, 5.25, 4.75, 18.25, 10.75), box(1, 1, 5, 2, 2, 11), box(14, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 32, 3), box(5, 12, 1, 11, 20, 5), box(5, 6, 1, 11, 29, 2), box(6, 6, 1, 10, 29, 2), box(5, 0, 13, 11, 32, 15), box(5, 6, 14, 11, 29, 15),
							box(6, 6, 14, 10, 29, 15), box(5, 11, 14, 11, 21, 15), box(5, 12, 13, 11, 20, 15), box(5.25, 13.75, 0.25, 10.75, 18.25, 4.75), box(5, 1, 1, 11, 2, 2), box(5, 1, 14, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 13, 11, 32, 15), box(5, 12, 11, 11, 20, 15), box(5, 6, 14, 11, 29, 15), box(6, 6, 14, 10, 29, 15), box(5, 0, 1, 11, 32, 3),
							box(5, 6, 1, 11, 29, 2), box(6, 6, 1, 10, 29, 2), box(5, 11, 1, 11, 21, 2), box(5, 12, 1, 11, 20, 3), box(5.25, 13.75, 11.25, 10.75, 18.25, 15.75), box(5, 1, 14, 11, 2, 15), box(5, 1, 1, 11, 2, 2));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(13, 0, 5, 15, 32, 11), box(11, 12, 5, 15, 20, 11), box(14, 6, 5, 15, 29, 11), box(14, 6, 6, 15, 29, 10), box(1, 0, 5, 3, 32, 11),
							box(1, 6, 5, 2, 29, 11), box(1, 6, 6, 2, 29, 10), box(1, 11, 5, 2, 21, 11), box(1, 12, 5, 3, 20, 11), box(11.25, 13.75, 5.25, 15.75, 18.25, 10.75), box(14, 1, 5, 15, 2, 11), box(1, 1, 5, 2, 2, 11));
				};
			} else if (state.getValue(PANEL_OPEN) == true && state.getValue(ARL_VARIAT) == 14) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 5, 1, 32, 11), box(15, 0, 5, 16, 32, 11), box(1, 0, 5, 3, 32, 11), box(1, 12, 5, 5, 20, 11), box(1, 6, 5, 2, 29, 11), box(1, 6, 6, 2, 29, 10), box(13, 0, 5, 15, 32, 11), box(14, 6, 5, 15, 29, 11),
							box(14, 6, 6, 15, 29, 10), box(14, 11, 5, 15, 21, 11), box(13, 12, 5, 15, 20, 11), box(0.25, 13.75, 5.25, 4.75, 18.25, 10.75), box(1, 1, 5, 2, 2, 11), box(14, 1, 5, 15, 2, 11));
					case EAST -> Shapes.or(box(5, 0, 0, 11, 32, 1), box(5, 0, 15, 11, 32, 16), box(5, 0, 1, 11, 32, 3), box(5, 12, 1, 11, 20, 5), box(5, 6, 1, 11, 29, 2), box(6, 6, 1, 10, 29, 2), box(5, 0, 13, 11, 32, 15), box(5, 6, 14, 11, 29, 15),
							box(6, 6, 14, 10, 29, 15), box(5, 11, 14, 11, 21, 15), box(5, 12, 13, 11, 20, 15), box(5.25, 13.75, 0.25, 10.75, 18.25, 4.75), box(5, 1, 1, 11, 2, 2), box(5, 1, 14, 11, 2, 15));
					case WEST -> Shapes.or(box(5, 0, 15, 11, 32, 16), box(5, 0, 0, 11, 32, 1), box(5, 0, 13, 11, 32, 15), box(5, 12, 11, 11, 20, 15), box(5, 6, 14, 11, 29, 15), box(6, 6, 14, 10, 29, 15), box(5, 0, 1, 11, 32, 3),
							box(5, 6, 1, 11, 29, 2), box(6, 6, 1, 10, 29, 2), box(5, 11, 1, 11, 21, 2), box(5, 12, 1, 11, 20, 3), box(5.25, 13.75, 11.25, 10.75, 18.25, 15.75), box(5, 1, 14, 11, 2, 15), box(5, 1, 1, 11, 2, 2));
					default -> Shapes.or(box(15, 0, 5, 16, 32, 11), box(0, 0, 5, 1, 32, 11), box(13, 0, 5, 15, 32, 11), box(11, 12, 5, 15, 20, 11), box(14, 6, 5, 15, 29, 11), box(14, 6, 6, 15, 29, 10), box(1, 0, 5, 3, 32, 11),
							box(1, 6, 5, 2, 29, 11), box(1, 6, 6, 2, 29, 10), box(1, 11, 5, 2, 21, 11), box(1, 12, 5, 3, 20, 11), box(11.25, 13.75, 5.25, 15.75, 18.25, 10.75), box(14, 1, 5, 15, 2, 11), box(1, 1, 5, 2, 2, 11));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> box(0, 0, 5, 16, 16, 11);
				case EAST -> box(5, 0, 0, 11, 16, 16);
				case WEST -> box(5, 0, 0, 11, 16, 16);
				default -> box(0, 0, 5, 16, 16, 11);
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
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(PANEL_OPEN, false).setValue(ARL_VARIAT, 0);
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
		return new GlassAirlockD1BlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}
}