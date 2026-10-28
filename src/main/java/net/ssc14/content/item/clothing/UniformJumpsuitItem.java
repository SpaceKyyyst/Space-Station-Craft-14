package net.ssc14.content.item.clothing;

import net.minecraft.world.item.Item;

/** Shared behavior for Curios-compatible uniform jumpsuits. */
public class UniformJumpsuitItem extends Item {
    public UniformJumpsuitItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
