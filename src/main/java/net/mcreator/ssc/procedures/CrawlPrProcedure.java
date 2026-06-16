
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.resources.ResourceLocation;

public class CrawlPrProcedure {
    public static final ResourceLocation CRAWL_SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("ssc_14", "crawl_slowdown");

    public static void execute(Entity entity) {
        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            boolean isCrawling = speedAttr.hasModifier(CRAWL_SPEED_MOD_ID);
            
            if (!isCrawling) {
                speedAttr.addTransientModifier(new AttributeModifier(
                    CRAWL_SPEED_MOD_ID, -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ));
                // 🔥 Принудительно ставим позу
                player.setForcedPose(Pose.SWIMMING);
                player.refreshDimensions();
            } else {
                speedAttr.removeModifier(CRAWL_SPEED_MOD_ID);
                // 🔥 Убираем принудительную позу
                player.setForcedPose(null);
                player.refreshDimensions();
            }
        }
    }
}
