package net.mcreator.ssc.medical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.mcreator.ssc.procedures.CrawlPrProcedure;

/** No medical policy: mirrors a server-owned pose on local and remote client players. */
public final class MedicalClientPresentation implements MedicalPresentationController.Target {
    private static final String STATE = "ssc_14:medical_client_presentation";
    private static final String PREVIOUS_POSE = "previous_forced_pose";
    private final Player player;
    private final CompoundTag state;

    private MedicalClientPresentation(Player player, CompoundTag state) {
        this.player = player;
        this.state = state;
    }

    public static void receive(Player player, boolean active) {
        if (!player.level().isClientSide()) return;
        var data = player.getPersistentData();
        var saved = data.getCompound(STATE).orElse(null);
        boolean previous = saved != null;
        var state = saved == null ? new CompoundTag() : saved;
        // Size observes the received state during setPose/refreshDimensions.
        if (active && !previous) data.put(STATE, state);
        if (!active) data.remove(STATE);
        MedicalPresentationController.apply(new MedicalClientPresentation(player, state), previous, active);
    }

    static boolean ownsPose(Player player) {
        return player.getPersistentData().contains(STATE) && player.getForcedPose() == Pose.SWIMMING;
    }

    @Override
    public void acquire() {
        if (player.getForcedPose() != null) state.putString(PREVIOUS_POSE, player.getForcedPose().name());
        player.setForcedPose(Pose.SWIMMING);
        player.setPose(Pose.SWIMMING);
        player.refreshDimensions();
    }

    @Override
    public void release() {
        // A different local presentation owner may have taken over since receipt.
        if (player.getForcedPose() == Pose.SWIMMING) {
            var previous = state.getString(PREVIOUS_POSE).map(Pose::valueOf).orElse(null);
            player.setForcedPose(previous);
            if (previous != null) player.setPose(previous);
            else if (player.getPose() == Pose.SWIMMING && !player.isInWater() && !player.isSwimming()
                    && !player.getPersistentData().getBooleanOr(CrawlPrProcedure.NBT_KEY, false)) player.setPose(Pose.STANDING);
        }
        player.refreshDimensions();
    }
}
