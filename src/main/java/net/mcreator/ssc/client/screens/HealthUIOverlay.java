package net.mcreator.ssc.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.procedures.*;

@EventBusSubscriber(Dist.CLIENT)
public class HealthUIOverlay {
	private static final ResourceLocation SPRITE_0 = ResourceLocation.parse("ssc_14:textures/screens/dead_anim.png");
	private static final ResourceLocation SPRITE_1 = ResourceLocation.parse("ssc_14:textures/screens/critical_anim.png");
	private static final ResourceLocation SPRITE_2 = ResourceLocation.parse("ssc_14:textures/screens/health4_anim.png");
	private static final ResourceLocation SPRITE_3 = ResourceLocation.parse("ssc_14:textures/screens/health3_anim.png");
	private static final ResourceLocation SPRITE_4 = ResourceLocation.parse("ssc_14:textures/screens/health2_anim.png");
	private static final ResourceLocation SPRITE_5 = ResourceLocation.parse("ssc_14:textures/screens/health1_anim.png");
	private static final ResourceLocation SPRITE_6 = ResourceLocation.parse("ssc_14:textures/screens/health0_anim.png");

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
		if (HealthUIdisplayProcedure.execute(entity)) {
			if (HPCorpseDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_0, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
			if (HPCriticalDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_1, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
			if (HPTerribleDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_2, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
			if (HPBadDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_3, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
			if (HPNotSoGoodDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_4, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
			if (HPGoodDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_5, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
			if (HPExcellentDisplayProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_6, w - 37, h / 2 + -48, 0, Mth.clamp((int) HealthUIanimateProcedure.execute(entity) * 32, 0, 864), 32, 32, 32, 896);
			}
		}
	}
}