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
        MICROWAVE("microwawe_gui_buttons"),
        AIRLOCK_PANEL("cabel_pannel_airlock_buttons"),
        DECAL_COLOR("decal_scolor_gui_buttons"),
        GRAVITY_GENERATOR("grav_gen_gui_buttons"),
        PDA_PASSENGER("pd_apassanger_gui_buttons");

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

    public static MenuButtonPayload airlockPanel(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.AIRLOCK_PANEL, buttonId, x, y, z);
    }

    public static MenuButtonPayload decalColor(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.DECAL_COLOR, buttonId, x, y, z);
    }

    public static MenuButtonPayload gravityGenerator(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.GRAVITY_GENERATOR, buttonId, x, y, z);
    }

    public static MenuButtonPayload pdaPassenger(int buttonId, int x, int y, int z) {
        return new MenuButtonPayload(Channel.PDA_PASSENGER, buttonId, x, y, z);
    }

    @Override
    public Type<MenuButtonPayload> type() {
        return channel.type();
    }
}
