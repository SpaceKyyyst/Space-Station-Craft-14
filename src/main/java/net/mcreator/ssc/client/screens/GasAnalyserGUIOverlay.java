package net.mcreator.ssc.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.procedures.O2textRedactProcedure;
import net.mcreator.ssc.procedures.GasAnalyserGUI_pr_1Procedure;

@EventBusSubscriber(Dist.CLIENT)
public class GasAnalyserGUIOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (GasAnalyserGUI_pr_1Procedure.execute(entity)) {
			if (GasAnalyserGUI_pr_1Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/gas_anal_menu.png"), w / 2 + -168, h / 2 + -106, 0, 0, 160, 200, 160, 200);
			}
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/o2_icon.png"), w / 2 + -162, h / 2 + -21, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/n2_icon.png"), w / 2 + -162, h / 2 + -4, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/co2_icon.png"), w / 2 + -162, h / 2 + 13, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.gas_analyser_gui.label_kislorod"), w / 2 + -145, h / 2 + -17, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					O2textRedactProcedure.execute(world, x, y, z), w / 2 + -88, h / 2 + -17, -1, false);
		}
	}
}