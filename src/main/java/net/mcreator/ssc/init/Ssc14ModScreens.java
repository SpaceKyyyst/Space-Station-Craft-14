/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.gui.*;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(Ssc14ModMenus.DOP_CRAFT_MENU.get(), DopCraftMenuScreen::new);
		event.register(Ssc14ModMenus.I_DCODE.get(), IDcodeScreen::new);
		event.register(Ssc14ModMenus.ACCESS_CONFIG_MENU.get(), AccessConfigMENUScreen::new);
		event.register(Ssc14ModMenus.ACCESS_CONFIG_GUI.get(), AccessConfigGUIScreen::new);
		event.register(Ssc14ModMenus.CABEL_PANNEL_AIRLOCK.get(), CabelPannelAirlockScreen::new);
		event.register(Ssc14ModMenus.GRAV_GEN_GUI.get(), GravGenGUIScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}