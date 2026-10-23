package net.ssc14.content.block.furniture;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SteelTableBlock extends ConnectedTableBlock {
    public SteelTableBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.LANTERN));
    }
}
