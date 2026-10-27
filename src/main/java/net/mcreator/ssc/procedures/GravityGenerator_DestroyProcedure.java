package net.mcreator.ssc.procedures;


public class GravityGenerator_DestroyProcedure {
	public static void execute() {
		net.ssc14.content.gravity.StationGravityState.setEnabled(false);
	}
}
