package net.mcreator.ssc;

import net.ssc14.common.atmosphere.model.GasType;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.Identifier;
import net.mcreator.ssc.network.Ssc14ModVariables;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertInstanceOf(DoubleTag.class, encoded.get("Station_Budget"));
        assertInstanceOf(StringTag.class, encoded.get("PalletRegistry"));
        assertEquals(125.5, encoded.getDoubleOr("Station_Budget", 0));
        assertEquals("legacy-pallets", encoded.getStringOr("PalletRegistry", ""));

        Ssc14ModVariables.WorldVariables roundTripped = new Ssc14ModVariables.WorldVariables();
        roundTripped.read(encoded, RegistryAccess.EMPTY);
        assertEquals(125.5, roundTripped.Station_Budget);
        assertEquals("legacy-pallets", roundTripped.PalletRegistry);

        CompoundTag partial = new CompoundTag();
        partial.putDouble("Station_Budget", 7);
        partial.putString("unknownLegacyField", "ignored");
        Ssc14ModVariables.WorldVariables restored = new Ssc14ModVariables.WorldVariables();
        restored.read(partial, RegistryAccess.EMPTY);
        assertEquals(7, restored.Station_Budget);
        assertEquals("", restored.PalletRegistry);

        Ssc14ModVariables.WorldVariables missing = new Ssc14ModVariables.WorldVariables();
        missing.read(new CompoundTag(), RegistryAccess.EMPTY);
        assertEquals(0, missing.Station_Budget);
        assertEquals("", missing.PalletRegistry);
    }

    @Test
    void generatedMapVariablesKeepKeysAndMissingFieldDefaults() {
        Ssc14ModVariables.MapVariables values = new Ssc14ModVariables.MapVariables();
        values.station_code = 14;
        values.station_name = "Station";
        values.Base_Radio_Frequency = true;
        values.Command_Radio_Frequency = false;
        values.Engeneer_Radio_Frequency = true;
        values.Medical_Radio_Frequency = false;
        values.RND_Radio_Frequency = true;
        values.Sec_Radio_Frequency = false;
        values.Serv_Radio_Frequency = true;
        values.Cargo_Radio_Frequency = false;
        CompoundTag encoded = values.save(new CompoundTag(), RegistryAccess.EMPTY);
        assertEquals(Set.of("station_code", "station_name", "Base_Radio_Frequency",
                "Command_Radio_Frequency", "Engeneer_Radio_Frequency", "Medical_Radio_Frequency",
                "RND_Radio_Frequency", "Sec_Radio_Frequency", "Serv_Radio_Frequency",
                "Cargo_Radio_Frequency"), encoded.keySet());
        assertInstanceOf(DoubleTag.class, encoded.get("station_code"));
        assertInstanceOf(StringTag.class, encoded.get("station_name"));
        for (String key : List.of("Base_Radio_Frequency", "Command_Radio_Frequency",
                "Engeneer_Radio_Frequency", "Medical_Radio_Frequency", "RND_Radio_Frequency",
                "Sec_Radio_Frequency", "Serv_Radio_Frequency", "Cargo_Radio_Frequency")) {
            assertInstanceOf(ByteTag.class, encoded.get(key));
        }
        assertEquals(14, encoded.getDoubleOr("station_code", 0));
        assertEquals("Station", encoded.getStringOr("station_name", ""));

        Ssc14ModVariables.MapVariables roundTripped = new Ssc14ModVariables.MapVariables();
        roundTripped.read(encoded, RegistryAccess.EMPTY);
        assertEquals(14, roundTripped.station_code);
        assertEquals("Station", roundTripped.station_name);
        assertTrue(roundTripped.Base_Radio_Frequency);
        assertFalse(roundTripped.Command_Radio_Frequency);
        assertTrue(roundTripped.Engeneer_Radio_Frequency);
        assertFalse(roundTripped.Medical_Radio_Frequency);
        assertTrue(roundTripped.RND_Radio_Frequency);
        assertFalse(roundTripped.Sec_Radio_Frequency);
        assertTrue(roundTripped.Serv_Radio_Frequency);
        assertFalse(roundTripped.Cargo_Radio_Frequency);

        CompoundTag partial = new CompoundTag();
        partial.putString("station_name", "Legacy");
        partial.putBoolean("unknownLegacyField", true);
        Ssc14ModVariables.MapVariables restored = new Ssc14ModVariables.MapVariables();
        restored.read(partial, RegistryAccess.EMPTY);
        assertEquals("Legacy", restored.station_name);
        assertEquals(0, restored.station_code);
        assertFalse(restored.Base_Radio_Frequency);
        assertFalse(restored.Cargo_Radio_Frequency);

        Ssc14ModVariables.MapVariables missing = new Ssc14ModVariables.MapVariables();
        missing.read(new CompoundTag(), RegistryAccess.EMPTY);
        assertEquals(0, missing.station_code);
        assertEquals("", missing.station_name);
        assertFalse(missing.Base_Radio_Frequency);
        assertFalse(missing.Command_Radio_Frequency);
        assertFalse(missing.Engeneer_Radio_Frequency);
        assertFalse(missing.Medical_Radio_Frequency);
        assertFalse(missing.RND_Radio_Frequency);
        assertFalse(missing.Sec_Radio_Frequency);
        assertFalse(missing.Serv_Radio_Frequency);
        assertFalse(missing.Cargo_Radio_Frequency);
    }
}
