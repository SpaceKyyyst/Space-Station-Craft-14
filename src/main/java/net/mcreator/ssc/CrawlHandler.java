
package net.mcreator.ssc;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "ssc_14")
public class CrawlHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player == null || player.isRemoved()) return;

        // Исправлена ошибка с Optional
        boolean isCrawling = player.getPersistentData().getBoolean("ssc14_is_crawling").orElse(false);

        if (isCrawling) {
            if (player.getPose() != Pose.SWIMMING) {
                player.setPose(Pose.SWIMMING);
                player.refreshDimensions();
            }
        } else {
            // makeBoundingBox в 1.21+ принимает Vec3. player.position() возвращает Vec3
            AABB standingBox = player.getDimensions(Pose.STANDING).makeBoundingBox(player.position());
            // noCollision проверяет, не пересекается ли новый хитбокс с блоками
            boolean canStand = player.level().noCollision(player, standingBox);

            if (!canStand) {
                // Места нет → остаёмся в позе ползания
                if (player.getPose() != Pose.SWIMMING) {
                    player.setPose(Pose.SWIMMING);
                    player.refreshDimensions();
                }
            } else if (player.getPose() == Pose.SWIMMING) {
                // Место есть → встаём
                player.setPose(Pose.STANDING);
                player.refreshDimensions();
            }
        }
    }
}
