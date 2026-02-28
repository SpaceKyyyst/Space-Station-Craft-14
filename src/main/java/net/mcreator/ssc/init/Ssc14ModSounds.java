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
}