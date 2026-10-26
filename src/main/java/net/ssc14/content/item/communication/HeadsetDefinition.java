package net.ssc14.content.item.communication;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/** Immutable default encryption-channel configuration for one headset type. */
public record HeadsetDefinition(Set<HeadsetChannel> defaultChannels) {
    public HeadsetDefinition {
        Objects.requireNonNull(defaultChannels, "defaultChannels");
        defaultChannels = Collections.unmodifiableSet(
                defaultChannels.isEmpty()
                        ? EnumSet.noneOf(HeadsetChannel.class)
                        : EnumSet.copyOf(defaultChannels));
    }

    public static HeadsetDefinition withChannels(HeadsetChannel... channels) {
        EnumSet<HeadsetChannel> defaults = EnumSet.of(HeadsetChannel.PASSANGER);
        Collections.addAll(defaults, channels);
        return new HeadsetDefinition(defaults);
    }
}
