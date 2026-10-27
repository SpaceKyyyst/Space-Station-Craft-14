package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.block.FenceBlockItem;

/** @deprecated Use {@link net.ssc14.content.block.decoration.FencePointBlock}. */
@Deprecated(forRemoval = false)
public class FencePointBlock extends net.ssc14.content.block.decoration.FencePointBlock {
    public FencePointBlock(BlockBehaviour.Properties properties) { super(properties); }

    /** @deprecated Use {@link FenceBlockItem}. */
    @Deprecated(forRemoval = false)
    public static class Item extends FenceBlockItem {
        public Item(net.minecraft.world.item.Item.Properties properties) {
            super(Ssc14Blocks.FENCE_POINT.get(), properties,
                    "block.ssc_14.fence_point.description_0");
        }
    }
}
