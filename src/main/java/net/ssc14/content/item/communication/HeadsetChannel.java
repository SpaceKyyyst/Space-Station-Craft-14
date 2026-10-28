package net.ssc14.content.item.communication;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.Ssc14Items;

/** Encryption-key channel and its stable headset inventory slot. */
public enum HeadsetChannel {
    PASSANGER(1),
    COMMAND(2),
    ENGINEERING(3),
    MEDICAL(4),
    RESEARCH(5),
    SECURITY(6),
    SERVICE(7),
    CARGO(8);

    private final int slot;

    HeadsetChannel(int slot) {
        this.slot = slot;
    }

    public int slot() {
        return slot;
    }

    public Item keyItem() {
        return switch (this) {
            case PASSANGER -> Ssc14Items.ENCRYPTION_KEY_PASSANGER.get();
            case COMMAND -> Ssc14Items.ENCRYPTION_KEY_COMMAND.get();
            case ENGINEERING -> Ssc14Items.ENCRYPTION_KEY_ENGENEER.get();
            case MEDICAL -> Ssc14Items.ENCRYPTION_KEY_MEDICAL.get();
            case RESEARCH -> Ssc14Items.ENCRYPTION_KEY_RND.get();
            case SECURITY -> Ssc14Items.ENCRYPTION_KEY_SECURITY.get();
            case SERVICE -> Ssc14Items.ENCRYPTION_KEY_SERVICE.get();
            case CARGO -> Ssc14Items.ENCRYPTION_KEY_CARGO.get();
        };
    }

    public static HeadsetChannel fromKey(Item item) {
        for (HeadsetChannel channel : values()) {
            if (channel.keyItem() == item) {
                return channel;
            }
        }
        return null;
    }
}
