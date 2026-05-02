package net.mcreator.ssc.init;

import top.theillusivec4.curios.api.client.ICurioRenderer;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import net.mcreator.ssc.client.renderer.*;

public class Ssc14ModCuriosRenderers {
	public static void registerRenderers(FMLClientSetupEvent event) {
		ICurioRenderer.register(Ssc14ModItems.RED_SCARF.get(), RedScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BLACK_SCARF.get(), BlackScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BLACK_GREEN_SCARF.get(), BlackGreenScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BLUE_SCARF.get(), BlueScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BROWN_SCARF.get(), BrownScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.GREEN_SCARF.get(), GreenScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.LIGHT_BLUE_SCARF.get(), LightBlueScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.LIGHT_BLUE_WHITE_SCARF.get(), LightBlueWhiteScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.ORANGE_SCARF.get(), OrangeScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.PURPLE_SCARF.get(), PurpleScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.SINDY_SCARF.get(), SindyScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.TRNS_SCARF.get(), TrnsScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.YELLOW_SCARF.get(), YellowScarfCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.ISOLATED_GLOVES.get(), IsolatedGlovesCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.SECURITY_JUMPSUIT.get(), SecurityRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.COMBAT_BOOTS_ITEM.get(), CombatBootsRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get(), MagneticBootsRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get(), MagneticBootsACTIVERenderer::new);
	}
}