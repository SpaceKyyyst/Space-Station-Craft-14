package net.mcreator.ssc.energy;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import net.ssc14.core.energy.ConnectedComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectedComponentsTest {
    @Test
    void removingBridgeRetainsBothDisconnectedSides() {
        Set<Integer> remaining = Set.of(0, 1, 3, 4);
        var components = ConnectedComponents.find(remaining, remaining::contains,
                position -> List.of(position - 1, position + 1));
        assertEquals(Set.of(Set.of(0, 1), Set.of(3, 4)), Set.copyOf(components));
    }

    @Test
    void discoversUnregisteredNeighborsAndVisitsEachComponentOnce() {
        Set<Integer> cables = Set.of(0, 1, 2, 3);
        var components = ConnectedComponents.find(List.of(0, 3), cables::contains,
                position -> List.of(position - 1, position + 1));
        assertEquals(List.of(cables), components);
    }

    @Test
    void unloadedBridgeDoesNotConnectLoadedSides() {
        Set<Integer> loaded = Set.of(0, 2);
        assertEquals(2, ConnectedComponents.find(List.of(0, 1, 2), loaded::contains,
                position -> List.of(position - 1, position + 1)).size());
    }
}
