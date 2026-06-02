package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.DECAL1spawner_BlockClickProcedure;

public class DECAL1spawnerItem extends Item {
	public DECAL1spawnerItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		DECAL1spawner_BlockClickProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getPlayer());
		return InteractionResult.SUCCESS;
	}
}