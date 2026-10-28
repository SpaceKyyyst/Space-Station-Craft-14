package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.block.FenceBlockItem;

/** @deprecated Use {@link net.ssc14.content.block.decoration.FenceLineBlock}. */
@Deprecated(forRemoval = false)
public class FenceLineBlock extends net.ssc14.content.block.decoration.FenceLineBlock {
    public FenceLineBlock(BlockBehaviour.Properties properties) { super(properties); }

    /** @deprecated Use {@link FenceBlockItem}. */
    @Deprecated(forRemoval = false)
    public static class Item extends FenceBlockItem {
        public Item(net.minecraft.world.item.Item.Properties properties) {
            super(Ssc14Blocks.FENCE_LINE.get(), properties,
                    "block.ssc_14.fence_line.description_0");
        }
    }
}
