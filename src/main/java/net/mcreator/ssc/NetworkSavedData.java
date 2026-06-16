
package net.mcreator.ssc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;

public class NetworkSavedData extends SavedData {
    public static class NetworkConnection {
        public BlockPos sourcePos;
        public String triggerId;
        public BlockPos targetPos;
        public String actionId;

        public NetworkConnection(BlockPos source, String trigger, BlockPos target, String action) {
            this.sourcePos = source;
            this.triggerId = trigger;
            this.targetPos = target;
            this.actionId = action;
        }
    }

    public static final Codec<NetworkConnection> CONNECTION_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("sourcePos").forGetter(c -> c.sourcePos),
            Codec.STRING.fieldOf("triggerId").forGetter(c -> c.triggerId),
            BlockPos.CODEC.fieldOf("targetPos").forGetter(c -> c.targetPos),
            Codec.STRING.fieldOf("actionId").forGetter(c -> c.actionId)
    ).apply(instance, NetworkConnection::new));

    public static final Codec<List<NetworkConnection>> CONNECTIONS_CODEC = CONNECTION_CODEC.listOf();

    public static final Codec<NetworkSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CONNECTIONS_CODEC.fieldOf("connections").forGetter(data -> data.connections)
    ).apply(instance, connections -> {
        NetworkSavedData data = new NetworkSavedData();
        data.connections = new ArrayList<>(connections);
        return data;
    }));

    public static final SavedDataType<NetworkSavedData> TYPE = new SavedDataType<NetworkSavedData>(
            "network_data",
            () -> new NetworkSavedData(),
            CODEC
    );

    public List<NetworkConnection> connections = new ArrayList<>();

    // ИСПРАВЛЕНО: Используем текущее измерение, а не overworld!
    public static NetworkSavedData get(Level world) {
        if (world instanceof ServerLevel serverLevel) {
            return serverLevel.getDataStorage().computeIfAbsent(TYPE);
        }
        return new NetworkSavedData();
    }

    public void addConnection(BlockPos source, String trigger, BlockPos target, String action) {
        connections.removeIf(c -> c.sourcePos.equals(source) && c.triggerId.equals(trigger) && c.targetPos.equals(target) && c.actionId.equals(action));
        connections.add(new NetworkConnection(source, trigger, target, action));
        this.setDirty();
    }

    public void removeConnection(BlockPos source, String trigger, BlockPos target, String action) {
        connections.removeIf(c -> c.sourcePos.equals(source) && c.triggerId.equals(trigger) && c.targetPos.equals(target) && c.actionId.equals(action));
        this.setDirty();
    }
}
