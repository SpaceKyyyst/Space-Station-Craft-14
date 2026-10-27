package net.ssc14.content.item.equipment;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

/** Canonical magnetic-boots item shared by inactive and active registry states. */
public class MagneticBootsItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    private final MagneticBootsState state;

    public MagneticBootsItem(Properties properties, MagneticBootsState state) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
        this.state = Objects.requireNonNull(state, "Magnetic boots state");
    }

    public MagneticBootsState state() {
        return state;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
            TooltipDisplay tooltipDisplay, Consumer<Component> componentConsumer,
            TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, componentConsumer, flag);
        componentConsumer.accept(Component.literal(description(stack)));
    }

    public static String description(ItemStack stack) {
        if (stack.getItem() instanceof MagneticBootsItem boots) {
            return Component.translatable(boots.state.tooltipKey()).getString();
        }
        return "";
    }
}
