package net.mcreator.ssc;



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
import net.ssc14.content.menu.station.CommunConsoleGUIMenu;
import net.ssc14.content.block.commerce.LegacyCommerceConsoleIntegrationAdapter;
import net.ssc14.content.block.access.LegacyIdConsoleIntegrationAdapter;
import net.ssc14.content.item.tool.LegacyNetworkConfiguratorMenuAdapter;
import net.ssc14.content.item.tool.LegacyRsuInteractionAdapter;
import net.ssc14.content.item.weapon.LegacyFirearmInteractionAdapter;
import net.ssc14.content.item.construction.LegacyConstructionItemInteractionAdapter;
import net.ssc14.content.block.airlock.LegacyShuttersIntegrationAdapter;
import net.ssc14.content.block.airlock.LegacyAirlockDoorIntegrationAdapter;
import net.ssc14.content.block.airlock.LegacyAirlockControllerInteractionAdapter;
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
import net.ssc14.content.block.communication.CommunicationConsoleIntegrationService;
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



import net.ssc14.integration.Ssc14Bootstrap;

@Mod("ssc_14")
public class Ssc14Mod {
	public static final Logger LOGGER = LogManager.getLogger(Ssc14Mod.class);
	public static final String MODID = Ssc14Identity.MOD_ID;

	public Ssc14Mod(IEventBus modEventBus) {
		Ssc14Bootstrap.initialize(modEventBus);
	}


	// Start of user code block mod methods
	// End of user code block mod methods
    /** Worker-safe admission; only the passed server's tick thread executes the action. */
    public static ServerTaskSubmission queueServerWork(MinecraftServer owner, int delay, Runnable action) {
        return ServerWorkScheduler.submit(owner, delay, action);
    }

    public static ServerTaskSubmission queueServerWork(ServerLevel owner, int delay, Runnable action) {
        ServerWorkScheduler.validateSubmission(delay, action);
        if (owner == null) return ServerTaskSubmission.INVALID_OWNER;
        return queueServerWork(owner.getServer(), delay, action);
    }

    /** Explicit side rejection for integrations that only have a LevelAccessor. */
    public static ServerTaskSubmission queueServerWork(LevelAccessor owner, int delay, Runnable action) {
        ServerWorkScheduler.validateSubmission(delay, action);
        if (owner == null) return ServerTaskSubmission.INVALID_OWNER;
        if (owner.isClientSide()) return ServerTaskSubmission.CLIENT_SIDE;
        if (owner instanceof ServerLevel level) return queueServerWork(level, delay, action);
        return ServerTaskSubmission.INVALID_OWNER;
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

