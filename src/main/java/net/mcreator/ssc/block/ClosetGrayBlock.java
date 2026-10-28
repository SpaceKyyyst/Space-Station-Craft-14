package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetGrayBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetGrayBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetGrayBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetGrayBlockEntity::new);
    }
}
