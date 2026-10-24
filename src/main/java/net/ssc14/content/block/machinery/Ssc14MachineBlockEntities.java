package net.ssc14.content.block.machinery;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for machine-frame content. */
public final class Ssc14MachineBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineFrameBlockEntity>>
            MACHINE_FRAME_2 = REGISTRY.register("machine_frame_2",
                    () -> new BlockEntityType<>(MachineFrameBlockEntity::new,
                            Ssc14Blocks.MACHINE_FRAME_2.get()));

    private Ssc14MachineBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, MACHINE_FRAME_2.get(),
                WorldlyContainerWrapper::new);
    }
}
