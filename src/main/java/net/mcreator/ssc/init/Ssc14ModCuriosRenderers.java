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
		ICurioRenderer.register(Ssc14ModItems.PASSANGER_JUMPSUIT.get(), PassangerRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.ATMOS_JUMPSUIT.get(), AtmosRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BOTANIST_JUMPSUIT.get(), BotanistRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CAPTAIN_JUMPSUIT.get(), CaptainRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CARGO_JUMPSUIT.get(), CargoRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CE_JUMPSUIT.get(), CeRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CHEMIST_JUMPSUIT.get(), ChemistRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CMO_JUMPSUIT.get(), CMORenderer::new);
		ICurioRenderer.register(Ssc14ModItems.ENGENEER_JUMPSUIT.get(), EngeneerRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.HO_P_JUMPSUIT.get(), HoPRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.HO_S_JUMPSUIT.get(), HoSRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MEDICAL_JUMPSUIT.get(), MedicalRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.QM_JUMPSUIT.get(), QMRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.RD_JUMPSUIT.get(), RDRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.RND_JUMPSUIT.get(), RNDRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.WARDEN_JUMPSUIT.get(), WardenRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BLACK_SOLES_ITEM.get(), SolesRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CLOAKS_CAP.get(), CloaksCapCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MANTLES_CAP.get(), MantlesCapCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.ARMOR_SECURITY.get(), ArmorSecurityCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.HELMET_SECURITY.get(), HelmetSecurityCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BELT_SECURITY.get(), BeltSecurityCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.HEADSET_BASE.get(), HeadsetBaseCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.PD_APASSANGER.get(), PDApassangerCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.OUTERCLOTHING_EVA.get(), OuterclothingEvaCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.HELMETS_EVA.get(), HelmetsEvaCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MALE_BOXERS.get(), MaleBoxersCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.GLASS_SECURITY.get(), GlassSecurityCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.GAS_MASK.get(), GasMaskCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.WORK_BOOTS.get(), WorkBootsCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CLOAKS_HO_S.get(), CloaksHoSCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MANTLES_HO_S_SHOULDER_CAP.get(), MantlesHoSShoulderCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.MANTLES_HO_S.get(), MantlesHoSCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.COAT_ATMOS.get(), CoatAtmosCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.COAT_SECURITY.get(), CoatSecurityCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.TIE_BLACK.get(), TieBlackCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.TIE_RED.get(), TieRedCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.TIE_SCIENCE.get(), TieScienceCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.GLASS_GLASSES.get(), GlassGlassesCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.GLASS_DARK.get(), GlassDarkCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.BLACK_GLOVES.get(), BlackGlovesCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.HELMET_COSMONAUT.get(), HelmetCosmonautCAPIRenderer::new);
		ICurioRenderer.register(Ssc14ModItems.CONE.get(), ConeHelmetRenderer::new);
	}
}