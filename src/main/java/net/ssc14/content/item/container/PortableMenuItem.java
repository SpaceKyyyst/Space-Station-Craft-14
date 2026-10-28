package net.ssc14.content.item.container;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        if (player instanceof ServerPlayer serverPlayer
                && PortableMenuInteractionService.canOpen(kind, serverPlayer, hand)) {
            PortableMenuInteractionService.open(kind, serverPlayer, hand);
        }
        return result;
    }

    @Override
    public void inventoryTick(
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        PortableMenuInteractionService.inventoryTick(
                kind, stack, level, entity, equipmentSlot);
    }
}
