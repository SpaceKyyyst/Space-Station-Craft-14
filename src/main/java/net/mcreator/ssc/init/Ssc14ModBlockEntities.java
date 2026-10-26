package net.mcreator.ssc.init;

import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.ssc.block.entity.*;
import net.mcreator.ssc.Ssc14Mod;
import net.ssc14.content.block.storage.ClosetBlockEntity;
import net.ssc14.content.block.storage.Ssc14ClosetBlockEntities;
import net.ssc14.content.block.lighting.LightingBlockEntity;
import net.ssc14.content.block.lighting.Ssc14LightingBlockEntities;
import net.ssc14.content.block.machinery.MachineFrameBlockEntity;
import net.ssc14.content.block.machinery.PortableSheaterBlockEntity;
import net.ssc14.content.block.machinery.MicrowaveBlockEntity;
import net.ssc14.content.block.machinery.Ssc14MachineBlockEntities;
import net.ssc14.content.block.energy.SheathingBlockEntity;
import net.ssc14.content.block.energy.Ssc14CableBlockEntities;
import net.ssc14.content.block.energy.ApcBlockEntity;
import net.ssc14.content.block.energy.PodstationBlockEntity;
import net.ssc14.content.block.energy.DebugGeneratorBlockEntity;
import net.ssc14.content.block.energy.SmesBlockEntity;
import net.ssc14.content.block.energy.Ssc14EnergyMachineBlockEntities;
import net.ssc14.content.block.commerce.SalePalletBlockEntity;
import net.ssc14.content.block.commerce.OrderPalletBlockEntity;
import net.ssc14.content.block.commerce.OrderConsoleBlockEntity;
import net.ssc14.content.block.commerce.SaleConsoleBlockEntity;
import net.ssc14.content.block.commerce.Ssc14CommerceBlockEntities;
import net.ssc14.content.block.commerce.VendingMachineBlockEntity;
import net.ssc14.content.block.fuel.Ssc14FuelBlockEntities;
import net.ssc14.content.block.fuel.FuelTankBlockEntity;
import net.ssc14.content.block.debug.FailureSentinelBlockEntity;
import net.ssc14.content.block.debug.DebugGravityGeneratorBlockEntity;
import net.ssc14.content.block.debug.Ssc14DebugBlockEntities;
import net.ssc14.content.block.explosives.C4ChargeBlockEntity;
import net.ssc14.content.block.explosives.Ssc14ExplosiveBlockEntities;
import net.ssc14.content.block.explosives.NuclearWarheadBlockEntity;
import net.ssc14.content.block.gravity.GravityGeneratorBlockEntity;
import net.ssc14.content.block.gravity.Ssc14GravityBlockEntities;
import net.ssc14.content.block.communication.CommunicationServersBlockEntity;
import net.ssc14.content.block.communication.CommunicationConsoleBlockEntity;
import net.ssc14.content.block.communication.Ssc14CommunicationBlockEntities;

