
package net.mcreator.ssc.item;

import net.mcreator.ssc.ModReagents;
import net.mcreator.ssc.ReagentColorHelper;
import net.mcreator.ssc.ReagentContainerConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ReagentContainerItem extends Item {
    private static final int DEFAULT_CAPACITY = 100;

    public ReagentContainerItem(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    public int getMaxCapacity() {
        Identifier key = BuiltInRegistries.ITEM.getKey(this);
        return ReagentContainerConfig.CAPACITIES.getOrDefault(key, DEFAULT_CAPACITY);
    }

    // ИСПРАВЛЕНО: Полное соответствие новой сигнатуре 26.1.2 из твоей шпаргалки (с TooltipDisplay и Consumer)
    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> componentConsumer, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, tooltipDisplay, componentConsumer, flag);
        int current = ModReagents.getReagents(itemstack).values().stream().mapToInt(Integer::intValue).sum();
        componentConsumer.accept(Component.translatable("tooltip.ssc_14.container.capacity", current, getMaxCapacity()).withStyle(net.minecraft.ChatFormatting.GRAY));
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack itemstack, LivingEntity livingEntity) {
        return 32;
    }

    // ИСПРАВЛЕНО: Идеальное соответствие ванильному использованию из твоей шпаргалки
    @Override
    public InteractionResult use(Level world, Player entity, InteractionHand hand) {
        ItemStack itemstack = entity.getItemInHand(hand);
        if (!ModReagents.getReagents(itemstack).isEmpty()) {
            entity.startUsingItem(hand);
            
            // Логика питья реагентов: если это серверная сторона, производим глоток
            if (!world.isClientSide() && entity instanceof ServerPlayer sp) {
                int amount = 10;
                if (ModReagents.drinkReagents(itemstack, amount, sp) > 0) {
                    world.playSound(null, sp.getX(), sp.getY(), sp.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !ModReagents.getReagents(stack).isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int current = ModReagents.getReagents(stack).values().stream().mapToInt(Integer::intValue).sum();
        return Math.round((float) current * 13.0F / (float) getMaxCapacity());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ReagentColorHelper.getMixedColor(stack);
    }
}
