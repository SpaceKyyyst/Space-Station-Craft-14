
package net.mcreator.ssc.procedures;

import net.mcreator.ssc.DecalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = "ssc_14")
public class DecalBlockBreakHandlerProcedure {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        LevelAccessor world = event.getLevel();
        // Логика выполняется строго на сервере
        if (world.isClientSide()) return;

        BlockPos pos = event.getPos();
        
        if (world.getChunk(pos) instanceof LevelChunk chunk) {
            // Проверяем все 6 сторон сломанного блока. 
            // Если на какой-то из сторон была закреплена декаль — удаляем её.
            for (Direction face : Direction.values()) {
                DecalRegistry.removeDecalAt(chunk, pos, face);
            }
        }
    }
}
