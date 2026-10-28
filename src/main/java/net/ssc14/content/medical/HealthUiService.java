package net.ssc14.content.medical;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.item.Ssc14Items;
import net.minecraft.world.item.ItemStack;

public final class HealthUiService {
    private HealthUiService() {
    }

    public static boolean isVisible(Entity entity) {
        return entity instanceof Player player
                && player.gameMode() != GameType.CREATIVE
                && player.gameMode() != GameType.SPECTATOR;
    }

    public static boolean isState(Entity entity, double state) {
        if (!(entity instanceof LivingEntity living)
                || !living.getAttributes().hasAttribute(Ssc14Attributes.HEALTH_U_IATTRIBUTE)) {
            return false;
        }
        return living.getAttribute(Ssc14Attributes.HEALTH_U_IATTRIBUTE).getValue() == state;
    }

    public static double animationFrame(Entity entity) {
        if (entity == null) {
            return 0.0;
        }
        return animationFrame(entity.level().getGameTime());
    }

    public static double animationFrame(long gameTime) {
        double loopTimer = (gameTime * 2.8) % 113.0;
        return Math.round(loopTimer / 4.0);
    }

    public static boolean hasHealthAnalyzer(Entity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return main.is(Ssc14Items.HEALTH_ANALYZER.get())
                || off.is(Ssc14Items.HEALTH_ANALYZER.get());
    }
}
