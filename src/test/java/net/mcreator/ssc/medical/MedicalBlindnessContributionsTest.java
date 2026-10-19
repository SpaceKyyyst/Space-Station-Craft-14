package net.mcreator.ssc.medical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicalBlindnessContributionsTest {
    @Test
    void shorterSameAmplifierNoOpKeepsSscOwnershipWithoutInventingAnExternalEffect() {
        var state = ownedState();
        var current = blindness(Integer.MAX_VALUE, 0);
        var incoming = blindness(20, 0);
        MedicalBlindnessContributions.record(state, current, incoming);
        assertFalse(current.update(incoming));
        assertTrue(state.getBooleanOr("blindness", false));
        assertNull(MedicalBlindnessContributions.remaining(state));
    }

    @Test
    void strongerShorterExternalEffectDoesNotCarryHiddenPermanentSscBlindnessIntoRecovery() {
        var state = ownedState();
        var current = blindness(Integer.MAX_VALUE, 0);
        var incoming = blindness(4, 1);
        MedicalBlindnessContributions.record(state, current, incoming);
        assertTrue(current.update(incoming));
        for (int tick = 0; tick < 4; tick++) current.tickClient();
        assertEquals(0, current.getAmplifier());
        assertEquals(Integer.MAX_VALUE - 4, current.getDuration(), "Real vanilla merge hides SSC's permanent effect");
        state.putLong("clock", 1);
        var recovered = MedicalBlindnessContributions.remaining(state);
        assertNotNull(recovered);
        assertEquals(1, recovered.getAmplifier());
        assertEquals(3, recovered.getDuration());
        for (int tick = 0; tick < 3; tick++) recovered.tickClient();
        assertEquals(0, recovered.getDuration(), "Recovery must not retain SSC in the hidden chain");
        state.putLong("clock", 4);
        assertNull(MedicalBlindnessContributions.remaining(state));
    }

    @Test
    void externalHiddenChainAndInfiniteDurationArePreserved() {
        var state = ownedState();
        var current = blindness(Integer.MAX_VALUE, 0);
        var first = blindness(40, 1);
        MedicalBlindnessContributions.record(state, current, first);
        current.update(first);
        var second = blindness(4, 2);
        MedicalBlindnessContributions.record(state, current, second);
        current.update(second);
        var recovered = MedicalBlindnessContributions.remaining(state);
        assertNotNull(recovered);
        for (int tick = 0; tick < 4; tick++) recovered.tickClient();
        assertEquals(1, recovered.getAmplifier());
        assertEquals(36, recovered.getDuration());
        for (int tick = 0; tick < 36; tick++) recovered.tickClient();
        assertEquals(0, recovered.getDuration());

        var infinite = blindness(-1, 0);
        var infiniteState = ownedState();
        MedicalBlindnessContributions.record(infiniteState, blindness(Integer.MAX_VALUE, 0), infinite);
        var recoveredInfinite = MedicalBlindnessContributions.remaining(infiniteState);
        assertNotNull(recoveredInfinite);
        assertEquals(-1, recoveredInfinite.getDuration());
    }

    @Test
    void hiddenPayloadOfAnIncomingEffectIsNotInventedWhenVanillaUpdateIgnoresIt() {
        var state = ownedState();
        var current = blindness(Integer.MAX_VALUE, 0);
        var incoming = new MobEffectInstance(MobEffects.BLINDNESS, 4, 1, false, false, false, blindness(-1, 0));
        MedicalBlindnessContributions.record(state, current, incoming);
        assertTrue(current.update(incoming));
        for (int tick = 0; tick < 4; tick++) current.tickClient();
        assertEquals(Integer.MAX_VALUE - 4, current.getDuration(), "Vanilla hides the previous SSC effect, not incoming's hidden payload");
        var external = MedicalBlindnessContributions.remaining(state);
        assertNotNull(external);
        for (int tick = 0; tick < 4; tick++) external.tickClient();
        assertEquals(0, external.getDuration(), "A hidden payload that did not contribute must not reappear on recovery");
    }

    @Test
    void ownershipSnapshotSurvivesSerializationAndDoesNotAccrueOfflineTime() {
        var state = ownedState();
        MedicalBlindnessContributions.record(state, blindness(Integer.MAX_VALUE, 0), blindness(100, 1));
        state.putLong("clock", 7);
        var reloaded = state.copy();
        var remaining = MedicalBlindnessContributions.remaining(reloaded);
        assertNotNull(remaining);
        assertEquals(93, remaining.getDuration());
        assertEquals(93, MedicalBlindnessContributions.remaining(reloaded).getDuration());
    }

    private static CompoundTag ownedState() {
        var state = new CompoundTag();
        state.putBoolean("blindness", true);
        return state;
    }

    private static MobEffectInstance blindness(int duration, int amplifier) {
        return new MobEffectInstance(MobEffects.BLINDNESS, duration, amplifier, false, false);
    }
}
