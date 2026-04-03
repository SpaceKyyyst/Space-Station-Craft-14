package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

import java.util.Optional;

import io.netty.buffer.Unpooled;

public class BaseAirlockOpenCloseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		// === КОНСТАНТЫ (без пробелов!) ===
		final String[] ACCESS_ROLES = {"Technical", "Service", "Out", "gun_room", "HoS", "Brig", "Medical", "Crio", "Security", "Ingeneer", "Command", "Detective", "PNT", "Scientist", "Supply_Deportament", "Atmos", "Kitchen", "Uridic", "Gidroponic",
				"Teatre", "Bar", "Cleaner", "Utilizat", "Chemistry", "Church", "CE", "Qm", "CMO", "RD", "HoP", "Capitan", "Blue_Sh"};
		final String PROP_PANEL_OPEN = "panel_open";
		final String PROP_BOLTED = "bolted";
		final String PROP_EMERGENCY_ACS = "emergency_acs";
		final String PROP_BLOCKSTATE = "blockstate";
		final String TAG_OPENING = "Opening";
		if (entity == null)
			return;
		BlockPos pos = BlockPos.containing(x, y, z);
		// === 1. Проверка предмета доступа (Access Config) ===
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
		// === 2. Проверка панели (Cable Panel) ===
		BooleanProperty panelProp = (BooleanProperty) blockstate.getBlock().getStateDefinition().getProperty(PROP_PANEL_OPEN);
		boolean panelOpen = (panelProp != null) && blockstate.getValue(panelProp);
		if (panelOpen && !entity.isShiftKeyDown()) {
			if (entity instanceof ServerPlayer _ent) {
				_ent.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("CabelPannelAirlock");
					}

					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new CabelPannelAirlockMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
					}
				}, pos);
			}
			return;
		}
		// === 3. Логика шлюза: открытие/закрытие ===
		boolean shiftPressed = entity.isShiftKeyDown();
		boolean shouldProcess = (!panelOpen) || (panelOpen && shiftPressed);
		if (!shouldProcess)
			return;
		// Проверка bolted
		BooleanProperty boltedProp = (BooleanProperty) blockstate.getBlock().getStateDefinition().getProperty(PROP_BOLTED);
		if ((boltedProp != null) && blockstate.getValue(boltedProp))
			return;
		// Проверка emergency
		BooleanProperty emergencyProp = (BooleanProperty) blockstate.getBlock().getStateDefinition().getProperty(PROP_EMERGENCY_ACS);
		boolean isEmergency = (emergencyProp != null) && blockstate.getValue(emergencyProp);
		// === ПРОВЕРКА ДОСТУПА (только если не аварийный режим) ===
		if (!isEmergency) {
			boolean hasAccess = true;
			for (String role : ACCESS_ROLES) {
				boolean entityHasRole = entity.getPersistentData().getBooleanOr(role, false);
				BlockEntity be = world.getBlockEntity(pos);
				boolean blockRequiresRole = (be != null) && be.getPersistentData().getBooleanOr(role, false);
				if (blockRequiresRole && !entityHasRole) {
					hasAccess = false;
					break;
				}
			}
			if (!hasAccess) {
				// ЗВУК ОТКАЗА — ТОЛЬКО ЗДЕСЬ, и сразу выходим
				if (world instanceof Level level) {
					Optional<SoundEvent> soundOpt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse("ssc_14:airlock_no_access"));
					if (soundOpt.isPresent()) {
						SoundEvent sound = soundOpt.get();
						if (!level.isClientSide())
							level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1, 1);
						else
							level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return; // ← НЕ запускаем анимацию, если нет доступа
			}
		}
		// === Устанавливаем флаг открытия (для синхронизации) ===
		if (!world.isClientSide()) {
			BlockEntity be = world.getBlockEntity(pos);
			if (be != null) {
				be.getPersistentData().putBoolean(TAG_OPENING, true);
				if (world instanceof Level _level)
					_level.sendBlockUpdated(pos, blockstate, blockstate, 3);
			}
		}
		// === ЗАПУСК АНИМАЦИИ ===
		Ssc14Mod.queueServerWork(1, () -> {
			BlockPos animPos = BlockPos.containing(x, y, z);
			BlockState currentState = world.getBlockState(animPos);
			if (!(world instanceof Level level))
				return;
			// Получаем isEmergencyACS заново (не переобъявляем emergencyProp!)
			BooleanProperty ep = (BooleanProperty) currentState.getBlock().getStateDefinition().getProperty(PROP_EMERGENCY_ACS);
			boolean isEmergencyACS = (ep != null) && currentState.getValue(ep);
			// === ОТКРЫТИЕ: если блок закрытый ===
			boolean isBaseAirlockClosed = currentState.getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get();
			IntegerProperty stateProp = (IntegerProperty) currentState.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
			int blockStateVal = (stateProp != null) ? currentState.getValue(stateProp) : -1;
			boolean validState = (blockStateVal == 0 || blockStateVal == 6 || blockStateVal == 7 || blockStateVal == 8);
			if (isBaseAirlockClosed && validState) {
				// Звук открытия
				Optional<SoundEvent> openSoundOpt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse("ssc_14:airlock_open"));
				if (openSoundOpt.isPresent()) {
					SoundEvent openSound = openSoundOpt.get();
					if (!level.isClientSide())
						level.playSound(null, animPos, openSound, SoundSource.NEUTRAL, 1, 1);
					else
						level.playLocalSound(x, y, z, openSound, SoundSource.NEUTRAL, 1, 1, false);
				}
				// Анимация состояний 1→2→3→4
				{
					BlockState bs = world.getBlockState(animPos);
					IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
					if (sp != null && sp.getPossibleValues().contains(1))
						world.setBlock(animPos, bs.setValue(sp, 1), 3);
				}
				Ssc14Mod.queueServerWork(2, () -> {
					{
						BlockState bs = world.getBlockState(animPos);
						IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
						if (sp != null && sp.getPossibleValues().contains(2))
							world.setBlock(animPos, bs.setValue(sp, 2), 3);
					}
					Ssc14Mod.queueServerWork(2, () -> {
						{
							BlockState bs = world.getBlockState(animPos);
							IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
							if (sp != null && sp.getPossibleValues().contains(3))
								world.setBlock(animPos, bs.setValue(sp, 3), 3);
						}
						Ssc14Mod.queueServerWork(2, () -> {
							{
								BlockState bs = world.getBlockState(animPos);
								IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
								if (sp != null && sp.getPossibleValues().contains(4))
									world.setBlock(animPos, bs.setValue(sp, 4), 3);
							}
							Ssc14Mod.queueServerWork(2, () -> {
								// Замена на открытый блок с сохранением NBT
								BlockPos swapPos = BlockPos.containing(x, y, z);
								BlockEntity oldBe = level.getBlockEntity(swapPos);
								CompoundTag persistentData = (oldBe != null) ? oldBe.getPersistentData().copy() : new CompoundTag();
								BlockState newBs = Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get().defaultBlockState();
								BlockState oldBs = level.getBlockState(swapPos);
								@SuppressWarnings({"rawtypes", "unchecked"})
								var props = oldBs.getProperties();
								for (@SuppressWarnings({"rawtypes", "unchecked"})
								Property pOld : props) {
									@SuppressWarnings({"rawtypes", "unchecked"})
									Property pNew = newBs.getBlock().getStateDefinition().getProperty(pOld.getName());
									if (pNew != null) {
										try {
											newBs = newBs.setValue(pNew, oldBs.getValue(pOld));
										} catch (Exception e) {
										}
									}
								}
								level.setBlock(swapPos, newBs, 3);
								BlockEntity newBe = level.getBlockEntity(swapPos);
								if (newBe != null && !persistentData.isEmpty())
									newBe.getPersistentData().merge(persistentData);
								// Верхний блок
								BlockPos topPos = BlockPos.containing(x, y + 1, z);
								BlockState newTopBs = Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get().defaultBlockState();
								BlockState oldTopBs = level.getBlockState(topPos);
								@SuppressWarnings({"rawtypes", "unchecked"})
								var topProps = oldTopBs.getProperties();
								for (@SuppressWarnings({"rawtypes", "unchecked"})
								Property pOld : topProps) {
									@SuppressWarnings({"rawtypes", "unchecked"})
									Property pNew = newTopBs.getBlock().getStateDefinition().getProperty(pOld.getName());
									if (pNew != null) {
										try {
											newTopBs = newTopBs.setValue(pNew, oldTopBs.getValue(pOld));
										} catch (Exception e) {
										}
									}
								}
								level.setBlock(topPos, newTopBs, 3);
							});
						});
					});
				});
			}
			// === ЗАКРЫТИЕ: если блок открытый (работает БЕЗ проверки на сущности) ===
			else if (currentState.getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get()) {
				// Звук закрытия
				Optional<SoundEvent> closeSoundOpt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse("ssc_14:airlock_close"));
				if (closeSoundOpt.isPresent()) {
					SoundEvent closeSound = closeSoundOpt.get();
					if (!level.isClientSide())
						level.playSound(null, animPos, closeSound, SoundSource.NEUTRAL, 1, 1);
					else
						level.playLocalSound(x, y, z, closeSound, SoundSource.NEUTRAL, 1, 1, false);
				}
				// Замена на закрытый блок с сохранением NBT
				BlockPos swapPos = BlockPos.containing(x, y, z);
				BlockEntity oldBe = level.getBlockEntity(swapPos);
				CompoundTag persistentData = (oldBe != null) ? oldBe.getPersistentData().copy() : new CompoundTag();
				BlockState newBs = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState();
				BlockState oldBs = level.getBlockState(swapPos);
				@SuppressWarnings({"rawtypes", "unchecked"})
				var props = oldBs.getProperties();
				for (@SuppressWarnings({"rawtypes", "unchecked"})
				Property pOld : props) {
					@SuppressWarnings({"rawtypes", "unchecked"})
					Property pNew = newBs.getBlock().getStateDefinition().getProperty(pOld.getName());
					if (pNew != null) {
						try {
							newBs = newBs.setValue(pNew, oldBs.getValue(pOld));
						} catch (Exception e) {
						}
					}
				}
				level.setBlock(swapPos, newBs, 3);
				BlockEntity newBe = level.getBlockEntity(swapPos);
				if (newBe != null && !persistentData.isEmpty())
					newBe.getPersistentData().merge(persistentData);
				// Верхний блок
				BlockPos topPos = BlockPos.containing(x, y + 1, z);
				BlockState newTopBs = Ssc14ModBlocks.AIRLOCK_UP_PLUG.get().defaultBlockState();
				BlockState oldTopBs = level.getBlockState(topPos);
				@SuppressWarnings({"rawtypes", "unchecked"})
				var topProps = oldTopBs.getProperties();
				for (@SuppressWarnings({"rawtypes", "unchecked"})
				Property pOld : topProps) {
					@SuppressWarnings({"rawtypes", "unchecked"})
					Property pNew = newTopBs.getBlock().getStateDefinition().getProperty(pOld.getName());
					if (pNew != null) {
						try {
							newTopBs = newTopBs.setValue(pNew, oldTopBs.getValue(pOld));
						} catch (Exception e) {
						}
					}
				}
				level.setBlock(topPos, newTopBs, 3);
				// Обратная анимация 4→3→2→1→0
				{
					BlockState bs = world.getBlockState(animPos);
					IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
					if (sp != null && sp.getPossibleValues().contains(4))
						world.setBlock(animPos, bs.setValue(sp, 4), 3);
				}
				Ssc14Mod.queueServerWork(2, () -> {
					{
						BlockState bs = world.getBlockState(animPos);
						IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
						if (sp != null && sp.getPossibleValues().contains(3))
							world.setBlock(animPos, bs.setValue(sp, 3), 3);
					}
					Ssc14Mod.queueServerWork(2, () -> {
						{
							BlockState bs = world.getBlockState(animPos);
							IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
							if (sp != null && sp.getPossibleValues().contains(2))
								world.setBlock(animPos, bs.setValue(sp, 2), 3);
						}
						Ssc14Mod.queueServerWork(2, () -> {
							{
								BlockState bs = world.getBlockState(animPos);
								IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
								if (sp != null && sp.getPossibleValues().contains(1))
									world.setBlock(animPos, bs.setValue(sp, 1), 3);
							}
							Ssc14Mod.queueServerWork(2, () -> {
								{
									BlockState bs = world.getBlockState(animPos);
									IntegerProperty sp = (IntegerProperty) bs.getBlock().getStateDefinition().getProperty(PROP_BLOCKSTATE);
									if (sp != null && sp.getPossibleValues().contains(0))
										world.setBlock(animPos, bs.setValue(sp, 0), 3);
								}
							});
						});
					});
				});
			}
			// Если блок не распознан — ничего не делаем (без звука!)
		});
		// .
		if (blockstate.getBlock().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			//КОСТЫЛЬ, НЕ УБИРАТЬ, ЕСЛИ ИСПОЛЬЗУЕТЕ MCreator. НА НЁМ ДЕРЖИТСЯ ПРОЦЕДУРА
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("Wait_WHAT") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
			}
			if (entity instanceof Player _player) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				_player.level().getBlockState(_bp).useWithoutItem(_player.level(), _player, BlockHitResult.miss(new Vec3(_bp.getX(), _bp.getY(), _bp.getZ()), Direction.UP, _bp));
			}
		}
	}
}