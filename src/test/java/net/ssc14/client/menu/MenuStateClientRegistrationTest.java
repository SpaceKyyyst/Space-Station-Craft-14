package net.ssc14.client.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;
import org.junit.jupiter.api.Test;

class MenuStateClientRegistrationTest {
    @Test
    void attachesClientHandlerWithoutRegisteringAnotherPayloadContract() {
        var calls = new ArrayList<CustomPacketPayload.Type<?>>();
        var work = new ArrayList<Runnable>();
        var event = new RegisterClientPayloadHandlersEvent() {
            @Override
            @SuppressWarnings("unchecked")
            public <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type,
                    IPayloadHandler<T> handler) {
                calls.add(type);
                var message = (T) new MenuStateUpdateMessage(0, "label", "ready");
                handler.handle(message, context(PacketFlow.SERVERBOUND, work));
                assertEquals(0, work.size());
                handler.handle(message, context(PacketFlow.CLIENTBOUND, work));
                assertEquals(1, work.size());
            }
        };

        MenuStateClientRegistration.register(event);

        assertEquals(1, calls.size());
        assertSame(MenuStateUpdateMessage.TYPE, calls.getFirst());
    }

    private static IPayloadContext context(PacketFlow flow, ArrayList<Runnable> work) {
        return (IPayloadContext) Proxy.newProxyInstance(IPayloadContext.class.getClassLoader(),
                new Class<?>[] {IPayloadContext.class}, (proxy, method, args) -> switch (method.getName()) {
                    case "flow" -> flow;
                    case "enqueueWork" -> {
                        work.add((Runnable) args[0]);
                        yield CompletableFuture.completedFuture(null);
                    }
                    default -> throw new AssertionError("Unexpected context call: " + method.getName());
                });
    }
}
