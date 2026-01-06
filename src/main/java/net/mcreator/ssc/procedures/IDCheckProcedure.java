package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class IDCheckProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		boolean b1 = false;
		entity.getPersistentData().putBoolean("gun_room", false);
		entity.getPersistentData().putBoolean("HoS", false);
		entity.getPersistentData().putBoolean("Brig", false);
		entity.getPersistentData().putBoolean("Security", false);
		entity.getPersistentData().putBoolean("Detective", false);
		entity.getPersistentData().putBoolean("PNT", false);
		entity.getPersistentData().putBoolean("Crio", false);
		entity.getPersistentData().putBoolean("CE", false);
		entity.getPersistentData().putBoolean("Atmos", false);
		entity.getPersistentData().putBoolean("Ingeneer", false);
		entity.getPersistentData().putBoolean("Out", false);
		entity.getPersistentData().putBoolean("Qm", false);
		entity.getPersistentData().putBoolean("Utilizat", false);
		entity.getPersistentData().putBoolean("Supply_Deportament", false);
		entity.getPersistentData().putBoolean("CMO", false);
		entity.getPersistentData().putBoolean("Chemistry", false);
		entity.getPersistentData().putBoolean("Medical", false);
		entity.getPersistentData().putBoolean("RD", false);
		entity.getPersistentData().putBoolean("Scientist", false);
		entity.getPersistentData().putBoolean("Technical", false);
		entity.getPersistentData().putBoolean("Church", false);
		entity.getPersistentData().putBoolean("HoP", false);
		entity.getPersistentData().putBoolean("Service", false);
		entity.getPersistentData().putBoolean("Kitchen", false);
		entity.getPersistentData().putBoolean("Gidroponic", false);
		entity.getPersistentData().putBoolean("Bar", false);
		entity.getPersistentData().putBoolean("Teatre", false);
		entity.getPersistentData().putBoolean("Cleaner", false);
		entity.getPersistentData().putBoolean("Capitan", false);
		entity.getPersistentData().putBoolean("Command", false);
		entity.getPersistentData().putBoolean("Blue_Sh", false);
		entity.getPersistentData().putBoolean("Uridic", false);
	}
}