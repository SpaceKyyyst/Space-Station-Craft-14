package net.mcreator.ssc.init;

import net.ssc14.content.entity.Ssc14EntityTypes;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.renderer.*;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.BASE_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_basic.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.TECH_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_maint.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.ATMOSPHERICS_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_atmospherics.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.CARGO_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_cargo.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.CENT_COM_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_centcomm.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.CHEMISTRY_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_chemistry.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.COMMAND_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_command.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.ENGINEERING_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_engineering.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.HYDROPONICS_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_hydroponics.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.MEDICAL_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_medical.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.SALVAGE_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_salvage.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.SCIENCE_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_science.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.SECURITY_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_security.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.SYNDICATE_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_syndicate.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.VIROLOGY_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/standard_airlock_virology.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.GLASS_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/glass_airlock.png")));
        event.registerBlockEntityRenderer(Ssc14ModBlockEntities.BASE_GLASS_AIRLOCK_D_1.get(), context -> new AirlockDoorRenderer<>(net.minecraft.resources.Identifier.parse("ssc_14:textures/block/glass_airlock_basic.png")));
		event.registerEntityRenderer(Ssc14EntityTypes.WALL_CARCASE_ENTIT.get(), WallCarcaseEntitRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.ID_CONSOLE_ENTITY.get(), IDConsoleENTITYRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.PLASSTEEL_WALL_CARCASE_ENTIT.get(), PlassteelWallCarcaseEntitRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.CHAIR_ENTITY.get(), ChairEntityRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.C_4_CRUTCH_ENT.get(), C4CrutchEntRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.MOUSE_BROWN.get(), MouseBrownRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.MOUSE_GRAY.get(), MouseGrayRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.MOUSE_WHITE.get(), MouseWhiteRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.BULLET_35.get(), Bullet35Renderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.FOAM_DART_PROJECTILE.get(), FoamDartProjectileRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.POSTER.get(), PosterRenderer::new);
		event.registerEntityRenderer(Ssc14EntityTypes.CARP.get(), CarpRenderer::new);
	}
}
