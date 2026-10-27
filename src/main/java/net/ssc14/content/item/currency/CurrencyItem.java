package net.ssc14.content.item.currency;

import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** Canonical item for station credits and the budget administration tool. */
public class CurrencyItem extends Item {
    private final CurrencyKind kind;

    public CurrencyItem(Properties properties, CurrencyKind kind) {
        super(configure(properties, kind));
        this.kind = Objects.requireNonNull(kind, "kind");
    }

    public final CurrencyKind kind() {
        return kind;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        if (kind.consolidatesOnUse()) {
            CurrencyInteractionService.use(kind, player);
        }
        return result;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        CurrencyInteractionService.useOn(kind, context);
        return InteractionResult.SUCCESS;
    }

    private static Properties configure(Properties properties, CurrencyKind kind) {
        Objects.requireNonNull(kind, "kind");
        properties.stacksTo(kind.maxStackSize()).rarity(kind.rarity());
        return kind.fireResistant() ? properties.fireResistant() : properties;
    }
}
