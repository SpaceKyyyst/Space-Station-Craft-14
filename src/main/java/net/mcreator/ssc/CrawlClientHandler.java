
package net.mcreator.ssc;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = "ssc14")
public class CrawlClientHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        boolean isCrawling = minecraft.player.getPersistentData()
                                            .getBoolean("ssc14_is_crawling")
                                            .orElse(false);


        if (isCrawling) {
            if (minecraft.player.getPose() != Pose.SWIMMING) {
                minecraft.player.setPose(Pose.SWIMMING);
            }
        } else {
            if (minecraft.player.getPose() == Pose.SWIMMING) {
                minecraft.player.setPose(Pose.STANDING);
            }
        }
    }
}
