package net.mcreator.ssc.medical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.mcreator.ssc.procedures.CrawlPrProcedure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/** Minecraft boundary for owned medical modifiers, blindness, and forced pose. */
@EventBusSubscriber(modid = "ssc_14")
public final class MedicalPlayerEffects implements MedicalEffectsController.Target {
    public static final String TOTAL_DAMAGE = "sscCustomHealth";
    public static final String ASPHYX_TIMER = "ssc14_asphyxTimer";
    private static final String CRITICAL = "ssc14_critical";
    private static final String OWNERSHIP = "ssc_14:medical_effects";
    private static final String BLINDNESS = "blindness";
    private static final String POSE = "pose";
    private static final String POSE_YIELDED = "pose_yielded";
    private static final float CRITICAL_WIDTH = 0.6f;
    private static final float CRITICAL_HEIGHT = 0.45f;
    private static final Identifier IMMOBILIZE = Identifier.fromNamespaceAndPath("ssc14", "crit_immobilize");
    private static final Identifier NO_JUMP = Identifier.fromNamespaceAndPath("ssc14", "crit_nojump");
    private final Player player;

    // Effect events are synchronous; keep no references between reconciliations.
    private static final ThreadLocal<MobEffectInstance> OWN_BLINDNESS_ADDITION = new ThreadLocal<>();

    private MedicalPlayerEffects(Player player) {
        this.player = player;
    }

    public static void reconcile(Player player, int blindnessTicks) {
        if (player.level().isClientSide()) return;
        apply(player, MedicalStatePolicy.isBuildRestricted(player.getPersistentData().getDoubleOr(TOTAL_DAMAGE, 0)), blindnessTicks);
    }

    public static void release(Player player) {
        if (!player.level().isClientSide()) apply(player, false, 0);
    }

    private static void apply(Player player, boolean critical, int blindnessTicks) {
        var data = player.getPersistentData();
        var saved = data.getCompound(OWNERSHIP).orElse(null);
        var before = saved == null ? MedicalEffectsController.Ownership.NONE
                : new MedicalEffectsController.Ownership(saved.getBooleanOr(BLINDNESS, false),
                        saved.getBooleanOr(POSE, false), saved.getBooleanOr(POSE_YIELDED, false));
        var adapter = new MedicalPlayerEffects(player);
        var after = MedicalEffectsController.apply(adapter, before, critical, blindnessTicks);
        if (after.equals(MedicalEffectsController.Ownership.NONE)) {
            if (saved != null) data.remove(OWNERSHIP);
        } else if (!after.equals(before)) {
            var state = saved == null ? new CompoundTag() : saved;
            state.putBoolean(BLINDNESS, after.blindness());
            state.putBoolean(POSE, after.pose());
            state.putBoolean(POSE_YIELDED, after.poseYielded());
            data.put(OWNERSHIP, state);
        }
        if (data.getBooleanOr(CRITICAL, false) != critical) data.putBoolean(CRITICAL, critical);
        if (!critical && data.contains(ASPHYX_TIMER)) data.remove(ASPHYX_TIMER);
        // Ownership must be stored before Size observes the new pose. Movement then
        // rebuilds the box from these dimensions without an every-tick box override.
        if (after.pose() && !before.pose()) player.refreshDimensions();
    }

    @Override
    public boolean isClientSide() { return player.level().isClientSide(); }
    @Override
    public boolean hasBlindness() { return player.hasEffect(MobEffects.BLINDNESS); }
    @Override
    public boolean addBlindness(int ticks) {
        player.getPersistentData().getCompound(OWNERSHIP).ifPresent(MedicalBlindnessContributions::clear);
        var blindness = new MobEffectInstance(MobEffects.BLINDNESS, ticks, 0, false, false);
        OWN_BLINDNESS_ADDITION.set(blindness);
        try {
            return player.addEffect(blindness);
        } finally {
            OWN_BLINDNESS_ADDITION.remove();
        }
    }
    @Override
    public void removeBlindness() {
        var state = player.getPersistentData().getCompound(OWNERSHIP).orElse(null);
        var external = state == null ? null : MedicalBlindnessContributions.remaining(state);
        if (external == null) {
            player.removeEffect(MobEffects.BLINDNESS);
        } else {
            // Replacing the merged instance strips every SSC hidden contribution while
            // retaining the external-only active/hidden chain and its remaining duration.
            OWN_BLINDNESS_ADDITION.set(external);
            try {
                player.forceAddEffect(external, null);
            } finally {
                OWN_BLINDNESS_ADDITION.remove();
            }
        }
    }

