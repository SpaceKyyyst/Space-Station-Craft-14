package net.mcreator.ssc.init;

import net.ssc14.client.menu.crafting.DopCraftScreen;
import net.ssc14.client.menu.headset.HeadsetScreen;
import net.ssc14.client.menu.access.AccessConfigItemScreen;
import net.ssc14.client.menu.access.AccessConfigMENUScreen;
import net.ssc14.client.menu.access.IDcodeScreen;
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
import net.ssc14.client.menu.commerce.OrderGUIScreen;
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
import net.ssc14.content.menu.Ssc14MenuTypes;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.ssc14.integration.neoforge.menu.network.MenuStateNetworkActions;
import net.ssc14.integration.neoforge.menu.network.MenuSlotNetworkActions;

/** Compatibility bootstrap for legacy screen implementations. */
@Deprecated(forRemoval = false)
@EventBusSubscriber(Dist.CLIENT)
public final class Ssc14ModScreens {
    static {
        MenuStateNetworkActions.installClientSender(ClientPacketDistributor::sendToServer);
        MenuSlotNetworkActions.installClientSender(ClientPacketDistributor::sendToServer);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.DOP_CRAFT_MENU, DopCraftScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.I_DCODE, IDcodeScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.ACCESS_CONFIG_MENU, AccessConfigMENUScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.ACCESS_CONFIG_GUI, AccessConfigItemScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.CABEL_PANNEL_AIRLOCK, CabelPannelAirlockScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.GRAV_GEN_GUI, GravGenGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.MAC_FRAME_GUI, MacFrameGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.PD_APASSANGER_GUI, PDApassangerGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.RCDGUI, RCDGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.RCDGU_IWALLFLOOR, RCDGUIwallfloorScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.HEADSET_GU_IKOSTIL, HeadsetScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.COMMUN_CONSOLE_GUI, CommunConsoleGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.VENDING_AUT_1_GUI, VendingAut1GUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.RCDGU_IWINDOWS, RCDGUIwindowsScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.NET_CONFIG_GUI, NetConfigGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.EGG_CARTON_GUI, EggCartonGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.MICROWAWE_GUI, MicrowaweGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.DECAL_SCOLOR_GUI, DECALScolorGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.BACKPACK_GUI, BackpackGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.ORDER_GUI, OrderGUIScreen::new);
        Ssc14ScreenBinder.bind(Ssc14MenuTypes.SALE_GUI, SaleGUIScreen::new);
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
