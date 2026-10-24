package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetBlockEntity::new);
    }
}