@EventBusSubscriber
public class Ssc14ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Mod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingBlockEntity>> SHEATHING = Ssc14CableBlockEntities.SHEATHING;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1BlockEntity>> BASE_AIRLOCK_D_1 = register("base_airlock_d_1", Ssc14ModBlocks.BASE_AIRLOCK_D_1, BaseAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConsoleOfIDBlockEntity>> CONSOLE_OF_ID = register("console_of_id", Ssc14ModBlocks.CONSOLE_OF_ID, ConsoleOfIDBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortableSheaterBlockEntity>> PORTABLE_SHEATER = Ssc14MachineBlockEntities.PORTABLE_SHEATER;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ApcBlockEntity>> APC = Ssc14EnergyMachineBlockEntities.APC;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PodstationBlockEntity>> PODSTATION = Ssc14EnergyMachineBlockEntities.PODSTATION;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DebugGeneratorBlockEntity>> DEBU_GGENERATOR = Ssc14EnergyMachineBlockEntities.DEBU_GGENERATOR;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LightingBlockEntity>> LAMP = Ssc14LightingBlockEntities.LAMP;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AirlockUpPlugBlockEntity>> AIRLOCK_UP_PLUG = register("airlock_up_plug", Ssc14ModBlocks.AIRLOCK_UP_PLUG, AirlockUpPlugBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AirlockUpPlugOPENBlockEntity>> AIRLOCK_UP_PLUG_OPEN = register("airlock_up_plug_open", Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN, AirlockUpPlugOPENBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SmesBlockEntity>> SMES = Ssc14EnergyMachineBlockEntities.SMES;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GravityGeneratorBlockEntity>> GRAVITY_GENERATOR = Ssc14GravityBlockEntities.GRAVITY_GENERATOR;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<C4ChargeBlockEntity>> C_4 = Ssc14ExplosiveBlockEntities.C_4;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineFrameBlockEntity>> MACHINE_FRAME_2 = Ssc14MachineBlockEntities.MACHINE_FRAME_2;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FailureSentinelBlockEntity>> KOSTILNIBLOK = Ssc14DebugBlockEntities.KOSTILNIBLOK;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DebugGravityGeneratorBlockEntity>> DEBUG_GRAV_GEN = Ssc14DebugBlockEntities.DEBUG_GRAV_GEN;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NuclearWarheadBlockEntity>> NUCLEAR_WARHEAD = Ssc14ExplosiveBlockEntities.NUCLEAR_WARHEAD;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_SECURE = Ssc14ClosetBlockEntities.CLOSET_SECURE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET = Ssc14ClosetBlockEntities.CLOSET;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CommunicationServersBlockEntity>> COMMUNICATION_SERVERS = Ssc14CommunicationBlockEntities.COMMUNICATION_SERVERS;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CommunicationConsoleBlockEntity>> COMMUNICATION_CONSOLE = Ssc14CommunicationBlockEntities.COMMUNICATION_CONSOLE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TechAirlockD1BlockEntity>> TECH_AIRLOCK_D_1 = register("tech_airlock_d_1", Ssc14ModBlocks.TECH_AIRLOCK_D_1, TechAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VendingMachineBlockEntity>> VENDING_AUT_1 = Ssc14CommerceBlockEntities.VENDING_AUT_1;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShuttersBlockEntity>> SHUTTERS = register("shutters", Ssc14ModBlocks.SHUTTERS, ShuttersBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ButtonBlockEntity>> BUTTON = register("button", Ssc14ModBlocks.BUTTON, ButtonBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MicrowaveBlockEntity>> MICROWAWE = Ssc14MachineBlockEntities.MICROWAWE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AtmosphericsAirlockD1BlockEntity>> ATMOSPHERICS_AIRLOCK_D_1 = register("atmospherics_airlock_d_1", Ssc14ModBlocks.ATMOSPHERICS_AIRLOCK_D_1,
			AtmosphericsAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CargoAirlockD1BlockEntity>> CARGO_AIRLOCK_D_1 = register("cargo_airlock_d_1", Ssc14ModBlocks.CARGO_AIRLOCK_D_1, CargoAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CentComAirlockD1BlockEntity>> CENT_COM_AIRLOCK_D_1 = register("cent_com_airlock_d_1", Ssc14ModBlocks.CENT_COM_AIRLOCK_D_1, CentComAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChemistryAirlockD1BlockEntity>> CHEMISTRY_AIRLOCK_D_1 = register("chemistry_airlock_d_1", Ssc14ModBlocks.CHEMISTRY_AIRLOCK_D_1, ChemistryAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CommandAirlockD1BlockEntity>> COMMAND_AIRLOCK_D_1 = register("command_airlock_d_1", Ssc14ModBlocks.COMMAND_AIRLOCK_D_1, CommandAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EngineeringAirlockD1BlockEntity>> ENGINEERING_AIRLOCK_D_1 = register("engineering_airlock_d_1", Ssc14ModBlocks.ENGINEERING_AIRLOCK_D_1, EngineeringAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HydroponicsAirlockD1BlockEntity>> HYDROPONICS_AIRLOCK_D_1 = register("hydroponics_airlock_d_1", Ssc14ModBlocks.HYDROPONICS_AIRLOCK_D_1, HydroponicsAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MedicalAirlockD1BlockEntity>> MEDICAL_AIRLOCK_D_1 = register("medical_airlock_d_1", Ssc14ModBlocks.MEDICAL_AIRLOCK_D_1, MedicalAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SalvageAirlockD1BlockEntity>> SALVAGE_AIRLOCK_D_1 = register("salvage_airlock_d_1", Ssc14ModBlocks.SALVAGE_AIRLOCK_D_1, SalvageAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ScienceAirlockD1BlockEntity>> SCIENCE_AIRLOCK_D_1 = register("science_airlock_d_1", Ssc14ModBlocks.SCIENCE_AIRLOCK_D_1, ScienceAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SecurityAirlockD1BlockEntity>> SECURITY_AIRLOCK_D_1 = register("security_airlock_d_1", Ssc14ModBlocks.SECURITY_AIRLOCK_D_1, SecurityAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SyndicateAirlockD1BlockEntity>> SYNDICATE_AIRLOCK_D_1 = register("syndicate_airlock_d_1", Ssc14ModBlocks.SYNDICATE_AIRLOCK_D_1, SyndicateAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VirologyAirlockD1BlockEntity>> VIROLOGY_AIRLOCK_D_1 = register("virology_airlock_d_1", Ssc14ModBlocks.VIROLOGY_AIRLOCK_D_1, VirologyAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_EMERG = Ssc14ClosetBlockEntities.CLOSET_EMERG;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_FIRE = Ssc14ClosetBlockEntities.CLOSET_FIRE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_BLACK = Ssc14ClosetBlockEntities.CLOSET_BLACK;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_BLUE = Ssc14ClosetBlockEntities.CLOSET_BLUE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_GRAY = Ssc14ClosetBlockEntities.CLOSET_GRAY;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_GREEN = Ssc14ClosetBlockEntities.CLOSET_GREEN;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_MIXED = Ssc14ClosetBlockEntities.CLOSET_MIXED;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_N_2 = Ssc14ClosetBlockEntities.CLOSET_N_2;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_ORANGE = Ssc14ClosetBlockEntities.CLOSET_ORANGE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_PINK = Ssc14ClosetBlockEntities.CLOSET_PINK;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GlassAirlockD1BlockEntity>> GLASS_AIRLOCK_D_1 = register("glass_airlock_d_1", Ssc14ModBlocks.GLASS_AIRLOCK_D_1, GlassAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseGlassAirlockD1BlockEntity>> BASE_GLASS_AIRLOCK_D_1 = register("base_glass_airlock_d_1", Ssc14ModBlocks.BASE_GLASS_AIRLOCK_D_1, BaseGlassAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FuelTankBlockEntity>> FUEL_TANK = Ssc14FuelBlockEntities.FUEL_TANK;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrderConsoleBlockEntity>> ORDER_CONSOLE = Ssc14CommerceBlockEntities.ORDER_CONSOLE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrderPalletBlockEntity>> ORDER_PALLET = Ssc14CommerceBlockEntities.ORDER_PALLET;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SalePalletBlockEntity>> SALE_PALLET = Ssc14CommerceBlockEntities.SALE_PALLET;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaleConsoleBlockEntity>> SALE_CONSOLE = Ssc14CommerceBlockEntities.SALE_CONSOLE;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LightingBlockEntity>> EMERGENCY_LIGHT = Ssc14LightingBlockEntities.EMERGENCY_LIGHT;

	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.Item.BLOCK, BASE_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CONSOLE_OF_ID.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG_OPEN.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TECH_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SHUTTERS.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, BUTTON.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, ATMOSPHERICS_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CARGO_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CENT_COM_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CHEMISTRY_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, COMMAND_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, ENGINEERING_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, HYDROPONICS_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, MEDICAL_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SALVAGE_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SCIENCE_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SECURITY_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SYNDICATE_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, VIROLOGY_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, GLASS_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, BASE_GLASS_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
	}
}
