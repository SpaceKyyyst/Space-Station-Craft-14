
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

@EventBusSubscriber(modid = "ssc14")
public class TICBodiesRottingProcedure {

    private static final int DECAY_INTERVAL = 100;
    private static final int BLEED_INTERVAL = 40;
    private static final String KEY_STATE = "ssc14_state";
    private static final String KEY_TOTAL = "ssc14_damage";
    private static final String KEY_BLEEDING = "ssc14_bleeding";
    private static final String KEY_DECAY_STARTED = "ssc14_decayStarted";
    private static final String KEY_LAST_DECAY = "ssc14_lastDecayTick";
    private static final String KEY_LAST_BLEED = "ssc14_lastBleedTick";
    private static final String KEY_GIBBED = "ssc14_gibbed";
    private static final String PREFIX = "ssc14_dmg_";

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        execute(event);
    }

    public static void execute() { execute(null); }

    private static void execute(@Nullable Event event) {
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;
        
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player == null) continue;
            var nbt = player.getPersistentData();
            
            processDecay(player, nbt);
            processBleeding(player, nbt);
        }
    }
    
    private static void processDecay(ServerPlayer player, net.minecraft.nbt.CompoundTag nbt) {
        if (nbt.getInt(KEY_STATE).orElse(0) != 2) return;
        
        if (!nbt.getBoolean(KEY_DECAY_STARTED).orElse(false)) {
            nbt.putLong(KEY_LAST_DECAY, player.level().getGameTime());
            nbt.putBoolean(KEY_DECAY_STARTED, true);
            System.out.println("[SSC14] 🧟 Гниение началось: " + player.getName().getString());
            return;
        }
        
        long now = player.level().getGameTime();
        long last = nbt.getLong(KEY_LAST_DECAY).orElse(0L);
        
        if (now - last >= DECAY_INTERVAL) {
            double blunt = nbt.getDouble(PREFIX + "blunt").orElse(0.0) + 0.06;
            double cellular = nbt.getDouble(PREFIX + "cellular").orElse(0.0) + 0.06;
            double total = nbt.getDouble(KEY_TOTAL).orElse(0.0) + 0.12;
            
            nbt.putDouble(PREFIX + "blunt", blunt);
            nbt.putDouble(PREFIX + "cellular", cellular);
            nbt.putDouble(KEY_TOTAL, total);
            nbt.putLong(KEY_LAST_DECAY, now);
            
            System.out.println("[SSC14] 🧟 Гниение: +0.06 blunt +0.06 cellular | " + player.getName().getString());
            
            if (cellular > 200 && !nbt.getBoolean(KEY_GIBBED).orElse(false)) {
                nbt.putBoolean(KEY_GIBBED, true);
                System.out.println("[SSC14] 💀 Тело " + player.getName().getString() + " разложилось");
            }
        }
    }
    
    private static void processBleeding(ServerPlayer player, net.minecraft.nbt.CompoundTag nbt) {
        if (nbt.getInt(KEY_STATE).orElse(0) >= 2) return;
        if (!nbt.getBoolean(KEY_BLEEDING).orElse(false)) return;
        
        long now = player.level().getGameTime();
        long last = nbt.getLong(KEY_LAST_BLEED).orElse(0L);
        
        if (now - last >= BLEED_INTERVAL) {
            double bloodloss = nbt.getDouble(PREFIX + "bloodloss").orElse(0.0) + 1.0;
            double total = nbt.getDouble(KEY_TOTAL).orElse(0.0) + 1.0;
            
            nbt.putDouble(PREFIX + "bloodloss", bloodloss);
            nbt.putDouble(KEY_TOTAL, total);
            nbt.putLong(KEY_LAST_BLEED, now);
            
            System.out.println("[SSC14] 🩸 Кровотечение тик: +1 | " + player.getName().getString());
        }
    }
}
