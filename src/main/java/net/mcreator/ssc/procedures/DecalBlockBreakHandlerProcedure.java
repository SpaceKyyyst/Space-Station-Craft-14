
package net.mcreator.ssc.procedures;

import net.mcreator.ssc.DecalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

// ПРАВИЛЬНЫЙ ИМПОРТ ИЗ ВАШЕЙ ВЕРСИИ NEOFORGE 26.1.2:
import net.neoforged.neoforge.event.level.block.BreakBlockEvent; 

@EventBusSubscriber(modid = "ssc_14")
public class DecalBlockBreakHandlerProcedure {
    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) { // Использован верный класс события
        LevelAccessor world = event.getLevel();
        if (world.isClientSide()) return;
        
        BlockPos pos = event.getPos();
        if (world.getChunk(pos) instanceof LevelChunk chunk) {
            Direction[] faces = Direction.values();
            int total = faces.length;
            for (int i = 0; total > i; i++) {
                DecalRegistry.removeDecalAt(chunk, pos, faces[i]);
            }
        }
    }
}
