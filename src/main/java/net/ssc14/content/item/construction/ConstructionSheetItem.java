package net.ssc14.content.item.construction;

import java.util.Objects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

/** Fire-resistant construction sheet with an explicit assembly action. */
public class ConstructionSheetItem extends Item {
    public static final int MAX_STACK_SIZE = 30;

    private final ConstructionItemKind kind;

    public ConstructionSheetItem(Properties properties, ConstructionItemKind kind) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
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
