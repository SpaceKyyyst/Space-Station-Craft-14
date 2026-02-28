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
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleSteelMiniBlockEntity>> TITLE_STEEL_MINI = register("title_steel_mini", Ssc14ModBlocks.TITLE_STEEL_MINI, TitleSteelMiniBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleSteelMonoBlockEntity>> TITLE_STEEL_MONO = register("title_steel_mono", Ssc14ModBlocks.TITLE_STEEL_MONO, TitleSteelMonoBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleSteelOffsetBlockEntity>> TITLE_STEEL_OFFSET = register("title_steel_offset", Ssc14ModBlocks.TITLE_STEEL_OFFSET, TitleSteelOffsetBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleSteelPavementBlockEntity>> TITLE_STEEL_PAVEMENT = register("title_steel_pavement", Ssc14ModBlocks.TITLE_STEEL_PAVEMENT, TitleSteelPavementBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleSteelHerringboneBlockEntity>> TITLE_STEEL_HERRINGBONE = register("title_steel_herringbone", Ssc14ModBlocks.TITLE_STEEL_HERRINGBONE,
			TitleSteelHerringboneBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitlePlasticBlockEntity>> TITLE_PLASTIC = register("title_plastic", Ssc14ModBlocks.TITLE_PLASTIC, TitlePlasticBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkMiniBlockEntity>> TITLE_DARK_MINI = register("title_dark_mini", Ssc14ModBlocks.TITLE_DARK_MINI, TitleDarkMiniBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkMonoBlockEntity>> TITLE_DARK_MONO = register("title_dark_mono", Ssc14ModBlocks.TITLE_DARK_MONO, TitleDarkMonoBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkOffsetBlockEntity>> TITLE_DARK_OFFSET = register("title_dark_offset", Ssc14ModBlocks.TITLE_DARK_OFFSET, TitleDarkOffsetBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkPavementBlockEntity>> TITLE_DARK_PAVEMENT = register("title_dark_pavement", Ssc14ModBlocks.TITLE_DARK_PAVEMENT, TitleDarkPavementBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkPavementVerticalBlockEntity>> TITLE_DARK_PAVEMENT_VERTICAL = register("title_dark_pavement_vertical", Ssc14ModBlocks.TITLE_DARK_PAVEMENT_VERTICAL,
			TitleDarkPavementVerticalBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkHerringboneBlockEntity>> TITLE_DARK_HERRINGBONE = register("title_dark_herringbone", Ssc14ModBlocks.TITLE_DARK_HERRINGBONE, TitleDarkHerringboneBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TitleDarkPlasticBlockEntity>> TITLE_DARK_PLASTIC = register("title_dark_plastic", Ssc14ModBlocks.TITLE_DARK_PLASTIC, TitleDarkPlasticBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SMESBlockEntity>> SMES = register("smes", Ssc14ModBlocks.SMES, SMESBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GravityGeneratorBlockEntity>> GRAVITY_GENERATOR = register("gravity_generator", Ssc14ModBlocks.GRAVITY_GENERATOR, GravityGeneratorBlockEntity::new);

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
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_STEEL_MINI.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_STEEL_MONO.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_STEEL_OFFSET.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_STEEL_PAVEMENT.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_STEEL_HERRINGBONE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_PLASTIC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_MINI.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_MONO.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_OFFSET.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_PAVEMENT.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_PAVEMENT_VERTICAL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_HERRINGBONE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TITLE_DARK_PLASTIC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SMES.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, GRAVITY_GENERATOR.get(), SidedInvWrapper::new);
	}
}