package net.mcreator.ssc.scheduling;

import java.util.concurrent.RejectedExecutionException;
import net.mcreator.ssc.Ssc14Mod;

/** Admission outcome; acceptance does not survive shutdown of the owning server. */
public enum SubmissionResult {
    ACCEPTED, FULL, UNAVAILABLE, STOPPED, INVALID_OWNER, CLIENT_SIDE;

    /** Generated gameplay callers fail visibly rather than silently losing a continuation. */
    public void orThrow() {
        if (this == ACCEPTED) return;
        var failure = new RejectedExecutionException("Server task submission rejected: " + name());
        Ssc14Mod.LOGGER.warn("Server task submission rejected: {}", this);
        throw failure;
    }
}
