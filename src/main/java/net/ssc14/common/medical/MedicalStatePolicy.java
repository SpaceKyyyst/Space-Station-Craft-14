package net.ssc14.common.medical;

/** Medical authorization is independent of game-mode abilities. */
public final class MedicalStatePolicy {
    public static final String TOTAL_DAMAGE_KEY = "sscCustomHealth";
    private static final double CRITICAL_MIN_DAMAGE = 100.0;
    private static final double CRITICAL_MAX_DAMAGE = 200.0;
    public enum MedicalState { UNRESTRICTED, CRITICAL }

    private MedicalStatePolicy() { }

    public static MedicalState evaluate(double damage) {
        return damage >= CRITICAL_MIN_DAMAGE && damage <= CRITICAL_MAX_DAMAGE
                ? MedicalState.CRITICAL : MedicalState.UNRESTRICTED;
    }

    public static boolean isBuildRestricted(double damage) {
        return evaluate(damage) == MedicalState.CRITICAL;
    }

    public static boolean canBuild(MedicalState state, boolean gameModePermitsBuilding) {
        return gameModePermitsBuilding && state != MedicalState.CRITICAL;
    }

    public static boolean shouldCancelBuilding(boolean clientSide, double damage) {
        return !clientSide && isBuildRestricted(damage);
    }
}
