
package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.Posters_InstallProcedure;

public class PosterRandomAnythingItem extends Item {
	public PosterRandomAnythingItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		Posters_InstallProcedure.execute(context);
		return InteractionResult.SUCCESS;
	}
}
