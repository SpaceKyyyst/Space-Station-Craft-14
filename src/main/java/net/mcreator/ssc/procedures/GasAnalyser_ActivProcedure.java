package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModAttributes;

public class GasAnalyser_ActivProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.GAS_ANALYSER.get()
				&& (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB) ? _livingEntity2.getAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB).getValue() : 0) == 0) {
			if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB))
				_livingEntity3.getAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB).setBaseValue(1);
		}
	}
}