package net.ssc14.content.item.communication;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/** Capability adapter for headset encryption-key storage. */
final class HeadsetStorage {
    private HeadsetStorage() { }

    static ResourceHandler<ItemResource> handler(ItemStack headset) {
        return headset.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(headset));
    }

    static ItemStack itemAt(ResourceHandler<ItemResource> handler, int slot) {
        return handler == null ? ItemStack.EMPTY : ItemUtil.getStack(handler, slot);
    }

    static void replace(
            ResourceHandler<ItemResource> handler, int slot, ItemResource resource, int amount) {
        if (handler == null) {
            return;
        }
        try (var transaction = Transaction.openRoot()) {
            if (!handler.getResource(slot).isEmpty()) {
                handler.extract(slot, handler.getResource(slot),
                        handler.getAmountAsInt(slot), transaction);
            }
            if (!resource.isEmpty() && amount > 0) {
                handler.insert(slot, resource, amount, transaction);
            }
            transaction.commit();
        }
    }
}
