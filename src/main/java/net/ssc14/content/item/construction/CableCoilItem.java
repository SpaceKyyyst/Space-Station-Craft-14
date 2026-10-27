package net.ssc14.content.item.construction;

import java.util.Objects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

/** Construction cable coil with an explicit voltage-tier action. */
public class CableCoilItem extends Item {
    public static final int MAX_STACK_SIZE = 30;

    private final ConstructionItemKind kind;

    public CableCoilItem(Properties properties, ConstructionItemKind kind) {
        super(properties.stacksTo(MAX_STACK_SIZE));
        if (kind != ConstructionItemKind.LOW_VOLTAGE_CABLE
                && kind != ConstructionItemKind.MEDIUM_VOLTAGE_CABLE
                && kind != ConstructionItemKind.HIGH_VOLTAGE_CABLE) {
            throw new IllegalArgumentException("CableCoilItem requires a cable kind");
        }
        this.kind = Objects.requireNonNull(kind, "kind");
    }

    public final ConstructionItemKind kind() {
        return kind;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        ConstructionItemInteractionService.use(kind, context);
        return InteractionResult.SUCCESS;
    }
}
