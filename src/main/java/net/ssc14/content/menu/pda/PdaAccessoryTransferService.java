package net.ssc14.content.menu.pda;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/** Canonical transfer of PDA accessories between the installed slot and offhand. */
public final class PdaAccessoryTransferService {
    private PdaAccessoryTransferService() {
    }

    public static void transfer(Player player, int slot, String acceptedTag) {
        if (player == null || slot < 0 || acceptedTag == null || acceptedTag.isBlank()) {
            return;
        }
        ItemStack pda = player.getMainHandItem();
        ResourceHandler<ItemResource> handler = pda.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(pda));
        if (handler == null) {
            return;
        }
        ItemStack installed = ItemUtil.getStack(handler, slot);
        ItemStack offhand = player.getOffhandItem();
        if (!installed.isEmpty()) {
            if (offhand.isEmpty()) {
                ItemStack copy = installed.copy();
                copy.setCount(1);
                player.setItemInHand(InteractionHand.OFF_HAND, copy);
            } else if (player.level() instanceof ServerLevel level) {
                ItemEntity entity = new ItemEntity(level, player.getX(), player.getY() + 0.05, player.getZ(), installed);
                entity.setPickUpDelay(0);
                entity.setUnlimitedLifetime();
                level.addFreshEntity(entity);
            } else {
                return;
            }
            clearSlot(handler, slot);
            player.getInventory().setChanged();
            return;
        }
        if (offhand.is(ItemTags.create(Identifier.parse(acceptedTag)))) {
            insertOne(handler, slot, offhand);
            offhand.shrink(1);
            player.getInventory().setChanged();
        }
    }

    private static void clearSlot(ResourceHandler<ItemResource> handler, int slot) {
        try (var transaction = Transaction.openRoot()) {
            if (!handler.getResource(slot).isEmpty()) {
                handler.extract(slot, handler.getResource(slot), handler.getAmountAsInt(slot), transaction);
            }
            transaction.commit();
        }
    }

    private static void insertOne(ResourceHandler<ItemResource> handler, int slot, ItemStack stack) {
        try (var transaction = Transaction.openRoot()) {
            handler.insert(slot, ItemResource.of(stack), 1, transaction);
            transaction.commit();
        }
    }
}
