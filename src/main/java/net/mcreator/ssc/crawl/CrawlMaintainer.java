package net.mcreator.ssc.crawl;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
@EventBusSubscriber(modid = Ssc14Mod.MODID)
public class CrawlMaintainer {
    public CrawlMaintainer() {
    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (CrawlSystem.isCrawling(player)) {
            if (player.isPassenger() || player.isSleeping() || player.isSwimming() ||
                    player.isFallFlying() || !player.isAlive() || player.isInWater()) {
                CrawlSystem.stopCrawling(player);
                return;
            }
            CrawlSystem.maintainCrawl(player);
        }
    }
}