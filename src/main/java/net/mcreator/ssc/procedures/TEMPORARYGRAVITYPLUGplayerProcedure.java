
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player; // 🔹 Добавлен импорт
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB; // 🔹 Добавлен импорт

import javax.annotation.Nullable;

@EventBusSubscriber
public class TEMPORARYGRAVITYPLUGplayerProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;
        
        // 🔹 Проверяем, что это игрок, и приводим тип
        if (!(entity instanceof Player player))
            return;

        if (entity.isNoGravity()) {
            // 1. Проверка: находится ли игрок вплотную к блоку
            // 🔹 ИСПРАВЛЕНО: добавлено определение box через getBoundingBox()
            AABB box = entity.getBoundingBox().inflate(0.5);
            
            boolean touchingBlock = BlockPos.betweenClosedStream(
                Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
                Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ)
            ).anyMatch(p -> !player.level().getBlockState(p).isAir());
            
            // 🔹 ИСПРАВЛЕНО: player вместо player (которого не было)
            Vec3 motion = player.getDeltaMovement();
            double newY = motion.y;
            
            // 2. Логика ВВЕРХ/ВНИЗ (работает ТОЛЬКО если touchingBlock == true)
            if (touchingBlock) {
                double speed = 0.15; // Скорость подъёма/спуска
                
                // ⚠️ ВНИМАНИЕ: event.getInput() НЕ работает с PlayerTickEvent!
                // В оригинале использовался MovementInputUpdateEvent.
                // Здесь используем заглушку через методы Player:
                if (player.isJumping()) { // Нажат прыжок (приблизительная проверка)
                    newY = speed; // → Движение ВВЕРХ
                } else if (player.isShiftKeyDown()) { // Нажат присед
                    newY = -speed; // → Движение ВНИЗ
                } else {
                    newY *= 0.9; // → Плавное торможение, если кнопки не нажаты
                }
            }
            // 3. Применение результата к игроку
            // 🔹 ИСПРАВЛЕНО: player вместо player
            player.setDeltaMovement(motion.x, newY, motion.z);
        }
    }
}
