package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModMenus;

public class DECALSGUIaccept2Procedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (0 <= parseDouble((entity instanceof Player _entity0 && _entity0.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "R", "") : "")
				&& 255 >= parseDouble((entity instanceof Player _entity1 && _entity1.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "R", "") : "")
				&& 0 <= parseDouble((entity instanceof Player _entity2 && _entity2.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu2) ? _menu2.getMenuState(0, "G", "") : "")
				&& 255 >= parseDouble((entity instanceof Player _entity3 && _entity3.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu3) ? _menu3.getMenuState(0, "G", "") : "")
				&& 0 <= parseDouble((entity instanceof Player _entity4 && _entity4.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu4) ? _menu4.getMenuState(0, "B", "") : "")
				&& 255 >= parseDouble((entity instanceof Player _entity5 && _entity5.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu5) ? _menu5.getMenuState(0, "B", "") : "")) {
			return true;
		}
		return false;
	}

	private static double parseDouble(String s) {
		try {
			return Double.parseDouble(s.trim());
		} catch (Exception e) {
			return 0;
		}
	}
}