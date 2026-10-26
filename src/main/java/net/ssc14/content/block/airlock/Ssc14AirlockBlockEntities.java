package net.ssc14.content.block.airlock;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for airlock-family content. */
public final class Ssc14AirlockBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShuttersBlockEntity>>
            SHUTTERS = REGISTRY.register("shutters", () -> new BlockEntityType<>(
                    ShuttersBlockEntity::new, Ssc14Blocks.SHUTTERS.get()));
    public static final DeferredHolder<BlockEntityType<?>,
            BlockEntityType<ClosedAirlockUpperPlugBlockEntity>> AIRLOCK_UP_PLUG =
            REGISTRY.register("airlock_up_plug", () -> new BlockEntityType<>(
                    ClosedAirlockUpperPlugBlockEntity::new,
                    Ssc14Blocks.AIRLOCK_UP_PLUG.get()));
    public static final DeferredHolder<BlockEntityType<?>,
            BlockEntityType<OpenAirlockUpperPlugBlockEntity>> AIRLOCK_UP_PLUG_OPEN =
            REGISTRY.register("airlock_up_plug_open", () -> new BlockEntityType<>(
                    OpenAirlockUpperPlugBlockEntity::new,
                    Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get()));

    private Ssc14AirlockBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, SHUTTERS.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG_OPEN.get(),
                WorldlyContainerWrapper::new);
    }
}
