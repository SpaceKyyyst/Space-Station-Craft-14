package net.ssc14.content.item.reagent;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

/** Canonical drinkable reagent container with an explicit capacity. */
public class ReagentContainerItem extends Item {
    public static final int MAX_STACK_SIZE = 1;
    public static final int DRINK_AMOUNT = 10;
    public static final int USE_DURATION_TICKS = 32;
    public static final int BAR_WIDTH = 13;

    private final int maxCapacity;

    public ReagentContainerItem(Properties properties) {
        this(properties, Ssc14ReagentContainers.DEFAULT_CAPACITY);
    }

    public ReagentContainerItem(Properties properties, int maxCapacity) {
        super(properties.stacksTo(MAX_STACK_SIZE));
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be positive");
        }
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay tooltipDisplay,
            Consumer<Component> componentConsumer,
            TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, componentConsumer, flag);
        componentConsumer.accept(Component.translatable(
                "tooltip.ssc_14.container.capacity",
                ReagentContainerAccess.totalAmount(stack), maxCapacity)
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return USE_DURATION_TICKS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (ReagentContainerAccess.contents(stack).isEmpty()) {
            return InteractionResult.FAIL;
        }

        player.startUsingItem(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer
                && ReagentContainerAccess.drink(stack, DRINK_AMOUNT, serverPlayer) > 0) {
            level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                    SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !ReagentContainerAccess.contents(stack).isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round((float) ReagentContainerAccess.totalAmount(stack)
                * BAR_WIDTH / maxCapacity);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ReagentContainerAccess.mixedColor(stack);
    }
}
