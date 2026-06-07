package net.mcreator.ssc.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.procedures.HealthAnalyzerUI_Display_ConditionsProcedure;
import net.mcreator.ssc.procedures.HealthAnalyzerUIEntityDisplayProcedure;

@EventBusSubscriber(Dist.CLIENT)
public class HealthAnalyzerUIOverlay {
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/health_analyzer_uv.png");

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
		if (HealthAnalyzerUI_Display_ConditionsProcedure.execute(entity)) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, 22, h / 2 + -116, 0, 0, 149, 225, 149, 225);

			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.health_analyzer_ui.label_miekhanichieskiie"), 37, h / 2 + -55, -16777216, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.health_analyzer_ui.label_fizichieskiie"), 37, h / 2 + -19, -16777216, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.health_analyzer_ui.label_toksiny"), 37, h / 2 + 26, -16777216, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.health_analyzer_ui.label_niekhvatka_vozdukha"), 37, h / 2 + 53, -16777216, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.health_analyzer_ui.label_gienietichieskiie"), 37, h / 2 + 80, -16777216, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.ssc_14.health_analyzer_ui.label_proc_hau_itext_all_damage"), 27, h / 2 + -67, -16777216, false);
			if (HealthAnalyzerUIEntityDisplayProcedure.execute(entity) instanceof LivingEntity livingEntity) {
				InventoryScreen.renderEntityInInventoryFollowsAngle(event.getGuiGraphics(), -958, h / 2 + -1081, 1042, h / 2 + 919, 15, -livingEntity.getBbHeight() / (2.0f * livingEntity.getScale()), 0f, 0, livingEntity);
			}
		}
	}
}