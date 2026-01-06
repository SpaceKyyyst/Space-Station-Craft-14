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
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingLVCBlockEntity>> SHEATHING_LVC = register("sheathing_lvc", Ssc14ModBlocks.SHEATHING_LVC, SheathingLVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingMVCBlockEntity>> SHEATHING_MVC = register("sheathing_mvc", Ssc14ModBlocks.SHEATHING_MVC, SheathingMVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingHVCBlockEntity>> SHEATHING_HVC = register("sheathing_hvc", Ssc14ModBlocks.SHEATHING_HVC, SheathingHVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingLMVCBlockEntity>> SHEATHING_LMVC = register("sheathing_lmvc", Ssc14ModBlocks.SHEATHING_LMVC, SheathingLMVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingLHVCBlockEntity>> SHEATHING_LHVC = register("sheathing_lhvc", Ssc14ModBlocks.SHEATHING_LHVC, SheathingLHVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingMHVCBlockEntity>> SHEATHING_MHVC = register("sheathing_mhvc", Ssc14ModBlocks.SHEATHING_MHVC, SheathingMHVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SheathingLMHVCBlockEntity>> SHEATHING_LMHVC = register("sheathing_lmhvc", Ssc14ModBlocks.SHEATHING_LMHVC, SheathingLMHVCBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorLvcBlockEntity>> ROD_FLOOR_LVC = register("rod_floor_lvc", Ssc14ModBlocks.ROD_FLOOR_LVC, RodFloorLvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorMvcBlockEntity>> ROD_FLOOR_MVC = register("rod_floor_mvc", Ssc14ModBlocks.ROD_FLOOR_MVC, RodFloorMvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorHvcBlockEntity>> ROD_FLOOR_HVC = register("rod_floor_hvc", Ssc14ModBlocks.ROD_FLOOR_HVC, RodFloorHvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorLMvcBlockEntity>> ROD_FLOOR_L_MVC = register("rod_floor_l_mvc", Ssc14ModBlocks.ROD_FLOOR_L_MVC, RodFloorLMvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorLHvcBlockEntity>> ROD_FLOOR_L_HVC = register("rod_floor_l_hvc", Ssc14ModBlocks.ROD_FLOOR_L_HVC, RodFloorLHvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorMHvcBlockEntity>> ROD_FLOOR_M_HVC = register("rod_floor_m_hvc", Ssc14ModBlocks.ROD_FLOOR_M_HVC, RodFloorMHvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RodFloorLMHvcBlockEntity>> ROD_FLOOR_LM_HVC = register("rod_floor_lm_hvc", Ssc14ModBlocks.ROD_FLOOR_LM_HVC, RodFloorLMHvcBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1BlockEntity>> BASE_AIRLOCK_D_1 = register("base_airlock_d_1", Ssc14ModBlocks.BASE_AIRLOCK_D_1, BaseAirlockD1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockU1BlockEntity>> BASE_AIRLOCK_U_1 = register("base_airlock_u_1", Ssc14ModBlocks.BASE_AIRLOCK_U_1, BaseAirlockU1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConsoleOfIDBlockEntity>> CONSOLE_OF_ID = register("console_of_id", Ssc14ModBlocks.CONSOLE_OF_ID, ConsoleOfIDBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AtmosBlockBlockEntity>> ATMOS_BLOCK = register("atmos_block", Ssc14ModBlocks.ATMOS_BLOCK, AtmosBlockBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1openBlockEntity>> BASE_AIRLOCK_D_1OPEN = register("base_airlock_d_1open", Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN, BaseAirlockD1openBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockU1openBlockEntity>> BASE_AIRLOCK_U_1OPEN = register("base_airlock_u_1open", Ssc14ModBlocks.BASE_AIRLOCK_U_1OPEN, BaseAirlockU1openBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortableSheaterBlockEntity>> PORTABLE_SHEATER = register("portable_sheater", Ssc14ModBlocks.PORTABLE_SHEATER, PortableSheaterBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_LVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_MVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_HVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_LMVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_LHVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_MHVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHEATHING_LMHVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_LVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_MVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_HVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_L_MVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_L_HVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_M_HVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ROD_FLOOR_LM_HVC.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BASE_AIRLOCK_D_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BASE_AIRLOCK_U_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CONSOLE_OF_ID.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ATMOS_BLOCK.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BASE_AIRLOCK_D_1OPEN.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BASE_AIRLOCK_U_1OPEN.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PORTABLE_SHEATER.get(), SidedInvWrapper::new);
	}
}