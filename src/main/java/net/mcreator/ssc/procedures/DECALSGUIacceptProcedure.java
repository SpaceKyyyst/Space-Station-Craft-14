package net.mcreator.ssc.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModMenus;

public class DECALSGUIacceptProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			final String _tagName = "R";
			final double _tagValue = parseDouble((entity instanceof Player _entity0 && _entity0.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "R", "") : "");
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
		}
		{
			final String _tagName = "G";
			final double _tagValue = parseDouble((entity instanceof Player _entity3 && _entity3.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu3) ? _menu3.getMenuState(0, "G", "") : "");
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
		}
		{
			final String _tagName = "B";
			final double _tagValue = parseDouble((entity instanceof Player _entity6 && _entity6.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu6) ? _menu6.getMenuState(0, "B", "") : "");
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
		}
	}

	private static double parseDouble(String s) {
		try {
			return Double.parseDouble(s.trim());
		} catch (Exception e) {
			return 0;
		}
	}
}