package net.ssc14.content.item.medical;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** Shared behavior for stackable fire-resistant medical supplies. */
public class MedicalSupplyItem extends Item {
    private static final int MAX_STACK_SIZE = 30;

    public MedicalSupplyItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 0.0F;
    }
}
