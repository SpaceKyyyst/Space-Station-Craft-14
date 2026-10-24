package net.ssc14.content.block.lighting;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical owner of lighting block-entity registrations. */
public final class Ssc14LightingBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LightingBlockEntity>> LAMP =
            register("lamp", "Lamp", true, Ssc14Blocks.LAMP);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LightingBlockEntity>> EMERGENCY_LIGHT =
            register("emergency_light", "Emergency Light", false, Ssc14Blocks.EMERGENCY_LIGHT);

    private Ssc14LightingBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, LAMP.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, EMERGENCY_LIGHT.get(),
                WorldlyContainerWrapper::new);
    }

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<LightingBlockEntity>> register(
            String path, String displayName, boolean energyAware, DeferredBlock<Block> block) {
        return REGISTRY.register(path, () -> new BlockEntityType<>(
                (pos, state) -> new LightingBlockEntity(
                        path, displayName, energyAware, pos, state),
                block.get()));
    }
}
