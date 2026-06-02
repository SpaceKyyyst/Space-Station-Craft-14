package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class DEBUG13PriShchielchkiePKMPoBlokuProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.xylophone")), SoundSource.BLOCKS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.note_block.xylophone")), SoundSource.BLOCKS, 1, 1, false);
			}
		}
		// <<<
		// <<<
		if (entity.isShiftKeyDown()) {
			Ssc14ModVariables.MapVariables.get(world).station_code = 0;
			Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
		} else {
			Ssc14ModVariables.MapVariables.get(world).station_code = Ssc14ModVariables.MapVariables.get(world).station_code + 1;
			Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}