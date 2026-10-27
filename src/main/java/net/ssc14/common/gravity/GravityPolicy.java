package net.ssc14.common.gravity;

/** The existing space/station/boots rule, independent of Minecraft state. */
public final class GravityPolicy {
    private GravityPolicy() { }

    public enum Decision { RELEASE, GRAVITY, NO_GRAVITY }

    public record EntitySnapshot(boolean inSpace, boolean stationGravity, boolean magneticBoots,
                                 boolean supportingBlock, boolean excluded) { }

    public static Decision evaluate(EntitySnapshot snapshot) {
        if (!snapshot.inSpace() || snapshot.excluded()) return Decision.RELEASE;
        boolean gravityEnabled = snapshot.stationGravity() || snapshot.magneticBoots();
        return gravityEnabled && snapshot.supportingBlock() ? Decision.GRAVITY : Decision.NO_GRAVITY;
    }
}
