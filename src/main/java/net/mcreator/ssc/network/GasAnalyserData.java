package net.mcreator.ssc.network;

import net.mcreator.ssc.AtmosCell;
import net.ssc14.common.atmosphere.model.GasType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Fixed-size server snapshot; it is stored on the receiving player, not globally. */
public record GasAnalyserData(String dimension, BlockPos position, float temperature, float pressure, float[] moles)
        implements CustomPacketPayload {
    public static final String CACHE_KEY = "ssc14_analyser_snapshot";
    public static final Type<GasAnalyserData> TYPE = new Type<>(Identifier.parse("ssc_14:gas_analyser"));
    public static final StreamCodec<FriendlyByteBuf, GasAnalyserData> CODEC = StreamCodec.of(
        (buffer, data) -> {
            buffer.writeUtf(data.dimension);
            buffer.writeBlockPos(data.position);
            buffer.writeFloat(data.temperature);
            buffer.writeFloat(data.pressure);
            for (float amount : data.moles) buffer.writeFloat(amount);
        }, buffer -> {
            String dimension = buffer.readUtf();
            BlockPos pos = buffer.readBlockPos();
            float temperature = buffer.readFloat();
            float pressure = buffer.readFloat();
            float[] amounts = new float[GasType.COUNT];
            for (int index = 0; index < amounts.length; index++) amounts[index] = buffer.readFloat();
            return new GasAnalyserData(dimension, pos, temperature, pressure, amounts);
        });

    public GasAnalyserData {
        if (moles.length != GasType.COUNT) throw new IllegalArgumentException("Invalid gas count");
        moles = moles.clone();
    }

    @Override public float[] moles() { return moles.clone(); }
    @Override public Type<GasAnalyserData> type() { return TYPE; }

    public static GasAnalyserData from(String dimension, BlockPos position, AtmosCell cell) {
        float[] amounts = new float[GasType.COUNT];
        for (int index = 0; index < amounts.length; index++) amounts[index] = cell.getMoles(index);
        return new GasAnalyserData(dimension, position, cell.getTemperature(), cell.getPressure(), amounts);
    }

    public static void handle(GasAnalyserData data, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            var tag = new net.minecraft.nbt.CompoundTag();
            tag.putLong("position", data.position.asLong());
            tag.putString("dimension", data.dimension);
            tag.putLong("received", player.level().getGameTime());
            tag.putFloat("temperature", data.temperature);
            tag.putFloat("pressure", data.pressure);
            for (int index = 0; index < GasType.COUNT; index++) tag.putFloat("gas_" + index, data.moles[index]);
            player.getPersistentData().put(CACHE_KEY, tag);
        });
    }
}
