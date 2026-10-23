package net.ssc14.content.block.geology;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

abstract class CaveFloorBlock extends Block {
    CaveFloorBlock(BlockBehaviour.Properties properties) {
        super(properties.strength(-1.0f, 3_600_000.0f));
    }
}
