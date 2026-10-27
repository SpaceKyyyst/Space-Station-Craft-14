package net.ssc14.content.item.tool;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** Canonical rapid-service unit item. */
public class RsuItem extends Item {
    public RsuItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
            TooltipDisplay display, Consumer<Component> output, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, output, flag);
        output.accept(Component.literal(RsuStateService.tooltip(stack)));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        RsuInteractionService.use(level, player, hand, player.getItemInHand(hand));
        return result;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        RsuInteractionService.useOn(context);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity,
            @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        if (slot == EquipmentSlot.MAINHAND) {
            RsuInteractionService.inventoryTick(level, entity, stack);
        }
    }
}
