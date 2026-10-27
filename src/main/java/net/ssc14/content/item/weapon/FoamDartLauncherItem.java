package net.ssc14.content.item.weapon;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical Astro-Ace foam-dart launcher item. */
public class FoamDartLauncherItem extends Item {
    public FoamDartLauncherItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 0.0F;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
            TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, consumer, flag);
        consumer.accept(Component.literal(FoamDartLauncherService.description(stack)));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        FoamDartLauncherService.toggleDart(level, player, player.getItemInHand(hand));
        return result;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity,
            InteractionHand hand) {
        boolean result = super.onEntitySwing(stack, entity, hand);
        FoamDartLauncherService.fire(entity.level(), entity, stack);
        return result;
    }
}
