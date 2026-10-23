package net.mcreator.ssc.block;

import java.util.function.Consumer;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.decoration.FenceCornerBlock}. */
@Deprecated(forRemoval = false)
public class FenceGBlock extends net.ssc14.content.block.decoration.FenceCornerBlock {
    public FenceGBlock(BlockBehaviour.Properties properties) { super(properties); }

    public static class Item extends BlockItem {
        public Item(net.minecraft.world.item.Item.Properties properties) {
            super(Ssc14ModBlocks.FENCE_G.get(), properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext context,
                TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
            super.appendHoverText(stack, context, display, consumer, flag);
            consumer.accept(Component.translatable("block.ssc_14.fence_g.description_0"));
        }
    }
}
