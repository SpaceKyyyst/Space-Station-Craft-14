package net.ssc14.content.item.food;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/** Wrapped energy bar that delegates unwrapping to the canonical food service. */
public class PackagedEnergyBarItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public PackagedEnergyBarItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        EnergyBarOpeningService.open(level, player.getX(), player.getY(), player.getZ(),
                player, player.getItemInHand(hand));
        return result;
    }
}
