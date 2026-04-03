package net.mcreator.ssc.block;

import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.client.renderer.BiomeColors;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class PlanetaryTreeFoliageBlock extends TintedParticleLeavesBlock {
	public PlanetaryTreeFoliageBlock(BlockBehaviour.Properties properties) {
		super(0f, properties.sound(SoundType.CHERRY_LEAVES).strength(1f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).isSuffocating((bs, br, bp) -> false).isViewBlocking((bs, br, bp) -> false));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 2;
	}

	public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
		event.register((bs, world, pos, index) -> {
			return world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.FOLIAGE_DEFAULT;
		}, Ssc14ModBlocks.PLANETARY_TREE_FOLIAGE.get());
	}
}