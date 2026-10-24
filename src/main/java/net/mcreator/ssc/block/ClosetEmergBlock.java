package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.ClosetEmergBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.storage.StationClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetEmergBlock extends net.ssc14.content.block.storage.StationClosetBlock {
    public ClosetEmergBlock(BlockBehaviour.Properties properties) {
        super(properties, ClosetEmergBlockEntity::new);
    }
}
