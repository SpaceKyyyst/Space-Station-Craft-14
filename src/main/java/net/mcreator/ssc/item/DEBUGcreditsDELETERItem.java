package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.Credits_USEProcedure;

public class DEBUGcreditsDELETERItem extends Item {
	public DEBUGcreditsDELETERItem(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).stacksTo(1).fireResistant());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		Credits_USEProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}