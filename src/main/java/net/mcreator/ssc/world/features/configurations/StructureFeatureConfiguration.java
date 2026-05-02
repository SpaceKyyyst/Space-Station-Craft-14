package net.mcreator.ssc.world.features.configurations;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Vec3i;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.HolderSet;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.Codec;

public record StructureFeatureConfiguration(ResourceLocation structure, boolean randomRotation, boolean randomMirror, HolderSet<Block> ignoredBlocks, Vec3i offset) implements FeatureConfiguration {
	public static final Codec<StructureFeatureConfiguration> CODEC = RecordCodecBuilder.create(builder -> builder.group(ResourceLocation.CODEC.fieldOf("structure").forGetter(StructureFeatureConfiguration::structure),
			Codec.BOOL.fieldOf("random_rotation").orElse(false).forGetter(StructureFeatureConfiguration::randomRotation), Codec.BOOL.fieldOf("random_mirror").orElse(false).forGetter(StructureFeatureConfiguration::randomMirror),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("ignored_blocks").forGetter(StructureFeatureConfiguration::ignoredBlocks),
			Vec3i.offsetCodec(48).optionalFieldOf("offset", Vec3i.ZERO).forGetter(StructureFeatureConfiguration::offset)).apply(builder, StructureFeatureConfiguration::new));
}