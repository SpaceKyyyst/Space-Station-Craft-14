package net.ssc14.content.block.control;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for network controls. */
public final class Ssc14ControlBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NetworkButtonBlockEntity>>
            BUTTON = REGISTRY.register("button", () -> new BlockEntityType<>(
                    NetworkButtonBlockEntity::new, Ssc14Blocks.BUTTON.get()));

    private Ssc14ControlBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, BUTTON.get(),
                WorldlyContainerWrapper::new);
    }
}
