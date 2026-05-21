
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import io.netty.buffer.Unpooled;

import java.util.Optional;
import java.util.List;

public class BaseAirlockOpenCloseProcedure {

	private static final String[] ACCESS_ROLES = {
		"Technical", "Service", "Out", "gun_room", "HoS", "Brig", "Medical", "Crio", "Security", 
		"Engineer", "Command", "Detective", "PNT", "Scientist", "Supply_Department", "Atmos", 
		"Kitchen", "Uridic", "Gidroponic", "Teatre", "Bar", "Cleaner", "Utilizat", "Chemistry", 
		"Church", "CE", "Qm", "CMO", "RD", "HoP", "Captain", "Blue_Sh"
	};

	public static void execute(Level world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (world.isClientSide() || entity == null) return;
		
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null) return;

		CompoundTag nbt = be.getPersistentData();
		if (nbt.getBoolean("_ssc14_processing").orElse(false)) return;
		nbt.putBoolean("_ssc14_processing", true);

		try {
			ItemStack mainHand = entity instanceof LivingEntity le ? le.getMainHandItem() : ItemStack.EMPTY;
			boolean isSneaking = entity.isShiftKeyDown();

			if (mainHand.getItem() == Ssc14ModItems.ACCESS_CONFIG.get() && !isSneaking) {
				openGui((ServerPlayer) entity, "AccessConfigMENU", pos);
				return;
			}

			BooleanProperty panelProp = getSafeProperty(blockstate, "panel_open", BooleanProperty.class);
			boolean panelOpen = panelProp != null && blockstate.getValue(panelProp);
			if (panelOpen && !isSneaking) {
				openGui((ServerPlayer) entity, "CabelPannelAirlock", pos);
				return;
			}

			boolean bolted = nbt.getBoolean("bolted").orElse(false);
			boolean welded = nbt.getBoolean("welded").orElse(false);
			
			IntegerProperty arlProp = getSafeProperty(blockstate, "arl_variat", IntegerProperty.class);
			int currentState = arlProp != null ? blockstate.getValue(arlProp) : -1;
			boolean hasPower = !(currentState == 1 || currentState == 9 || currentState == 10);
			
			if (bolted || welded || !hasPower) return;

			boolean emergency = nbt.getBoolean("emergency_acs").orElse(false);
			boolean hasAccess = checkAccess(nbt, entity, emergency);
			
			if (!hasAccess) {
				if (currentState != 11 && !(currentState >= 12 && currentState <= 15)) {
					handleAccessDenied(world, pos, nbt, currentState, arlProp);
				}
				return;
			}

			if (arlProp == null) return;
			if (currentState == 11) {
				_playSound(world, pos, "ssc_14:airlock_close");
				scheduleCloseAnimation(world, pos, nbt, arlProp);
			} else if (currentState == 0 || currentState == 4 || currentState == 8) {
				_playSound(world, pos, "ssc_14:airlock_open");
				scheduleOpenAnimation(world, pos, arlProp);
				scheduleAutoCloseCheck(world, pos, nbt, arlProp);
			}
		} finally {
			nbt.putBoolean("_ssc14_processing", false);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T getSafeProperty(BlockState state, String name, Class<T> type) {
		var prop = state.getBlock().getStateDefinition().getProperty(name);
		return type.isInstance(prop) ? (T) prop : null;
	}

	private static boolean checkAccess(CompoundTag blockNbt, Entity entity, boolean emergency) {
		if (emergency) return true;
		boolean anyRequired = false;
		for (String role : ACCESS_ROLES) if (blockNbt.getBoolean(role).orElse(false)) { anyRequired = true; break; }
		if (!anyRequired) return true;
		CompoundTag playerData = entity.getPersistentData();
		for (String role : ACCESS_ROLES) if (blockNbt.getBoolean(role).orElse(false) && playerData.getBoolean(role).orElse(false)) return true;
		return false;
	}

	// 🔹 Отказ в доступе — ИСПРАВЛЕННАЯ ВЕРСИЯ
	private static void handleAccessDenied(Level level, BlockPos pos, CompoundTag nbt, int prevState, IntegerProperty prop) {
	    if (nbt.getBoolean("_ssc14_denied_anim").orElse(false)) return;
	    nbt.putBoolean("_ssc14_processing", true); // 🔧 Блокируем другие действия
	    nbt.putBoolean("_ssc14_denied_anim", true);
	    nbt.putInt("prev_arl_variat_on_deny", prevState);
	
	    _setBlockState(level, pos, 8, prop);
	    _playSound(level, pos, "ssc_14:airlock_no_access");
	
	    Ssc14Mod.queueServerWork(10, () -> {
	        if (level.isClientSide()) return;
	        BlockState current = level.getBlockState(pos);
	        IntegerProperty curProp = getSafeProperty(current, "arl_variat", IntegerProperty.class);
	        
	        if (curProp != null && current.getValue(curProp) == 8) {
	            // 🔧 FIX: Правильно извлекаем int из Optional
	            int restoreState = nbt.getInt("prev_arl_variat_on_deny").orElse(prevState);
	            _setBlockState(level, pos, restoreState, curProp);
	        }
	        nbt.putBoolean("_ssc14_denied_anim", false);
	        nbt.putBoolean("_ssc14_processing", false);
	    });
	}

	private static void scheduleAutoCloseCheck(Level level, BlockPos pos, CompoundTag nbt, IntegerProperty prop) {
		Ssc14Mod.queueServerWork(80, () -> tryAutoClose(level, pos, nbt, prop));
	}

	private static void tryAutoClose(Level level, BlockPos pos, CompoundTag nbt, IntegerProperty prop) {
		if (level.isClientSide()) return;
		BlockState current = level.getBlockState(pos);
		IntegerProperty curProp = getSafeProperty(current, "arl_variat", IntegerProperty.class);
		if (curProp == null) return;
		int state = current.getValue(curProp);
		if (state != 11) return;

		if (!canCloseAirlock(level, pos, nbt)) {
			Ssc14Mod.queueServerWork(10, () -> tryAutoClose(level, pos, nbt, prop));
			return;
		}
		_playSound(level, pos, "ssc_14:airlock_close");
		scheduleCloseAnimation(level, pos, nbt, curProp);
	}

	private static boolean canCloseAirlock(Level level, BlockPos pos, CompoundTag nbt) {
		if (nbt.getBoolean("bolted").orElse(false) || nbt.getBoolean("welded").orElse(false)) return false;
		if (!nbt.getBoolean("powered").orElse(true)) return false;
		if (nbt.getBoolean("safe").orElse(true) && hasBlockingEntities(level, pos)) return false;
		return true;
	}

	private static boolean hasBlockingEntities(Level level, BlockPos pos) {
		AABB box = new AABB(pos.getX()-0.2, pos.getY()-0.2, pos.getZ()-0.2, pos.getX()+1.2, pos.getY()+2.2, pos.getZ()+1.2);
		return !level.getEntitiesOfClass(Entity.class, box, e -> !(e instanceof ItemEntity)).isEmpty();
	}

	private static void _setBlockState(Level level, BlockPos pos, int value, IntegerProperty prop) {
		if (!level.hasChunkAt(pos) || level.getBlockState(pos).isAir()) return;
		if (prop == null || !prop.getPossibleValues().contains(value)) return;
		BlockState oldState = level.getBlockState(pos);
		BlockState newState = oldState.setValue(prop, value);
		level.setBlock(pos, newState, 3);
		level.sendBlockUpdated(pos, oldState, newState, 3);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static void _updateTopPlugState(Level level, BlockPos mainPos, boolean toOpen) {
		BlockPos topPos = mainPos.above();
		BlockState topState = level.getBlockState(topPos);  
		String topName = BuiltInRegistries.BLOCK.getKey(topState.getBlock()).getPath();
		if (!topName.contains("airlock_up_plug")) return;

		String targetName = toOpen 
			? (topName.endsWith("_open") ? topName : topName + "_open")
			: (topName.endsWith("_open") ? topName.substring(0, topName.length() - 5) : topName);
		
		Optional<Block> optBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse("ssc_14:" + targetName));
		if (optBlock.isEmpty() || optBlock.get() == topState.getBlock()) return;
		Block targetBlock = optBlock.get();

		BlockState newTopState = targetBlock.defaultBlockState();
		for (Property<?> oldProp : topState.getProperties()) {
			Property<?> newProp = newTopState.getBlock().getStateDefinition().getProperty(oldProp.getName());
			if (newProp != null && newProp.getValueClass().isAssignableFrom(oldProp.getValueClass())) {
				try {
					Property rawProp = (Property) newProp;
					Comparable value = (Comparable) topState.getValue(oldProp);
					newTopState = newTopState.setValue(rawProp, value);
				} catch (Exception ignored) {}
			}
		}
		level.setBlock(topPos, newTopState, 3);
		level.sendBlockUpdated(topPos, topState, newTopState, 3);
	}

	private static void scheduleOpenAnimation(Level level, BlockPos pos, IntegerProperty prop) {
		_setBlockState(level, pos, 15, prop);
		Ssc14Mod.queueServerWork(4, () -> _setBlockState(level, pos, 12, prop));
		Ssc14Mod.queueServerWork(8, () -> {
			_setBlockState(level, pos, 13, prop);
			_updateTopPlugState(level, pos, true);
		});
		Ssc14Mod.queueServerWork(12, () -> _setBlockState(level, pos, 14, prop));
		Ssc14Mod.queueServerWork(16, () -> _setBlockState(level, pos, 11, prop));
	}

	private static void scheduleCloseAnimation(Level level, BlockPos pos, CompoundTag nbt, IntegerProperty prop) {
		_setBlockState(level, pos, 14, prop);
		Ssc14Mod.queueServerWork(4, () -> _setBlockState(level, pos, 13, prop));
		Ssc14Mod.queueServerWork(8, () -> {
			_setBlockState(level, pos, 12, prop);
			_updateTopPlugState(level, pos, false);
		});
		Ssc14Mod.queueServerWork(12, () -> _setBlockState(level, pos, 15, prop));
		Ssc14Mod.queueServerWork(16, () -> {
			int target = nbt.getBoolean("emergency_acs").orElse(false) ? 4 : 0;
			_setBlockState(level, pos, target, prop);
		});
	}

	private static void openGui(ServerPlayer player, String name, BlockPos pos) {
		player.openMenu(new MenuProvider() {
			@Override public Component getDisplayName() { return Component.literal(name); }
			@Override public boolean shouldTriggerClientSideContainerClosingOnOpen() { return false; }
			@Override public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
				return name.equals("AccessConfigMENU") 
					? new AccessConfigMENUMenu(id, inv, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos))
					: new CabelPannelAirlockMenu(id, inv, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
			}
		}, pos);
	}

	private static void _playSound(Level level, BlockPos pos, String soundId) {
		Optional<SoundEvent> opt = BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse(soundId));
		opt.ifPresent(s -> level.playSound(null, pos, s, SoundSource.BLOCKS, 1f, 1f));
	}
}
