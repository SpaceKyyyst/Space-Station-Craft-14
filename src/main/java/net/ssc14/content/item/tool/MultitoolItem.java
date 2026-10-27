package net.ssc14.content.item.tool;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

/** Canonical implementation of the voltage-network multitool. */
public class MultitoolItem extends Item {
    public MultitoolItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
            TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, consumer, flag);
        String text = MultitoolModeService.description(stack);
        for (String line : text.split("\n")) {
            consumer.accept(Component.literal(line));
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        MultitoolModeService.switchMode(player, player.getItemInHand(hand));
        return result;
    }
}
