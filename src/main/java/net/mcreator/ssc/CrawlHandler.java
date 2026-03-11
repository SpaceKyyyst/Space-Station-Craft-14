
package net.mcreator.ssc;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "ssc14")
public class CrawlHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
    	System.out.println("[CRAWL] EVENT FIRED!");
        Player player = event.getEntity();
        
        boolean isCrawling = player.getPersistentData()
                                   .getBoolean("ssc14_is_crawling")
                                   .orElse(false);

        System.out.println("[CRAWL] State: " + isCrawling + ", Pose: " + player.getPose());

        if (isCrawling) {
            // Устанавливаем позу плавания
            if (player.getPose() != Pose.SWIMMING) {
                player.setPose(Pose.SWIMMING);
                player.refreshDimensions();
            }
            
            // Уменьшаем хитбокс до размеров "ползания"
            double width = 0.6;
            double height = 0.45;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            player.setBoundingBox(new AABB(
                x - width / 2, y, z - width / 2,
                x + width / 2, y + height, z + width / 2
            ));
            
            // Снижаем скорость движения
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
        } else {
            // Возвращаем нормальную позу
            if (player.getPose() == Pose.SWIMMING) {
                player.setPose(Pose.STANDING);
                player.refreshDimensions();
            }
            
            // Возвращаем стандартный хитбокс (автоматически при смене позы)
            
            // Возвращаем нормальную скорость
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1); // или 0.07 для обычной скорости
        }
    }
}
