package net.ssc14.integration;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;

import net.ssc14.integration.neoforge.worldgen.Ssc14StructureFeature;
import net.ssc14.integration.neoforge.state.Ssc14WorldVariables;
import net.ssc14.common.station.StationCodeService;
import net.ssc14.common.station.StationNameService;
import net.ssc14.common.station.StationBudgetService;
import net.ssc14.content.item.reagent.ReagentService;
import net.ssc14.content.item.Ssc14ItemCapabilities;
import net.ssc14.integration.neoforge.player.Ssc14PlayerAttachments;
import net.ssc14.client.Ssc14CuriosRenderers;
import net.ssc14.content.entity.Ssc14EntityBehaviorHooks;
import net.ssc14.content.equipment.Ssc14CuriosCompatibility;
import net.ssc14.integration.neoforge.server.ServerTaskSubmission;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;
import net.ssc14.content.menu.machinery.MacFrameGUIMenu;
import net.ssc14.content.menu.Ssc14MenuTypes;
import net.ssc14.content.tab.Ssc14CreativeTabs;
import net.ssc14.content.menu.station.GravGenGUIMenu;
import net.ssc14.content.menu.food.MicrowaweGUIMenu;
import net.ssc14.content.block.commerce.LegacyCommerceConsoleIntegrationAdapter;
import net.ssc14.content.block.access.LegacyIdConsoleIntegrationAdapter;
import net.ssc14.content.item.tool.LegacyNetworkConfiguratorMenuAdapter;
import net.ssc14.content.item.weapon.LegacyFirearmInteractionAdapter;
import net.ssc14.content.item.construction.LegacyConstructionItemInteractionAdapter;
import net.ssc14.content.block.airlock.LegacyShuttersIntegrationAdapter;
import net.ssc14.content.block.airlock.LegacyAirlockDoorIntegrationAdapter;
import net.ssc14.content.gravity.ui.GravityGeneratorWindow;
import net.ssc14.content.item.decal.LegacyDecalToolInteractionAdapter;
import net.ssc14.content.item.container.LegacyPortableMenuInteractionAdapter;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.entity.Ssc14EntityBehavior;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.construction.ConstructionItemInteractionService;
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
import net.ssc14.common.commerce.PalletRegistryCodec;
import net.ssc14.content.block.commerce.CommerceConsoleIntegrationService;
import net.ssc14.content.block.control.NetworkButtonInteractionService;
import net.ssc14.content.block.control.Ssc14ControlBlockEntities;
import net.ssc14.content.block.communication.CommunicationServerLifecycleService;
import net.ssc14.content.block.communication.CommunicationFrequencyStateService;
import net.ssc14.content.block.communication.Ssc14CommunicationBlockEntities;
import net.ssc14.content.block.fuel.Ssc14FuelBlockEntities;
import net.ssc14.content.block.gravity.GravityGeneratorPlugLifecycleService;
import net.ssc14.content.block.gravity.GravityGeneratorLifecycleService;
import net.ssc14.content.block.gravity.GravityGeneratorUiService;
import net.ssc14.content.block.gravity.Ssc14GravityBlockEntities;
import net.ssc14.content.block.debug.StationCodeDebugInteractionService;
import net.ssc14.content.block.debug.Ssc14DebugBlockEntities;
import net.ssc14.content.block.explosives.Ssc14ExplosiveBlockEntities;
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
import net.ssc14.common.network.MenuStateTransportPort;
import net.ssc14.integration.neoforge.menu.network.MenuStateNetworkActions;
import net.ssc14.content.block.machinery.Ssc14MachineBlockEntities;
import net.ssc14.content.block.energy.CableTopologyService;
import net.ssc14.content.energy.EnergyNetworkService;
import net.ssc14.content.block.energy.Ssc14CableBlockEntities;
import net.ssc14.content.block.energy.Ssc14EnergyMachineBlockEntities;
import net.ssc14.content.particle.Ssc14ParticleTypes;
import net.ssc14.content.sound.Ssc14SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;

import io.netty.buffer.Unpooled;



public final class Ssc14Bootstrap {
    private Ssc14Bootstrap() {
    }

