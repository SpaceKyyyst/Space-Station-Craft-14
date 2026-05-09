
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;

@EventBusSubscriber(modid = "ssc14")
public class SSC14CriticalInteractionBlockerProcedure {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (isCritical(event.getEntity())) event.setCanceled(true);
    }
    
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (isCritical(event.getEntity())) event.setCanceled(true);
    }
    
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (isCritical(event.getEntity())) event.setCanceled(true);
    }
    
    private static boolean isCritical(Player player) {
        if (player == null) return false;
        double dmg = player.getPersistentData().getDouble("sscCustomHealth").orElse(0.0);
        return (dmg >= 100.0 && dmg < 200.0);
    }
}
