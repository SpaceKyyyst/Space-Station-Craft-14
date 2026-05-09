
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.init.Ssc14ModItems;

import io.netty.buffer.Unpooled;

public class ClosetOpenCloseBASEPrProcedure {
	
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null) return;
		
		// ✅ Выполняем основную логику ТОЛЬКО на сервере (предотвращает дублирование действий и звуков)
		if (world.isClientSide()) return;
		
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(pos);
		
		
		boolean isOpen = getBooleanProperty(blockstate, "open", false);
		boolean shiftPressed = entity.isShiftKeyDown();
			
		if (!isOpen) {
				// 🔊 Звук: открытие дверцы шкафа
				setBooleanProperty(world, pos, blockstate, "open", true);
				playSound(world, x, y, z, pos, "ssc_14:locker_open");
				handleOpenActions(world, x, y, z, pos, entity);
				
		} else if (isOpen) {
			// 🔊 Звук: закрытие дверцы шкафа
			setBooleanProperty(world, pos, blockstate, "open", false);
			playSound(world, x, y, z, pos, "ssc_14:locker_close");
			handleCloseActions(world, x, y, z, pos, entity);
		}
	}
	
	// === ОБРАБОТКА ОТКРЫТИЯ ===
	private static void handleOpenActions(LevelAccessor world, double x, double y, double z, BlockPos pos, Entity entity) {
		double minX = x + 2.0 / 16.0;
		double maxX = x + 14.0 / 16.0;
		double minY = y + 1.0 / 16.0;
		double maxY = y + 2.0 - 1.0 / 16.0;
		double minZ = z + 2.0 / 16.0;
		double maxZ = z + 14.0 / 16.0;
		AABB lockerArea = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
		
		if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) instanceof IItemHandler handler) {
			for (int _slot = 0; _slot < handler.getSlots(); _slot++) {
				ItemStack _stack = handler.getStackInSlot(_slot);
				if (!_stack.isEmpty()) {
					ItemStack _extracted = handler.extractItem(_slot, _stack.getCount(), false);
					if (!_extracted.isEmpty()) {
						ItemEntity _itemEntity = new ItemEntity((Level) world, x + 0.5, y + 0.1, z + 0.5, _extracted);
						_itemEntity.setDeltaMovement(new Vec3(
							world.getRandom().nextDouble() * 0.04 - 0.02,
							0.03,
							world.getRandom().nextDouble() * 0.04 - 0.02
						));
						_itemEntity.setPickUpDelay(10);
						world.addFreshEntity(_itemEntity);
					}
				}
			}
		}
		
		for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, lockerArea, e -> true)) {
			if (entityiterator instanceof Player player || 
			    entityiterator.getType().is(net.minecraft.tags.TagKey.create(
			        net.minecraft.core.registries.Registries.ENTITY_TYPE, 
			        ResourceLocation.parse("ssc14:lockableentitys")))) {
				
				if (entityiterator instanceof Player player) {
					player.setDeltaMovement(Vec3.ZERO);
					player.fallDistance = 0;
					player.setOnGround(true);
					player.setInvisible(false);
				} else {
					entityiterator.setDeltaMovement(Vec3.ZERO);
					entityiterator.setInvisible(false);
				}
				entityiterator.getPersistentData().remove("ssc14:lockedInCloset");
			}
		}
	}
	
	// === ОБРАБОТКА ЗАКРЫТИЯ ===
	private static void handleCloseActions(LevelAccessor world, double x, double y, double z, BlockPos pos, Entity entity) {
		double minX = x + 2.0 / 16.0;
		double maxX = x + 14.0 / 16.0;
		double minY = y + 1.0 / 16.0;
		double maxY = y + 2.0 - 1.0 / 16.0;
		double minZ = z + 2.0 / 16.0;
		double maxZ = z + 14.0 / 16.0;
		AABB lockerArea = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
		
		for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, lockerArea, e -> true)) {
			if (entityiterator instanceof ItemEntity itemEntity) {
				if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) instanceof IItemHandlerModifiable handler) {
					ItemStack _stack = itemEntity.getItem().copy();
					ItemStack _remaining = ItemHandlerHelper.insertItemStacked(handler, _stack, false);
					if (_remaining.isEmpty()) {
						itemEntity.discard();
					} else {
						itemEntity.setItem(_remaining);
					}
				}
			} else if (entityiterator instanceof Player player || 
			           entityiterator.getType().is(net.minecraft.tags.TagKey.create(
			               net.minecraft.core.registries.Registries.ENTITY_TYPE, 
			               ResourceLocation.parse("ssc14:lockableentitys")))) {
				
				entityiterator.teleportTo((x + 0.5), (y + 0.08), (z + 0.5));
				if (entityiterator instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((x + 0.5), (y + 0.08), (z + 0.5), entityiterator.getYRot(), entityiterator.getXRot());
				
				entityiterator.setDeltaMovement(Vec3.ZERO);
				entityiterator.fallDistance = 0;
				entityiterator.setOnGround(true);
				entityiterator.setInvisible(true);
				entityiterator.getPersistentData().putBoolean("ssc14:lockedInCloset", true);
			}
		}
	}
	
	// === ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ===
	private static boolean getBooleanProperty(BlockState state, String name, boolean defaultValue) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(name);
		return (prop instanceof BooleanProperty bp) ? state.getValue(bp) : defaultValue;
	}
	
	private static void setBooleanProperty(LevelAccessor world, BlockPos pos, BlockState state, String name, boolean value) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(name);
		if (prop instanceof BooleanProperty bp) {
			world.setBlock(pos, state.setValue(bp, value), 3);
		}
	}
	
	// ✅ Исправлено: звук проигрывается ТОЛЬКО на сервере (клиент получит его автоматически)
	private static void playSound(LevelAccessor world, double x, double y, double z, BlockPos pos, String soundId) {
		if (world instanceof Level _level && !_level.isClientSide()) {
			var soundOpt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse(soundId));
			if (soundOpt.isPresent()) {
				_level.playSound(null, pos, soundOpt.get(), SoundSource.BLOCKS, 1, 1);
			}
		}
	}
}
