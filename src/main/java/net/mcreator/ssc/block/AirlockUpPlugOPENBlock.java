package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.mcreator.ssc.block.entity.AirlockUpPlugOPENBlockEntity;

/** @deprecated Use {@link net.ssc14.content.block.airlock.AirlockUpperPlugBlock}. */
@Deprecated(forRemoval = false)
public class AirlockUpPlugOPENBlock
        extends net.ssc14.content.block.airlock.AirlockUpperPlugBlock {
    public AirlockUpPlugOPENBlock(BlockBehaviour.Properties properties) {
        super(properties, true, AirlockUpPlugOPENBlockEntity::new);
    }
}
