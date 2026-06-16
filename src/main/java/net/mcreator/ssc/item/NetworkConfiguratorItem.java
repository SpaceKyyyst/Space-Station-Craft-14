package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.NetworkConfigurator_USE_Procedure;
import net.mcreator.ssc.procedures.NetworkConfigurator_DROP_Procedure;

public class NetworkConfiguratorItem extends Item {
	public NetworkConfiguratorItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		NetworkConfigurator_USE_Procedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
		NetworkConfigurator_DROP_Procedure.execute(itemstack);
		return true;
	}
}