package net.ssc14.client.medical;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.ssc14.common.medical.MedicalEffectsController;

/** Client-only dimensions for server-owned medical presentation snapshots. */
@EventBusSubscriber(modid = "ssc_14")
public final class MedicalClientSizeEvents {
    private MedicalClientSizeEvents() { }

    @SubscribeEvent
    public static void onSize(EntityEvent.Size event) {
        if (event.getPose() == Pose.SWIMMING && event.getEntity() instanceof Player player
                && player.level().isClientSide() && MedicalClientPresentation.ownsPose(player)) {
            event.setNewSize(EntityDimensions.scalable(MedicalEffectsController.CRITICAL_WIDTH,
                    MedicalEffectsController.CRITICAL_HEIGHT).withEyeHeight(event.getNewSize().eyeHeight()));
        }
    }
}
