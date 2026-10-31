package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyEnergyRemovalTest {
    private static final String[] REMOVED_TYPES = {
        "net.mcreator.ssc.EnergyNetworkManager",
        "net.mcreator.ssc.energy.CableDeviceMapper",
        "net.mcreator.ssc.energy.EnergyTelemetry",
        "net.mcreator.ssc.energy.EnergyTopologyService",
        "net.mcreator.ssc.energy.LampTickBatch",
        "net.mcreator.ssc.energy.LowVoltageTickBatch"
    };

    private static final String[] CANONICAL_TYPES = {
        "net.ssc14.content.energy.EnergyNetworkService",
        "net.ssc14.content.energy.EnergyTopologyService",
        "net.ssc14.content.energy.CableDeviceMapper",
        "net.ssc14.content.energy.EnergyTelemetry",
        "net.ssc14.content.energy.LampTickBatch",
        "net.ssc14.content.energy.LowVoltageTickBatch"
    };

    @Test
    void legacyEnergyTypesAreRemoved() {
        for (String typeName : REMOVED_TYPES) {
            assertThrows(ClassNotFoundException.class, () -> Class.forName(typeName), typeName);
        }
    }

    @Test
    void canonicalEnergyTypesRemainAvailable() {
        for (String typeName : CANONICAL_TYPES) {
            assertDoesNotThrow(() -> Class.forName(typeName), typeName);
        }
    }
}
