package net.ssc14.content.item.communication;

import net.minecraft.world.item.Item;

/** Shared behavior for radio-channel encryption keys. */
public class RadioEncryptionKeyItem extends Item {
    public RadioEncryptionKeyItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }
}
