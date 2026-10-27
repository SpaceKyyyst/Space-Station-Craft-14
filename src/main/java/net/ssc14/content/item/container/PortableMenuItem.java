package net.ssc14.content.item.container;

import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/** Canonical item implementation for inventories opened from a held item. */
public class PortableMenuItem extends Item {
    private final PortableMenuItemKind kind;

    public PortableMenuItem(Properties properties, PortableMenuItemKind kind) {
        super(Objects.requireNonNull(kind, "kind").applyTo(properties));
        this.kind = kind;
    }

    public PortableMenuItemKind kind() {
        return kind;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        if (player instanceof ServerPlayer serverPlayer) {
            PortableMenuInteractionService.open(kind, serverPlayer, hand);
        }
        return result;
    }
}
