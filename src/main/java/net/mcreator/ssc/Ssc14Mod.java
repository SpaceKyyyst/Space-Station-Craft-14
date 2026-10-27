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
import net.ssc14.integration.neoforge.player.Ssc14PlayerAttachments;
import net.mcreator.ssc.init.*;
import net.mcreator.ssc.scheduling.ServerTaskScheduler;
import net.mcreator.ssc.scheduling.SubmissionResult;
import net.mcreator.ssc.world.inventory.MacFrameGUIMenu;
import net.mcreator.ssc.world.inventory.GravGenGUIMenu;
import net.mcreator.ssc.world.inventory.MicrowaweGUIMenu;
import net.mcreator.ssc.world.inventory.CommunConsoleGUIMenu;
import net.mcreator.ssc.integration.LegacyCommerceConsoleIntegrationAdapter;
import net.mcreator.ssc.integration.LegacyIdConsoleIntegrationAdapter;
import net.mcreator.ssc.integration.LegacyNetworkButtonIntegrationAdapter;
import net.mcreator.ssc.integration.LegacyNetworkConfiguratorMenuAdapter;
import net.mcreator.ssc.integration.LegacyRsuInteractionAdapter;
import net.mcreator.ssc.integration.LegacyShuttersIntegrationAdapter;
import net.mcreator.ssc.integration.LegacyAirlockUpperPlugIntegrationAdapter;
import net.mcreator.ssc.integration.LegacyAirlockDoorIntegrationAdapter;
import net.mcreator.ssc.integration.LegacyAirlockControllerInteractionAdapter;
import net.mcreator.ssc.integration.LegacyVendingMachineIntegrationAdapter;
import net.mcreator.ssc.gravity.ui.GravityGeneratorWindow;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.entity.Ssc14EntityBehavior;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.combat.GlassShardInteractionService;
import net.ssc14.content.item.construction.ConstructionItemInteractionService;
import net.ssc14.content.item.currency.CurrencyInteractionService;
import net.ssc14.content.item.decal.DecalToolInteractionService;
import net.ssc14.content.item.container.PortableMenuInteractionService;
import net.ssc14.content.item.weapon.FirearmInteractionService;
import net.ssc14.content.item.tool.AirlockControllerService;
import net.ssc14.content.item.tool.NetworkConfiguratorService;
import net.ssc14.content.item.tool.RsuInteractionService;
import net.ssc14.content.block.access.IdConsoleIntegrationService;
import net.ssc14.content.block.access.Ssc14AccessBlockEntities;
import net.ssc14.content.block.airlock.ShuttersIntegrationService;
import net.ssc14.content.block.airlock.AirlockUpperPlugIntegrationService;
import net.ssc14.content.block.airlock.AirlockDoorIntegrationService;
import net.ssc14.content.block.airlock.Ssc14AirlockBlockEntities;
import net.ssc14.content.block.commerce.Ssc14CommerceBlockEntities;
import net.ssc14.content.block.commerce.OrderPalletRegistryService;
import net.ssc14.content.block.commerce.CommerceConsoleIntegrationService;
import net.ssc14.content.block.commerce.VendingMachineIntegrationService;
import net.ssc14.content.block.control.NetworkButtonInteractionService;
import net.ssc14.content.block.control.Ssc14ControlBlockEntities;
import net.ssc14.content.block.communication.CommunicationServerLifecycleService;
import net.ssc14.content.block.communication.CommunicationConsoleIntegrationService;
import net.ssc14.content.block.communication.Ssc14CommunicationBlockEntities;
import net.ssc14.content.block.fuel.FuelTankInteractionService;
import net.ssc14.content.block.fuel.Ssc14FuelBlockEntities;
import net.ssc14.content.block.gravity.GravityGeneratorPlugLifecycleService;
import net.ssc14.content.block.gravity.GravityGeneratorLifecycleService;
import net.ssc14.content.block.gravity.GravityGeneratorUiService;
import net.ssc14.content.block.gravity.Ssc14GravityBlockEntities;
import net.ssc14.content.block.atmosphere.GasProducerO2TickService;
import net.ssc14.content.block.debug.StationCodeDebugInteractionService;
import net.ssc14.content.block.debug.Ssc14DebugBlockEntities;
import net.ssc14.content.block.debug.DebugGravityStateService;
import net.ssc14.content.block.explosives.C4ChargeInteractionService;
import net.ssc14.content.block.explosives.Ssc14ExplosiveBlockEntities;
import net.ssc14.content.block.explosives.NuclearWarheadInteractionService;
import net.ssc14.content.block.storage.Ssc14ClosetBlockEntities;
import net.ssc14.content.block.storage.SecureClosetInteractionService;
import net.ssc14.content.block.storage.ExtinguisherCabinetInteractionService;
import net.ssc14.content.block.storage.UpperClosetPlugInteractionService;
import net.ssc14.content.block.lighting.EmergencyLightingService;
import net.ssc14.content.block.lighting.LightingEnergyService;
import net.ssc14.content.block.lighting.Ssc14LightingBlockEntities;
import net.ssc14.content.block.machinery.MachineFrameAssemblyService;
import net.ssc14.content.block.machinery.MachineFrameMenuService;
import net.ssc14.content.block.machinery.MicrowaveMenuService;
import net.ssc14.content.block.machinery.Ssc14MachineBlockEntities;
import net.ssc14.content.block.energy.CableTopologyService;
import net.ssc14.content.block.energy.Ssc14CableBlockEntities;
import net.ssc14.content.block.energy.Ssc14EnergyMachineBlockEntities;
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
		Ssc14AccessBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14AccessBlockEntities::registerCapabilities);
		Ssc14AirlockBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14AirlockBlockEntities::registerCapabilities);
		Ssc14LightingBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14LightingBlockEntities::registerCapabilities);
		Ssc14ClosetBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14ClosetBlockEntities::registerCapabilities);
		Ssc14MachineBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14MachineBlockEntities::registerCapabilities);
		Ssc14DebugBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14DebugBlockEntities::registerCapabilities);
		DebugGravityStateService.install(enabled -> Ssc14ModVariables.station_gravity = enabled);
		Ssc14GravityBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14GravityBlockEntities::registerCapabilities);
		Ssc14ExplosiveBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14ExplosiveBlockEntities::registerCapabilities);
		Ssc14CableBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14CableBlockEntities::registerCapabilities);
		Ssc14EnergyMachineBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14EnergyMachineBlockEntities::registerCapabilities);
		Ssc14CommerceBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14CommerceBlockEntities::registerCapabilities);
		Ssc14ControlBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14ControlBlockEntities::registerCapabilities);
		Ssc14FuelBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14FuelBlockEntities::registerCapabilities);
		Ssc14CommunicationBlockEntities.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14CommunicationBlockEntities::registerCapabilities);
		Ssc14ModBlockEntities.REGISTRY.register(modEventBus);
		Ssc14Items.REGISTRY.register(modEventBus);
		Ssc14ModItems.REGISTRY.register(modEventBus);
		LightingEnergyService.install(EnergyNetworkManager::queueUpdate);
		CableTopologyService.install(new CableTopologyService.Adapter() {
			@Override
			public void queueUpdate(LevelAccessor level, net.minecraft.core.BlockPos pos) {
				EnergyNetworkManager.queueUpdate(level, pos);
			}

			@Override
			public void invalidate(LevelAccessor level, net.minecraft.core.BlockPos pos) {
				EnergyNetworkManager.invalidate(level, pos);
			}
		});
		OrderPalletRegistryService.install((level, pos) -> {
			var variables = Ssc14ModVariables.WorldVariables.get(level);
			String key = pos.getX() + "," + pos.getY() + "," + pos.getZ() + ";";
			if (!variables.PalletRegistry.contains(key)) {
				variables.PalletRegistry += key;
				variables.setDirty();
			}
		});
		FuelTankInteractionService.install(new FuelTankInteractionService.Adapter() {
			@Override
			public void initialize(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos) {
				net.mcreator.ssc.procedures.FuelTank_GENERATE_Procedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ());
			}

			@Override
			public void interact(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos, Player player) {
				net.mcreator.ssc.procedures.FuelTank_USE_Procedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), player);
			}
		});
		C4ChargeInteractionService.install(new C4ChargeInteractionService.Adapter() {
			@Override
			public void plant(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos) {
				net.mcreator.ssc.procedures.C4_PlantProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ());
			}

			@Override
			public void activate(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos, Player player) {
				net.mcreator.ssc.procedures.C4_activate_PRProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), player);
			}

			@Override
			public void detonate(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos) {
				net.mcreator.ssc.procedures.C4_detonateProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ());
			}
		});
		NuclearWarheadInteractionService.install((level, pos) ->
				net.mcreator.ssc.procedures.NuclearWarhead_ExpProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ()));
		GravityGeneratorPlugLifecycleService.install(
				new GravityGeneratorPlugLifecycleService.Adapter() {
					@Override
					public void neighborChanged(net.minecraft.world.level.Level level,
							net.minecraft.core.BlockPos pos) {
						net.mcreator.ssc.procedures.GravGenPlugBlock_Trigger_Procedure.execute(
								level, pos.getX(), pos.getY(), pos.getZ());
					}

					@Override
					public void destroyed(net.minecraft.world.level.Level level,
							net.minecraft.core.BlockPos pos) {
						net.mcreator.ssc.procedures.GravGenPlugBlock_Destroy_Procedure.execute(
								level, pos.getX(), pos.getY(), pos.getZ());
					}
				});
		GravityGeneratorLifecycleService.install(new GravityGeneratorLifecycleService.Adapter() {
			@Override
			public boolean canSurvive(LevelAccessor level, net.minecraft.core.BlockPos pos) {
				return net.mcreator.ssc.procedures.GravityGenerator_Placement_TermsProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ());
			}

			@Override
			public void placed(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos,
					net.minecraft.world.level.block.state.BlockState state) {
				net.mcreator.ssc.procedures.GravityGenerator_PlugBlock_Generate_Procedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), state);
			}

			@Override
			public void tick(ServerLevel level, net.minecraft.core.BlockPos pos,
					net.minecraft.world.level.block.state.BlockState state) {
				net.mcreator.ssc.procedures.GravGenTICprocedureProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), state);
			}

			@Override
			public void destroyed() {
				net.mcreator.ssc.procedures.GravityGenerator_DestroyProcedure.execute();
			}
		});
		GravityGeneratorUiService.install(new GravityGeneratorUiService.Adapter() {
			@Override
			public boolean canAccess(
					net.ssc14.content.block.gravity.GravityGeneratorBlockEntity generator,
					Player player, net.minecraft.core.GlobalPos source) {
				return GravityGeneratorWindow.canAccess(generator, player, source);
			}

			@Override
			public com.lowdragmc.lowdraglib2.gui.ui.ModularUI create(
					net.ssc14.content.block.gravity.GravityGeneratorBlockEntity generator,
					net.minecraft.core.GlobalPos source, Player player) {
				return GravityGeneratorWindow.create(generator, source, player);
			}

			@Override
			public com.ssc14.handy.window.WindowProperties windowProperties() {
				return new com.ssc14.handy.window.WindowProperties()
						.width(GravityGeneratorWindow.WIDTH).height(GravityGeneratorWindow.HEIGHT)
						.minWidth(GravityGeneratorWindow.WIDTH)
						.minHeight(GravityGeneratorWindow.HEIGHT);
			}

			@Override
			public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
					net.ssc14.content.block.gravity.GravityGeneratorBlockEntity generator,
					int id, net.minecraft.world.entity.player.Inventory inventory) {
				return new GravGenGUIMenu(id, inventory,
						new FriendlyByteBuf(Unpooled.buffer())
								.writeBlockPos(generator.getBlockPos()));
			}
		});
		GasProducerO2TickService.install((level, pos) ->
				net.mcreator.ssc.procedures.GasProducerO2_tick_PR_Procedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ()));
		CommunicationServerLifecycleService.install(
				new CommunicationServerLifecycleService.Adapter() {
					@Override
					public void tick(ServerLevel level, net.minecraft.core.BlockPos pos,
							net.minecraft.world.level.block.state.BlockState state) {
						net.mcreator.ssc.procedures.CommunicationServers_TIC_PR_Procedure.execute(
								level, pos.getX(), pos.getY(), pos.getZ(), state);
					}

					@Override
					public void use(net.minecraft.world.level.Level level,
							net.minecraft.core.BlockPos pos,
							net.minecraft.world.level.block.state.BlockState state,
							Player player) {
						net.mcreator.ssc.procedures.CommunicationServers_Click_USEProcedure.execute(
								level, pos.getX(), pos.getY(), pos.getZ(), state, player);
					}
				});
		CommunicationConsoleIntegrationService.install(
				new CommunicationConsoleIntegrationService.Adapter() {
					@Override
					public void use(net.minecraft.world.level.Level level,
							net.minecraft.core.BlockPos pos, Player player) {
						if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
							serverPlayer.openMenu(new net.minecraft.world.MenuProvider() {
								@Override
								public net.minecraft.network.chat.Component getDisplayName() {
									return net.minecraft.network.chat.Component.literal("Communication console");
								}

								@Override
								public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
										int id, net.minecraft.world.entity.player.Inventory inventory,
										Player menuPlayer) {
									return new CommunConsoleGUIMenu(id, inventory,
											new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
								}
							}, pos);
						}
						net.mcreator.ssc.procedures.ConsolesOpenMenuSoundProcedure.execute(
								level, pos.getX(), pos.getY(), pos.getZ());
					}

					@Override
					public net.minecraft.world.inventory.AbstractContainerMenu createMenu(
							int id, net.minecraft.world.entity.player.Inventory inventory,
							net.minecraft.core.BlockPos pos) {
						return new CommunConsoleGUIMenu(id, inventory,
								new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
					}
				});
		StationCodeDebugInteractionService.install((level, pos, player) ->
				net.mcreator.ssc.procedures.DEBUG13PriShchielchkiePKMPoBlokuProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), player));
		UpperClosetPlugInteractionService.install((level, pos, player) ->
				net.mcreator.ssc.procedures.UpClosetPLUG_clickProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), player));
		SecureClosetInteractionService.install((level, pos, state, player) ->
				net.mcreator.ssc.procedures.Closet_OpenClose_PrProcedure.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), state, player));
		ExtinguisherCabinetInteractionService.install(
				Ssc14Items.EXTINGUISHER, Ssc14Items.EXTINGUISHER_OPEN);
		EmergencyLightingService.install(level ->
				(int) Ssc14ModVariables.MapVariables.get(level).station_code);
		MachineFrameAssemblyService.install(Ssc14ModItems.LOW_VOLTAGE_CABLE,
				Ssc14Blocks.MACHINE_FRAME_2);
		MachineFrameMenuService.install((id, inventory, pos) -> new MacFrameGUIMenu(
				id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos)));
		MicrowaveMenuService.install((id, inventory, pos) -> new MicrowaweGUIMenu(
				id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos)));
		CommerceConsoleIntegrationService.install(
				new LegacyCommerceConsoleIntegrationAdapter(
						net.mcreator.ssc.procedures.ConsolesOpenMenuSoundProcedure::execute));
		IdConsoleIntegrationService.install(new LegacyIdConsoleIntegrationAdapter());
		VendingMachineIntegrationService.install(
				new LegacyVendingMachineIntegrationAdapter());
		NetworkButtonInteractionService.install(
				new LegacyNetworkButtonIntegrationAdapter());
		NetworkConfiguratorService.install(new LegacyNetworkConfiguratorMenuAdapter());
		RsuInteractionService.install(new LegacyRsuInteractionAdapter());
		ShuttersIntegrationService.install(new LegacyShuttersIntegrationAdapter());
		AirlockUpperPlugIntegrationService.install(
				new LegacyAirlockUpperPlugIntegrationAdapter());
		AirlockDoorIntegrationService.install(new LegacyAirlockDoorIntegrationAdapter());
		AirlockControllerService.install(new LegacyAirlockControllerInteractionAdapter());
		ConstructionItemInteractionService.install(
				LegacyConstructionItemInteractionAdapter.INSTANCE);
		DecalToolInteractionService.install(LegacyDecalToolInteractionAdapter.INSTANCE);
		CurrencyInteractionService.install(LegacyCurrencyInteractionAdapter.INSTANCE);
		GlassShardInteractionService.install(LegacyGlassShardInteractionAdapter.INSTANCE);
		PortableMenuInteractionService.install(LegacyPortableMenuInteractionAdapter.INSTANCE);
		FirearmInteractionService.install(LegacyFirearmInteractionAdapter.INSTANCE);
		Ssc14EntityBehavior.install(Ssc14LegacyEntityBehavior.INSTANCE);
		Ssc14EntityTypes.REGISTRY.register(modEventBus);
		Ssc14ModTabs.REGISTRY.register(modEventBus);
		Ssc14PlayerAttachments.register(modEventBus);
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
