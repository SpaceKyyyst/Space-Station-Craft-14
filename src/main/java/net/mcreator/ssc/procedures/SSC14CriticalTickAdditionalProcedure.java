package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class SSC14CriticalTickAdditionalProcedure {
    
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null) return;
        
        if (!(entity instanceof Player player)) return;
        
        var nbt = player.getPersistentData();
        double totalDamage = nbt.getDouble("sscCustomHealth"); // убрали orElse
        
        if (totalDamage >= 100.0) {
            player.getAbilities().mayBuild = false;
            player.onUpdateAbilities();
        } else {
            player.getAbilities().mayBuild = true;
            player.onUpdateAbilities();
        }
    }
}