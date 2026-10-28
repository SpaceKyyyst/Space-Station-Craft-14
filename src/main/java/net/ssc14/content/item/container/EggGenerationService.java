package net.ssc14.content.item.container;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.ssc14.content.item.Ssc14Items;

/** Canonical one-time filling of egg-carton inventory slots. */
public final class EggGenerationService {
    private static final int SLOT_COUNT = 10;
    private static final String GENERATED_KEY = "generated";

    private EggGenerationService() { }

    public static void execute(ItemStack stack) {
        if (stack == null || stack.isEmpty()
                || stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                        .copyTag().getBooleanOr(GENERATED_KEY, false)) return;
        if (stack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(stack))
                instanceof ResourceHandler<ItemResource> handler) {
            for (int slot = 0; slot < SLOT_COUNT; slot++) {
                replace(handler, slot, ItemResource.of(new ItemStack(Ssc14Items.EGG.get())));
            }
        }
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putBoolean(GENERATED_KEY, true));
    }

    private static void replace(ResourceHandler<ItemResource> handler,
            int slot, ItemResource resource) {
        try (var transaction = Transaction.openRoot()) {
            if (!handler.getResource(slot).isEmpty()) {
                handler.extract(slot, handler.getResource(slot),
                        handler.getAmountAsInt(slot), transaction);
            }
            handler.insert(slot, resource, 1, transaction);
            transaction.commit();
        }
    }
}
