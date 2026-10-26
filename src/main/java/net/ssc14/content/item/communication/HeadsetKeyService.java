package net.ssc14.content.item.communication;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.ssc14.common.Ssc14Identity;

/** Adds and removes encryption keys through the headset's item capability. */
public final class HeadsetKeyService {
    private static final Identifier SCREWDRIVER_ID = Identifier.fromNamespaceAndPath(
            Ssc14Identity.MOD_ID, "screwdriver");
    private static final int FIRST_STORAGE_SLOT = 1;
    private static final int LAST_REMOVABLE_SLOT = 15;

    private HeadsetKeyService() { }

    public static void interact(LevelAccessor world, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        ItemStack headset = livingEntity.getMainHandItem();
        ItemStack offhand = livingEntity.getOffhandItem();
        if (headset.is(Ssc14CommunicationTags.HEADSETS)
                && offhand.is(Ssc14CommunicationTags.ENCRYPTION_KEYS)) {
            insertKey(headset, offhand);
        } else if (headset.is(Ssc14CommunicationTags.HEADSETS)
                && SCREWDRIVER_ID.equals(BuiltInRegistries.ITEM.getKey(offhand.getItem()))) {
            removeKeys(world, entity, headset);
        }
    }

    private static void insertKey(ItemStack headset, ItemStack keyStack) {
        HeadsetChannel channel = HeadsetChannel.fromKey(keyStack.getItem());
        if (channel == null) {
            return;
        }
        var handler = HeadsetStorage.handler(headset);
        if (HeadsetStorage.itemAt(handler, channel.slot()).is(channel.keyItem())) {
            return;
        }
        HeadsetStorage.replace(handler, channel.slot(), ItemResource.of(keyStack), 1);
        keyStack.shrink(1);
    }

    private static void removeKeys(LevelAccessor world, Entity owner, ItemStack headset) {
        var handler = HeadsetStorage.handler(headset);
        for (int slot = FIRST_STORAGE_SLOT; slot <= LAST_REMOVABLE_SLOT; slot++) {
            ItemStack stored = HeadsetStorage.itemAt(handler, slot);
            if (stored.isEmpty()) {
                continue;
            }
            if (world instanceof ServerLevel serverLevel) {
                ItemEntity dropped = new ItemEntity(serverLevel,
                        owner.getX(), owner.getY(), owner.getZ(), stored.copy());
                dropped.setPickUpDelay(0);
                dropped.setUnlimitedLifetime();
                serverLevel.addFreshEntity(dropped);
            }
            HeadsetStorage.replace(handler, slot, ItemResource.EMPTY, 0);
        }
    }
}
