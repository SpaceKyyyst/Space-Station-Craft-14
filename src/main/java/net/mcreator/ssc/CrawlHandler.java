
package net.mcreator.ssc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.mcreator.ssc.procedures.CrawlPrProcedure;

@EventBusSubscriber(modid = "ssc_14")
public class CrawlHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        boolean isCrawling = speedAttr != null && speedAttr.hasModifier(CrawlPrProcedure.CRAWL_SPEED_MOD_ID);

        if (isCrawling) {
            // 🔥 Принудительно ставим позу КАЖДЫЙ ТИК
            if (player.getForcedPose() != Pose.SWIMMING) {
                player.setForcedPose(Pose.SWIMMING);
                player.refreshDimensions();
            }
        } else {
            // 🔥 Убираем принудительную позу
            if (player.getForcedPose() == Pose.SWIMMING) {
                player.setForcedPose(null);
                player.refreshDimensions();
            }
        }
    }
}
