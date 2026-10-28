package net.ssc14.content.block.airlock;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.menu.access.AccessConfigMENUMenu;
import net.ssc14.content.menu.airlock.CabelPannelAirlockMenu;
import io.netty.buffer.Unpooled;
import java.util.Optional;

public final class AirlockLifecycleService {
	private AirlockLifecycleService() {
	}
    // Persist only the active operation. Vanilla owns its one scheduled block tick,
    // so unloading a chunk pauses the door without retaining/loading the world.
    private static final String ACTION = "_ssc14_airlock_action";
    private static final String STEP = "_ssc14_airlock_step";
    private static final int OPENING = 1, CLOSING = 2, DENIED = 3, WAITING = 4, RELEASE_CRUSHED = 5;
    // Corvax dd8b6e2f: 0.4s to change collision + 0.2s to finish, 5s auto-close.
    private static final int AUTO_CLOSE_TICKS = 100;
    private static final int DENY_TICKS = 9;
    private static final int RETRY_TICKS = 20;
    private static final int[] OPEN_FRAMES = {15, 12, 13, 14, 11};
    private static final int[] CLOSE_FRAMES = {14, 13, 12, 15};



    public static void execute(Level world, double x, double y, double z, BlockState ignored, Entity entity) {
        if (!(world instanceof ServerLevel level) || entity == null) return;
        BlockPos pos = BlockPos.containing(x, y, z);
        if (!level.hasChunkAt(pos)) return;
        BlockState state = level.getBlockState(pos);
        IntegerProperty prop = getSafeProperty(state, "arl_variat", IntegerProperty.class);
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null || prop == null) return;
        CompoundTag nbt = be.getPersistentData();
        ItemStack hand = entity instanceof LivingEntity le ? le.getMainHandItem() : ItemStack.EMPTY;
        if (entity instanceof ServerPlayer player && !entity.isShiftKeyDown()) {
            if (hand.getItem() == Ssc14Items.ACCESS_CONFIG.get()) {
                openGui(player, "AccessConfigMENU", pos);
                return;
            }
            BooleanProperty panel = getSafeProperty(state, "panel_open", BooleanProperty.class);
            if (panel != null && state.getValue(panel)) {
                openGui(player, "CabelPannelAirlock", pos);
                return;
            }
        }
        int current = state.getValue(prop);
        int action = nbt.getIntOr(ACTION, 0);
        // Old versions lost queued lambdas across restarts. Recover their unfinished
        // animation on interaction, without scanning loaded chunks or doors every tick.
        if (action == 0 && current >= 12 && current <= 15) {
            begin(level, pos, be, WAITING, 11, autoCloseDelay(nbt));
            updateTop(level, pos, true);
            current = 11;
        }
        if (action == OPENING || action == CLOSING) return;
        if (nbt.getBooleanOr("bolted", false) || nbt.getBooleanOr("welded", false)
                || !nbt.getBooleanOr("powered", true) || current == 1 || current == 9 || current == 10) return;
        if (!AirlockAccessPolicyService.permits(nbt, entity, nbt.getBooleanOr("emergency_acs", false))) {
            if (action == DENIED) return; // Do not restart denial for repeated unauthorized clicks.
            if (current != 11 && !(current >= 12 && current <= 15)) {
                nbt.putInt("_ssc14_airlock_denied_state", current);
                begin(level, pos, be, DENIED, 8, DENY_TICKS);
                _playSound(level, pos, "ssc_14:airlock_no_access");
            }
            return;
        }
        if (current == 11) {
            // Manual closing uses the same safety sensor as automatic closing.
            if (canCloseAirlock(level, pos, nbt)) startClose(level, pos, be);
        } else if (current == 0 || current == 4 || current == 8) {
            begin(level, pos, be, OPENING, OPEN_FRAMES[0], 4);
            _playSound(level, pos, "ssc_14:airlock_open");
        }
    }

    /** Called exclusively by the scheduled server tick of each lower airlock block. */
    public static void tick(ServerLevel level, BlockPos pos) {
        if (!level.hasChunkAt(pos)) return;
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return;
        BlockState state = level.getBlockState(pos);
        IntegerProperty prop = getSafeProperty(state, "arl_variat", IntegerProperty.class);
        if (prop == null) return;
        CompoundTag nbt = be.getPersistentData();
        int action = nbt.getIntOr(ACTION, 0);
        int frame = nbt.getIntOr(STEP, 0);
        if (action == RELEASE_CRUSHED) {
            begin(level, pos, be, OPENING, OPEN_FRAMES[0], 4);
            _playSound(level, pos, "ssc_14:airlock_open");
        } else if (action == WAITING) {
            if (state.getValue(prop) != 11) { finish(be); return; }
            if (nbt.getBooleanOr(AirlockSignalHandler.HOLD, false)) { finish(be); return; }
            if (canCloseAirlock(level, pos, nbt)) {
                startClose(level, pos, be);
            } else {
                boolean locked = nbt.getBooleanOr("bolted", false) || nbt.getBooleanOr("welded", false)
                        || !nbt.getBooleanOr("powered", true);
                if (locked) finish(be); // Control/power events restart the timer when usable again.
                else level.scheduleTick(pos, state.getBlock(), RETRY_TICKS);
            }
        } else if (action == DENIED) {
            int previous = nbt.getIntOr("_ssc14_airlock_denied_state", 0);
            // Preserve the special hacked variants; ordinary variants reflect current controls.
            int restore = previous == 6 || previous == 7 ? previous : closedState(nbt);
            finish(be);
            if (state.getValue(prop) == 8) setState(level, pos, restore);
        } else if (action == OPENING || action == CLOSING) {
            int[] frames = action == OPENING ? OPEN_FRAMES : CLOSE_FRAMES;
            // A command/replacement must not be overwritten by an obsolete animation.
            if (frame < 0 || frame >= (action == OPENING ? frames.length - 1 : frames.length)
                    || state.getValue(prop) != frames[frame]) {
                finish(be);
                return;
            }
            int next = frame + 1;
            if (action == CLOSING && next == 2
                    && (nbt.getBooleanOr("bolted", false) || nbt.getBooleanOr("welded", false)
                    || (nbt.getBooleanOr("safe", true) && hasBlockingEntities(level, pos)))) {
                // Corvax checks again at the collision transition, not only on click.
                begin(level, pos, be, WAITING, 11, 4);
                updateTop(level, pos, true);
                return;
            }
            nbt.putInt(STEP, next);
            be.setChanged();
            if (action == OPENING) {
                setState(level, pos, OPEN_FRAMES[next]);
                if (next == 2) updateTop(level, pos, true);
                if (next == 4) {
                    refreshAutoClose(level, pos);
                } else {
                    level.scheduleTick(pos, state.getBlock(), next < 2 ? 4 : 2);
                }
            } else if (next == 4) {
                nbt.putBoolean(AirlockSignalHandler.HOLD, false);
                boolean crushed = nbt.getBooleanOr("_ssc14_crushed", false);
                nbt.remove("_ssc14_crushed");
                finish(be);
                setState(level, pos, closedState(nbt));
                if (crushed) begin(level, pos, be, RELEASE_CRUSHED, closedState(nbt), net.ssc14.content.block.airlock.AirlockCrushing.RELEASE_TICKS);
            } else {
                setState(level, pos, CLOSE_FRAMES[next]);
                if (next == 2) {
                    updateTop(level, pos, false);
                    if (!nbt.getBooleanOr("safe", true) && net.ssc14.content.block.airlock.AirlockCrushing.apply(level, pos))
                        nbt.putBoolean("_ssc14_crushed", true);
                }
                level.scheduleTick(pos, state.getBlock(), next < 2 ? 4 : 2);
            }
        }
    }

    private static int autoCloseDelay(CompoundTag nbt) {
        // TIMR cut => modifier 0.01 in Corvax, not disabled automatic closing.
        return nbt.getBooleanOr("timer", true) ? AUTO_CLOSE_TICKS : 1;
    }

    /** Power restoration and unbolting start a fresh full delay, as in Corvax. */
    public static void refreshAutoClose(ServerLevel level, BlockPos pos) {
        if (!level.hasChunkAt(pos)) return;
        BlockState state = level.getBlockState(pos);
        IntegerProperty prop = getSafeProperty(state, "arl_variat", IntegerProperty.class);
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null || prop == null || state.getValue(prop) != 11) return;
        CompoundTag nbt = be.getPersistentData();
        if (!nbt.getBooleanOr("powered", true) || nbt.getBooleanOr("bolted", false)
                || nbt.getBooleanOr("welded", false) || nbt.getBooleanOr(AirlockSignalHandler.HOLD, false)) {
            if (level.getBlockTicks().hasScheduledTick(pos, state.getBlock()))
                level.getBlockTicks().clearArea(new BoundingBox(pos));
            finish(be);
        } else {
            begin(level, pos, be, WAITING, 11, autoCloseDelay(nbt));
        }
    }

    private static void begin(ServerLevel level, BlockPos pos, BlockEntity be, int action, int state, int delay) {
        // Vanilla deduplicates by position and block, not by deadline. Explicitly
        // replace an old auto-close tick before requesting an earlier animation tick.
        Block block = level.getBlockState(pos).getBlock();
        if (level.getBlockTicks().hasScheduledTick(pos, block) || level.getBlockTicks().willTickThisTick(pos, block)) {
            level.getBlockTicks().clearArea(new BoundingBox(pos));
        }
        CompoundTag nbt = be.getPersistentData();
        nbt.remove("_ssc14_processing");
        nbt.remove("_ssc14_denied_anim");
        nbt.remove("prev_arl_variat_on_deny");
        if (action == OPENING || action == CLOSING || action == DENIED)
            AirlockEventLogService.append(be, action == OPENING ? "OPEN" : action == CLOSING ? "CLOSE" : "ACCESS_DENIED");
        nbt.putInt(ACTION, action);
        nbt.putInt(STEP, 0);
        nbt.putInt("_ssc14_visual_action", action);
        nbt.putLong("_ssc14_visual_started", level.getGameTime());
        be.setChanged();
        setState(level, pos, state);
        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), Block.UPDATE_CLIENTS);
        level.scheduleTick(pos, block, delay);
    }

    private static void startClose(ServerLevel level, BlockPos pos, BlockEntity be) {
        begin(level, pos, be, CLOSING, CLOSE_FRAMES[0], 4);
        _playSound(level, pos, "ssc_14:airlock_close");
    }

    /** Device signals bypass ID credentials, but use the same physical interlocks as interaction. */
    public static void requestSignal(ServerLevel level, BlockPos pos, boolean open, boolean toggle) {
        if (!level.hasChunkAt(pos)) return;
        var be = level.getBlockEntity(pos);
        var state = level.getBlockState(pos);
        var frame = getSafeProperty(state, "arl_variat", IntegerProperty.class);
        if (be == null || frame == null) return;
        var data = be.getPersistentData();
        if (!data.getBooleanOr("powered", false) || data.getBooleanOr("bolted", false)
                || data.getBooleanOr("welded", false)) return;
        int value = state.getValue(frame);
        int action = data.getIntOr(ACTION, 0);
        if (action == OPENING || action == CLOSING || action == DENIED) return;
        if (toggle) open = value != 11;
        if (open && value != 11 && (value < 12 || value > 15)) {
            begin(level, pos, be, OPENING, OPEN_FRAMES[0], 4);
            _playSound(level, pos, "ssc_14:airlock_open");
        } else if (!open && value == 11 && canCloseAirlock(level, pos, data)) startClose(level, pos, be);
    }

    public static void openManually(ServerLevel level, BlockPos pos) {
        if (!level.hasChunkAt(pos)) return;
        var be = level.getBlockEntity(pos);
        var state = level.getBlockState(pos);
        var frame = getSafeProperty(state, "arl_variat", IntegerProperty.class);
        if (be == null || frame == null || state.getValue(frame) >= 12 && state.getValue(frame) <= 15) return;
        var data = be.getPersistentData();
        if (data.getBooleanOr("powered", true) || data.getBooleanOr("bolted", false) || data.getBooleanOr("welded", false)) return;
        if (state.getValue(frame) == 11) {
            if (!data.getBooleanOr("safe", true) || !hasBlockingEntities(level, pos)) startClose(level, pos, be);
            return;
        }
        begin(level, pos, be, OPENING, OPEN_FRAMES[0], 4);
        _playSound(level, pos, "ssc_14:airlock_open");
    }

    private static void finish(BlockEntity be) {
        be.getPersistentData().remove(ACTION);
        be.getPersistentData().remove(STEP);
        be.getPersistentData().remove("_ssc14_airlock_denied_state");
        be.setChanged();
    }

    private static int closedState(CompoundTag nbt) {
        boolean welded = nbt.getBooleanOr("welded", false);
        boolean bolted = nbt.getBooleanOr("bolted", false);
        if (!nbt.getBooleanOr("powered", true)) return welded ? 10 : bolted ? 1 : 9;
        if (bolted) return welded ? 3 : 2;
        if (nbt.getBooleanOr("emergency_acs", false)) return welded ? 5 : 4;
        return welded ? 16 : 0;
    }

    private static boolean canCloseAirlock(Level level, BlockPos pos, CompoundTag nbt) {
        if (nbt.getBooleanOr("bolted", false) || nbt.getBooleanOr("welded", false)
                || !nbt.getBooleanOr("powered", true)) return false;
        return !nbt.getBooleanOr("safe", true) || !hasBlockingEntities(level, pos);
    }

    private static boolean hasBlockingEntities(Level level, BlockPos pos) {
        AABB box = new AABB(pos.getX() - 0.2, pos.getY() - 0.2, pos.getZ() - 0.2,
                pos.getX() + 1.2, pos.getY() + 2.2, pos.getZ() + 1.2);
        return !level.getEntitiesOfClass(Entity.class, box, e -> !(e instanceof ItemEntity) && !e.isSpectator()).isEmpty();
    }

    private static void setState(Level level, BlockPos pos, int value) {
        if (!level.hasChunkAt(pos)) return;
        BlockState oldState = level.getBlockState(pos);
        IntegerProperty prop = getSafeProperty(oldState, "arl_variat", IntegerProperty.class);
        if (prop == null || !prop.getPossibleValues().contains(value) || oldState.getValue(prop) == value) return;
        // setBlock with UPDATE_CLIENTS already sends the update. Do not send it twice.
        level.setBlock(pos, oldState.setValue(prop, value), Block.UPDATE_ALL);
    }

    private static void updateTop(Level level, BlockPos pos, boolean open) {
        BlockPos top = pos.above();
        if (!level.hasChunkAt(top)) return;
        BlockState current = level.getBlockState(top);
        Block closedBlock = Ssc14Blocks.AIRLOCK_UP_PLUG.get();
        Block openBlock = Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get();
        if (current.getBlock() != closedBlock && current.getBlock() != openBlock) return;
        Block target = open ? openBlock : closedBlock;
        if (current.getBlock() == target) return;
        BlockState next = target.defaultBlockState();
        if (current.hasProperty(HorizontalDirectionalBlock.FACING) && next.hasProperty(HorizontalDirectionalBlock.FACING)) {
            next = next.setValue(HorizontalDirectionalBlock.FACING, current.getValue(HorizontalDirectionalBlock.FACING));
        }
        level.setBlock(top, next, Block.UPDATE_ALL);
    }

    private static <T> T getSafeProperty(BlockState state, String name, Class<T> type) {
        var prop = state.getBlock().getStateDefinition().getProperty(name);
        return type.isInstance(prop) ? type.cast(prop) : null;
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
		Optional<SoundEvent> opt = BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.parse(soundId));
		opt.ifPresent(s -> level.playSound(null, pos, s, SoundSource.BLOCKS, 1f, 1f));
	}
}
