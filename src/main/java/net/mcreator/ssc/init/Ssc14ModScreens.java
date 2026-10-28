package net.mcreator.ssc.init;

import net.mcreator.ssc.client.gui.*;
import net.ssc14.client.menu.crafting.DopCraftScreen;
import net.ssc14.client.menu.headset.HeadsetScreen;
import net.ssc14.client.menu.access.AccessConfigItemScreen;
import net.ssc14.client.menu.access.AccessConfigMENUScreen;
import net.ssc14.client.menu.airlock.CabelPannelAirlockScreen;
import net.ssc14.client.menu.pda.PDApassangerGUIScreen;
import net.ssc14.client.menu.food.EggCartonGUIScreen;
import net.ssc14.client.menu.food.MicrowaweGUIScreen;
import net.ssc14.client.menu.storage.BackpackGUIScreen;
import net.ssc14.client.menu.construction.RCDGUIScreen;
import net.ssc14.client.menu.construction.RCDGUIwallfloorScreen;
import net.ssc14.client.menu.construction.RCDGUIwindowsScreen;
import net.ssc14.client.menu.station.GravGenGUIScreen;
import net.ssc14.client.menu.station.CommunConsoleGUIScreen;
import net.ssc14.client.menu.commerce.SaleGUIScreen;
import net.ssc14.client.menu.commerce.VendingAut1GUIScreen;
import net.ssc14.client.menu.decal.DECALScolorGUIScreen;
import net.ssc14.client.menu.network.NetConfigGUIScreen;
import net.ssc14.client.menu.machinery.MacFrameGUIScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.ssc14.client.menu.MenuStateReceiver;
import net.ssc14.client.menu.Ssc14ScreenBinder;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.ssc14.integration.neoforge.menu.network.MenuStateNetworkActions;

/** Compatibility bootstrap for legacy screen implementations. */
@Deprecated(forRemoval = false)
@EventBusSubscriber(Dist.CLIENT)
public final class Ssc14ModScreens {
    static {
        MenuStateNetworkActions.installClientSender(ClientPacketDistributor::sendToServer);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.DOP_CRAFT_MENU, DopCraftScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.I_DCODE, IDcodeScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.ACCESS_CONFIG_MENU, AccessConfigMENUScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.ACCESS_CONFIG_GUI, AccessConfigItemScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.CABEL_PANNEL_AIRLOCK, CabelPannelAirlockScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.GRAV_GEN_GUI, GravGenGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.MAC_FRAME_GUI, MacFrameGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.PD_APASSANGER_GUI, PDApassangerGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.RCDGUI, RCDGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.RCDGU_IWALLFLOOR, RCDGUIwallfloorScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.HEADSET_GU_IKOSTIL, HeadsetScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.COMMUN_CONSOLE_GUI, CommunConsoleGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.VENDING_AUT_1_GUI, VendingAut1GUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.RCDGU_IWINDOWS, RCDGUIwindowsScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.NET_CONFIG_GUI, NetConfigGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.EGG_CARTON_GUI, EggCartonGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.MICROWAWE_GUI, MicrowaweGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.DECAL_SCOLOR_GUI, DECALScolorGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.BACKPACK_GUI, BackpackGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.ORDER_GUI, OrderGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14ModMenus.SALE_GUI, SaleGUIScreen::new);
    }

    private Ssc14ModScreens() { }

    @SubscribeEvent
    public static void clientLoad(RegisterMenuScreensEvent event) {
        Ssc14ScreenBinder.registerScreens(event);
    }

    /** @deprecated implement {@link MenuStateReceiver} directly. */
    @Deprecated(forRemoval = false)
    public interface ScreenAccessor extends MenuStateReceiver { }
}
