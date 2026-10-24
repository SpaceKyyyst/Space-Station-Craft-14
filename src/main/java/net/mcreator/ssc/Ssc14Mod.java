package net.mcreator.ssc;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
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
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.ssc.world.features.StructureFeature;
import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.*;
import net.mcreator.ssc.scheduling.ServerTaskScheduler;
import net.mcreator.ssc.scheduling.SubmissionResult;
import net.mcreator.ssc.world.inventory.MacFrameGUIMenu;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.entity.Ssc14EntityBehavior;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.block.storage.Ssc14ClosetBlockEntities;
import net.ssc14.content.block.lighting.EmergencyLightingService;
import net.ssc14.content.block.lighting.LightingEnergyService;
import net.ssc14.content.block.lighting.Ssc14LightingBlockEntities;
import net.ssc14.content.block.machinery.MachineFrameAssemblyService;
import net.ssc14.content.block.machinery.MachineFrameMenuService;
import net.ssc14.content.block.machinery.Ssc14MachineBlockEntities;
import net.ssc14.content.particle.Ssc14ParticleTypes;
import net.ssc14.content.sound.Ssc14SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.HashMap;

import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;

import io.netty.buffer.Unpooled;


@Mod("ssc_14")
public class Ssc14Mod {
	public static final Logger LOGGER = LogManager.getLogger(Ssc14Mod.class);
	public static final String MODID = Ssc14Identity.MOD_ID;
	private static final String NETWORK_VERSION = MODID;

	public Ssc14Mod(IEventBus modEventBus) {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		NeoForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::registerNetworking);
		if (ModList.get().isLoaded("curios")) {
			modEventBus.addListener(Ssc14ModCuriosCompat::registerCapabilities);
			modEventBus.addListener(Ssc14ModCuriosRenderers::registerRenderers);
		}
		Ssc14SoundEvents.REGISTRY.register(modEventBus);
		Ssc14ModBlocks.initializeLegacyEntries();
		Ssc14Blocks.REGISTRY.register(modEventBus);
		Ssc14LightingBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14LightingBlockEntities::registerCapabilities);
		Ssc14ClosetBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14ClosetBlockEntities::registerCapabilities);
		Ssc14MachineBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14MachineBlockEntities::registerCapabilities);
		Ssc14ModBlockEntities.REGISTRY.register(modEventBus);
		Ssc14ModItems.REGISTRY.register(modEventBus);
		LightingEnergyService.install(EnergyNetworkManager::queueUpdate);
		EmergencyLightingService.install(level ->
				(int) Ssc14ModVariables.MapVariables.get(level).station_code);
		MachineFrameAssemblyService.install(Ssc14ModItems.LOW_VOLTAGE_CABLE,
				Ssc14Blocks.MACHINE_FRAME_2);
		MachineFrameMenuService.install((id, inventory, pos) -> new MacFrameGUIMenu(
				id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos)));
		Ssc14EntityBehavior.install(Ssc14LegacyEntityBehavior.INSTANCE);
		Ssc14EntityTypes.REGISTRY.register(modEventBus);
		Ssc14ModTabs.REGISTRY.register(modEventBus);
		Ssc14ModVariables.ATTACHMENT_TYPES.register(modEventBus);
		StructureFeature.REGISTRY.register(modEventBus);
		Ssc14ModMenus.REGISTRY.register(modEventBus);
		Ssc14ParticleTypes.REGISTRY.register(modEventBus);
		Ssc14Attributes.REGISTRY.register(modEventBus);
		// Start of user code block mod init
		ModReagents.register(modEventBus);
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private enum PayloadDirection {
		TO_SERVER, TO_CLIENT, BIDIRECTIONAL
	}

	private record NetworkMessage<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> id,
			StreamCodec<? super RegistryFriendlyByteBuf, T> reader, IPayloadHandler<T> handler, PayloadDirection direction) {
		void register(CompatibilityPayloadRegistrar registrar) {
			switch (direction) {
				case TO_SERVER -> registrar.playToServer(id, reader, handler);
				case TO_CLIENT -> registrar.playToClient(id, reader, handler);
				case BIDIRECTIONAL -> registrar.playBidirectional(id, reader, handler, handler);
			}
		}
	}

	public static <T extends CustomPacketPayload> void addServerboundNetworkMessage(CustomPacketPayload.Type<T> id,
			StreamCodec<? super RegistryFriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		addNetworkMessage(id, reader, handler, PayloadDirection.TO_SERVER);
	}

	public static <T extends CustomPacketPayload> void addClientboundNetworkMessage(CustomPacketPayload.Type<T> id,
			StreamCodec<? super RegistryFriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		addNetworkMessage(id, reader, handler, PayloadDirection.TO_CLIENT);
	}

	public static <T extends CustomPacketPayload> void addBidirectionalNetworkMessage(CustomPacketPayload.Type<T> id,
			StreamCodec<? super RegistryFriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		addNetworkMessage(id, reader, handler, PayloadDirection.BIDIRECTIONAL);
	}

	private static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id,
			StreamCodec<? super RegistryFriendlyByteBuf, T> reader, IPayloadHandler<T> handler, PayloadDirection direction) {
		synchronized (MESSAGES) {
			if (networkingRegistered)
				throw new IllegalStateException("Cannot register new network messages after networking has been registered");
			if (MESSAGES.putIfAbsent(id, new NetworkMessage<>(id, reader, handler, direction)) != null)
				throw new IllegalStateException("Duplicate network message: " + id);
		}
	}

	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final CompatibilityPayloadRegistrar registrar = new CompatibilityPayloadRegistrar(
				event.registrar(NETWORK_VERSION), NETWORK_VERSION);
		synchronized (MESSAGES) {
			MESSAGES.values().forEach(message -> message.register(registrar));
			networkingRegistered = true;
		}
	}

    /** Worker-safe admission; only the passed server's tick thread executes the action. */
    public static SubmissionResult queueServerWork(MinecraftServer owner, int delay, Runnable action) {
        return ServerTaskScheduler.submit(owner, delay, action);
    }

    public static SubmissionResult queueServerWork(ServerLevel owner, int delay, Runnable action) {
        ServerTaskScheduler.validateSubmission(delay, action);
        if (owner == null) return SubmissionResult.INVALID_OWNER;
        return queueServerWork(owner.getServer(), delay, action);
    }

    /** Explicit side rejection for integrations that only have a LevelAccessor. */
    public static SubmissionResult queueServerWork(LevelAccessor owner, int delay, Runnable action) {
        ServerTaskScheduler.validateSubmission(delay, action);
        if (owner == null) return SubmissionResult.INVALID_OWNER;
        if (owner.isClientSide()) return SubmissionResult.CLIENT_SIDE;
        if (owner instanceof ServerLevel level) return queueServerWork(level, delay, action);
        return SubmissionResult.INVALID_OWNER;
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
