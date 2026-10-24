package net.ssc14.content.block.energy;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for energy machines. */
public final class Ssc14EnergyMachineBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ApcBlockEntity>> APC =
            REGISTRY.register("apc", () -> new BlockEntityType<>(ApcBlockEntity::new,
                    Ssc14Blocks.APC.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PodstationBlockEntity>>
            PODSTATION = REGISTRY.register("podstation",
                    () -> new BlockEntityType<>(PodstationBlockEntity::new,
                            Ssc14Blocks.PODSTATION.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DebugGeneratorBlockEntity>>
            DEBU_GGENERATOR = REGISTRY.register("debu_ggenerator",
                    () -> new BlockEntityType<>(DebugGeneratorBlockEntity::new,
                            Ssc14Blocks.DEBU_GGENERATOR.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SmesBlockEntity>> SMES =
            REGISTRY.register("smes", () -> new BlockEntityType<>(SmesBlockEntity::new,
                    Ssc14Blocks.SMES.get()));

    private Ssc14EnergyMachineBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, APC.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, PODSTATION.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, DEBU_GGENERATOR.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, SMES.get(),
                WorldlyContainerWrapper::new);
    }
}
