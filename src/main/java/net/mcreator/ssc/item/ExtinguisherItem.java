package net.mcreator.ssc.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import net.mcreator.ssc.procedures.Extinguisher_OpenProcedure;

public class ExtinguisherItem extends Item {
	public ExtinguisherItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		Extinguisher_OpenProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}
}