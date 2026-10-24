package net.ssc14.content.block.storage;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical owner of standard closet block entity registrations. */
public final class Ssc14ClosetBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Ssc14Identity.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET =
            register("closet", "Closet", Ssc14Blocks.CLOSET);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>>
            CLOSET_SECURE = register(
                    "closet_secure", "Closet Secure", Ssc14Blocks.CLOSET_SECURE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_EMERG =
            register("closet_emerg", "Emergency closet", Ssc14Blocks.CLOSET_EMERG);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_FIRE =
            register("closet_fire", "Fire closet", Ssc14Blocks.CLOSET_FIRE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_BLACK =
            register("closet_black", "Black closet", Ssc14Blocks.CLOSET_BLACK);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_BLUE =
            register("closet_blue", "Blue closet", Ssc14Blocks.CLOSET_BLUE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_GRAY =
            register("closet_gray", "Gray closet", Ssc14Blocks.CLOSET_GRAY);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_GREEN =
            register("closet_green", "Green closet", Ssc14Blocks.CLOSET_GREEN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_MIXED =
            register("closet_mixed", "Mixed closet", Ssc14Blocks.CLOSET_MIXED);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_N_2 =
            register("closet_n_2", "N2 closet", Ssc14Blocks.CLOSET_N_2);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_ORANGE =
            register("closet_orange", "Orange closet", Ssc14Blocks.CLOSET_ORANGE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> CLOSET_PINK =
            register("closet_pink", "Pink closet", Ssc14Blocks.CLOSET_PINK);

    private Ssc14ClosetBlockEntities() { }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_SECURE.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_EMERG.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_FIRE.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_BLACK.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_BLUE.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_GRAY.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_GREEN.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_MIXED.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_N_2.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_ORANGE.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CLOSET_PINK.get(),
                WorldlyContainerWrapper::new);
    }

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<ClosetBlockEntity>> register(
            String path, String displayName, DeferredBlock<Block> block) {
        return REGISTRY.register(path, () -> new BlockEntityType<>(
                (pos, state) -> new ClosetBlockEntity(path, displayName, pos, state), block.get()));
    }
}
