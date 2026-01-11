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

import net.mcreator.ssc.procedures.*;

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
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/gas_anal_menu.png"), w / 2 + -207, h / 2 + -101, 0, 0, 200, 200, 200, 200);
			}
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/o2_icon.png"), w / 2 + -203, h / 2 + -50, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/n2_icon.png"), w / 2 + -203, h / 2 + -34, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/co2_icon.png"), w / 2 + -203, h / 2 + -18, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/water_vapor_icon.png"), w / 2 + -203, h / 2 + -2, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/plasma_icon.png"), w / 2 + -203, h / 2 + 14, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/miasm_icon.png"), w / 2 + -203, h / 2 + 30, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/no2_icon.png"), w / 2 + -203, h / 2 + 46, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/tritium_icon.png"), w / 2 + -203, h / 2 + 62, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/frezon_icon.png"), w / 2 + -203, h / 2 + 78, 0, 0, 14, 15, 14, 15);

			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.gas_analyser_gui.label_1000"), w / 2 + -102, h / 2 + -96, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					O2textRedactProcedure.execute(world, x, y, z), w / 2 + -131, h / 2 + -46, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					TemperatureTextRedactProcedure.execute(world, x, y, z), w / 2 + -102, h / 2 + -74, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					KPaTextRedactProcedure.execute(world, x, y, z), w / 2 + -102, h / 2 + -85, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					N2textRedactProcedure.execute(world, x, y, z), w / 2 + -131, h / 2 + -30, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					O2PercentTextProcedure.execute(world, x, y, z), w / 2 + -69, h / 2 + -46, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					N2PercentTextProcedure.execute(world, x, y, z), w / 2 + -69, h / 2 + -30, -1, false);
		}
	}
}