package net.ssc14.integration.neoforge.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Places a configured structure template during world generation. */
public final class Ssc14StructureFeature extends Feature<Ssc14StructureFeatureConfiguration> {
    private static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, "ssc_14");

    public static final DeferredHolder<Feature<?>, Ssc14StructureFeature> STRUCTURE_FEATURE =
            FEATURES.register("structure_feature", () ->
                    new Ssc14StructureFeature(Ssc14StructureFeatureConfiguration.CODEC));

    private Ssc14StructureFeature(Codec<Ssc14StructureFeatureConfiguration> codec) {
        super(codec);
    }

    public static void register(IEventBus modEventBus) {
        FEATURES.register(modEventBus);
    }

    @Override
    public boolean place(FeaturePlaceContext<Ssc14StructureFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        Ssc14StructureFeatureConfiguration configuration = context.config();
        Rotation rotation = configuration.randomRotation()
                ? Rotation.getRandom(random) : Rotation.NONE;
        Mirror mirror = configuration.randomMirror()
                ? Mirror.values()[random.nextInt(2)] : Mirror.NONE;
        StructureTemplateManager templates = level.getLevel().getServer().getStructureManager();
        StructureTemplate template = templates.getOrCreate(configuration.structure());
        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(mirror)
                .setRandom(random)
                .setIgnoreEntities(false)
                .addProcessor(new BlockIgnoreProcessor(
                        configuration.ignoredBlocks().stream().map(Holder::value).toList()));
        BlockPos position = context.origin().offset(
                StructureTemplate.calculateRelativePosition(
                        settings, new BlockPos(configuration.offset())));
        template.placeInWorld(level, position, position, settings, random, 2);
        return true;
    }
}
