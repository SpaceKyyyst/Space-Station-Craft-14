package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetBlackBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetBlackBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetBlackBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetBlackBlockEntity::new);
    }
}
