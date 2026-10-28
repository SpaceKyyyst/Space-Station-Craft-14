package net.ssc14.integration.neoforge.menu.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.ssc14.common.Ssc14Identity;

/** Shared wire shape for legacy GUI button channels. */
public record MenuButtonPayload(Channel channel, int buttonId, int x, int y, int z)
        implements CustomPacketPayload {

    public enum Channel {
        VENDING("vending_aut_1_gui_buttons"),
        SALE("sale_gui_buttons"),
        ORDER("order_gui_buttons"),
        MICROWAVE("microwawe_gui_buttons");

        private final Type<MenuButtonPayload> type;

        Channel(String path) {
            this.type = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path));
        }

        public Type<MenuButtonPayload> type() {
            return type;
        }
    }

    public static StreamCodec<RegistryFriendlyByteBuf, MenuButtonPayload> codec(Channel channel) {
        return StreamCodec.of(
                (buffer, message) -> {
                    if (message.channel != channel) {
                        throw new IllegalArgumentException("Payload channel does not match registered codec");
                    }
                    buffer.writeInt(message.buttonId);
                    buffer.writeInt(message.x);
                    buffer.writeInt(message.y);
                    buffer.writeInt(message.z);
                },
                buffer -> new MenuButtonPayload(
                        channel, buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
    }

    public static MenuButtonPayload vending(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.VENDING, buttonId, x, y, z);
    }

    public static MenuButtonPayload sale(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.SALE, buttonId, x, y, z);
    }

    public static MenuButtonPayload order(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.ORDER, buttonId, x, y, z);
    }

    public static MenuButtonPayload microwave(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.MICROWAVE, buttonId, x, y, z);
    }

    @Override
    public Type<MenuButtonPayload> type() {
        return channel.type();
    }
}
