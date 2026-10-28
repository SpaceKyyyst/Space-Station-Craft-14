package net.ssc14.integration.neoforge.server;

import java.util.concurrent.RejectedExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Admission outcome for work owned by one Minecraft server lifetime. */
public enum ServerTaskSubmission {
    ACCEPTED, FULL, UNAVAILABLE, STOPPED, INVALID_OWNER, CLIENT_SIDE;

    private static final Logger LOGGER = LogManager.getLogger(ServerTaskSubmission.class);

    public void orThrow() {
        if (this == ACCEPTED) {
            return;
        }
        LOGGER.warn("Server task submission rejected: {}", this);
        throw new RejectedExecutionException("Server task submission rejected: " + name());
    }
}
