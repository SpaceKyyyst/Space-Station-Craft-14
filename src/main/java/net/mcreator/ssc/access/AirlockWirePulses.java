package net.mcreator.ssc.access;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import net.mcreator.ssc.procedures.CPlogLampPrProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

/** Only doors with pending pulses are visited; deadlines survive chunk unloading. */
@EventBusSubscriber(modid = "ssc_14")
public final class AirlockWirePulses {
    public static final int DURATION_TICKS = 200;
    private static final String PREFIX = "_ssc14_wire_pulse_";
    private static final Set<BlockEntity> ACTIVE = Collections.newSetFromMap(new WeakHashMap<>());

    private AirlockWirePulses() { }

    public static boolean active(CompoundTag data, int function, long now) {
        return data.getLongOr(PREFIX + function, 0) > now;
    }

    public static void start(BlockEntity door, int function) {
        if (!(door.getLevel() instanceof ServerLevel level) || function < 0 || function > 7) return;
        var data = door.getPersistentData();
        if (function == 2) {
            if (!data.getBooleanOr("powered", true) || data.getBooleanOr("_ssc14_bolt_wire_cut", false)) return;
            data.putBoolean("bolted", !data.getBooleanOr("bolted", false));
        } else {
            data.putLong(PREFIX + function, level.getGameTime() + DURATION_TICKS);
            ACTIVE.add(door);
        }
        refresh(door);
    }

    private static void refresh(BlockEntity door) {
        var pos = door.getBlockPos();
        var level = (ServerLevel) door.getLevel();
        door.setChanged();
        CPlogLampPrProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), door.getBlockState());
        net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), door.getBlockState());
        net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure.refreshAutoClose(level, pos);
        level.sendBlockUpdated(pos, door.getBlockState(), door.getBlockState(), 3);
    }

    @SubscribeEvent
    public static void load(ChunkEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel && event.getChunk() instanceof LevelChunk chunk) {
            for (var door : chunk.getBlockEntities().values()) {
                if (door.getBlockState().getBlock().getStateDefinition().getProperty("arl_variat") == null) continue;
                for (int function = 0; function < 8; function++) {
                    if (door.getPersistentData().contains(PREFIX + function)) { ACTIVE.add(door); break; }
                }
            }
        }
    }

    @SubscribeEvent
    public static void tick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        var iterator = ACTIVE.iterator();
        while (iterator.hasNext()) {
            var door = iterator.next();
            if (door.getLevel() != level) continue;
            if (door.isRemoved() || !level.hasChunkAt(door.getBlockPos())) { iterator.remove(); continue; }
            var data = door.getPersistentData();
            boolean pending = false, expired = false;
            for (int function = 0; function < 8; function++) {
                String key = PREFIX + function;
                if (!data.contains(key)) continue;
                if (active(data, function, level.getGameTime())) pending = true;
                else { data.remove(key); expired = true; }
            }
            if (!pending) iterator.remove();
            if (expired) refresh(door);
        }
    }
}
