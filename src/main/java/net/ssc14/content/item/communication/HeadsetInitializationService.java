package net.ssc14.content.item.communication;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.transfer.item.ItemResource;

/** Initializes a headset's default encryption keys exactly once. */
public final class HeadsetInitializationService {
    private static final String GENERATED_TAG = "generated";

    private HeadsetInitializationService() { }

    public static void initialize(ItemStack headset) {
        if (headset.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getBooleanOr(GENERATED_TAG, false)) {
            return;
        }
        CustomData.update(DataComponents.CUSTOM_DATA, headset,
                tag -> tag.putBoolean(GENERATED_TAG, true));
        if (!headset.is(Ssc14CommunicationTags.HEADSETS)
                || !(headset.getItem() instanceof HeadsetItem headsetItem)) {
            return;
        }

        var handler = HeadsetStorage.handler(headset);
        for (HeadsetChannel channel : headsetItem.definition().defaultChannels()) {
            HeadsetStorage.replace(handler, channel.slot(),
                    ItemResource.of(new ItemStack(channel.keyItem())), 1);
        }
    }
}
