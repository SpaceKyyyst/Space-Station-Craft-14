package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.mcreator.ssc.block.entity.AirlockUpPlugBlockEntity;

/** @deprecated Use {@link net.ssc14.content.block.airlock.AirlockUpperPlugBlock}. */
@Deprecated(forRemoval = false)
public class AirlockUpPlugBlock
        extends net.ssc14.content.block.airlock.AirlockUpperPlugBlock {
    public AirlockUpPlugBlock(BlockBehaviour.Properties properties) {
        super(properties, false, AirlockUpPlugBlockEntity::new);
    }
}
