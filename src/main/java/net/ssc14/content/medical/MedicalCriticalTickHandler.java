package net.ssc14.content.medical;

import net.minecraft.world.entity.player.Player;
import net.ssc14.common.medical.MedicalStatePolicy;

/** Applies periodic critical-state effects on the authoritative server tick. */
public final class MedicalCriticalTickHandler {
    private static final int ASPHYX_INTERVAL_TICKS = 20;
    private static final int BLINDNESS_DURATION_TICKS = 220;
    private static final String ASPHYX_DAMAGE = "ssc14_dmg_asphyx";

    private MedicalCriticalTickHandler() { }

    public static void onPlayerTick(Player player) {
        if (player == null || player.level().isClientSide()) return;
        var data = player.getPersistentData();
        double totalDamage = data.getDoubleOr(MedicalPlayerEffects.TOTAL_DAMAGE, 0);
        if (MedicalStatePolicy.isBuildRestricted(totalDamage)) {
            int timer = data.getIntOr(MedicalPlayerEffects.ASPHYX_TIMER, 0);
            if (timer >= ASPHYX_INTERVAL_TICKS) {
                data.putDouble(ASPHYX_DAMAGE, data.getDoubleOr(ASPHYX_DAMAGE, 0) + 1.0);
                data.putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, totalDamage + 1.0);
                data.putInt(MedicalPlayerEffects.ASPHYX_TIMER, 0);
            } else {
                data.putInt(MedicalPlayerEffects.ASPHYX_TIMER, timer + 1);
            }
        }
        // Re-read after asphyxia: crossing 200 -> 201 releases this tick.
        MedicalPlayerEffects.reconcile(player, BLINDNESS_DURATION_TICKS);
    }
}
