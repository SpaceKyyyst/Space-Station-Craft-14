/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.gui.IDcodeScreen;
import net.mcreator.ssc.client.gui.DopCraftMenuScreen;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(Ssc14ModMenus.DOP_CRAFT_MENU.get(), DopCraftMenuScreen::new);
		event.register(Ssc14ModMenus.I_DCODE.get(), IDcodeScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}