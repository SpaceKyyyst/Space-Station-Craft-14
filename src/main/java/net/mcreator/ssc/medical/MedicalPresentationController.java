package net.mcreator.ssc.medical;

/** Client presentation transitions driven solely by received server snapshots. */
public final class MedicalPresentationController {
    public interface Target {
        void acquire();
        void release();
    }

    private MedicalPresentationController() { }

    public static boolean apply(Target target, boolean previous, boolean received) {
        if (previous != received) {
            if (received) target.acquire();
            else target.release();
        }
        return received;
    }
}
