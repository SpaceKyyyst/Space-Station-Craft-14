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

@EventBusSubscriber
public class Ssc14ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Mod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingBlockEntity>> SHEATHING = register("sheathing", Ssc14ModBlocks.SHEATHING, SheathingBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1BlockEntity>> BASE_AIRLOCK_D_1 = register("base_airlock_d_1", Ssc14ModBlocks.BASE_AIRLOCK_D_1, BaseAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConsoleOfIDBlockEntity>> CONSOLE_OF_ID = register("console_of_id", Ssc14ModBlocks.CONSOLE_OF_ID, ConsoleOfIDBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortableSheaterBlockEntity>> PORTABLE_SHEATER = register("portable_sheater", Ssc14ModBlocks.PORTABLE_SHEATER, PortableSheaterBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<APCBlockEntity>> APC = register("apc", Ssc14ModBlocks.APC, APCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PodstationBlockEntity>> PODSTATION = register("podstation", Ssc14ModBlocks.PODSTATION, PodstationBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DEBUGgeneratorBlockEntity>> DEBU_GGENERATOR = register("debu_ggenerator", Ssc14ModBlocks.DEBU_GGENERATOR, DEBUGgeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LampBlockEntity>> LAMP = register("lamp", Ssc14ModBlocks.LAMP, LampBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AirlockUpPlugBlockEntity>> AIRLOCK_UP_PLUG = register("airlock_up_plug", Ssc14ModBlocks.AIRLOCK_UP_PLUG, AirlockUpPlugBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AirlockUpPlugOPENBlockEntity>> AIRLOCK_UP_PLUG_OPEN = register("airlock_up_plug_open", Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN, AirlockUpPlugOPENBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SMESBlockEntity>> SMES = register("smes", Ssc14ModBlocks.SMES, SMESBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GravityGeneratorBlockEntity>> GRAVITY_GENERATOR = register("gravity_generator", Ssc14ModBlocks.GRAVITY_GENERATOR, GravityGeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<C4BlockEntity>> C_4 = register("c_4", Ssc14ModBlocks.C_4, C4BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineFrame2BlockEntity>> MACHINE_FRAME_2 = register("machine_frame_2", Ssc14ModBlocks.MACHINE_FRAME_2, MachineFrame2BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KostilniblokBlockEntity>> KOSTILNIBLOK = register("kostilniblok", Ssc14ModBlocks.KOSTILNIBLOK, KostilniblokBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DebugGravGenBlockEntity>> DEBUG_GRAV_GEN = register("debug_grav_gen", Ssc14ModBlocks.DEBUG_GRAV_GEN, DebugGravGenBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NuclearWarheadBlockEntity>> NUCLEAR_WARHEAD = register("nuclear_warhead", Ssc14ModBlocks.NUCLEAR_WARHEAD, NuclearWarheadBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetSecureBlockEntity>> CLOSET_SECURE = register("closet_secure", Ssc14ModBlocks.CLOSET_SECURE, ClosetSecureBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET = register("closet", Ssc14ModBlocks.CLOSET, ClosetBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CommunicationServersBlockEntity>> COMMUNICATION_SERVERS = register("communication_servers", Ssc14ModBlocks.COMMUNICATION_SERVERS, CommunicationServersBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CommunicationConsoleBlockEntity>> COMMUNICATION_CONSOLE = register("communication_console", Ssc14ModBlocks.COMMUNICATION_CONSOLE, CommunicationConsoleBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TechAirlockD1BlockEntity>> TECH_AIRLOCK_D_1 = register("tech_airlock_d_1", Ssc14ModBlocks.TECH_AIRLOCK_D_1, TechAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VendingAut1BlockEntity>> VENDING_AUT_1 = register("vending_aut_1", Ssc14ModBlocks.VENDING_AUT_1, VendingAut1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShuttersBlockEntity>> SHUTTERS = register("shutters", Ssc14ModBlocks.SHUTTERS, ShuttersBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ButtonBlockEntity>> BUTTON = register("button", Ssc14ModBlocks.BUTTON, ButtonBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MicrowaweBlockEntity>> MICROWAWE = register("microwawe", Ssc14ModBlocks.MICROWAWE, MicrowaweBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleMeatBlockEntity>> TITLE_MEAT = register("title_meat", Ssc14ModBlocks.TITLE_MEAT, TitleMeatBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleSteeldamagedBlockEntity>> TITLE_STEELDAMAGED = register("title_steeldamaged", Ssc14ModBlocks.TITLE_STEELDAMAGED, TitleSteeldamagedBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleWoodbrokenBlockEntity>> TITLE_WOODBROKEN = register("title_woodbroken", Ssc14ModBlocks.TITLE_WOODBROKEN, TitleWoodbrokenBlockEntity::new);
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
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetEmergBlockEntity>> CLOSET_EMERG = register("closet_emerg", Ssc14ModBlocks.CLOSET_EMERG, ClosetEmergBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetFireBlockEntity>> CLOSET_FIRE = register("closet_fire", Ssc14ModBlocks.CLOSET_FIRE, ClosetFireBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlackBlockEntity>> CLOSET_BLACK = register("closet_black", Ssc14ModBlocks.CLOSET_BLACK, ClosetBlackBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlueBlockEntity>> CLOSET_BLUE = register("closet_blue", Ssc14ModBlocks.CLOSET_BLUE, ClosetBlueBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetGrayBlockEntity>> CLOSET_GRAY = register("closet_gray", Ssc14ModBlocks.CLOSET_GRAY, ClosetGrayBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetGreenBlockEntity>> CLOSET_GREEN = register("closet_green", Ssc14ModBlocks.CLOSET_GREEN, ClosetGreenBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetMixedBlockEntity>> CLOSET_MIXED = register("closet_mixed", Ssc14ModBlocks.CLOSET_MIXED, ClosetMixedBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetN2BlockEntity>> CLOSET_N_2 = register("closet_n_2", Ssc14ModBlocks.CLOSET_N_2, ClosetN2BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetOrangeBlockEntity>> CLOSET_ORANGE = register("closet_orange", Ssc14ModBlocks.CLOSET_ORANGE, ClosetOrangeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetPinkBlockEntity>> CLOSET_PINK = register("closet_pink", Ssc14ModBlocks.CLOSET_PINK, ClosetPinkBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GlassAirlockD1BlockEntity>> GLASS_AIRLOCK_D_1 = register("glass_airlock_d_1", Ssc14ModBlocks.GLASS_AIRLOCK_D_1, GlassAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseGlassAirlockD1BlockEntity>> BASE_GLASS_AIRLOCK_D_1 = register("base_glass_airlock_d_1", Ssc14ModBlocks.BASE_GLASS_AIRLOCK_D_1, BaseGlassAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FuelTankBlockEntity>> FUEL_TANK = register("fuel_tank", Ssc14ModBlocks.FUEL_TANK, FuelTankBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrderConsoleBlockEntity>> ORDER_CONSOLE = register("order_console", Ssc14ModBlocks.ORDER_CONSOLE, OrderConsoleBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrderPalletBlockEntity>> ORDER_PALLET = register("order_pallet", Ssc14ModBlocks.ORDER_PALLET, OrderPalletBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SalePalletBlockEntity>> SALE_PALLET = register("sale_pallet", Ssc14ModBlocks.SALE_PALLET, SalePalletBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaleConsoleBlockEntity>> SALE_CONSOLE = register("sale_console", Ssc14ModBlocks.SALE_CONSOLE, SaleConsoleBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EmergencyLightBlockEntity>> EMERGENCY_LIGHT = register("emergency_light", Ssc14ModBlocks.EMERGENCY_LIGHT, EmergencyLightBlockEntity::new);

	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.Item.BLOCK, SHEATHING.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, BASE_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CONSOLE_OF_ID.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, PORTABLE_SHEATER.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, APC.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, PODSTATION.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, DEBU_GGENERATOR.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, LAMP.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG_OPEN.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SMES.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, GRAVITY_GENERATOR.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, C_4.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, MACHINE_FRAME_2.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, KOSTILNIBLOK.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, DEBUG_GRAV_GEN.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, NUCLEAR_WARHEAD.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_SECURE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, COMMUNICATION_SERVERS.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, COMMUNICATION_CONSOLE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TECH_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, VENDING_AUT_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SHUTTERS.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, BUTTON.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, MICROWAWE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TITLE_MEAT.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TITLE_STEELDAMAGED.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TITLE_WOODBROKEN.get(), WorldlyContainerWrapper::new);
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
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_EMERG.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_FIRE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_BLACK.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_BLUE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_GRAY.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_GREEN.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_MIXED.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_N_2.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_ORANGE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_PINK.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, GLASS_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, BASE_GLASS_AIRLOCK_D_1.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, FUEL_TANK.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, ORDER_CONSOLE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, ORDER_PALLET.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SALE_PALLET.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SALE_CONSOLE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, EMERGENCY_LIGHT.get(), WorldlyContainerWrapper::new);
	}
}
