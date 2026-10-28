package net.ssc14.content.item.reagent;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** A single-stack package that opens into a filled reagent container. */
public class SealedReagentContainerItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    private final ReagentContainerDefinition definition;
    private final Supplier<? extends Item> openItem;

    public SealedReagentContainerItem(
            Properties properties,
            ReagentContainerDefinition definition,
            Supplier<? extends Item> openItem) {
        super(properties.stacksTo(MAX_STACK_SIZE));
        this.definition = Objects.requireNonNull(definition, "definition");
        this.openItem = Objects.requireNonNull(openItem, "openItem");
    }

    public final ReagentContainerDefinition definition() {
        return definition;
    }

    public final Item openItem() {
        return openItem.get();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        ItemStack stack = player.getItemInHand(hand);
        ReagentContainerOpeningService.open(
                level, player, hand, stack, definition, openItem);
        return result;
    }
}
