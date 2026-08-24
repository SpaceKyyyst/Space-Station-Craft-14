package net.mcreator.ssc.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;

import net.mcreator.ssc.procedures.DEBUG_item_PR_Procedure;

public class DEBUGitemItem extends Item {
	public DEBUGitemItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
		boolean retval = super.onEntitySwing(itemstack, entity, hand);
		DEBUG_item_PR_Procedure.execute(entity.level());
		return retval;
	}
}