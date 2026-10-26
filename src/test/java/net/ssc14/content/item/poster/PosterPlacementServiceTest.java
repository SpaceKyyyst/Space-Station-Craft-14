package net.ssc14.content.item.poster;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PosterPlacementServiceTest {
    @Test
    void snapsClickCoordinatesToLowCenterAndHighAnchors() {
        assertEquals(0.0D, PosterPlacementService.smartOffset(0.0D));
        assertEquals(0.0D, PosterPlacementService.smartOffset(0.25D));
        assertEquals(0.5D, PosterPlacementService.smartOffset(0.5D));
        assertEquals(0.5D, PosterPlacementService.smartOffset(0.75D));
        assertEquals(1.0D, PosterPlacementService.smartOffset(0.76D));
    }
}
