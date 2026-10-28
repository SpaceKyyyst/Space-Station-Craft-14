package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.storage.SecureClosetBlock;

/** @deprecated Use {@link SecureClosetBlock}. */
@Deprecated(forRemoval = false)
public class ClosetSecureBlock extends SecureClosetBlock {
    public ClosetSecureBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
