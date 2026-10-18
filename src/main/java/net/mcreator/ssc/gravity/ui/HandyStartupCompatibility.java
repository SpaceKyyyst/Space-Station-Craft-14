package net.mcreator.ssc.gravity.ui;

import net.mcreator.ssc.Ssc14Mod;
import com.lowdragmc.lowdraglib2.gui.factory.PlayerUIMenuType;
import com.ssc14.handy.window.RootMUI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

/** Handy's overlay sync is independent of its empty login container. */
@EventBusSubscriber(modid = Ssc14Mod.MODID)
public final class HandyStartupCompatibility {
    private HandyStartupCompatibility() { }

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            String version = ModList.get().getModContainerById("handy").orElseThrow().getModInfo().getVersion().toString();
            if (!"0.1.0".equals(version)) return;
            // Handy registers login via a Consumer, so unregistering a Method does nothing.
            // Disable only the empty menu factory; overlay creation and sync stay registered.
            PlayerUIMenuType.unregister(RootMUI.START_UI);
            Ssc14Mod.LOGGER.info("Disabled Handy 0.1.0 empty login screen");
        });
    }
}
