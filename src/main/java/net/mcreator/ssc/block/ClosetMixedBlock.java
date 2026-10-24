package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetMixedBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetMixedBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetMixedBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetMixedBlockEntity::new);
    }
}
