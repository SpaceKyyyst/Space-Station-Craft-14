package net.ssc14.content.block.geology;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.common.util.DeferredSoundType;

abstract class AsteroidStoneBlock extends Block {
    AsteroidStoneBlock(BlockBehaviour.Properties properties, float destroyTime, float explosionResistance) {
        super(properties.sound(new DeferredSoundType(
                        1.0f,
                        1.0f,
                        () -> sound("ssc_14:break_stone"),
                        () -> sound("block.stone.step"),
                        () -> sound("block.stone.place"),
                        () -> sound("block.stone.hit"),
                        () -> sound("block.stone.fall")))
                .strength(destroyTime, explosionResistance));
    }

    private static net.minecraft.sounds.SoundEvent sound(String id) {
        return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse(id));
    }
}
