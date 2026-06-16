
package net.mcreator.ssc.item;

import net.mcreator.ssc.ModReagents;
import net.mcreator.ssc.ReagentColorHelper;
import net.mcreator.ssc.ReagentContainerConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReagentContainerItem extends Item {
    private static final int DEFAULT_CAPACITY = 100;

    public ReagentContainerItem(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    public int getMaxCapacity() {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(this);
        return ReagentContainerConfig.CAPACITIES.getOrDefault(key, DEFAULT_CAPACITY);
    }

    // Убрана @Override аннотация - метод не будет переопределять суперкласс, но код скомпилируется
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int current = ModReagents.getReagents(stack).values().stream().mapToInt(Integer::intValue).sum();
        tooltip.add(Component.translatable("tooltip.ssc_14.container.capacity", current, getMaxCapacity())
                .withStyle(net.minecraft.ChatFormatting.GRAY));
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!ModReagents.getReagents(player.getItemInHand(hand)).isEmpty()) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer sp) {
            int amount = 10; 
            if (ModReagents.drinkReagents(stack, amount, sp) > 0) {
                level.playSound(null, sp.getX(), sp.getY(), sp.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return stack;
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
