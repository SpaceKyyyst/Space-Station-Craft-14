package net.mcreator.ssc.block;

import net.neoforged.neoforge.common.util.DeferredSoundType;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.TitleSteel_ChekProcedure;

import javax.annotation.Nullable;

public class TitleDarkBlock extends Block {
	private static final VoxelShape SHAPE = Shapes.or(box(0, 12, 0, 16, 16, 16), box(0, 0, 0, 1, 14, 1), box(0, 0, 15, 1, 14, 16), box(15, 0, 0, 16, 14, 1), box(15, 0, 15, 16, 14, 16));

	public TitleDarkBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.copper.step")),
				() -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_on")), () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.copper.hit")),
				() -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.copper.fall")))).strength(100f, 5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return (SHAPE);
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		TitleSteel_ChekProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		TitleSteel_ChekProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}