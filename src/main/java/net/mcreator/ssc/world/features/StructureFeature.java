package net.mcreator.ssc.world.features;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.world.features.configurations.StructureFeatureConfiguration;
import net.mcreator.ssc.Ssc14Mod;

import com.mojang.serialization.Codec;

public class StructureFeature extends Feature<StructureFeatureConfiguration> {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registries.FEATURE, Ssc14Mod.MODID);
	public static final DeferredHolder<Feature<?>, StructureFeature> STRUCTURE_FEATURE = REGISTRY.register("structure_feature", () -> new StructureFeature(StructureFeatureConfiguration.CODEC));

	public StructureFeature(Codec<StructureFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<StructureFeatureConfiguration> context) {
		RandomSource random = context.random();
		WorldGenLevel worldGenLevel = context.level();
		StructureFeatureConfiguration config = context.config();
		Rotation rotation = config.randomRotation() ? Rotation.getRandom(random) : Rotation.NONE;
		Mirror mirror = config.randomMirror() ? Mirror.values()[random.nextInt(2)] : Mirror.NONE;
		// Load the structure template
		StructureTemplateManager structureManager = worldGenLevel.getLevel().getServer().getStructureManager();
		StructureTemplate template = structureManager.getOrCreate(config.structure());
		StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setRotation(rotation).setMirror(mirror).setRandom(random).setIgnoreEntities(false)
				.addProcessor(new BlockIgnoreProcessor(config.ignoredBlocks().stream().map(Holder::value).toList()));
		BlockPos placePos = context.origin().offset(StructureTemplate.calculateRelativePosition(placeSettings, new BlockPos(config.offset())));
		template.placeInWorld(worldGenLevel, placePos, placePos, placeSettings, random, 2);
		return true;
	}
}