package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetPinkBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetPinkBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetPinkBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetPinkBlockEntity::new);
    }
}
