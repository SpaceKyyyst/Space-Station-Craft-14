
package net.mcreator.ssc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "ssc_14")
public class PopupTextServerManager {

    private static class ActivePopup {
        final Player targetPlayer;
        final AreaEffectCloud textCloud;
        final String rawText;
        final int baseColor;
        final boolean isItalic;
        
        int ageTicks = 0;
        double stackYOffset = 0.0;

        ActivePopup(Player player, AreaEffectCloud cloud, String text, int color, boolean italic) {
            this.targetPlayer = player;
            this.textCloud = cloud;
            this.rawText = text;
            this.baseColor = color;
            this.isItalic = italic;
        }
    }

    private static final List<ActivePopup> POPUP_TRACKER = new ArrayList<>();

    public static void addPopup(Player player, String text, int color, boolean isItalic) {
        if (player == null) return;
        if (player.level().isClientSide()) return;

        if (player.level() instanceof ServerLevel serverLevel) {
            // ОЧЕРЕДЬ: Сдвигаем старые сообщения игрока вверх
            synchronized (POPUP_TRACKER) {
                for (int i = 0; POPUP_TRACKER.size() > i; i++) {
                    ActivePopup oldPopup = POPUP_TRACKER.get(i);
                    if (oldPopup.targetPlayer == player && oldPopup.textCloud.isAlive()) {
                        oldPopup.stackYOffset = oldPopup.stackYOffset + 0.5;
                    }
                }
            }

            AreaEffectCloud cloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD, serverLevel);
            
            float playerHeight = player.getBbHeight();
            double extraHeight = playerHeight / 3.0;
            double finalYOffset = playerHeight + extraHeight - 1.0 - 0.2;
            
            cloud.setPos(player.getX(), player.getY() + finalYOffset, player.getZ());
            
            cloud.setRadius(0.0F);
            cloud.setRadiusOnUse(0.0F);
            cloud.setRadiusPerTick(0.0F);
            cloud.setWaitTime(0);
            cloud.setCustomParticle(net.minecraft.core.particles.ParticleTypes.POOF);
            
            Style startStyle = Style.EMPTY.withColor(TextColor.fromRgb(0x000000)).withItalic(isItalic);
            cloud.setCustomName(Component.literal(text).withStyle(startStyle));
            cloud.setCustomNameVisible(true);
            
            cloud.setDuration(150);
            
            serverLevel.addFreshEntity(cloud);

            synchronized (POPUP_TRACKER) {
                POPUP_TRACKER.add(new ActivePopup(player, cloud, text, color, isItalic));
            }

            // ЗВУКОВАЯ СИСТЕМА SS14: Безопасно раскрываем Optional и Holder для SoundEvent
            BlockPos pos = BlockPos.containing(player.getX(), player.getY() + playerHeight, player.getZ());
            RandomSource rand = RandomSource.create();

            int randomSoundNumber = rand.nextInt(4) + 1;
            var soundKey = Identifier.parse("ssc_14:voice_talk_speak_" + randomSoundNumber);
            
            // ИСПРАВЛЕНО: Правильно вытаскиваем значение из ванильного Optional и Holder контейнера
            SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(soundKey)
                .map(holder -> holder.value())
                .orElse(null);
            
            if (soundEvent != null) {
                double baseVolume = Mth.nextDouble(rand, 0.9, 1.1);
                
                if (isItalic) {
                    baseVolume = baseVolume / 2.0;
                }
                
                float finalVolume = (float) baseVolume;
                float pitch = (float) Mth.nextDouble(rand, 0.9, 1.1);
                
                serverLevel.playSound(null, pos, soundEvent, SoundSource.PLAYERS, finalVolume, pitch);
            }
        }
    }
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        synchronized (POPUP_TRACKER) {
            if (POPUP_TRACKER.isEmpty()) return;

            List<ActivePopup> toRemove = new ArrayList<>();

            for (int i = 0; POPUP_TRACKER.size() > i; i++) {
                ActivePopup popup = POPUP_TRACKER.get(i);
                
                if (!popup.targetPlayer.isAlive() || !popup.textCloud.isAlive() || popup.ageTicks > 120) {
                    if (popup.textCloud.isAlive()) {
                        popup.textCloud.discard();
                    }
                    toRemove.add(popup);
                    continue;
                }

                float currentHeight = popup.targetPlayer.getBbHeight();
                double currentOffset = currentHeight + (currentHeight / 3.0) - 1.0;

                double animYOffset = 0.0;
                float alphaFactor = 1.0F;

                if (10 > popup.ageTicks) {
                    float progress = (float) popup.ageTicks / 10.0F;
                    animYOffset = -0.2 * (1.0F - progress);
                    alphaFactor = progress;
                } else if (popup.ageTicks > 90) {
                    float progress = (float) (popup.ageTicks - 90) / 30.0F;
                    alphaFactor = 1.0F - progress;
                }

                int r = (popup.baseColor >> 16) & 0xFF;
                int g = (popup.baseColor >> 8) & 0xFF;
                int b = popup.baseColor & 0xFF;
                
                int animatedR = (int) (r * alphaFactor);
                int animatedG = (int) (g * alphaFactor);
                int animatedB = (int) (b * alphaFactor);
                int finalColor = (animatedR << 16) | (animatedG << 8) | animatedB;

                Style liveStyle = Style.EMPTY.withColor(TextColor.fromRgb(finalColor)).withItalic(popup.isItalic);
                popup.textCloud.setCustomName(Component.literal(popup.rawText).withStyle(liveStyle));

                popup.textCloud.setPos(
                    popup.targetPlayer.getX(), 
                    popup.targetPlayer.getY() + currentOffset + animYOffset + popup.stackYOffset, 
                    popup.targetPlayer.getZ()
                );

                popup.ageTicks = popup.ageTicks + 1;
            }

            POPUP_TRACKER.removeAll(toRemove);
        }
    }
}
