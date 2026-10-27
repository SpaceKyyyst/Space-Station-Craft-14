package net.ssc14.content.item.safety;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

/** Canonical item implementation shared by the closed and open extinguisher IDs. */
public class ExtinguisherItem extends Item {
    public static final int MAX_STACK_SIZE = 1;
    public static final int OPEN_USE_DURATION = -20;

    private final ExtinguisherState state;

    public ExtinguisherItem(Properties properties, ExtinguisherState state) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
        this.state = state;
    }

    public ExtinguisherState state() {
        return state;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return state == ExtinguisherState.OPEN
                ? ItemUseAnimation.SPEAR : super.getUseAnimation(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return state == ExtinguisherState.OPEN
                ? OPEN_USE_DURATION : super.getUseDuration(stack, livingEntity);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        ExtinguisherUseService.execute(level, player, player.getItemInHand(hand));
        return result;
    }
}
