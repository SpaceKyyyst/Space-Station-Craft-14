package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

public class DEBUG13PriShchielchkiePKMPoBlokuProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.xylophone")), SoundSource.BLOCKS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.xylophone")), SoundSource.BLOCKS, 1, 1, false);
			}
		}
		// <<<
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
			if (world instanceof Level _level)
				_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
		}
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles_up")))) {
			if (world instanceof Level _level)
				_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
		}
		// <<<
	}
}