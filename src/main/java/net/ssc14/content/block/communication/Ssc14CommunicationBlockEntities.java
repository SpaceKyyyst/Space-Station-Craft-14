package net.ssc14.content.block.communication;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for communication systems. */
public final class Ssc14CommunicationBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>,
            BlockEntityType<CommunicationServersBlockEntity>> COMMUNICATION_SERVERS =
            REGISTRY.register("communication_servers",
                    () -> new BlockEntityType<>(CommunicationServersBlockEntity::new,
                            Ssc14Blocks.COMMUNICATION_SERVERS.get()));

    private Ssc14CommunicationBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, COMMUNICATION_SERVERS.get(),
                WorldlyContainerWrapper::new);
    }
}
