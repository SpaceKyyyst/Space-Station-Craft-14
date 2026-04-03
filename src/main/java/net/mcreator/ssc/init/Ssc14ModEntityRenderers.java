/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.renderer.WallCarcaseEntitRenderer;
import net.mcreator.ssc.client.renderer.PlassteelWallCarcaseEntitRenderer;
import net.mcreator.ssc.client.renderer.IDConsoleENTITYRenderer;
import net.mcreator.ssc.client.renderer.ChairEntityRenderer;
import net.mcreator.ssc.client.renderer.C4CrutchEntRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(Ssc14ModEntities.WALL_CARCASE_ENTIT.get(), WallCarcaseEntitRenderer::new);
		event.registerEntityRenderer(Ssc14ModEntities.ID_CONSOLE_ENTITY.get(), IDConsoleENTITYRenderer::new);
		event.registerEntityRenderer(Ssc14ModEntities.PLASSTEEL_WALL_CARCASE_ENTIT.get(), PlassteelWallCarcaseEntitRenderer::new);
		event.registerEntityRenderer(Ssc14ModEntities.CHAIR_ENTITY.get(), ChairEntityRenderer::new);
		event.registerEntityRenderer(Ssc14ModEntities.C_4_CRUTCH_ENT.get(), C4CrutchEntRenderer::new);
	}
}