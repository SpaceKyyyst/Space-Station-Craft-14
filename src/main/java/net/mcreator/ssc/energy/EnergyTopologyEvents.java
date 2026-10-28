package net.mcreator.ssc.energy;

import net.ssc14.content.block.energy.CableTopologyService;
import net.ssc14.content.energy.EnergyNetworkService;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

@EventBusSubscriber(modid = "ssc_14")
public final class EnergyTopologyEvents {
    private EnergyTopologyEvents() { }

    @SubscribeEvent
    public static void unload(ChunkEvent.Unload event) {
        EnergyNetworkService.chunkAvailabilityChanged(event.getLevel(), event.getChunk().getPos());
    }

    @SubscribeEvent
    public static void load(ChunkEvent.Load event) {
        if (event.getLevel() instanceof net.minecraft.server.level.ServerLevel level
                && event.getChunk() instanceof net.minecraft.world.level.chunk.LevelChunk chunk)
            for (var door : java.util.List.copyOf(chunk.getBlockEntities().values()))
                if (net.mcreator.ssc.access.AirlockPower.isDoor(door.getBlockState()))
                    net.mcreator.ssc.access.AirlockPower.supply(level, door.getBlockPos(), false);
        EnergyNetworkService.chunkAvailabilityChanged(event.getLevel(), event.getChunk().getPos());
    }

    @SubscribeEvent
    public static void unloadWorld(LevelEvent.Unload event) {
        EnergyNetworkService.unloadWorld(event.getLevel());
    }

    @SubscribeEvent
    public static void placed(BlockEvent.EntityPlaceEvent event) {
        invalidateDevice(event.getLevel(), event.getPos());
    }

    @SubscribeEvent
    public static void broken(BreakBlockEvent event) {
        invalidateDevice(event.getLevel(), event.getPos());
    }

    private static void invalidateDevice(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos position) {
        var state = level.getBlockState(position);
        if (state.getBlock() instanceof net.ssc14.content.block.energy.SheathingBlock
                || state.is(net.mcreator.ssc.init.Ssc14ModBlocks.APC.get())
                || state.is(net.mcreator.ssc.init.Ssc14ModBlocks.PODSTATION.get())
                || state.is(net.mcreator.ssc.init.Ssc14ModBlocks.DEBU_GGENERATOR.get())
                || state.is(net.mcreator.ssc.init.Ssc14ModBlocks.LAMP.get())
                || net.mcreator.ssc.access.AirlockPower.isDoor(state)) {
            CableTopologyService.invalidate(level, position);
        }
    }
}
