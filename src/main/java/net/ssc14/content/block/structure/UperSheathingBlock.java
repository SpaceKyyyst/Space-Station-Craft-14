package net.ssc14.content.block.structure;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class UperSheathingBlock extends Block {
    public UperSheathingBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.IRON).strength(-1.0f, 20.0f));
    }
}
