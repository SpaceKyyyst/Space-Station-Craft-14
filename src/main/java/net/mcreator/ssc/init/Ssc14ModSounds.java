/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.ssc.Ssc14Mod;

public class Ssc14ModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, Ssc14Mod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> TITLE_ON = REGISTRY.register("title_on", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "title_on")));
	public static final DeferredHolder<SoundEvent, SoundEvent> TITLE_OFF = REGISTRY.register("title_off", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "title_off")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WALL_DESTROY = REGISTRY.register("wall_destroy", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "wall_destroy")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DEVICE_DESTROY = REGISTRY.register("device_destroy", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "device_destroy")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WALLDEVICE_DESTROY = REGISTRY.register("walldevice_destroy", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "walldevice_destroy")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_USE = REGISTRY.register("welder_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "welder_use")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_OFF = REGISTRY.register("welder_off", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "welder_off")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_REFUELING = REGISTRY.register("welder_refueling", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "welder_refueling")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WELDER_ON = REGISTRY.register("welder_on", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "welder_on")));
	public static final DeferredHolder<SoundEvent, SoundEvent> NIPPERS_USE = REGISTRY.register("nippers_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "nippers_use")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SPANNER_USE = REGISTRY.register("spanner_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "spanner_use")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SCREWDRIVER = REGISTRY.register("screwdriver", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "screwdriver")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_OPEN = REGISTRY.register("airlock_open", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_open")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_CLOSE = REGISTRY.register("airlock_close", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_close")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_NO_ACCESS = REGISTRY.register("airlock_no_access", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_no_access")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CONSOLE_USE_1 = REGISTRY.register("console_use_1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "console_use_1")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CONSOLE_USE_2 = REGISTRY.register("console_use_2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "console_use_2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CONSOLE_USE_3 = REGISTRY.register("console_use_3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "console_use_3")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LOCK_CLOSE = REGISTRY.register("lock_close", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "lock_close")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LOCK_OPEN = REGISTRY.register("lock_open", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "lock_open")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LOCKER_CLOSE = REGISTRY.register("locker_close", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "locker_close")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LOCKER_OPEN = REGISTRY.register("locker_open", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "locker_open")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EATING_SOUNDS = REGISTRY.register("eating_sounds", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "eating_sounds")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DRINK_SOUNDS = REGISTRY.register("drink_sounds", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "drink_sounds")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CAN_OPEN_SOUND = REGISTRY.register("can_open_sound", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "can_open_sound")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BOTTLE_SOUND = REGISTRY.register("bottle_sound", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "bottle_sound")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_BOLT_OFF = REGISTRY.register("airlock_bolt_off", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_bolt_off")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_BOLT_ON = REGISTRY.register("airlock_bolt_on", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_bolt_on")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_EMERGENCY_OFF = REGISTRY.register("airlock_emergency_off", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_emergency_off")));
	public static final DeferredHolder<SoundEvent, SoundEvent> AIRLOCK_EMERGENCY_ON = REGISTRY.register("airlock_emergency_on", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "airlock_emergency_on")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BREAK_STONE = REGISTRY.register("break_stone", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "break_stone")));
	public static final DeferredHolder<SoundEvent, SoundEvent> WELDING_WORK = REGISTRY.register("welding_work", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "welding_work")));
	public static final DeferredHolder<SoundEvent, SoundEvent> C4_PIP = REGISTRY.register("c4_pip", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "c4_pip")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EXTINGUISH_EFFECT = REGISTRY.register("extinguish_effect", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "extinguish_effect")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_FLASK_CLOSE1 = REGISTRY.register("items_flask_close1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "items_flask_close1")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_PEN_CLICK = REGISTRY.register("items_pen_click", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "items_pen_click")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_FLASHLIGHT_PDA = REGISTRY.register("items_flashlight_pda", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "items_flashlight_pda")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ITEMS_RCD_DECONSTRUCT = REGISTRY.register("items_rcd_deconstruct", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "items_rcd_deconstruct")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MEDICAL_SCANNER_USE = REGISTRY.register("medical_scanner_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "medical_scanner_use")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ANNOUNCE_SOUND = REGISTRY.register("announce_sound", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "announce_sound")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ATTENTION = REGISTRY.register("attention", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "attention")));
	public static final DeferredHolder<SoundEvent, SoundEvent> ION_STORM = REGISTRY.register("ion_storm", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "ion_storm")));
	public static final DeferredHolder<SoundEvent, SoundEvent> RED_CODE = REGISTRY.register("red_code", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "red_code")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BLUE_CODE = REGISTRY.register("blue_code", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "blue_code")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DELTA_CODE = REGISTRY.register("delta_code", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "delta_code")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EPSILON_CODE = REGISTRY.register("epsilon_code", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "epsilon_code")));
	public static final DeferredHolder<SoundEvent, SoundEvent> GAMMA_CODE = REGISTRY.register("gamma_code", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "gamma_code")));
	public static final DeferredHolder<SoundEvent, SoundEvent> GREEN_CODE = REGISTRY.register("green_code", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "green_code")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_BASE_EQUIP = REGISTRY.register("equip_base_equip", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "equip_base_equip")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_HELMETS = REGISTRY.register("equip_helmets", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "equip_helmets")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_GLOVES = REGISTRY.register("equip_gloves", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "equip_gloves")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_BACKPACKS = REGISTRY.register("equip_backpacks", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "equip_backpacks")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_VENDING_EJECT = REGISTRY.register("machines_vending_eject", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "machines_vending_eject")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_VENDING_JINGLE = REGISTRY.register("machines_vending_jingle",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "machines_vending_jingle")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MOUSE_SQUEAK = REGISTRY.register("mouse_squeak", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "mouse_squeak")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_SHUTTER = REGISTRY.register("machines_shutter", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "machines_shutter")));
	public static final DeferredHolder<SoundEvent, SoundEvent> EFFECTS_GLASS_KNOCK = REGISTRY.register("effects_glass_knock", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "effects_glass_knock")));
	public static final DeferredHolder<SoundEvent, SoundEvent> MACHINES_BUTTON = REGISTRY.register("machines_button", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("ssc_14", "machines_button")));
}