package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = "ssc_14")
public final class AtmosForgeEventHandler {
    private AtmosForgeEventHandler() { }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            AtmosphereManager.get(level).tick();
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        handleBlockChange(event.getLevel(), event.getPos());
        if (event instanceof BlockEvent.EntityMultiPlaceEvent multiple) {
            for (var snapshot : multiple.getReplacedBlockSnapshots()) {
                handleBlockChange(event.getLevel(), snapshot.getPos());
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) { handleBlockChange(event.getLevel(), event.getPos()); }

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        handleBlockChange(event.getLevel(), event.getPos());
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel level) AtmosphereManager.onWorldUnload(level);
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            AtmosphereManager.get(level).onChunkLoaded(event.getChunk().getPos());
        }
    }

    private static void handleBlockChange(LevelAccessor level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel && pos != null) AtmosphereManager.get(serverLevel).onBlockChanged(pos);
    }
}
