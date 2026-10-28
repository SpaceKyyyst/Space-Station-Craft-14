package net.ssc14.common.network;

import java.util.List;

/** Describes trigger commands exposed by a network-controlled block. */
public interface NetworkTrigger {
    List<String> getAvailableTriggers();

    String getTriggerName(String triggerId);
}
