package net.ssc14.content.gravity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.ssc14.common.gravity.GravityPolicy;

/** Server adapter. Ownership follows entity serialization without retaining entity references. */
@EventBusSubscriber(modid = "ssc_14")
public final class EntityGravityController {
    private static final String STATE = "ssc_14:gravity_override";
    private static final String BASELINE = "baseline";
    private static final String LAST_WRITTEN = "last_written";
    private static final String OWNED = "owned";
    private static final String YIELDED = "yielded";

    private EntityGravityController() { }

    /** Called by the server tick adapter after evaluating the pure policy. */
    public static void apply(Entity entity, GravityPolicy.Decision decision) {
        if (decision == GravityPolicy.Decision.RELEASE || entity.isRemoved()) {
            release(entity);
            return;
        }
        boolean observed = entity.isNoGravity();
        var state = entity.getPersistentData().getCompound(STATE).orElse(null);
        if (state == null) {
            state = new CompoundTag();
            state.putBoolean(BASELINE, observed);
            state.putBoolean(LAST_WRITTEN, observed);
            state.putBoolean(OWNED, false);
            // A pre-existing true flag belongs to the entity or another system.
            state.putBoolean(YIELDED, observed);
            entity.getPersistentData().put(STATE, state);
        }
        if (state.getBooleanOr(YIELDED, true)) return;
        if (observed != state.getBooleanOr(LAST_WRITTEN, observed)) {
            state.putBoolean(YIELDED, true);
            state.putBoolean(OWNED, false);
            return;
        }
        boolean desired = decision == GravityPolicy.Decision.NO_GRAVITY;
        if (observed == desired) return;
        entity.setNoGravity(desired);
        if (entity.isNoGravity() != desired) {
            // A specialized entity setter may refuse the override.
            state.putBoolean(YIELDED, true);
            state.putBoolean(OWNED, false);
            return;
        }
        state.putBoolean(LAST_WRITTEN, desired);
        state.putBoolean(OWNED, true);
    }

    private static void release(Entity entity) {
        var data = entity.getPersistentData();
        var state = data.getCompound(STATE).orElse(null);
        if (state == null) return;
        data.remove(STATE);
        boolean observed = entity.isNoGravity();
        if (!state.getBooleanOr(OWNED, false) || state.getBooleanOr(YIELDED, true)
                || observed != state.getBooleanOr(LAST_WRITTEN, observed)) return;
        boolean baseline = state.getBooleanOr(BASELINE, observed);
        if (observed != baseline) entity.setNoGravity(baseline);
    }

    @SubscribeEvent
    public static void onLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide()) release(event.getEntity());
    }

    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        // Saving can precede the leave callback. Reconcile the serialized flag and marker
        // before a loaded/teleported entity starts a fresh policy episode. No world reads.
        if (!event.getLevel().isClientSide()) release(event.getEntity());
    }
}
