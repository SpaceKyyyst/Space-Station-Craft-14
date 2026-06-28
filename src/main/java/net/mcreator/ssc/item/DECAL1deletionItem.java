
package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.DECAL1deletion_USE_Procedure;

public class DECAL1deletionItem extends Item {
	public DECAL1deletionItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		// Передаем честные аргументы уровня и игрока в процедуру удаления
		DECAL1deletion_USE_Procedure.execute(context.getLevel(), context.getPlayer());
		return InteractionResult.SUCCESS;
	}
}
