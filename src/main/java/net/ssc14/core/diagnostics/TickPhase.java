package net.ssc14.core.diagnostics;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;

/** Optional JFR timings; no disk logging or background thread when recording is disabled. */
@Name("ssc14.TickPhase")
@Label("SSC14 server phase")
@Category({"Minecraft", "SSC14"})
@StackTrace(false)
public final class TickPhase extends Event implements AutoCloseable {
    @Label("Subsystem") public String subsystem;
    @Label("Pending work") public int workItems;

    public static TickPhase start(String subsystem, int workItems) {
        var event = new TickPhase();
        event.subsystem = subsystem;
        event.workItems = workItems;
        if (event.isEnabled()) event.begin();
        return event;
    }

    @Override public void close() {
        if (isEnabled()) {
            end();
            if (shouldCommit()) commit();
        }
    }
}
