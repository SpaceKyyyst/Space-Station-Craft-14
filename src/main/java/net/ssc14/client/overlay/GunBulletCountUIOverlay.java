package net.ssc14.client.overlay;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import net.ssc14.content.firearm.FirearmUiService;

@EventBusSubscriber(Dist.CLIENT)
public class GunBulletCountUIOverlay {
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/mk_58_bullet_ui.png");
	private static final Identifier IMAGE_1 = Identifier.parse("ssc_14:textures/screens/bullet_ui_sprite_2.png");
	private static final Identifier SPRITE_0 = Identifier.parse("ssc_14:textures/screens/bullet_ui_sprites.png");

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
		if (FirearmUiService.isMk58(entity)) {
			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, w / 2 + -117, h - 38, 0, 0, 26, 16, 26, 16);

			if (FirearmUiService.chamberLoaded(entity)) {
				event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, IMAGE_1, w / 2 + -115, h - 32, 0, 0, 6, 4, 6, 4);
			}

			event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, SPRITE_0, w / 2 + -111, h - 38, 0, Mth.clamp((int) FirearmUiService.magazineBullets(entity) * 16, 0, 160), 20, 16, 20, 176);

		}
	}
}
