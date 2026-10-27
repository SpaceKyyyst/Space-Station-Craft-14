
package net.ssc14.content.player.crawl;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "ssc_14")
public final class CrawlEventHandler {
    private CrawlEventHandler() { }

    private static final String NBT_KEY = CrawlService.NBT_KEY;

    @SubscribeEvent
    public static void onEntitySize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player) {
            CompoundTag nbt = player.getPersistentData();
            if (nbt.getBoolean(NBT_KEY).orElse(false)) {
                EntityDimensions crawlDims = player.getDimensions(Pose.SWIMMING);
                
                event.setNewSize(crawlDims);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        CompoundTag nbt = player.getPersistentData();
        
        if (nbt.getBoolean(NBT_KEY).orElse(false)) {
            if (player.isSpectator() || player.isSleeping() || player.isInWater() || !player.isAlive()) {
                if (!player.level().isClientSide()) {
                    nbt.putBoolean(NBT_KEY, false);
                    AttributeInstance speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (speedAttr != null && speedAttr.hasModifier(CrawlService.CRAWL_SPEED_MOD_ID)) {
                        speedAttr.removeModifier(CrawlService.CRAWL_SPEED_MOD_ID);
                    }
                    player.refreshDimensions();
                }
                return;
            }

            if (player.getPose() != Pose.SWIMMING) {
                player.setPose(Pose.SWIMMING);
            }

            if (player.level().isClientSide() && player.isShiftKeyDown()) {
                player.setShiftKeyDown(false);
            }
        }
    }
}
