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
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.procedures.*;

@EventBusSubscriber(Dist.CLIENT)
public class ActionIndicatorOverlay {
	@SubscribeEvent(priority = EventPriority.HIGH)
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
		if (true) {
			if (ActGUIProcedureProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/progress_bar_1.png"), w / 2 + -8, h / 2 + -16, 0, 0, 16, 16, 16, 16);
			}
			if (ActGUIProcedure2Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/progress_bar_2.png"), w / 2 + -8, h / 2 + -16, 0, 0, 16, 16, 16, 16);
			}
			if (ActGUIProcedure3Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/progress_bar_3.png"), w / 2 + -8, h / 2 + -16, 0, 0, 16, 16, 16, 16);
			}
			if (ActGUIProcedure4Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/progress_bar_4.png"), w / 2 + -8, h / 2 + -16, 0, 0, 16, 16, 16, 16);
			}
			if (ActGUIProcedure5Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/progress_bar_5.png"), w / 2 + -8, h / 2 + -16, 0, 0, 16, 16, 16, 16);
			}
			if (ActGUIProcedure6Procedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/progress_bar_6.png"), w / 2 + -8, h / 2 + -16, 0, 0, 16, 16, 16, 16);
			}
		}
	}
}