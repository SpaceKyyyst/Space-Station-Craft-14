
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

public class CrawlPrProcedure {
    private static final ResourceLocation CRAWL_SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("ssc_14", "crawl_slowdown");

    public static void execute(Entity entity) {
        if (!(entity instanceof Player player)) return;

        // .orElse(false) разворачивает Optional<Boolean> в примитив boolean
        boolean isCrawling = player.getPersistentData().getBoolean("ssc14_is_crawling").orElse(false);
        player.getPersistentData().putBoolean("ssc14_is_crawling", !isCrawling);

        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            if (!isCrawling) { // ВКЛЮЧАЕМ ползание
                speedAttr.addTransientModifier(new AttributeModifier(
                    CRAWL_SPEED_MOD_ID,
                    -0.15,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ));
            } else { // ВЫКЛЮЧАЕМ ползание
                speedAttr.removeModifier(CRAWL_SPEED_MOD_ID);
            }
        }
    }
}
