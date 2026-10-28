package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.ssc14.client.menu.commerce.CommerceDisplayText;
import net.ssc14.common.station.StationBudgetService;

public class OrderGUIbudgetTEXTProcedure {
	public static String execute(LevelAccessor world) {
		return CommerceDisplayText.currency(StationBudgetService.current(world));
	}
}
