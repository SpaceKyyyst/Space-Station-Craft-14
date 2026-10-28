package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetFireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetFireBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetFireBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetFireBlockEntity::new);
    }
}
