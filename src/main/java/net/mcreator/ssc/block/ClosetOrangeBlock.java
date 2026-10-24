package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetOrangeBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetOrangeBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetOrangeBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetOrangeBlockEntity::new);
    }
}
