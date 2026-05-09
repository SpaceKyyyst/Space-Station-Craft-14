
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.init.Ssc14ModAttributes;

@EventBusSubscriber(modid = "ssc14")
public class TICBodiesRottingProcedure {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        
        CompoundTag nbt = player.getPersistentData();
        if (nbt.getBoolean("ssc14_dead").orElse(false)) return; // Игнорируем мёртвых

        double total = nbt.getDouble("sscCustomHealth").orElse(0.0);

        // === 🩹 РЕГЕНЕРАЦИЯ (< 20 урона) ===
        if (total > 0 && total < 20.0) {
            int timer = nbt.getInt("ssc14_regenTimer").orElse(0);
            if (timer >= 20) { // Каждую секунду
                double blunt = nbt.getDouble("ssc14_dmg_blunt").orElse(0.0);
                double heat = nbt.getDouble("ssc14_dmg_heat").orElse(0.0);
                
                double healBlunt = Math.min(blunt, 0.02);
                double healHeat = Math.min(heat, 0.07);
                
                if (healBlunt > 0 || healHeat > 0) {
                    nbt.putDouble("ssc14_dmg_blunt", blunt - healBlunt);
                    nbt.putDouble("ssc14_dmg_heat", heat - healHeat);
                    double newTotal = Math.max(0, total - (healBlunt + healHeat));
                    nbt.putDouble("sscCustomHealth", newTotal);
                    updateHealthUI(player, newTotal);
                }
                nbt.putInt("ssc14_regenTimer", 0);
            } else {
                nbt.putInt("ssc14_regenTimer", timer + 1);
            }
        } else {
            nbt.putInt("ssc14_regenTimer", 0);
        }

        // === 🩸 КРОВОТЕЧЕНИЕ (каждые 2 сек +1 bloodloss) ===
        if (nbt.getBoolean("ssc14_bleeding").orElse(false)) {
            int bleedTimer = nbt.getInt("ssc14_bleedTick").orElse(0);
            if (bleedTimer >= 40) {
                double bloodloss = nbt.getDouble("ssc14_dmg_bloodloss").orElse(0.0);
                nbt.putDouble("ssc14_dmg_bloodloss", bloodloss + 1.0);
                double newTotal = nbt.getDouble("sscCustomHealth").orElse(0.0) + 1.0;
                nbt.putDouble("sscCustomHealth", newTotal);
                updateHealthUI(player, newTotal);
                nbt.putInt("ssc14_bleedTick", 0);
            } else {
                nbt.putInt("ssc14_bleedTick", bleedTimer + 1);
            }
        }
    }

    private static void updateHealthUI(Player player, double totalDamage) {
        var attr = player.getAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE);
        if (attr != null) {
            int uiState = totalDamage <= 12 ? 0 : totalDamage <= 37 ? 1 : totalDamage <= 62 ? 2 : 
                          totalDamage <= 87 ? 3 : totalDamage <= 100 ? 4 : totalDamage <= 200 ? 5 : 6;
            attr.setBaseValue(uiState);
        }
    }
}
