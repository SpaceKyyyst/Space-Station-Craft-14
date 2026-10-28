package net.ssc14.content.block.fuel;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for fuel storage blocks. */
public final class Ssc14FuelBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FuelTankBlockEntity>>
            FUEL_TANK = REGISTRY.register("fuel_tank",
                    () -> new BlockEntityType<>(FuelTankBlockEntity::new,
                            Ssc14Blocks.FUEL_TANK.get()));

    private Ssc14FuelBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, FUEL_TANK.get(),
                WorldlyContainerWrapper::new);
    }
}
