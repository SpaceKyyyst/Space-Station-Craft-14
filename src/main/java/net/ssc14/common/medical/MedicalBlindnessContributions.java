package net.ssc14.common.medical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.effect.MobEffectInstance;

/** Tracks external contributions separately from SSC's mergeable blindness. */
public final class MedicalBlindnessContributions {
    private static final String EXTERNAL = "external_blindness";
    private static final String OBSERVED = "observed_blindness";
    private static final String HIDDEN = "hidden_effect";
    private MedicalBlindnessContributions() { }

    public static void record(CompoundTag state, MobEffectInstance current, MobEffectInstance incoming) {
        var before = encode(current);
        var merged = decode(before.copy());
        merged.update(incoming);
        if (sameContributions(before, encode(merged))) return;
        var external = remaining(state, current);
        // Vanilla update takes incoming's details, never incoming's hidden payload.
        if (external == null) external = new MobEffectInstance(incoming);
        else external.update(incoming);
        state.put(EXTERNAL, encode(external));
        // Added fires before update. Capture its exact predicted result, including
        // SSC's hidden duration counter, for comparison with the next observation.
        state.put(OBSERVED, encode(merged));
        state.remove("clock");
        state.remove("external_clock");
    }

    public static MobEffectInstance remaining(CompoundTag state, MobEffectInstance current) {
        var external = state.getCompound(EXTERNAL).map(CompoundTag::copy).orElse(null);
        if (external == null || current == null) return null;
        // Legacy snapshots lack OBSERVED: their external nodes can still be matched
        // to the active chain. Never charge a clock phase that did not tick effects.
        var observed = state.getCompound(OBSERVED).orElse(external);
        long elapsed = elapsedTicks(observed, encode(current));
        // All hidden durations tick concurrently in vanilla. Subtract once per node,
        // then promote the first remaining contribution; never loop over elapsed ticks.
        for (var node = external; node != null; node = node.getCompound(HIDDEN).orElse(null)) {
            int duration = node.getIntOr("duration", 0);
            if (duration != MobEffectInstance.INFINITE_DURATION) {
                node.putInt("duration", (int) Math.max(0, duration - elapsed));
            }
        }
        while (external != null && (external.getIntOr("duration", 0) == 0
                || external.getIntOr("amplifier", 0) > current.getAmplifier())) {
            var hidden = external.getCompound(HIDDEN).orElse(null);
            if (hidden != null) hidden.putString("id", external.getStringOr("id", "minecraft:blindness"));
            external = hidden;
        }
        return external == null ? null : decode(external);
    }

    public static void clear(CompoundTag state) {
        state.remove(EXTERNAL);
        state.remove(OBSERVED);
        state.remove("clock");
        state.remove("external_clock");
    }

    private static long elapsedTicks(CompoundTag previous, CompoundTag current) {
        long minimumElapsed = 0;
        // Vanilla hidden chains have descending amplifier levels. Matching an
        // unchanged finite node reveals real ticks even after root promotion or
        // while an infinite root hides SSC's finite duration counter.
        while (previous != null && current != null) {
            int previousAmplifier = previous.getIntOr("amplifier", 0);
            int currentAmplifier = current.getIntOr("amplifier", 0);
            if (previousAmplifier == currentAmplifier) {
                int before = previous.getIntOr("duration", 0);
                int after = current.getIntOr("duration", 0);
                if (before > 0 && after > 0 && after <= before) return (long) before - after;
                if (before > 0 && after == 0) minimumElapsed = Math.max(minimumElapsed, before);
            }
            if (previousAmplifier >= currentAmplifier) previous = previous.getCompound(HIDDEN).orElse(null);
            if (currentAmplifier >= previousAmplifier) current = current.getCompound(HIDDEN).orElse(null);
        }
        // Infinite-only chains do not age. Saturated zero counters provide a lower
        // bound sufficient to discard the finite contributions that have expired.
        return minimumElapsed;
    }

    private static boolean sameContributions(CompoundTag first, CompoundTag second) {
        while (first != null && second != null) {
            if (first.getIntOr("duration", 0) != second.getIntOr("duration", 0)
                    || first.getIntOr("amplifier", 0) != second.getIntOr("amplifier", 0)) return false;
            first = first.getCompound(HIDDEN).orElse(null);
            second = second.getCompound(HIDDEN).orElse(null);
        }
        return first == second;
    }

    private static CompoundTag encode(MobEffectInstance effect) {
        return (CompoundTag) MobEffectInstance.CODEC.encodeStart(NbtOps.INSTANCE, effect).getOrThrow();
    }

    private static MobEffectInstance decode(CompoundTag tag) {
        return MobEffectInstance.CODEC.parse(NbtOps.INSTANCE, tag).getOrThrow();
    }
}
