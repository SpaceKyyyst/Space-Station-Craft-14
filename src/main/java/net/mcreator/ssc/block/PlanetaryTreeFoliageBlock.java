package net.mcreator.ssc.block;

import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.client.color.block.BlockTintSources;

import net.mcreator.ssc.init.Ssc14ModBlocks;

import java.util.List;

public class PlanetaryTreeFoliageBlock extends TintedParticleLeavesBlock {
	public PlanetaryTreeFoliageBlock(BlockBehaviour.Properties properties) {
		super(0f, properties.sound(SoundType.CHERRY_LEAVES).strength(1f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).isSuffocating((bs, br, bp) -> false).isViewBlocking((bs, br, bp) -> false));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 2;
	}

	public static void blockColorLoad(RegisterColorHandlersEvent.BlockTintSources event) {
		event.getBlockColors().register(List.of(BlockTintSources.foliage()), Ssc14ModBlocks.PLANETARY_TREE_FOLIAGE.get());
	}
}