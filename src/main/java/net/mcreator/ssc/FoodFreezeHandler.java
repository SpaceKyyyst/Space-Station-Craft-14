
package net.mcreator.ssc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "ssc_14")
public class FoodFreezeHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        
        if (!player.level().isClientSide()) {
            // 1. Заморозка ванильного голода
            FoodData foodData = player.getFoodData();
            if (foodData.getFoodLevel() < 20) {
                foodData.setFoodLevel(20);
                foodData.setSaturation(20.0F);
            }

            // 2. 🔥 ФИКС ОПЫТА: Если у игрока появился опыт или уровень, мгновенно обнуляем их
            if (player.experienceProgress > 0 || player.experienceLevel > 0) {
                player.experienceProgress = 0.0F;
                player.experienceLevel = 0;
                player.totalExperience = 0;
            }
        }
    }
}
