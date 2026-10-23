package net.mcreator.ssc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import net.mcreator.ssc.network.Ssc14ModVariables;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CompatibilityPersistenceTest {
    @Test
    void gasOrdinalsRemainStableForSavedAtmosphereCells() {
        assertEquals(List.of("oxygen", "nitrogen", "carbon_dioxide", "nitrous_oxide", "plasma",
                "ammonia", "tritium", "freon", "helium", "water_vapor"),
                Arrays.stream(GasType.values()).map(GasType::getName).toList());
    }

    @Test
    void savedDataIdentifiersKeepPrimaryAndLegacyNamespaces() {
        assertEquals(Identifier.parse("ssc_14:atmosphere"), AtmosphereSavedData.DATA_ID);
        assertEquals(Identifier.parse("ssc14:network_data"), NetworkSavedData.DATA_ID);
        assertEquals(Identifier.parse("ssc_14:worldvars"), Ssc14ModVariables.WorldVariables.DATA_ID);
        assertEquals(Identifier.parse("ssc_14:mapvars"), Ssc14ModVariables.MapVariables.DATA_ID);
    }

    @Test
    void networkSavedDataKeepsLegacyConnectionSchemaAndToleratesUnknownFields() {
        NetworkSavedData data = new NetworkSavedData();
        data.addConnection(new BlockPos(1, 2, 3), "trigger", new BlockPos(-4, 5, 6), "action");

        CompoundTag encoded = assertInstanceOf(CompoundTag.class,
                NetworkSavedData.CODEC.encodeStart(NbtOps.INSTANCE, data).getOrThrow());
        assertEquals(Set.of("connections"), encoded.keySet());
        ListTag connections = encoded.getList("connections").orElseThrow();
        CompoundTag connection = connections.getCompound(0).orElseThrow();
        assertEquals(Set.of("sourcePos", "triggerId", "targetPos", "actionId"), connection.keySet());

        encoded.putString("unknownLegacyField", "preserved by old worlds");
        NetworkSavedData restored = NetworkSavedData.CODEC.parse(NbtOps.INSTANCE, encoded).getOrThrow();
        assertEquals(1, restored.connections.size());
        assertEquals(new BlockPos(1, 2, 3), restored.connections.getFirst().sourcePos);
        assertEquals("trigger", restored.connections.getFirst().triggerId);
        assertEquals(new BlockPos(-4, 5, 6), restored.connections.getFirst().targetPos);
        assertEquals("action", restored.connections.getFirst().actionId);
    }

    @Test
    void generatedWorldVariablesKeepKeysAndMissingFieldDefaults() {
        Ssc14ModVariables.WorldVariables values = new Ssc14ModVariables.WorldVariables();
        values.Station_Budget = 125.5;
        values.PalletRegistry = "legacy-pallets";
        CompoundTag encoded = values.save(new CompoundTag(), RegistryAccess.EMPTY);
        assertEquals(Set.of("Station_Budget", "PalletRegistry"), encoded.keySet());

        CompoundTag partial = new CompoundTag();
        partial.putDouble("Station_Budget", 7);
        partial.putString("unknownLegacyField", "ignored");
        Ssc14ModVariables.WorldVariables restored = new Ssc14ModVariables.WorldVariables();
        restored.read(partial, RegistryAccess.EMPTY);
        assertEquals(7, restored.Station_Budget);
        assertEquals("", restored.PalletRegistry);
    }

    @Test
    void generatedMapVariablesKeepKeysAndMissingFieldDefaults() {
        Ssc14ModVariables.MapVariables values = new Ssc14ModVariables.MapVariables();
        values.station_code = 14;
        values.station_name = "Station";
        values.Base_Radio_Frequency = true;
        values.Cargo_Radio_Frequency = true;
        CompoundTag encoded = values.save(new CompoundTag(), RegistryAccess.EMPTY);
        assertEquals(Set.of("station_code", "station_name", "Base_Radio_Frequency",
                "Command_Radio_Frequency", "Engeneer_Radio_Frequency", "Medical_Radio_Frequency",
                "RND_Radio_Frequency", "Sec_Radio_Frequency", "Serv_Radio_Frequency",
                "Cargo_Radio_Frequency"), encoded.keySet());

        CompoundTag partial = new CompoundTag();
        partial.putString("station_name", "Legacy");
        partial.putBoolean("unknownLegacyField", true);
        Ssc14ModVariables.MapVariables restored = new Ssc14ModVariables.MapVariables();
        restored.read(partial, RegistryAccess.EMPTY);
        assertEquals("Legacy", restored.station_name);
        assertEquals(0, restored.station_code);
        assertFalse(restored.Base_Radio_Frequency);
        assertFalse(restored.Cargo_Radio_Frequency);
    }
}
