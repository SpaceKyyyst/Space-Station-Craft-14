package net.mcreator.ssc.crawl;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
public class CrawlSystem {
    private static final Set<UUID> CRAWLING_PLAYERS = new HashSet<>();
    private static final float CRAWL_HEIGHT = 0.6F;
    public static void startCrawling(Player player) {
        CRAWLING_PLAYERS.add(player.getUUID());
        player.setPose(Pose.SWIMMING);
        player.setForcedPose(Pose.SWIMMING);
        player.setSwimming(true);
        player.setBoundingBox(new AABB(player.getX() - 0.3, player.getY(), player.getZ() - 0.3, player.getX() + 0.3, player.getY() + CRAWL_HEIGHT, player.getZ() + 0.3));
        player.refreshDimensions();
    }
    public static void stopCrawling(Player player) {
        CRAWLING_PLAYERS.remove(player.getUUID());
        player.setForcedPose(null);
        player.setSwimming(false);
        player.refreshDimensions();
    }
    public static boolean isCrawling(Player player) {
        return CRAWLING_PLAYERS.contains(player.getUUID());
    }
    public static void maintainCrawl(Player player) {
        if (isCrawling(player) && player.getPose() != Pose.SWIMMING) {
            player.setPose(Pose.SWIMMING);
        }
    }
}