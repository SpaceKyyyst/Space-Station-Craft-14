
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.Identifier;
import net.minecraft.nbt.CompoundTag;

public class CrawlPrProcedure {
    public static final Identifier CRAWL_SPEED_MOD_ID = Identifier.fromNamespaceAndPath("ssc_14", "crawl_slowdown");
    public static final String NBT_KEY = net.ssc14.common.player.PlayerStateKeys.CRAWL_ACTIVE;

    public static void execute(Entity entity) {
        if (!(entity instanceof Player player)) return;
        
        CompoundTag nbt = player.getPersistentData();
        boolean isCrawling = nbt.getBoolean(NBT_KEY).orElse(false);

        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            if (!isCrawling) {
                nbt.putBoolean(NBT_KEY, true);
                
                if (!player.level().isClientSide() && !speedAttr.hasModifier(CRAWL_SPEED_MOD_ID)) {
                    speedAttr.addTransientModifier(new AttributeModifier(
                        CRAWL_SPEED_MOD_ID, -0.60, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ));
                }
            } else {
                if (!player.level().noCollision(player, player.getType().getDimensions().makeBoundingBox(player.getX(), player.getY(), player.getZ()))) {
                    return; 
                }

                nbt.putBoolean(NBT_KEY, false);
                
                if (!player.level().isClientSide() && speedAttr.hasModifier(CRAWL_SPEED_MOD_ID)) {
                    speedAttr.removeModifier(CRAWL_SPEED_MOD_ID);
                }
            }
            
            player.refreshDimensions();
        }
    }
}
