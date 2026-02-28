package net.mcreator.ssc.block;

import net.neoforged.neoforge.common.util.DeferredSoundType;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

public class AsteroidStoneSilverBlock extends Block {
	public AsteroidStoneSilverBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:break_stone")), () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.stone.step")),
				() -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.stone.place")), () -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.stone.hit")),
				() -> BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.stone.fall")))).strength(25f, 15f));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}