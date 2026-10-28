package net.ssc14.content.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Ssc14EntityBehaviorTest {
    @Test
    void registryFailsFastBeforeInstallationAndRejectsReplacement() {
        var registry = new Ssc14EntityBehavior.HookRegistry();
        var first = new NoOpHooks();
        var replacement = new NoOpHooks();

        assertThrows(IllegalStateException.class, registry::current);
        registry.install(first);
        assertSame(first, registry.current());
        assertDoesNotThrow(() -> registry.install(first));
        assertThrows(IllegalStateException.class, () -> registry.install(replacement));
        assertThrows(NullPointerException.class,
                () -> new Ssc14EntityBehavior.HookRegistry().install(null));
    }

    private static final class NoOpHooks implements Ssc14EntityBehavior.Hooks {
        @Override
        public void bulletFlight(LevelAccessor level, double x, double y, double z) {
        }

        @Override
        public void bulletBlockCollision(LevelAccessor level, Entity projectile) {
        }

        @Override
        public void c4Tick(LevelAccessor level, Entity entity) {
        }

        @Override
        public void chairTick(LevelAccessor level, Entity entity) {
        }

        @Override
        public void spannerInteraction(
                LevelAccessor level, double x, double y, double z, Entity entity, Entity sourceEntity) {
        }
    }
}
