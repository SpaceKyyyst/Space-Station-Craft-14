package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetGreenBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetGreenBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetGreenBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetGreenBlockEntity::new);
    }
}
