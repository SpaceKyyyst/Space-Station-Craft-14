package net.ssc14.content.item.block;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

/** Canonical BlockItem for directional fence variants. */
public class FenceBlockItem extends BlockItem {
    private final String tooltipKey;

    public FenceBlockItem(Block block, Properties properties, String tooltipKey) {
        super(block, properties.stacksTo(1));
        this.tooltipKey = Objects.requireNonNull(tooltipKey, "tooltipKey");
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> components,
            TooltipFlag flag) {
        super.appendHoverText(stack, context, display, components, flag);
        components.accept(Component.translatable(tooltipKey));
    }
}
