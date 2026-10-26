package net.ssc14.content.item.clothing;

import net.minecraft.world.item.Item;

/** Shared behavior for passive Curios-compatible equipment. */
public class PassiveEquipmentItem extends Item {
    public PassiveEquipmentItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
