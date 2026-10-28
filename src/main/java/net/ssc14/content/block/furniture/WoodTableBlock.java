package net.ssc14.content.block.furniture;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoodTableBlock extends ConnectedTableBlock {
    public WoodTableBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.WOOD));
    }
}
