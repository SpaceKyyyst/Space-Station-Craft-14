
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DecalData(BlockPos pos, Direction face, String decalId, int rotation, int color) {
    // Конструктор по умолчанию для обратной совместимости, если цвет не указан
    public DecalData(BlockPos pos, Direction face, String decalId, int rotation) {
        this(pos, face, decalId, rotation, -1);
    }

    public static final Codec<DecalData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(DecalData::pos),
            Direction.CODEC.fieldOf("face").forGetter(DecalData::face),
            Codec.STRING.fieldOf("id").forGetter(DecalData::decalId),
            Codec.INT.fieldOf("rot").forGetter(DecalData::rotation),
            Codec.INT.optionalFieldOf("color", -1).forGetter(DecalData::color)
        ).apply(instance, DecalData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DecalData> STREAM_CODEC =
        new StreamCodec<>() {
            @Override
            public DecalData decode(RegistryFriendlyByteBuf buffer) {
                return new DecalData(
                    buffer.readBlockPos(), 
                    buffer.readEnum(Direction.class),
                    buffer.readUtf(), 
                    buffer.readVarInt(),
                    buffer.readInt() // Читаем упакованный цвет
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, DecalData value) {
                buffer.writeBlockPos(value.pos());
                buffer.writeEnum(value.face());
                buffer.writeUtf(value.decalId());
                buffer.writeVarInt(value.rotation());
                buffer.writeInt(value.color()); // Записываем упакованный цвет
            }
        };
}
