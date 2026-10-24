package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

import net.ssc14.content.block.ceiling.CeilingTileBlock;

/** @deprecated Use {@link CeilingTileBlock} through {@code Ssc14Blocks}. */
@Deprecated(forRemoval = false)
public class UpTitleBlock extends CeilingTileBlock {
	public UpTitleBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
}
