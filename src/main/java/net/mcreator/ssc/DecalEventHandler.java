
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.mcreator.ssc.entity.Decal1Entity;
import java.util.List;

@EventBusSubscriber(modid = "ssc_14")
public class DecalEventHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel(); // Каст для совместимости
        BlockPos brokenPos = event.getPos();
        
        // Ищем декали в радиусе 1 блока
        AABB searchBox = new AABB(
            brokenPos.getX() - 1, brokenPos.getY() - 1, brokenPos.getZ() - 1,
            brokenPos.getX() + 2, brokenPos.getY() + 2, brokenPos.getZ() + 2
        );
        
        List<Decal1Entity> decals = level.getEntitiesOfClass(Decal1Entity.class, searchBox);
        for (Decal1Entity decal : decals) {
            // Если декаль привязана к этому блоку — удаляем
            if (decal.attachedPos != null && decal.attachedPos.equals(brokenPos)) {
                decal.discard();
                break;
            }
        }
    }
}
