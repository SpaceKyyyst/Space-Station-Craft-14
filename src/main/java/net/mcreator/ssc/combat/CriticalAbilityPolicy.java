package net.mcreator.ssc.combat;

/** Decides whether a player ability update must be sent to clients. */
public final class CriticalAbilityPolicy {
    private CriticalAbilityPolicy() {
    }

    public static boolean shouldSync(boolean currentMayBuild, boolean requiredMayBuild) {
        return currentMayBuild != requiredMayBuild;
    }
}
