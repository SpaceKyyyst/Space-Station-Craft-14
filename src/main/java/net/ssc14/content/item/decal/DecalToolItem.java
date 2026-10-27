package net.ssc14.content.item.decal;

import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** Shared canonical item for decal placement and deletion tools. */
public class DecalToolItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    private final DecalToolKind kind;

    public DecalToolItem(Properties properties, DecalToolKind kind) {
        super(properties.stacksTo(MAX_STACK_SIZE));
        this.kind = Objects.requireNonNull(kind, "kind");
    }

    public final DecalToolKind kind() {
        return kind;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        if (kind.isSpawner()) {
            DecalToolInteractionService.use(kind, player, hand);
        }
        return result;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        DecalToolInteractionService.useOn(kind, context);
        return InteractionResult.SUCCESS;
    }
}
