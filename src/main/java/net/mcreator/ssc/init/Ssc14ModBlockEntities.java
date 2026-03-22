/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
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
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1BlockEntity>> BASE_AIRLOCK_D_1 = register("base_airlock_d_1", Ssc14ModBlocks.BASE_AIRLOCK_D_1, BaseAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConsoleOfIDBlockEntity>> CONSOLE_OF_ID = register("console_of_id", Ssc14ModBlocks.CONSOLE_OF_ID, ConsoleOfIDBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AtmosBlockBlockEntity>> ATMOS_BLOCK = register("atmos_block", Ssc14ModBlocks.ATMOS_BLOCK, AtmosBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1openBlockEntity>> BASE_AIRLOCK_D_1OPEN = register("base_airlock_d_1open", Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN, BaseAirlockD1openBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortableSheaterBlockEntity>> PORTABLE_SHEATER = register("portable_sheater", Ssc14ModBlocks.PORTABLE_SHEATER, PortableSheaterBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<APCBlockEntity>> APC = register("apc", Ssc14ModBlocks.APC, APCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PodstationBlockEntity>> PODSTATION = register("podstation", Ssc14ModBlocks.PODSTATION, PodstationBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DEBUGgeneratorBlockEntity>> DEBU_GGENERATOR = register("debu_ggenerator", Ssc14ModBlocks.DEBU_GGENERATOR, DEBUGgeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AirlockUpPlugBlockEntity>> AIRLOCK_UP_PLUG = register("airlock_up_plug", Ssc14ModBlocks.AIRLOCK_UP_PLUG, AirlockUpPlugBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AirlockUpPlugOPENBlockEntity>> AIRLOCK_UP_PLUG_OPEN = register("airlock_up_plug_open", Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN, AirlockUpPlugOPENBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SMESBlockEntity>> SMES = register("smes", Ssc14ModBlocks.SMES, SMESBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GravityGeneratorBlockEntity>> GRAVITY_GENERATOR = register("gravity_generator", Ssc14ModBlocks.GRAVITY_GENERATOR, GravityGeneratorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<C4BlockEntity>> C_4 = register("c_4", Ssc14ModBlocks.C_4, C4BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineFrame2BlockEntity>> MACHINE_FRAME_2 = register("machine_frame_2", Ssc14ModBlocks.MACHINE_FRAME_2, MachineFrame2BlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BASE_AIRLOCK_D_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CONSOLE_OF_ID.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ATMOS_BLOCK.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BASE_AIRLOCK_D_1OPEN.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PORTABLE_SHEATER.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, APC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PODSTATION.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DEBU_GGENERATOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, AIRLOCK_UP_PLUG.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, AIRLOCK_UP_PLUG_OPEN.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SMES.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, GRAVITY_GENERATOR.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, C_4.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MACHINE_FRAME_2.get(), SidedInvWrapper::new);
	}
}