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

import net.mcreator.ssc.procedures.WorldObjectCheckUIProcedure;
import net.mcreator.ssc.procedures.WOCtextPinnedProcedure;
import net.mcreator.ssc.procedures.WOCtextP2Procedure;
import net.mcreator.ssc.procedures.WOCtextP1Procedure;
import net.mcreator.ssc.procedures.WOCtextNoPinnedProcedure;

@EventBusSubscriber(Dist.CLIENT)
public class WorldCheckUIOverlay {
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
		if (WorldObjectCheckUIProcedure.execute(world, entity)) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/world_check_ui.png"), w / 2 + 1, h / 2 + 0, 0, 0, 151, 100, 151, 100);

			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					WOCtextP1Procedure.execute(world, entity), w / 2 + 5, h / 2 + 3, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.world_check_ui.label_proc_wo_ctext_p_2"), w / 2 + 5, h / 2 + 26, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					WOCtextP2Procedure.execute(world, entity), w / 2 + 5, h / 2 + 38, -16711681, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.world_check_ui.label_eto"), w / 2 + 5, h / 2 + 14, -1, false);
			if (WOCtextNoPinnedProcedure.execute())
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.world_check_ui.label_nie_zakrieplieno"), w / 2 + 28, h / 2 + 14, -7667712, false);
			if (WOCtextPinnedProcedure.execute(entity))
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.world_check_ui.label_zakrieplieno"), w / 2 + 28, h / 2 + 14, -16744704, false);
		}
	}
}