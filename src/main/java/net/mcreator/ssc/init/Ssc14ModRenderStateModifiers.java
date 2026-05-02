package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.context.ContextKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;

import com.google.common.reflect.TypeToken;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModRenderStateModifiers {
	public static final ContextKey<LivingEntity> LIVING_ENTITY = new ContextKey<>(ResourceLocation.parse("c:living_entity_attachment"));

	@SubscribeEvent
	public static void registerModifiers(RegisterRenderStateModifiersEvent event) {
		event.registerEntityModifier(new TypeToken<LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>>() {
		}, (entity, state) -> state.setRenderData(LIVING_ENTITY, (LivingEntity) entity));
	}

	public static LivingEntityRenderState cloneRenderState(LivingEntityRenderState newState, LivingEntityRenderState originalState) {
		newState.x = originalState.x;
		newState.y = originalState.y;
		newState.z = originalState.z;
		newState.ageInTicks = originalState.ageInTicks;
		newState.distanceToCameraSq = originalState.distanceToCameraSq;
		newState.displayFireAnimation = originalState.displayFireAnimation;
		newState.wornHeadAnimationPos = originalState.wornHeadAnimationPos;
		newState.partialTick = originalState.partialTick;
		newState.passengerOffset = originalState.passengerOffset;
		newState.eyeHeight = originalState.eyeHeight;
		newState.bodyRot = originalState.bodyRot;
		newState.yRot = originalState.yRot;
		newState.xRot = originalState.xRot;
		newState.deathTime = originalState.deathTime;
		newState.walkAnimationPos = originalState.walkAnimationPos;
		newState.walkAnimationSpeed = originalState.walkAnimationSpeed;
		newState.scale = originalState.scale;
		newState.ageScale = originalState.ageScale;
		newState.isBaby = originalState.isBaby;
		newState.hasRedOverlay = originalState.hasRedOverlay;
		newState.isInvisibleToPlayer = originalState.isInvisibleToPlayer;
		return newState;
	}
}