package net.ssc14.content.item.food;

import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/** Canonical item adapter for SSC14's timed food-consumption flow. */
public class EdibleFoodItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    private final FoodDefinition definition;

    public EdibleFoodItem(Properties properties, FoodDefinition definition) {
        super(properties.stacksTo(MAX_STACK_SIZE));
        this.definition = Objects.requireNonNull(definition, "Food definition");
    }

    public FoodDefinition definition() {
        return definition;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        FoodConsumptionService.consume(level, player.getX(), player.getY(), player.getZ(),
                player, player.getItemInHand(hand), definition);
        return result;
    }
}
