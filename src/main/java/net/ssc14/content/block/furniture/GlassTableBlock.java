package net.ssc14.content.block.furniture;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GlassTableBlock extends ConnectedTableBlock {
    public GlassTableBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.LANTERN));
    }
}
