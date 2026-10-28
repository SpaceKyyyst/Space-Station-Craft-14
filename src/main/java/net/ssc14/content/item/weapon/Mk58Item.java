package net.ssc14.content.item.weapon;

import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical MK-58 item implementation shared by all persisted visual states. */
public class Mk58Item extends Item {
    private final Mk58State state;

    public Mk58Item(Properties properties, Mk58State state) {
        super(properties.stacksTo(1).fireResistant());
        this.state = Objects.requireNonNull(state, "state");
    }

    public Mk58State state() {
        return state;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.CROSSBOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return -10;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 0.0F;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> components,
            TooltipFlag flag) {
        super.appendHoverText(stack, context, display, components, flag);
        appendTooltip(components, FirearmInteractionService.tooltip(state, stack));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        FirearmInteractionService.use(state, level, player, player.getItemInHand(hand));
        return result;
    }

    @Override
    public boolean onEntitySwing(
            ItemStack stack, LivingEntity entity, InteractionHand hand) {
        boolean result = super.onEntitySwing(stack, entity, hand);
        FirearmInteractionService.swing(state, entity, stack);
        return result;
    }

    @Override
    public void inventoryTick(
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        FirearmInteractionService.inventoryTick(
                state, stack, level, entity, equipmentSlot);
    }

    static void appendTooltip(Consumer<Component> components, @Nullable String tooltip) {
        if (tooltip == null) {
            return;
        }
        for (String line : tooltip.split("\n")) {
            components.accept(Component.literal(line));
        }
    }
}
