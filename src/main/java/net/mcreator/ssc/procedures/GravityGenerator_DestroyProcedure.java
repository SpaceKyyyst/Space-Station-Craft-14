package net.mcreator.ssc.procedures;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class GravityGenerator_DestroyProcedure {
	public static void execute() {
		Ssc14ModVariables.station_gravity = false;
	}
}