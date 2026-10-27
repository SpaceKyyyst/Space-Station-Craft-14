package net.ssc14.common.medical;

/** Owns only medical effect transitions; the adapter performs world mutations. */
public final class MedicalEffectsController {
    public static final float CRITICAL_WIDTH = 0.6f;
    public static final float CRITICAL_HEIGHT = 0.45f;
    public record Ownership(boolean blindness, boolean pose, boolean poseYielded) {
        public static final Ownership NONE = new Ownership(false, false, false);
    }

    public interface Target {
        boolean isClientSide();
        boolean hasBlindness();
        boolean addBlindness(int ticks);
        /** True only after the owned contribution is demonstrably absent. */
        boolean removeBlindness();
        boolean canAcquirePose();
        boolean hasMedicalPose();
        void acquirePose();
        void releasePose();
        void reconcileModifiers(boolean critical);
    }

    private MedicalEffectsController() { }

    public static Ownership apply(Target target, Ownership previous, boolean critical, int blindnessTicks) {
        if (target.isClientSide()) return previous;
        target.reconcileModifiers(critical);
        if (!critical) {
            boolean pendingBlindness = previous.blindness() && target.hasBlindness() && !target.removeBlindness();
            if (previous.pose() && target.hasMedicalPose()) target.releasePose();
            return pendingBlindness ? new Ownership(true, false, false) : Ownership.NONE;
        }
        boolean blindness = previous.blindness();
        if (!target.hasBlindness()) blindness = target.addBlindness(blindnessTicks);
        boolean pose = previous.pose();
        boolean yielded = previous.poseYielded();
        if (pose && !target.hasMedicalPose()) {
            pose = false;
            yielded = true;
        }
        if (!pose && !yielded) {
            if (target.canAcquirePose()) {
                target.acquirePose();
                pose = target.hasMedicalPose();
            } else {
                yielded = true;
            }
        }
        return new Ownership(blindness, pose, yielded);
    }
}
