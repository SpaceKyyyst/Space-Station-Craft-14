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

import net.mcreator.ssc.procedures.Thirst2PrProcedure;
import net.mcreator.ssc.procedures.Thirst2BlinkingPrProcedure;

@EventBusSubscriber(Dist.CLIENT)
public class Thirst2Overlay {
	private static final ResourceLocation SPRITE_0 = ResourceLocation.parse("ssc_14:textures/screens/thirsty3.png");

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
		if (true) {
			if (Thirst2PrProcedure.execute(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_0, w - 37, h / 2 + 16, 0, Mth.clamp((int) Thirst2BlinkingPrProcedure.execute(entity) * 32, 0, 32), 32, 32, 32, 64);
			}
		}
	}
}