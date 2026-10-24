package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetN2BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetN2Block extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetN2Block(BlockBehaviour.Properties properties) {
        super(properties, ClosetN2BlockEntity::new);
    }
}
