package net.ssc14.integration.neoforge.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/** Codec-compatible configuration for placing a structure template as a feature. */
public record Ssc14StructureFeatureConfiguration(
        Identifier structure,
        boolean randomRotation,
        boolean randomMirror,
        HolderSet<Block> ignoredBlocks,
        Vec3i offset) implements FeatureConfiguration {
    public static final Codec<Ssc14StructureFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Identifier.CODEC.fieldOf("structure")
                            .forGetter(Ssc14StructureFeatureConfiguration::structure),
                    Codec.BOOL.fieldOf("random_rotation").orElse(false)
                            .forGetter(Ssc14StructureFeatureConfiguration::randomRotation),
                    Codec.BOOL.fieldOf("random_mirror").orElse(false)
                            .forGetter(Ssc14StructureFeatureConfiguration::randomMirror),
                    RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("ignored_blocks")
                            .forGetter(Ssc14StructureFeatureConfiguration::ignoredBlocks),
                    Vec3i.offsetCodec(48).optionalFieldOf("offset", Vec3i.ZERO)
                            .forGetter(Ssc14StructureFeatureConfiguration::offset))
                    .apply(instance, Ssc14StructureFeatureConfiguration::new));
}