    public static void advanceEffectClock(Player player) {
        if (!player.level().isClientSide()) {
            player.getPersistentData().getCompound(OWNERSHIP).ifPresent(MedicalBlindnessContributions::advance);
        }
    }
    @Override
    public boolean canAcquirePose() {
        return player.getForcedPose() == null && player.getPose() == Pose.STANDING && !hasExternalSwimming();
    }
    @Override
    public boolean hasMedicalPose() { return player.getForcedPose() == Pose.SWIMMING; }
    @Override
    public void acquirePose() {
        player.setForcedPose(Pose.SWIMMING);
        if (player.getPose() != Pose.SWIMMING) player.setPose(Pose.SWIMMING);
    }
    @Override
    public void releasePose() {
        player.setForcedPose(null);
        if (player.getPose() == Pose.SWIMMING && !hasExternalSwimming()) player.setPose(Pose.STANDING);
        player.refreshDimensions();
    }

    private boolean hasExternalSwimming() {
        return player.isInWater() || player.isSwimming() || player.getPersistentData().getBooleanOr(CrawlPrProcedure.NBT_KEY, false);
    }

    @Override
    public void reconcileModifiers(boolean critical) {
        reconcileModifier(player.getAttribute(Attributes.MOVEMENT_SPEED), IMMOBILIZE, critical);
        reconcileModifier(player.getAttribute(Attributes.JUMP_STRENGTH), NO_JUMP, critical);
    }

    private static void reconcileModifier(AttributeInstance attribute, Identifier id, boolean critical) {
        if (attribute == null) return;
        if (critical && !attribute.hasModifier(id)) {
            attribute.addTransientModifier(new AttributeModifier(id, -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else if (!critical && attribute.hasModifier(id)) {
            attribute.removeModifier(id);
        }
    }

    @SubscribeEvent
    public static void onSize(EntityEvent.Size event) {
        if (event.getPose() == Pose.SWIMMING && event.getEntity() instanceof Player player
                && !player.level().isClientSide() && player.getForcedPose() == Pose.SWIMMING
                && player.getPersistentData().getCompound(OWNERSHIP).map(state -> state.getBooleanOr(POSE, false)).orElse(false)) {
            event.setNewSize(EntityDimensions.scalable(CRITICAL_WIDTH, CRITICAL_HEIGHT)
                    .withEyeHeight(event.getNewSize().eyeHeight()));
        }
    }

    @SubscribeEvent
    public static void onBlindnessAdded(MobEffectEvent.Added event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()
                && event.getEffectInstance().getEffect().equals(MobEffects.BLINDNESS)
                && event.getEffectInstance() != OWN_BLINDNESS_ADDITION.get()) {
            player.getPersistentData().getCompound(OWNERSHIP).ifPresent(state -> {
                if (!state.getBooleanOr(BLINDNESS, false)) return;
                if (event.getOldEffectInstance() == null) {
                    state.putBoolean(BLINDNESS, false);
                    MedicalBlindnessContributions.clear(state);
                } else {
                    MedicalBlindnessContributions.record(state, event.getOldEffectInstance(), event.getEffectInstance());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) release(player);
    }
    @SubscribeEvent
    public static void onLeave(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) release(player);
    }
    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        // Never transfer runtime ownership from the original player to a new entity.
        release(event.getOriginal());
        release(event.getEntity());
    }
}
