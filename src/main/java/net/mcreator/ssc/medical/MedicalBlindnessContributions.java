package net.mcreator.ssc.medical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.effect.MobEffectInstance;

/** Tracks external contributions separately from SSC's mergeable blindness. */
final class MedicalBlindnessContributions {
    private static final String EXTERNAL = "external_blindness";
    private static final String CAPTURED_CLOCK = "external_clock";
    private static final String CLOCK = "clock";
    private static final String HIDDEN = "hidden_effect";
    private MedicalBlindnessContributions() { }

    static void record(CompoundTag state, MobEffectInstance current, MobEffectInstance incoming) {
        var before = encode(current);
        var merged = decode(before.copy());
        merged.update(incoming);
        if (sameContributions(before, encode(merged))) return;
        var external = remaining(state);
        // Vanilla update takes incoming's details, never incoming's hidden payload.
        if (external == null) external = new MobEffectInstance(incoming);
        else external.update(incoming);
        state.put(EXTERNAL, encode(external));
        state.putLong(CAPTURED_CLOCK, state.getLongOr(CLOCK, 0));
    }

    static MobEffectInstance remaining(CompoundTag state) {
        var external = state.getCompound(EXTERNAL).map(CompoundTag::copy).orElse(null);
        if (external == null) return null;
        long elapsed = Math.max(0, state.getLongOr(CLOCK, 0) - state.getLongOr(CAPTURED_CLOCK, 0));
        // All hidden durations tick concurrently in vanilla. Subtract once per node,
        // then promote the first remaining contribution; never loop over elapsed ticks.
        for (var node = external; node != null; node = node.getCompound(HIDDEN).orElse(null)) {
            int duration = node.getIntOr("duration", 0);
            if (duration != MobEffectInstance.INFINITE_DURATION) {
                node.putInt("duration", (int) Math.max(0, duration - elapsed));
            }
        }
        while (external != null && external.getIntOr("duration", 0) == 0) {
            var hidden = external.getCompound(HIDDEN).orElse(null);
            if (hidden != null) hidden.putString("id", external.getStringOr("id", "minecraft:blindness"));
            external = hidden;
        }
        return external == null ? null : decode(external);
    }

    static void advance(CompoundTag state) {
        state.putLong(CLOCK, state.getLongOr(CLOCK, 0) + 1);
    }

    static void clear(CompoundTag state) {
        state.remove(EXTERNAL);
        state.remove(CAPTURED_CLOCK);
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
