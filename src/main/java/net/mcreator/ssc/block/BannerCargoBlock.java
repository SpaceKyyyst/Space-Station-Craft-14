package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.decoration.DirectionalBannerBlock;

/** @deprecated Use {@link DirectionalBannerBlock}. */
@Deprecated(forRemoval = false)
public class BannerCargoBlock extends DirectionalBannerBlock {
    public BannerCargoBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
