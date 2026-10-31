package net.ssc14.architecture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.neoforged.bus.api.IEventBus;
import net.ssc14.integration.Ssc14Bootstrap;
import org.junit.jupiter.api.Test;
class Ssc14BootstrapBoundaryTest {
    @Test
    void canonicalBootstrapOwnsModEventBusInitialization() throws ReflectiveOperationException {
        Method initialize = Ssc14Bootstrap.class.getMethod("initialize", IEventBus.class);
        assertTrue(Modifier.isStatic(initialize.getModifiers()));
        assertEquals(void.class, initialize.getReturnType());
    }
}
