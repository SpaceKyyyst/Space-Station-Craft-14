package net.ssc14.content.item.tool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.item.Ssc14Items;

/** Owns the player attribute that controls gas-analyser overlay visibility. */
@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class GasAnalyserOverlayService {
    public static final double HIDDEN = 0.0D;
    public static final double VISIBLE = 1.0D;

    private GasAnalyserOverlayService() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        refreshHeldState(event.getEntity());
    }

    public static void activate(Entity entity) {
        if (!(entity instanceof LivingEntity living)
                || !living.getMainHandItem().is(Ssc14Items.GAS_ANALYSER.get())) {
            return;
        }
        var attribute = living.getAttribute(Ssc14Attributes.GAS_AN_GU_IATRIB);
        if (attribute != null && Double.compare(attribute.getValue(), HIDDEN) == 0) {
            attribute.setBaseValue(VISIBLE);
        }
    }

    public static boolean isVisible(Entity entity) {
        if (!(entity instanceof LivingEntity living)) {
            return false;
        }
        var attribute = living.getAttribute(Ssc14Attributes.GAS_AN_GU_IATRIB);
        return attribute != null && Double.compare(attribute.getValue(), VISIBLE) == 0;
    }

    public static void refreshHeldState(Entity entity) {
        if (!(entity instanceof LivingEntity living) || !isVisible(living)) {
            return;
        }
        boolean holdingAnalyser = living.getMainHandItem().is(Ssc14Items.GAS_ANALYSER.get())
                || living.getOffhandItem().is(Ssc14Items.GAS_ANALYSER.get());
        if (!holdingAnalyser) {
            var attribute = living.getAttribute(Ssc14Attributes.GAS_AN_GU_IATRIB);
            if (attribute != null) {
                attribute.setBaseValue(HIDDEN);
            }
        }
    }
}
