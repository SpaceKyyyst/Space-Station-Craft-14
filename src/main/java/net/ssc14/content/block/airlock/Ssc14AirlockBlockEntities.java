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
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseAirlockD1BlockEntity>>
            BASE_AIRLOCK_D_1 = REGISTRY.register("base_airlock_d_1",
                    () -> new BlockEntityType<>(BaseAirlockD1BlockEntity::new,
                            Ssc14Blocks.BASE_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TechAirlockD1BlockEntity>>
            TECH_AIRLOCK_D_1 = REGISTRY.register("tech_airlock_d_1", () -> new BlockEntityType<>(
                    TechAirlockD1BlockEntity::new, Ssc14Blocks.TECH_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AtmosphericsAirlockD1BlockEntity>>
            ATMOSPHERICS_AIRLOCK_D_1 = REGISTRY.register("atmospherics_airlock_d_1", () -> new BlockEntityType<>(
                    AtmosphericsAirlockD1BlockEntity::new, Ssc14Blocks.ATMOSPHERICS_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CargoAirlockD1BlockEntity>>
            CARGO_AIRLOCK_D_1 = REGISTRY.register("cargo_airlock_d_1", () -> new BlockEntityType<>(
                    CargoAirlockD1BlockEntity::new, Ssc14Blocks.CARGO_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CentComAirlockD1BlockEntity>>
            CENT_COM_AIRLOCK_D_1 = REGISTRY.register("cent_com_airlock_d_1", () -> new BlockEntityType<>(
                    CentComAirlockD1BlockEntity::new, Ssc14Blocks.CENT_COM_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChemistryAirlockD1BlockEntity>>
            CHEMISTRY_AIRLOCK_D_1 = REGISTRY.register("chemistry_airlock_d_1", () -> new BlockEntityType<>(
                    ChemistryAirlockD1BlockEntity::new, Ssc14Blocks.CHEMISTRY_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CommandAirlockD1BlockEntity>>
            COMMAND_AIRLOCK_D_1 = REGISTRY.register("command_airlock_d_1", () -> new BlockEntityType<>(
                    CommandAirlockD1BlockEntity::new, Ssc14Blocks.COMMAND_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EngineeringAirlockD1BlockEntity>>
            ENGINEERING_AIRLOCK_D_1 = REGISTRY.register("engineering_airlock_d_1", () -> new BlockEntityType<>(
                    EngineeringAirlockD1BlockEntity::new, Ssc14Blocks.ENGINEERING_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HydroponicsAirlockD1BlockEntity>>
            HYDROPONICS_AIRLOCK_D_1 = REGISTRY.register("hydroponics_airlock_d_1", () -> new BlockEntityType<>(
                    HydroponicsAirlockD1BlockEntity::new, Ssc14Blocks.HYDROPONICS_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MedicalAirlockD1BlockEntity>>
            MEDICAL_AIRLOCK_D_1 = REGISTRY.register("medical_airlock_d_1", () -> new BlockEntityType<>(
                    MedicalAirlockD1BlockEntity::new, Ssc14Blocks.MEDICAL_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SalvageAirlockD1BlockEntity>>
            SALVAGE_AIRLOCK_D_1 = REGISTRY.register("salvage_airlock_d_1", () -> new BlockEntityType<>(
                    SalvageAirlockD1BlockEntity::new, Ssc14Blocks.SALVAGE_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ScienceAirlockD1BlockEntity>>
            SCIENCE_AIRLOCK_D_1 = REGISTRY.register("science_airlock_d_1", () -> new BlockEntityType<>(
                    ScienceAirlockD1BlockEntity::new, Ssc14Blocks.SCIENCE_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SecurityAirlockD1BlockEntity>>
            SECURITY_AIRLOCK_D_1 = REGISTRY.register("security_airlock_d_1", () -> new BlockEntityType<>(
                    SecurityAirlockD1BlockEntity::new, Ssc14Blocks.SECURITY_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SyndicateAirlockD1BlockEntity>>
            SYNDICATE_AIRLOCK_D_1 = REGISTRY.register("syndicate_airlock_d_1", () -> new BlockEntityType<>(
                    SyndicateAirlockD1BlockEntity::new, Ssc14Blocks.SYNDICATE_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VirologyAirlockD1BlockEntity>>
            VIROLOGY_AIRLOCK_D_1 = REGISTRY.register("virology_airlock_d_1", () -> new BlockEntityType<>(
                    VirologyAirlockD1BlockEntity::new, Ssc14Blocks.VIROLOGY_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GlassAirlockD1BlockEntity>>
            GLASS_AIRLOCK_D_1 = REGISTRY.register("glass_airlock_d_1", () -> new BlockEntityType<>(
                    GlassAirlockD1BlockEntity::new, Ssc14Blocks.GLASS_AIRLOCK_D_1.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseGlassAirlockD1BlockEntity>>
            BASE_GLASS_AIRLOCK_D_1 = REGISTRY.register("base_glass_airlock_d_1", () -> new BlockEntityType<>(
                    BaseGlassAirlockD1BlockEntity::new, Ssc14Blocks.BASE_GLASS_AIRLOCK_D_1.get()));
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
        event.registerBlockEntity(Capabilities.Item.BLOCK, BASE_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, TECH_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ATMOSPHERICS_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CARGO_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CENT_COM_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, CHEMISTRY_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, COMMAND_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ENGINEERING_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, HYDROPONICS_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, MEDICAL_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, SALVAGE_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, SCIENCE_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, SECURITY_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, SYNDICATE_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, VIROLOGY_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, GLASS_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, BASE_GLASS_AIRLOCK_D_1.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG.get(),
                WorldlyContainerWrapper::new);
        event.registerBlockEntity(Capabilities.Item.BLOCK, AIRLOCK_UP_PLUG_OPEN.get(),
                WorldlyContainerWrapper::new);
    }
}
