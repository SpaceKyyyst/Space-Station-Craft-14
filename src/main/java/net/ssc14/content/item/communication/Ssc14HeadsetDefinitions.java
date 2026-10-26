package net.ssc14.content.item.communication;

/** Canonical default channel definitions for registered headset variants. */
public final class Ssc14HeadsetDefinitions {
    private static final HeadsetDefinition ALL_CHANNELS = HeadsetDefinition.withChannels(
            HeadsetChannel.COMMAND,
            HeadsetChannel.ENGINEERING,
            HeadsetChannel.MEDICAL,
            HeadsetChannel.RESEARCH,
            HeadsetChannel.SECURITY,
            HeadsetChannel.SERVICE,
            HeadsetChannel.CARGO);

    public static final HeadsetDefinition BASE = HeadsetDefinition.withChannels();
    public static final HeadsetDefinition BRIGMEDIC = HeadsetDefinition.withChannels(
            HeadsetChannel.MEDICAL, HeadsetChannel.SECURITY);
    public static final HeadsetDefinition CARGO = HeadsetDefinition.withChannels(
            HeadsetChannel.CARGO);
    public static final HeadsetDefinition CENTCOM = ALL_CHANNELS;
    public static final HeadsetDefinition COMMAND = ALL_CHANNELS;
    public static final HeadsetDefinition ENGINEERING = HeadsetDefinition.withChannels(
            HeadsetChannel.ENGINEERING);
    public static final HeadsetDefinition MEDICAL = HeadsetDefinition.withChannels(
            HeadsetChannel.MEDICAL);
    public static final HeadsetDefinition MEDICAL_SCIENCE = HeadsetDefinition.withChannels(
            HeadsetChannel.MEDICAL, HeadsetChannel.RESEARCH);
    public static final HeadsetDefinition MINING = HeadsetDefinition.withChannels(
            HeadsetChannel.CARGO);
    public static final HeadsetDefinition ROBOTICS = HeadsetDefinition.withChannels(
            HeadsetChannel.RESEARCH);
    public static final HeadsetDefinition SCIENCE = HeadsetDefinition.withChannels(
            HeadsetChannel.RESEARCH);
    public static final HeadsetDefinition SECURITY = HeadsetDefinition.withChannels(
            HeadsetChannel.SECURITY);
    public static final HeadsetDefinition SERVICE = HeadsetDefinition.withChannels(
            HeadsetChannel.SERVICE);
    public static final HeadsetDefinition SYNDICATE = HeadsetDefinition.withChannels();

    private Ssc14HeadsetDefinitions() { }
}
