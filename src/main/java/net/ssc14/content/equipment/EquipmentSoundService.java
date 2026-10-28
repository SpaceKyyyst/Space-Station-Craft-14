package net.ssc14.content.equipment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Shared equip sound playback for Curios equipment hooks. */
public final class EquipmentSoundService {
    private EquipmentSoundService() { }

    public static void playGloves(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level)) return;
        var sound = BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:equip_gloves"));
        BlockPos pos = BlockPos.containing(x, y, z);
        if (level.isClientSide()) level.playLocalSound(x, y, z, sound, SoundSource.PLAYERS, 1, 1, false);
        else level.playSound(null, pos, sound, SoundSource.PLAYERS, 1, 1);
    }

    public static void playHelmet(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level)) return;
        var sound = BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:equip_helmets"));
        BlockPos pos = BlockPos.containing(x, y, z);
        if (level.isClientSide()) level.playLocalSound(x, y, z, sound, SoundSource.PLAYERS, 1, 1, false);
        else level.playSound(null, pos, sound, SoundSource.PLAYERS, 1, 1);
    }

    public static void playBase(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level)) return;
        var sound = BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:equip_base_equip"));
        BlockPos pos = BlockPos.containing(x, y, z);
        if (level.isClientSide()) level.playLocalSound(x, y, z, sound, SoundSource.PLAYERS, 1, 1, false);
        else level.playSound(null, pos, sound, SoundSource.PLAYERS, 1, 1);
    }
}
