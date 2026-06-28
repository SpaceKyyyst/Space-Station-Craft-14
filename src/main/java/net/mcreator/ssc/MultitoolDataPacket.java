
package net.mcreator.ssc;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.client.Minecraft;

public record MultitoolDataPacket(int mode, long current, long battery, long theoretical, long ideal, 
                                  long inStored, long inMax, long outStored, long outMax) implements CustomPacketPayload {

    public static final Type<MultitoolDataPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("ssc_14", "multitool_data"));

    public static final StreamCodec<FriendlyByteBuf, MultitoolDataPacket> CODEC = StreamCodec.of(
        (buf, val) -> {
            buf.writeInt(val.mode);
            buf.writeLong(val.current);
            buf.writeLong(val.battery);
            buf.writeLong(val.theoretical);
            buf.writeLong(val.ideal);
            buf.writeLong(val.inStored);
            buf.writeLong(val.inMax);
            buf.writeLong(val.outStored);
            buf.writeLong(val.outMax);
        },
        buf -> new MultitoolDataPacket(
            buf.readInt(),
            buf.readLong(),
            buf.readLong(),
            buf.readLong(),
            buf.readLong(),
            buf.readLong(),
            buf.readLong(),
            buf.readLong(),
            buf.readLong()
        )
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleClient(final MultitoolDataPacket packet, final IPayloadContext context) {
        // Метод context.enqueueWork теперь идеально отработает в связке с PacketDistributor
        context.enqueueWork(() -> {
            MultitoolClientCache.currentMode = packet.mode;
            MultitoolClientCache.currentPower = packet.current;
            MultitoolClientCache.batteryPower = packet.battery;
            MultitoolClientCache.theoreticalSupply = packet.theoretical;
            MultitoolClientCache.idealConsumption = packet.ideal;
            MultitoolClientCache.inputStored = packet.inStored;
            MultitoolClientCache.inputMax = packet.inMax;
            MultitoolClientCache.outputStored = packet.outStored;
            MultitoolClientCache.outputMax = packet.outMax;
        });
    }
}
