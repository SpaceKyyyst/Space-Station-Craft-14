package net.ssc14.content.sound;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import net.mcreator.ssc.init.Ssc14ModSounds;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Ssc14SoundEventsCompatibilityTest {
    private static final Set<String> EXPECTED_PATHS = Set.of(
            "title_on",
            "title_off",
            "wall_destroy",
            "device_destroy",
            "walldevice_destroy",
            "welder_use",
            "welder_off",
            "welder_refueling",
            "welder_on",
            "nippers_use",
            "spanner_use",
            "screwdriver",
            "airlock_open",
            "airlock_close",
            "airlock_no_access",
            "console_use_1",
            "console_use_2",
            "console_use_3",
            "lock_close",
            "lock_open",
            "locker_close",
            "locker_open",
            "eating_sounds",
            "drink_sounds",
            "can_open_sound",
            "bottle_sound",
            "airlock_bolt_off",
            "airlock_bolt_on",
            "airlock_emergency_off",
            "airlock_emergency_on",
            "break_stone",
            "welding_work",
            "c4_pip",
            "extinguish_effect",
            "items_flask_close1",
            "items_pen_click",
            "items_flashlight_pda",
            "items_rcd_deconstruct",
            "medical_scanner_use",
            "announce_sound",
            "attention",
            "ion_storm",
            "red_code",
            "blue_code",
            "delta_code",
            "epsilon_code",
            "gamma_code",
            "green_code",
            "equip_base_equip",
            "equip_helmets",
            "equip_gloves",
            "equip_backpacks",
            "machines_vending_eject",
            "machines_vending_jingle",
            "mouse_squeak",
            "machines_shutter",
            "effects_glass_knock",
            "machines_button",
            "items_bottle_open",
            "microwave_start_beep",
            "microwave_done_beep",
            "microwave_loop_6",
            "package_opening",
            "chemistry_bubbles",
            "gunshots_mk58",
            "guns_bolt_open",
            "guns_bolt_closed",
            "items_pistol_reloading",
            "items_pistol_mag_in",
            "items_pistol_mag_out",
            "foam_rifle_charging",
            "foam_rifle_shot",
            "foam_rifle_discharge",
            "effects_refill",
            "mobs_bite",
            "voice_talk_speak_1",
            "voice_talk_speak_2",
            "voice_talk_speak_3",
            "voice_talk_speak_4",
            "cargo_buy_success",
            "cargo_buy_error");

    @Test
    void legacyRegistryExposesExactSoundFieldsAndIds() throws IllegalAccessException {
        Set<Field> holders = publicHolders(Ssc14ModSounds.class);

        assertEquals(EXPECTED_PATHS.stream()
                .map(path -> path.toUpperCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet()), holderNames(holders));
        assertEquals(EXPECTED_PATHS, holderPaths(holders));
    }

    @Test
    void legacyRegistryKeepsItsPublicNoArgumentConstructor() throws ReflectiveOperationException {
        Ssc14ModSounds.class.getConstructor();
    }

    private static Set<Field> publicHolders(Class<?> owner) {
        return Arrays.stream(owner.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> DeferredHolder.class.isAssignableFrom(field.getType()))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> holderNames(Set<Field> holders) {
        return holders.stream().map(Field::getName).collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> holderPaths(Set<Field> holders) throws IllegalAccessException {
        return holders.stream().map(field -> {
            try {
                return ((DeferredHolder<?, ?>) field.get(null)).getId().getPath();
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        }).collect(Collectors.toUnmodifiableSet());
    }
}
