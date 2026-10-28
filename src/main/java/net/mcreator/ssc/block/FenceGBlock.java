package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.block.FenceBlockItem;

/** @deprecated Use {@link net.ssc14.content.block.decoration.FenceCornerBlock}. */
@Deprecated(forRemoval = false)
public class FenceGBlock extends net.ssc14.content.block.decoration.FenceCornerBlock {
    public FenceGBlock(BlockBehaviour.Properties properties) { super(properties); }

    /** @deprecated Use {@link FenceBlockItem}. */
    @Deprecated(forRemoval = false)
    public static class Item extends FenceBlockItem {
        public Item(net.minecraft.world.item.Item.Properties properties) {
            super(Ssc14Blocks.FENCE_G.get(), properties,
                    "block.ssc_14.fence_g.description_0");
        }
    }
}
