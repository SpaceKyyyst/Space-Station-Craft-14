package net.mcreator.ssc;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.ssc.world.features.StructureFeature;
import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.*;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.HashMap;

import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;


@Mod("ssc_14")
public class Ssc14Mod {
	public static final Logger LOGGER = LogManager.getLogger(Ssc14Mod.class);
	public static final String MODID = "ssc_14";

	public Ssc14Mod(IEventBus modEventBus) {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		NeoForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::registerNetworking);
		if (ModList.get().isLoaded("curios")) {
			modEventBus.addListener(Ssc14ModCuriosCompat::registerCapabilities);
			modEventBus.addListener(Ssc14ModCuriosRenderers::registerRenderers);
		}
		Ssc14ModSounds.REGISTRY.register(modEventBus);
		Ssc14ModBlocks.REGISTRY.register(modEventBus);
		Ssc14ModBlockEntities.REGISTRY.register(modEventBus);
		Ssc14ModItems.REGISTRY.register(modEventBus);
		Ssc14ModEntities.REGISTRY.register(modEventBus);
		Ssc14ModTabs.REGISTRY.register(modEventBus);
		Ssc14ModVariables.ATTACHMENT_TYPES.register(modEventBus);
		StructureFeature.REGISTRY.register(modEventBus);
		Ssc14ModMenus.REGISTRY.register(modEventBus);
		Ssc14ModParticleTypes.REGISTRY.register(modEventBus);
		Ssc14ModAttributes.REGISTRY.register(modEventBus);
		// Start of user code block mod init
		ModReagents.register(modEventBus);
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
	}

	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler(), ((NetworkMessage) networkMessage).handler()));
		networkingRegistered = true;
	}

    public static void queueServerWork(int delay, Runnable action) {
        var server = net.neoforged.neoforge.server.ServerLifecycleHooks.getCurrentServer();
        if (server != null && server.isSameThread()) {
            if (!net.mcreator.ssc.scheduling.ServerTaskScheduler.submit(server, delay, action)) {
                throw new java.util.concurrent.RejectedExecutionException("Server task queue is unavailable or full");
            }
        }
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        net.mcreator.ssc.scheduling.ServerTaskScheduler.tick(event.getServer());
    }

	private static Object minecraft;
	private static MethodHandle playerHandle;

	@Nullable
	public static Player clientPlayer() {
		if (FMLEnvironment.getDist().isClient()) {
			try {
				if (minecraft == null || playerHandle == null) {
					Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
					minecraft = MethodHandles.publicLookup().findStatic(minecraftClass, "getInstance", MethodType.methodType(minecraftClass)).invoke();
					playerHandle = MethodHandles.publicLookup().findGetter(minecraftClass, "player", Class.forName("net.minecraft.client.player.LocalPlayer"));
				}
				return (Player) playerHandle.invoke(minecraft);
			} catch (Throwable e) {
				LOGGER.error("Failed to get client player", e);
				return null;
			}
		} else {
			return null;
		}
	}

	public static class CuriosApiHelper {
		private static final EntityCapability<ResourceHandler, Void> CURIOS_INVENTORY = EntityCapability.createVoid(Identifier.fromNamespaceAndPath("curios", "item_handler"), ResourceHandler.class);

		public static ResourceHandler<ItemResource> getCuriosInventory(Player player) {
			if (ModList.get().isLoaded("curios")) {
				return player.getCapability(CURIOS_INVENTORY);
			}
			return null;
		}

		public static boolean isCurioItem(ItemStack itemstack) {
			return BuiltInRegistries.ITEM.getTags().filter(named -> named.key().location().getNamespace().equals("curios")).anyMatch(named -> itemstack.is(named.key()));
		}
	}
}
