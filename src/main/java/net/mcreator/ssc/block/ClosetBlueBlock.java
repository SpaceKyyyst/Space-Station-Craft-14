package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetBlueBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetBlueBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetBlueBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetBlueBlockEntity::new);
    }
}
