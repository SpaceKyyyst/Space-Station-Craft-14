package net.mcreator.ssc.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class SheathingBlock extends Block {
	public SheathingBlock(BlockBehaviour.Properties properties) {
		super(properties.mapColor(MapColor.COLOR_GRAY).sound(SoundType.IRON).strength(-1f, 10f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}