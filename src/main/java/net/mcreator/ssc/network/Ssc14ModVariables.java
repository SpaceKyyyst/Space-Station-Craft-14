package net.mcreator.ssc.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public class Ssc14ModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Ssc14Mod.MODID);
	public static boolean station_gravity = false;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
	}
}