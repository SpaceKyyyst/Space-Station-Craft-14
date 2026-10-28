package net.ssc14.integration.neoforge.menu.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.ssc14.common.Ssc14Identity;

/** Shared six-integer wire contract for generated menu slot changes. */
public record MenuSlotPayload(Channel channel, int slotId, int x, int y, int z, int changeType, int meta)
        implements CustomPacketPayload {
    public enum Channel {
        ID_CODE("i_dcode_slots"),
        MACHINE_FRAME("mac_frame_gui_slots");

        private final Type<MenuSlotPayload> type;

        Channel(String path) {
            type = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path));
        }

        public Type<MenuSlotPayload> type() {
            return type;
        }
    }

    public static StreamCodec<RegistryFriendlyByteBuf, MenuSlotPayload> codec(Channel channel) {
        return StreamCodec.of((buffer, message) -> {
            if (message.channel != channel) {
                throw new IllegalArgumentException("Payload channel does not match registered codec");
            }
            buffer.writeInt(message.slotId);
            buffer.writeInt(message.x);
            buffer.writeInt(message.y);
            buffer.writeInt(message.z);
            buffer.writeInt(message.changeType);
            buffer.writeInt(message.meta);
        }, buffer -> new MenuSlotPayload(channel, buffer.readInt(), buffer.readInt(), buffer.readInt(),
                buffer.readInt(), buffer.readInt(), buffer.readInt()));
    }

    public static MenuSlotPayload idCode(int slotId, int x, int y, int z, int changeType, int meta) {
        return new MenuSlotPayload(Channel.ID_CODE, slotId, x, y, z, changeType, meta);
    }

    public static MenuSlotPayload machineFrame(int slotId, int x, int y, int z, int changeType, int meta) {
        return new MenuSlotPayload(Channel.MACHINE_FRAME, slotId, x, y, z, changeType, meta);
    }

    @Override
    public Type<MenuSlotPayload> type() {
        return channel.type();
    }
}
