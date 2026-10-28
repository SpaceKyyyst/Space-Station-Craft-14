package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.ssc14.content.particle.Ssc14ParticleTypes;

/** Feedback runs only for active work, at two updates per second. */
public final class AirlockWorkFeedback {
    private static final int INTERVAL_TICKS = 10;
    private AirlockWorkFeedback() { }
    public static void progress(net.minecraft.world.entity.player.Player player, BlockPos pos, long remaining, boolean welding) {
        if (!(player instanceof ServerPlayer serverPlayer) || player.level().getGameTime() % INTERVAL_TICKS != 0) return;
        serverPlayer.sendSystemMessage(Component.translatable("message.ssc_14.airlock_work", Math.max(0, (remaining + 19) / 20)), true);
        if (!welding) return;
        var level = serverPlayer.level();
        level.sendParticles(Ssc14ParticleTypes.SPARK.get(),
                pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 3, .15, .3, .15, .02);
        BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.parse("ssc_14:welding_work"))
                .ifPresent(sound -> level.playSound(null, pos, sound, SoundSource.BLOCKS, .2f, 1));
    }
}
