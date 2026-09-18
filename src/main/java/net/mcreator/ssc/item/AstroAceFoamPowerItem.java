package net.mcreator.ssc.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.mcreator.ssc.procedures.FoamDartReloadingProcedure;
import net.mcreator.ssc.procedures.AstroAceFP_FIREProcedure;
import net.mcreator.ssc.procedures.AstroAceFP_DescriptionProcedure;
import net.mcreator.ssc.Ssc14Mod;

import java.util.function.Consumer;

public class AstroAceFoamPowerItem extends Item {
	public AstroAceFoamPowerItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState state) {
		return 0f;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> componentConsumer, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, componentConsumer, flag);
		Entity entity = Ssc14Mod.clientPlayer();
		String hoverText = AstroAceFP_DescriptionProcedure.execute(itemstack);
		if (hoverText != null) {
			for (String line : hoverText.split("\n")) {
				componentConsumer.accept(Component.literal(line));
			}
		}
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		FoamDartReloadingProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
		boolean retval = super.onEntitySwing(itemstack, entity, hand);
		AstroAceFP_FIREProcedure.execute(entity.level(), entity, itemstack);
		return retval;
	}
}