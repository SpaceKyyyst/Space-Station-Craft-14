package net.ssc14.content.decal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

/** Removes decals from every face when their backing block is broken. */
@EventBusSubscriber(modid = "ssc_14")
public final class DecalBlockBreakHandler {
    private DecalBlockBreakHandler() {
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        LevelAccessor world = event.getLevel();
        if (world.isClientSide()) {
            return;
        }

        BlockPos pos = event.getPos();
        if (world.getChunk(pos) instanceof LevelChunk chunk) {
            for (Direction face : Direction.values()) {
                DecalService.removeDecalAt(chunk, pos, face);
            }
        }
    }
}
