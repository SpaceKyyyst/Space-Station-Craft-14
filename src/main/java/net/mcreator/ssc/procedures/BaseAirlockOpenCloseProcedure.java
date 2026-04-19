package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.Ssc14Mod;

import java.util.Optional;

import io.netty.buffer.Unpooled;

public class BaseAirlockOpenCloseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if ((getPropertyByName(blockstate, "kostil") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) == 13 && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:skeletons")))
				&& (world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("minecraft:logs")))) {
			// 👨‍🦽 КОСТЫЛЬ ДЛЯ ИМПОРТОВ MCreator
			if (world instanceof ServerLevel _level) {
				_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("Airlock error").withColor(0x000000).withStyle(ChatFormatting.ITALIC), false);
			}
		}
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null && be.getPersistentData().getBooleanOr("_ssc14_processing", false))
			return;
		if (!world.isClientSide() && be != null)
			be.getPersistentData().putBoolean("_ssc14_processing", true);
		try {
			final String[] ACCESS_ROLES = {"Technical", "Service", "Out", "gun_room", "HoS", "Brig", "Medical", "Crio", "Security", "Engineer", "Command", "Detective", "PNT", "Scientist", "Supply_Department", "Atmos", "Kitchen", "Uridic",
					"Gidroponic", "Teatre", "Bar", "Cleaner", "Utilizat", "Chemistry", "Church", "CE", "Qm", "CMO", "RD", "HoP", "Captain", "Blue_Sh"};
			final String PROP_PANEL_OPEN = "panel_open";
			final String PROP_BOLTED = "bolted";
			final String PROP_EMERGENCY_ACS = "emergency_acs";
			final String PROP_BLOCKSTATE = "blockstate";
			final String TAG_OPENING = "Opening";
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
			BooleanProperty panelProp = _getProperty(blockstate, PROP_PANEL_OPEN, BooleanProperty.class);
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
			boolean shiftPressed = entity.isShiftKeyDown();
			boolean shouldProcess = (!panelOpen) || (panelOpen && shiftPressed);
			if (!shouldProcess)
				return;
			BooleanProperty boltedProp = _getProperty(blockstate, PROP_BOLTED, BooleanProperty.class);
			if ((boltedProp != null) && blockstate.getValue(boltedProp))
				return;
			BooleanProperty emergencyProp = _getProperty(blockstate, PROP_EMERGENCY_ACS, BooleanProperty.class);
			boolean isEmergency = (emergencyProp != null) && blockstate.getValue(emergencyProp);
			if (!isEmergency) {
				boolean hasAccess = true;
				for (String role : ACCESS_ROLES) {
					boolean entityHasRole = entity.getPersistentData().getBooleanOr(role, false);
					BlockEntity beCheck = world.getBlockEntity(pos);
					boolean blockRequiresRole = (beCheck != null) && beCheck.getPersistentData().getBooleanOr(role, false);
					if (blockRequiresRole && !entityHasRole) {
						hasAccess = false;
						break;
					}
				}
				if (!hasAccess) {
					_playSound(world, x, y, z, pos, "ssc_14:airlock_no_access");
					return;
				}
			}
			if (!world.isClientSide() && be != null)
				be.getPersistentData().putBoolean(TAG_OPENING, true);
			Ssc14Mod.queueServerWork(1, () -> _handleAirlockAnimation(world, x, y, z, pos, blockstate));
		} finally {
			if (!world.isClientSide() && be != null)
				be.getPersistentData().putBoolean("_ssc14_processing", false);
		}
	}

	private static void _handleAirlockAnimation(LevelAccessor world, double x, double y, double z, BlockPos pos, BlockState blockstate) {
		if (!(world instanceof Level level) || world.isClientSide())
			return;
		BlockPos animPos = BlockPos.containing(x, y, z);
		BlockState currentState = world.getBlockState(animPos);
		boolean isClosed = currentState.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks_closed")));
		boolean isOpen = currentState.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks_open")));
		IntegerProperty stateProp = _getProperty(currentState, "blockstate", IntegerProperty.class);
		int blockStateVal = (stateProp != null) ? currentState.getValue(stateProp) : -1;
		boolean validState = (blockStateVal == 0 || blockStateVal == 6 || blockStateVal == 7 || blockStateVal == 8);
		if (isClosed && validState) {
			_playSound(level, x, y, z, animPos, "ssc_14:airlock_open");
			_runAnimation(level, animPos, stateProp, new int[]{1, 2, 3, 4}, () -> {
				_replaceAirlockBlock(level, animPos, true, x, y, z);
			});
		} else if (isOpen) {
			_playSound(level, x, y, z, animPos, "ssc_14:airlock_close");
			// 🔧 Закрытие: сначала замена, потом анимация
			_replaceAirlockBlock(level, animPos, false, x, y, z);
			BlockState afterReplace = level.getBlockState(animPos);
			IntegerProperty newStateProp = _getProperty(afterReplace, "blockstate", IntegerProperty.class);
			if (newStateProp != null && newStateProp.getPossibleValues().contains(4)) {
				level.setBlock(animPos, afterReplace.setValue(newStateProp, 4), 3);
			}
			_runAnimation(level, animPos, newStateProp, new int[]{4, 3, 2, 1, 0}, null);
		}
	}

	private static void _runAnimation(Level level, BlockPos pos, IntegerProperty prop, int[] states, Runnable onComplete) {
		if (prop == null || states.length == 0) {
			if (onComplete != null)
				onComplete.run();
			return;
		}
		_runAnimationStep(level, pos, prop, states, 0, onComplete);
	}

	private static void _runAnimationStep(Level level, BlockPos pos, IntegerProperty prop, int[] states, int index, Runnable onComplete) {
		if (index >= states.length) {
			if (onComplete != null)
				onComplete.run();
			return;
		}
		int targetState = states[index];
		BlockState current = level.getBlockState(pos);
		if (prop.getPossibleValues().contains(targetState))
			level.setBlock(pos, current.setValue(prop, targetState), 2);
		if (index + 1 < states.length)
			Ssc14Mod.queueServerWork(2, () -> _runAnimationStep(level, pos, prop, states, index + 1, onComplete));
		else if (onComplete != null)
			Ssc14Mod.queueServerWork(2, onComplete);
	}

	private static void _replaceAirlockBlock(Level level, BlockPos pos, boolean toOpen, double x, double y, double z) {
		BlockState oldState = level.getBlockState(pos);
		Block oldBlock = oldState.getBlock();
		ResourceLocation oldId = BuiltInRegistries.BLOCK.getKey(oldBlock);
		String oldName = oldId.getPath();
		String newName;
		boolean isCurrentlyOpen = oldName.endsWith("open");
		if (toOpen) {
			newName = isCurrentlyOpen ? oldName : oldName + "open";
		} else {
			newName = isCurrentlyOpen ? oldName.substring(0, oldName.length() - 4) : oldName;
		}
		Block newBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse(oldId.getNamespace() + ":" + newName)).orElse(oldBlock);
		if (newBlock == oldBlock)
			return;
		CompoundTag persistentData = null;
		BlockEntity oldBe = level.getBlockEntity(pos);
		if (oldBe != null) {
			persistentData = oldBe.getPersistentData().copy();
			oldBe.setRemoved();
		}
		BlockState newBs = _copyProperties(oldState, newBlock.defaultBlockState());
		level.setBlock(pos, newBs, 3);
		if (persistentData != null) {
			BlockEntity newBe = level.getBlockEntity(pos);
			if (newBe != null)
				newBe.getPersistentData().merge(persistentData);
		}
		BlockPos topPos = BlockPos.containing(x, y + 1, z);
		BlockState oldTop = level.getBlockState(topPos);
		String topOldName = BuiltInRegistries.BLOCK.getKey(oldTop.getBlock()).getPath();
		String topNewName;
		boolean isTopOpen = topOldName.endsWith("open");
		if (toOpen) {
			topNewName = isTopOpen ? topOldName : topOldName + "open";
		} else {
			topNewName = isTopOpen ? topOldName.substring(0, topOldName.length() - 4) : topOldName;
		}
		Block newTopBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse(oldId.getNamespace() + ":" + topNewName)).orElse(oldTop.getBlock());
		if (newTopBlock != oldTop.getBlock()) {
			BlockState newTopState = _copyProperties(oldTop, newTopBlock.defaultBlockState());
			level.setBlock(topPos, newTopState, 3);
		}
	}

	private static BlockState _copyProperties(BlockState from, BlockState to) {
		BlockState result = to;
		for (Property<?> oldProp : from.getProperties()) {
			Property<?> newProp = result.getBlock().getStateDefinition().getProperty(oldProp.getName());
			if (newProp != null && newProp.getValueClass().isAssignableFrom(oldProp.getValueClass())) {
				try {
					@SuppressWarnings({"rawtypes", "unchecked"})
					Property rawNewProp = newProp;
					@SuppressWarnings({"rawtypes", "unchecked"})
					Comparable value = (Comparable) from.getValue(oldProp);
					result = result.setValue(rawNewProp, value);
				} catch (Exception ignored) {
				}
			}
		}
		return result;
	}

	private static void _playSound(LevelAccessor world, double x, double y, double z, BlockPos pos, String soundId) {
		if (world instanceof Level level) {
			Optional<SoundEvent> soundOpt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse(soundId));
			if (soundOpt.isPresent()) {
				SoundEvent sound = soundOpt.get();
				if (!level.isClientSide())
					level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1f, 1f);
				else
					level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1f, 1f, false);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Property<V>, V extends Comparable<V>> T _getProperty(BlockState state, String name, Class<T> type) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(name);
		return type.isInstance(prop) ? (T) prop : null;
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