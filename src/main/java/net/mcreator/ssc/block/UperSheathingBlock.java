package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class UperSheathingBlock extends Block {
	public UperSheathingBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.IRON).strength(-1f, 10f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}