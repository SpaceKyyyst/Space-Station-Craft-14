package net.mcreator.ssc.medical;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

/** Restricts building on the server without modifying game-mode abilities. */
@EventBusSubscriber(modid = "ssc_14")
public final class MedicalActionEvents {
    private MedicalActionEvents() { }

    static void restrictBuild(ICancellableEvent event, boolean clientSide, double damage) {
        if (!MedicalStatePolicy.shouldCancelBuilding(clientSide, damage)) return;
        event.setCanceled(true);
        if (event instanceof BreakBlockEvent breaking) breaking.setNotifyClient(true);
    }

    /** Shared authoritative predicate for block events and packet commands. */
    public static boolean isBuildRestricted(Player player) {
        return MedicalStatePolicy.shouldCancelBuilding(player.level().isClientSide(),
                player.getPersistentData().getDoubleOr(MedicalPlayerEffects.TOTAL_DAMAGE, 0));
    }

    @SubscribeEvent
    public static void onPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            restrictBuild(event, event.getLevel().isClientSide(),
                    player.getPersistentData().getDoubleOr(MedicalPlayerEffects.TOTAL_DAMAGE, 0));
        }
    }

    @SubscribeEvent
    public static void onBreak(BreakBlockEvent event) {
        restrictBuild(event, event.getLevel().isClientSide(),
                event.getPlayer().getPersistentData().getDoubleOr(MedicalPlayerEffects.TOTAL_DAMAGE, 0));
    }
}