    public static void initialize(IEventBus modEventBus) {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		modEventBus.addListener(Ssc14NetworkPayloads::register);
		if (ModList.get().isLoaded("curios")) {
			modEventBus.addListener(Ssc14CuriosCompatibility::registerCapabilities);
			modEventBus.addListener(Ssc14CuriosRenderers::registerRenderers);
		}
		Ssc14SoundEvents.REGISTRY.register(modEventBus);
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
		Ssc14Items.REGISTRY.register(modEventBus);
		modEventBus.addListener(Ssc14ItemCapabilities::register);
		LightingEnergyService.install(CableTopologyService::queueUpdate);
		CableTopologyService.install(new CableTopologyService.Adapter() {
			@Override
			public void queueUpdate(LevelAccessor level, net.minecraft.core.BlockPos pos) {
				EnergyNetworkService.queueUpdate(level, pos);
			}

			@Override
			public void invalidate(LevelAccessor level, net.minecraft.core.BlockPos pos) {
				EnergyNetworkService.invalidate(level, pos);
			}
		});
		OrderPalletRegistryService.install((level, pos) -> {
			var variables = Ssc14WorldVariables.WorldVariables.get(level);
			String updatedRegistry = PalletRegistryCodec.appendIfAbsent(variables.PalletRegistry, pos);
			if (!updatedRegistry.equals(variables.PalletRegistry)) {
				variables.PalletRegistry = updatedRegistry;
				variables.setDirty();
			}
		});
		GravityGeneratorPlugLifecycleService.installGeneratorLocator((level, pos) ->
			level.getBlockState(pos).is(Ssc14Blocks.GRAVITY_GENERATOR.get()));
		GravityGeneratorPlugLifecycleService.installPlugBlockFactory(() ->
			Ssc14Blocks.GRAV_GEN_PLUG_BLOCK.get().defaultBlockState());
		GravityGeneratorPlugLifecycleService.installPlacementPolicy((level, pos) ->
				level.getBlockState(pos).is(net.minecraft.tags.BlockTags.create(net.minecraft.resources.Identifier.parse("minecraft:replaceable")))
						|| level.getBlockState(pos).is(Ssc14Blocks.GRAV_GEN_PLUG_BLOCK.get()));
		GravityGeneratorLifecycleService.install(new GravityGeneratorLifecycleService.Adapter() {
			@Override
			public boolean canSurvive(LevelAccessor level, net.minecraft.core.BlockPos pos) {
				return net.ssc14.content.block.gravity.GravityGeneratorPlugLifecycleService.canPlace(
						(net.minecraft.world.level.Level) level, pos);
			}

			@Override
			public void placed(net.minecraft.world.level.Level level,
					net.minecraft.core.BlockPos pos,
					net.minecraft.world.level.block.state.BlockState state) {
				net.ssc14.content.block.gravity.GravityGeneratorPlugLifecycleService.generate(level, pos, state);
			}

			@Override
			public void tick(ServerLevel level, net.minecraft.core.BlockPos pos,
					net.minecraft.world.level.block.state.BlockState state) {
				net.ssc14.content.block.gravity.GravityGeneratorService.tick(level, pos);
			}

			@Override
			public void destroyed() {
				net.ssc14.content.gravity.GravityGeneratorDestroyHandler.execute();
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
		CommunicationServerLifecycleService.install(
				new CommunicationServerLifecycleService.Adapter() {
					@Override
					public void use(net.minecraft.world.level.Level level,
							net.minecraft.core.BlockPos pos,
							net.minecraft.world.level.block.state.BlockState state,
							Player player) {
						net.ssc14.content.block.communication.CommunicationServerInteractionService.execute(
								level, pos.getX(), pos.getY(), pos.getZ(), state, player);
					}
				});
		CommunicationFrequencyStateService.install((level, stateKey, enabled) -> {
			var state = Ssc14WorldVariables.MapVariables.get(level);
			switch (stateKey) {
				case "Base_Radio_Frequency" -> state.Base_Radio_Frequency = enabled;
				case "Command_Radio_Frequency" -> state.Command_Radio_Frequency = enabled;
				case "Engeneer_Radio_Frequency" -> state.Engeneer_Radio_Frequency = enabled;
				case "Medical_Radio_Frequency" -> state.Medical_Radio_Frequency = enabled;
				case "RND_Radio_Frequency" -> state.RND_Radio_Frequency = enabled;
				case "Sec_Radio_Frequency" -> state.Sec_Radio_Frequency = enabled;
				case "Serv_Radio_Frequency" -> state.Serv_Radio_Frequency = enabled;
				case "Cargo_Radio_Frequency" -> state.Cargo_Radio_Frequency = enabled;
				default -> { return; }
			}
			state.markSyncDirty();
		});
		UpperClosetPlugInteractionService.install((level, pos, player) ->
					net.ssc14.content.block.furniture.ClosetInteractionService.execute(
						level, pos.getX(), pos.getY() - 1, pos.getZ(),
						level.getBlockState(pos.below()), player));
		SecureClosetInteractionService.install((level, pos, state, player) ->
					net.ssc14.content.block.furniture.ClosetInteractionService.execute(
						level, pos.getX(), pos.getY(), pos.getZ(), state, player));
		ExtinguisherCabinetInteractionService.install(
				Ssc14Items.EXTINGUISHER, Ssc14Items.EXTINGUISHER_OPEN);
		StationCodeService.install(level ->
				(int) Ssc14WorldVariables.MapVariables.get(level).station_code);
		StationCodeService.installMutator((level, value) -> {
			Ssc14WorldVariables.MapVariables variables =
					Ssc14WorldVariables.MapVariables.get(level);
			variables.station_code = value;
			variables.markSyncDirty();
		});
		StationNameService.install(level ->
				Ssc14WorldVariables.MapVariables.get(level).station_name);
		StationBudgetService.install(level ->
				Ssc14WorldVariables.WorldVariables.get(level).Station_Budget);
		StationBudgetService.installSpender((level, amount) -> {
			var variables = Ssc14WorldVariables.WorldVariables.get(level);
			if (variables.Station_Budget < amount) return false;
			variables.Station_Budget -= amount;
			variables.markSyncDirty();
			return true;
		});
		StationBudgetService.installEditor(new StationBudgetService.Editor() {
			@Override
			public void credit(LevelAccessor level, double amount) {
				var variables = Ssc14WorldVariables.WorldVariables.get(level);
				variables.Station_Budget += amount;
				variables.markSyncDirty();
			}

			@Override
			public void reset(LevelAccessor level) {
				var variables = Ssc14WorldVariables.WorldVariables.get(level);
				variables.Station_Budget = 0;
				variables.markSyncDirty();
			}
		});
		MachineFrameAssemblyService.install(Ssc14Items.LOW_VOLTAGE_CABLE,
				Ssc14Blocks.MACHINE_FRAME_2);
		MachineFrameMenuService.install((id, inventory, pos) -> new MacFrameGUIMenu(
				id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos)));
		MicrowaveMenuService.install((id, inventory, pos) -> new MicrowaweGUIMenu(
				id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos)));
		MenuStateTransportPort.install(MenuStateNetworkActions::send);
		CommerceConsoleIntegrationService.install(
					new LegacyCommerceConsoleIntegrationAdapter());
		IdConsoleIntegrationService.install(new LegacyIdConsoleIntegrationAdapter());
		NetworkConfiguratorService.install(new LegacyNetworkConfiguratorMenuAdapter());
		ShuttersIntegrationService.install(new LegacyShuttersIntegrationAdapter());
		AirlockUpperPlugIntegrationService.installUseHandler(
				net.ssc14.content.airlock.AirlockUpperPlugInteractionService::execute);
		AirlockDoorIntegrationService.install(new LegacyAirlockDoorIntegrationAdapter());
		ConstructionItemInteractionService.install(
				LegacyConstructionItemInteractionAdapter.INSTANCE);
		DecalToolInteractionService.install(LegacyDecalToolInteractionAdapter.INSTANCE);
		PortableMenuInteractionService.install(LegacyPortableMenuInteractionAdapter.INSTANCE);
		FirearmInteractionService.install(LegacyFirearmInteractionAdapter.INSTANCE);
		Ssc14EntityBehavior.install(Ssc14EntityBehaviorHooks.INSTANCE);
		Ssc14EntityTypes.REGISTRY.register(modEventBus);
		Ssc14CreativeTabs.REGISTRY.register(modEventBus);
		Ssc14PlayerAttachments.register(modEventBus);
		Ssc14StructureFeature.register(modEventBus);
		Ssc14MenuTypes.REGISTRY.register(modEventBus);
		Ssc14ParticleTypes.REGISTRY.register(modEventBus);
		Ssc14Attributes.REGISTRY.register(modEventBus);
		// Start of user code block mod init
		ReagentService.register(modEventBus);
		// End of user code block mod init
	    }
}
