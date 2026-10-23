package net.ssc14.content.entity;

import java.util.Objects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

/**
 * Compatibility port for entity behavior that has not yet moved out of the legacy procedure layer.
 */
public final class Ssc14EntityBehavior {
    private static final HookRegistry HOOKS = new HookRegistry();

    private Ssc14EntityBehavior() {
    }

    public static void install(Hooks hooks) {
        HOOKS.install(hooks);
    }

    public static void bulletFlight(LevelAccessor level, double x, double y, double z) {
        hooks().bulletFlight(level, x, y, z);
    }

    public static void bulletBlockCollision(LevelAccessor level, Entity projectile) {
        hooks().bulletBlockCollision(level, projectile);
    }

    public static void c4Tick(LevelAccessor level, Entity entity) {
        hooks().c4Tick(level, entity);
    }

    public static void chairTick(LevelAccessor level, Entity entity) {
        hooks().chairTick(level, entity);
    }

    public static void spannerInteraction(
            LevelAccessor level, double x, double y, double z, Entity entity, Entity sourceEntity) {
        hooks().spannerInteraction(level, x, y, z, entity, sourceEntity);
    }

    private static Hooks hooks() {
        return HOOKS.current();
    }

    static final class HookRegistry {
        private volatile Hooks installedHooks;

        synchronized void install(Hooks hooks) {
            Objects.requireNonNull(hooks, "hooks");
            if (installedHooks != null && installedHooks != hooks) {
                throw new IllegalStateException("SSC14 entity behavior hooks are already installed");
            }
            installedHooks = hooks;
        }

        Hooks current() {
            Hooks hooks = installedHooks;
            if (hooks == null) {
                throw new IllegalStateException("SSC14 entity behavior hooks were not installed during bootstrap");
            }
            return hooks;
        }
    }

    public interface Hooks {
        void bulletFlight(LevelAccessor level, double x, double y, double z);

        void bulletBlockCollision(LevelAccessor level, Entity projectile);

        void c4Tick(LevelAccessor level, Entity entity);

        void chairTick(LevelAccessor level, Entity entity);

        void spannerInteraction(
                LevelAccessor level, double x, double y, double z, Entity entity, Entity sourceEntity);
    }
}
