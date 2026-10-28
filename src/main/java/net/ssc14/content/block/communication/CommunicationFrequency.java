package net.ssc14.content.block.communication;

/** Stable mapping between communication-server block states and saved radio flags. */
public enum CommunicationFrequency {
    BASE(1, "Base_Radio_Frequency"),
    COMMAND(2, "Command_Radio_Frequency"),
    ENGINEERING(3, "Engeneer_Radio_Frequency"),
    MEDICAL(4, "Medical_Radio_Frequency"),
    RESEARCH(5, "RND_Radio_Frequency"),
    SECURITY(6, "Sec_Radio_Frequency"),
    SERVICE(7, "Serv_Radio_Frequency"),
    CARGO(8, "Cargo_Radio_Frequency");

    private final int blockType;
    private final String stateKey;

    CommunicationFrequency(int blockType, String stateKey) {
        this.blockType = blockType;
        this.stateKey = stateKey;
    }

    public int blockType() {
        return blockType;
    }

    public String stateKey() {
        return stateKey;
    }

    public static CommunicationFrequency fromBlockType(int blockType) {
        for (CommunicationFrequency frequency : values()) {
            if (frequency.blockType == blockType) return frequency;
        }
        return null;
    }
}
