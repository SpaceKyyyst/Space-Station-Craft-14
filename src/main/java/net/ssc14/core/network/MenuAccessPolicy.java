package net.ssc14.core.network;

/** Pure authorization policy for menu-originated gameplay payloads. */
public final class MenuAccessPolicy {
    public static final double MAX_DISTANCE_SQUARED = 64.0;

    private MenuAccessPolicy() { }

    public static boolean canUse(boolean alive, boolean spectator, boolean hasMenu, boolean menuStillValid) {
        return alive && !spectator && hasMenu && menuStillValid;
    }

    public static boolean matches(boolean sameMenu, boolean coordinatesMatch, boolean canUse,
            boolean chunkLoaded, double distanceSquared, boolean mayInteract) {
        return sameMenu && coordinatesMatch && canUse && chunkLoaded
                && distanceSquared <= MAX_DISTANCE_SQUARED && mayInteract;
    }
}
