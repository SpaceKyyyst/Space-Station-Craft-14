package net.ssc14.content.item.equipment;

/** Stable magnetic-boots states and their compatibility tooltip keys. */
public enum MagneticBootsState {
    INACTIVE("translation.key.magboots_description_off"),
    ACTIVE("translation.key.magboots_description_on");

    private final String tooltipKey;

    MagneticBootsState(String tooltipKey) {
        this.tooltipKey = tooltipKey;
    }

    public String tooltipKey() {
        return tooltipKey;
    }
}
