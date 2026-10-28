package net.ssc14.content.item.utility;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Debug item that broadcasts its primary-click marker on entity swing. */
public class DebugBroadcastItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public DebugBroadcastItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        boolean result = super.onEntitySwing(stack, entity, hand);
        DebugBroadcastService.broadcast(entity.level());
        return result;
    }
}
