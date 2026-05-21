
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

// 🔑 ГЛАВНОЕ ИСПРАВЛЕНИЕ: modid = "ssc_14" (должен ТОЧНО совпадать с @Mod в главном классе)
@EventBusSubscriber(modid = "ssc_14")
public class AtmosForgeEventHandler {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
            AtmosphereManager.get(serverLevel).tick();
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        handleBlockChange(event.getLevel(), event.getPos());
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        handleBlockChange(event.getLevel(), event.getPos());
    }

    private static void handleBlockChange(LevelAccessor level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
            AtmosphereManager.get(serverLevel).onBlockChanged(pos);
        }
    }
}
