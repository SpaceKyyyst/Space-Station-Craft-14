package net.ssc14.content.block.commerce;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical block-entity registrations for commerce blocks. */
public final class Ssc14CommerceBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SalePalletBlockEntity>>
            SALE_PALLET = REGISTRY.register("sale_pallet",
                    () -> new BlockEntityType<>(SalePalletBlockEntity::new,
                            Ssc14Blocks.SALE_PALLET.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrderPalletBlockEntity>>
            ORDER_PALLET = REGISTRY.register("order_pallet",
                    () -> new BlockEntityType<>(OrderPalletBlockEntity::new,
                            Ssc14Blocks.ORDER_PALLET.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrderConsoleBlockEntity>>
            ORDER_CONSOLE = REGISTRY.register("order_console",
                    () -> new BlockEntityType<>(OrderConsoleBlockEntity::new,
                            Ssc14Blocks.ORDER_CONSOLE.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaleConsoleBlockEntity>>
            SALE_CONSOLE = REGISTRY.register("sale_console",
                    () -> new BlockEntityType<>(SaleConsoleBlockEntity::new,
                            Ssc14Blocks.SALE_CONSOLE.get()));

    private Ssc14CommerceBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, SALE_PALLET.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ORDER_PALLET.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ORDER_CONSOLE.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, SALE_CONSOLE.get(),
                WorldlyContainerWrapper::new);
    }
}
