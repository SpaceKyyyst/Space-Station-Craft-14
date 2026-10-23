package net.ssc14.content.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;

public final class Ssc14SoundEvents {
    public static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(Registries.SOUND_EVENT, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> TITLE_ON = register("title_on");
    public static final DeferredHolder<SoundEvent, SoundEvent> TITLE_OFF = register("title_off");
    public static final DeferredHolder<SoundEvent, SoundEvent> WALL_DESTROY = register("wall_destroy");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEVICE_DESTROY = register("device_destroy");
    public static final DeferredHolder<SoundEvent, SoundEvent> WALLDEVICE_DESTROY = register("walldevice_destroy");
    public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_USE = register("welder_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_OFF = register("welder_off");
    public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_REFUELING = register("welder_refueling");
    public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_ON = register("welder_on");
    public static final DeferredHolder<SoundEvent, SoundEvent> NIPPERS_USE = register("nippers_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPANNER_USE = register("spanner_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCREWDRIVER = register("screwdriver");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_OPEN = register("airlock_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_CLOSE = register("airlock_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_NO_ACCESS = register("airlock_no_access");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONSOLE_USE_1 = register("console_use_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONSOLE_USE_2 = register("console_use_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONSOLE_USE_3 = register("console_use_3");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOCK_CLOSE = register("lock_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOCK_OPEN = register("lock_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOCKER_CLOSE = register("locker_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOCKER_OPEN = register("locker_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> EATING_SOUNDS = register("eating_sounds");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRINK_SOUNDS = register("drink_sounds");
    public static final DeferredHolder<SoundEvent, SoundEvent> CAN_OPEN_SOUND = register("can_open_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOTTLE_SOUND = register("bottle_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_BOLT_OFF = register("airlock_bolt_off");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_BOLT_ON = register("airlock_bolt_on");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_EMERGENCY_OFF = register("airlock_emergency_off");
    public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_EMERGENCY_ON = register("airlock_emergency_on");
    public static final DeferredHolder<SoundEvent, SoundEvent> BREAK_STONE = register("break_stone");
    public static final DeferredHolder<SoundEvent, SoundEvent> WELDING_WORK = register("welding_work");
    public static final DeferredHolder<SoundEvent, SoundEvent> C4_PIP = register("c4_pip");
    public static final DeferredHolder<SoundEvent, SoundEvent> EXTINGUISH_EFFECT = register("extinguish_effect");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_FLASK_CLOSE1 = register("items_flask_close1");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_PEN_CLICK = register("items_pen_click");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_FLASHLIGHT_PDA = register("items_flashlight_pda");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_RCD_DECONSTRUCT = register("items_rcd_deconstruct");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEDICAL_SCANNER_USE = register("medical_scanner_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANNOUNCE_SOUND = register("announce_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> ATTENTION = register("attention");
    public static final DeferredHolder<SoundEvent, SoundEvent> ION_STORM = register("ion_storm");
    public static final DeferredHolder<SoundEvent, SoundEvent> RED_CODE = register("red_code");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLUE_CODE = register("blue_code");
    public static final DeferredHolder<SoundEvent, SoundEvent> DELTA_CODE = register("delta_code");
    public static final DeferredHolder<SoundEvent, SoundEvent> EPSILON_CODE = register("epsilon_code");
    public static final DeferredHolder<SoundEvent, SoundEvent> GAMMA_CODE = register("gamma_code");
    public static final DeferredHolder<SoundEvent, SoundEvent> GREEN_CODE = register("green_code");
    public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_BASE_EQUIP = register("equip_base_equip");
    public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_HELMETS = register("equip_helmets");
    public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_GLOVES = register("equip_gloves");
    public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_BACKPACKS = register("equip_backpacks");
    public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_VENDING_EJECT = register("machines_vending_eject");
    public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_VENDING_JINGLE = register("machines_vending_jingle");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOUSE_SQUEAK = register("mouse_squeak");
    public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_SHUTTER = register("machines_shutter");
    public static final DeferredHolder<SoundEvent, SoundEvent> EFFECTS_GLASS_KNOCK = register("effects_glass_knock");
    public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_BUTTON = register("machines_button");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_BOTTLE_OPEN = register("items_bottle_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> MICROWAVE_START_BEEP = register("microwave_start_beep");
    public static final DeferredHolder<SoundEvent, SoundEvent> MICROWAVE_DONE_BEEP = register("microwave_done_beep");
    public static final DeferredHolder<SoundEvent, SoundEvent> MICROWAVE_LOOP_6 = register("microwave_loop_6");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACKAGE_OPENING = register("package_opening");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHEMISTRY_BUBBLES = register("chemistry_bubbles");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUNSHOTS_MK58 = register("gunshots_mk58");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUNS_BOLT_OPEN = register("guns_bolt_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUNS_BOLT_CLOSED = register("guns_bolt_closed");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_PISTOL_RELOADING = register("items_pistol_reloading");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_PISTOL_MAG_IN = register("items_pistol_mag_in");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_PISTOL_MAG_OUT = register("items_pistol_mag_out");
    public static final DeferredHolder<SoundEvent, SoundEvent> FOAM_RIFLE_CHARGING = register("foam_rifle_charging");
    public static final DeferredHolder<SoundEvent, SoundEvent> FOAM_RIFLE_SHOT = register("foam_rifle_shot");
    public static final DeferredHolder<SoundEvent, SoundEvent> FOAM_RIFLE_DISCHARGE = register("foam_rifle_discharge");
    public static final DeferredHolder<SoundEvent, SoundEvent> EFFECTS_REFILL = register("effects_refill");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOBS_BITE = register("mobs_bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOICE_TALK_SPEAK_1 = register("voice_talk_speak_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOICE_TALK_SPEAK_2 = register("voice_talk_speak_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOICE_TALK_SPEAK_3 = register("voice_talk_speak_3");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOICE_TALK_SPEAK_4 = register("voice_talk_speak_4");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARGO_BUY_SUCCESS = register("cargo_buy_success");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARGO_BUY_ERROR = register("cargo_buy_error");

    private Ssc14SoundEvents() {
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String path) {
        return REGISTRY.register(path,
                () -> SoundEvent.createVariableRangeEvent(
                        Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path)));
    }
}
