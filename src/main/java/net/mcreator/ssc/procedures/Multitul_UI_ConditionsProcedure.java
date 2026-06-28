
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.CableType;
import net.mcreator.ssc.EnergyNetwork;
import net.mcreator.ssc.EnergyNetworkManager;
import net.mcreator.ssc.MultitoolDataPacket;

public class Multitul_UI_ConditionsProcedure {
	public static boolean execute(LevelAccessor world, Entity entity) {
		if (entity == null || world == null)
			return false;

		ItemStack mainHand = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
		if (mainHand.isEmpty() || mainHand.getItem() != Ssc14ModItems.MULTITUL.get()) {
			return false;
		}

		CustomData customData = mainHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		int toolMode = 1;
		
		if (!customData.isEmpty() && customData.getUnsafe().contains("Mode")) {
			var modeOpt = customData.getUnsafe().getDouble("Mode");
			if (modeOpt.isPresent()) {
				toolMode = modeOpt.get().intValue();
			}
		}
		
		if (toolMode < 1 || toolMode > 3) {
			toolMode = 1;
		}

		CableType targetType = switch (toolMode) {
			case 1 -> CableType.LV;
			case 2 -> CableType.MV;
			case 3 -> CableType.HV;
			default -> CableType.LV;
		};

		HitResult hit = entity.level().clip(new ClipContext(
				entity.getEyePosition(1f), 
				entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), 
				ClipContext.Block.OUTLINE, 
				ClipContext.Fluid.NONE, 
				entity
		));

		if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult blockHit) {
			BlockPos pos = blockHit.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if (state.is(BlockTags.create(ResourceLocation.parse("ssc14:multitul_check")))) {
				String propertyName = targetType.getProperty().getName();
				boolean hasTargetCable = getPropertyByName(state, propertyName) instanceof BooleanProperty bp && state.getValue(bp);

				if (hasTargetCable) {
					if (!world.isClientSide()) {
						if (entity instanceof ServerPlayer serverPlayer) {
							
							EnergyNetworkManager.updatePosition(world, pos);

							boolean networkFound = false;
							for (EnergyNetwork net : EnergyNetworkManager.getNetworks(world, targetType)) {
								if (net.getCables().contains(pos)) {
									// ИСПРАВЛЕНИЕ ДЛЯ 1.21.8: Используем метод sendToPlayer вместо .PLAYER.send()
									PacketDistributor.sendToPlayer(serverPlayer, new MultitoolDataPacket(
										toolMode, net.currentPower, net.batteryPower, net.theoreticalSupply, net.idealConsumption,
										net.inputStored, net.inputMax, net.outputStored, net.outputMax
									));
									networkFound = true;
									break;
								}
							}
							if (!networkFound) {
								// ИСПРАВЛЕНИЕ ДЛЯ 1.21.8: Пустой пакет через sendToPlayer
								PacketDistributor.sendToPlayer(serverPlayer, new MultitoolDataPacket(toolMode, 0, 0, 0, 0, 0, 0, 0, 0));
							}
						}
					}
					return true;
				} else {
					if (!world.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
						// ИСПРАВЛЕНИЕ ДЛЯ 1.21.8: Пакет сброса через sendToPlayer
						PacketDistributor.sendToPlayer(serverPlayer, new MultitoolDataPacket(toolMode, 0, 0, 0, 0, 0, 0, 0, 0));
					}
				}
			}
		}
		return false;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}
