
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

public class Closet_OpenClose_PrProcedure {
	
	private static final String[] ACCESS_ROLES = {
		"Technical", "Service", "Out", "gun_room", "HoS", "Brig", "Medical", "Crio", 
		"Security", "Engineer", "Command", "Detective", "PNT", "Scientist", 
		"Supply_Department", "Atmos", "Kitchen", "Uridic", "Gidroponic", "Teatre", 
		"Bar", "Cleaner", "Utilizat", "Chemistry", "Church", "CE", "Qm", "CMO", 
		"RD", "HoP", "Captain", "Blue_Sh"
	};

	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null) return;
		if (world.isClientSide()) return;
		
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(pos);
		
		// === ПРОВЕРКА: ACCESS_CONFIG → открыть меню доступа ===
		ItemStack mainHand = (entity instanceof LivingEntity _livEnt) ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
		if (mainHand.getItem() == Ssc14ModItems.ACCESS_CONFIG.get() && !entity.isShiftKeyDown()) {
			if (entity instanceof ServerPlayer _ent) {
				_ent.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("AccessConfigMENU");
					}
					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}
					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new AccessConfigMENUMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
					}
				}, pos);
			}
			return;
		}
		
		// === ОПРЕДЕЛЕНИЕ СОСТОЯНИЯ И ДЕЙСТВИЯ ===
		boolean isBlocked = getBooleanProperty(blockstate, "blocked", false);
		boolean isOpen = getBooleanProperty(blockstate, "open", false);
		boolean shiftPressed = entity.isShiftKeyDown();
		
		// 🔐 Доступ проверяется ТОЛЬКО для операций с замком:
		// 1. Разблокировка запертого шкафа (isBlocked=true)
		// 2. Запирание разблокированного закрытого шкафа (shiftPressed && !isOpen && !isBlocked)
		boolean needsAccessCheck = isBlocked || (shiftPressed && !isOpen && !isBlocked);
		
		// === ПРОВЕРКА ДОСТУПА (только если нужно) ===
		if (needsAccessCheck) {
			CompoundTag entityData = entity.getPersistentData();
			CompoundTag blockData = (be != null) ? be.getPersistentData() : null;
			
			boolean hasAccess = true;
			if (blockData != null) {
				for (String role : ACCESS_ROLES) {
					boolean entityHasRole = entityData.getBoolean(role).orElse(false);
					boolean blockRequiresRole = blockData.getBoolean(role).orElse(false);
					if (blockRequiresRole && !entityHasRole) {
						hasAccess = false;
						break;
					}
				}
			}
			
			if (!hasAccess) {
				showLockedMessage(entity);
				return;
			}
		}
		
		// === СТРОГАЯ ЛОГИКА ПО ТЗ ===
		// 🔊 Звуки: 
		// "ssc_14:lock_open"  — звук отпирания замка
		// "ssc_14:lock_close" — звук запирания замка
		// "ssc_14:locker_open"  — звук открытия дверцы шкафа
		// "ssc_14:locker_close" — звук закрытия дверцы шкафа
		
		if (isBlocked && !isOpen) {
			// 1️⃣ ПКМ по запертому (закрыт) → разблокировать (закрыт)
			// 🔊 Звук: отпирание замка
			setBooleanProperty(world, pos, blockstate, "blocked", false);
			playSound(world, x, y, z, pos, "ssc_14:lock_open");
			
		} else if (!isBlocked && !isOpen) {
			// Разблокирован + Закрыт
			if (shiftPressed) {
				// 2️⃣ Shift+ПКМ → запереть (закрыт)
				// 🔊 Звук: запирание замка (ЕДИНСТВЕННОЕ МЕСТО, где играет lock_close!)
				setBooleanProperty(world, pos, blockstate, "blocked", true);
				playSound(world, x, y, z, pos, "ssc_14:lock_close");
			} else {
				// 3️⃣ ПКМ → открыть (открыт) — ✅ БЕЗ ПРОВЕРКИ ДОСТУПА
				// 🔊 Звук: открытие дверцы шкафа
				setBooleanProperty(world, pos, blockstate, "open", true);
				playSound(world, x, y, z, pos, "ssc_14:locker_open");
				handleOpenActions(world, x, y, z, pos, entity);
			}
			
		} else if (!isBlocked && isOpen) {
			// 4️⃣ ПКМ по разблокированному (открыт) → закрыть (закрыт) — ✅ БЕЗ ПРОВЕРКИ ДОСТУПА
			// 🔊 Звук: закрытие дверцы шкафа
			setBooleanProperty(world, pos, blockstate, "open", false);
			playSound(world, x, y, z, pos, "ssc_14:locker_close");
			handleCloseActions(world, x, y, z, pos, entity);
		}
		// Все остальные комбинации игнорируются
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
	
	private static void playSound(LevelAccessor world, double x, double y, double z, BlockPos pos, String soundId) {
		if (world instanceof Level _level && !_level.isClientSide()) {
			var soundOpt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse(soundId));
			if (soundOpt.isPresent()) {
				_level.playSound(null, pos, soundOpt.get(), SoundSource.BLOCKS, 1, 1);
			}
		}
	}
	
	private static void showLockedMessage(Entity entity) {
	    if (entity instanceof ServerPlayer player) {
	        Component msg = Component.literal("§f§o" + Component.translatable("translation.key.itclosed").getString() + "§r");
	        player.displayClientMessage(msg, true);
	    }
	}
}
