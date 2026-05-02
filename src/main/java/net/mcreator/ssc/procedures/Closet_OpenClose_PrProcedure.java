package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import java.util.Comparator;

public class Closet_OpenClose_PrProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		// === ОТКРЫТИЕ ШКАФА: ИЗВЛЕЧЕНИЕ ПРЕДМЕТОВ ===
		if (false == (getPropertyByName(blockstate, "open") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1))) {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:locker_open")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:locker_open")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			{
				// 📦 ИЗВЛЕЧЕНИЕ ПРЕДМЕТОВ ИЗ ИНВЕНТАРЯ БЛОКА
				BlockPos _pos = BlockPos.containing(x, y, z);
				if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, _pos, null) instanceof IItemHandler handler) {
					for (int _slot = 0; _slot < handler.getSlots(); _slot++) {
						ItemStack _stack = handler.getStackInSlot(_slot);
						if (!_stack.isEmpty()) {
							ItemStack _extracted = handler.extractItem(_slot, _stack.getCount(), false);
							if (!_extracted.isEmpty()) {
								ItemEntity _itemEntity = new ItemEntity((Level) world, x + 0.5, y + 0.1, z + 0.5, _extracted);
								// 🐌 Уменьшенная скорость: оригинальные значения / 5
								_itemEntity.setDeltaMovement(new Vec3(world.getRandom().nextDouble() * 0.04 - 0.02, // было: * 0.2 - 0.1
										0.03, // было: 0.15
										world.getRandom().nextDouble() * 0.04 - 0.02 // было: * 0.2 - 0.1
								));
								_itemEntity.setPickUpDelay(10);
								world.addFreshEntity(_itemEntity);
								if (world instanceof ServerLevel _level) {
								}
							}
						}
					}
				}
				// 🔄 Разморозка игроков и лок-сущностей при открытии
				final Vec3 _center = new Vec3((x + 0.5), (y + 0.4), (z + 0.5));
				for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
					// ✅ Игрок ИЛИ сущность с тегом "можно запереть"
					if (entityiterator instanceof Player player || entityiterator.getType().is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, ResourceLocation.parse("ssc14:lockableentitys")))) {
						if (entityiterator instanceof Player player) {
							player.setDeltaMovement(Vec3.ZERO);
							player.fallDistance = 0;
							player.setOnGround(true);
							player.setInvisible(false);
						} else {
							// Для сущностей с тегом: просто снимаем невидимость и сбрасываем движение
							entityiterator.setDeltaMovement(Vec3.ZERO);
							entityiterator.setInvisible(false);
						}
						if (world instanceof ServerLevel _level) {
						}
						// После player.setInvisible(false); добавь:
						entityiterator.getPersistentData().remove("ssc14:lockedInCloset");
					}
				}
			}
			// === ЗАКРЫТИЕ ШКАФА: ЗАХВАТ ПРЕДМЕТОВ И ЗАМОРОЗКА ===
		} else if (true == (getPropertyByName(blockstate, "open") instanceof BooleanProperty _getbp9 && blockstate.getValue(_getbp9))) {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:locker_close")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:locker_close")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			{
				final Vec3 _center = new Vec3((x + 0.5), (y + 0.4), (z + 0.5));
				for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
					// 📦 ЗАХВАТ ПРЕДМЕТОВ-СУЩНОСТЕЙ В ИНВЕНТАРЬ
					if (entityiterator instanceof ItemEntity itemEntity) {
						BlockPos _pos = BlockPos.containing(x, y, z);
						if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, _pos, null) instanceof IItemHandlerModifiable handler) {
							ItemStack _stack = itemEntity.getItem().copy();
							ItemStack _remaining = ItemHandlerHelper.insertItemStacked(handler, _stack, false);
							if (_remaining.isEmpty()) {
								itemEntity.discard();
							} else {
								itemEntity.setItem(_remaining);
							}
						}
						if (world instanceof ServerLevel _level) {
						}
						// 🔒 ЗАМОРОЗКА: Игрок ИЛИ сущность с тегом "можно запереть"
					} else if (entityiterator instanceof Player player || entityiterator.getType().is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, ResourceLocation.parse("ssc14:lockableentitys")))) {
						// Телепортация в центр шкафа
						entityiterator.teleportTo((x + 0.5), (y + 0.08), (z + 0.5));
						if (entityiterator instanceof ServerPlayer _serverPlayer)
							_serverPlayer.connection.teleport((x + 0.5), (y + 0.08), (z + 0.5), entityiterator.getYRot(), entityiterator.getXRot());
						// Жёсткая заморозка
						entityiterator.setDeltaMovement(Vec3.ZERO);
						entityiterator.fallDistance = 0;
						entityiterator.setOnGround(true);
						entityiterator.setInvisible(true);
						if (world instanceof ServerLevel _level) {
						}
						// После entityiterator.setInvisible(true); добавь:
						entityiterator.getPersistentData().putBoolean("ssc14:lockedInCloset", true);
					}
				}
			}
		}
		if (getPropertyByName(blockstate, "kostil123") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1) && entity instanceof LivingEntity _livEnt2 && _livEnt2.isSleeping()) {
			if (world instanceof Level _level)
				_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
		}
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