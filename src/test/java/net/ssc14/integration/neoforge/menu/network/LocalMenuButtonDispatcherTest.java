package net.ssc14.integration.neoforge.menu.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class LocalMenuButtonDispatcherTest {
    @Test
    void dispatchesThroughInstalledHandler() {
        var calls = new AtomicInteger();
        var dispatcher = new LocalMenuButtonDispatcher();
        dispatcher.install((player, payload) -> calls.incrementAndGet());

        dispatcher.dispatch(null, MenuButtonPayload.sale(0, 1, 2, 3));

        assertEquals(1, calls.get());
    }

    @Test
    void rejectsMissingOrDuplicateHandler() {
        var dispatcher = new LocalMenuButtonDispatcher();
        assertThrows(IllegalStateException.class,
                () -> dispatcher.dispatch(null, MenuButtonPayload.sale(0, 1, 2, 3)));

        dispatcher.install((player, payload) -> { });
        assertThrows(IllegalStateException.class,
                () -> dispatcher.install((player, payload) -> { }));
    }
}
