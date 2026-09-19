
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent; // ИСПРАВЛЕНО: Верный путь к пакету .block в NeoForge 26.x

@EventBusSubscriber(modid = "ssc_14")
public class AtmosForgeEventHandler {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (!serverLevel.isClientSide()) {
                AtmosphereManager.get(serverLevel).tick();
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        handleBlockChange(event.getLevel(), event.getPos());
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) { // Теперь класс распознается компилятором идеально
        if (event.getPos() != null) {
            handleBlockChange(event.getLevel(), event.getPos());
        }
    }

    private static void handleBlockChange(LevelAccessor level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel && pos != null) {
            if (!serverLevel.isClientSide()) {
                AtmosphereManager.get(serverLevel).onBlockChanged(pos);
            }
        }
    }
}
