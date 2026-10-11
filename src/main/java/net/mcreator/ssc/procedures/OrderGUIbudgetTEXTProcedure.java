package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class OrderGUIbudgetTEXTProcedure {
	public static String execute(LevelAccessor world) {
		return "$:  " + Math.round(Ssc14ModVariables.WorldVariables.get(world).Station_Budget);
	}
}