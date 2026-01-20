package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.Sheathing_Cab_InstalProcedure;

public class LowVoltageCableItem extends Item {
	public LowVoltageCableItem(Item.Properties properties) {
		super(properties.stacksTo(30));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		Sheathing_Cab_InstalProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getPlayer());
		return InteractionResult.SUCCESS;
	}
}