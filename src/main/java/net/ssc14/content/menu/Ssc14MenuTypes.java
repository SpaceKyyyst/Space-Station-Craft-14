package net.ssc14.content.menu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.menu.crafting.DopCraftMenu;
import net.ssc14.content.menu.headset.HeadsetMenu;
import net.ssc14.content.menu.access.AccessConfigItemMenu;
import net.ssc14.content.menu.access.AccessConfigMENUMenu;
import net.ssc14.content.menu.access.IDcodeMenu;
import net.ssc14.content.menu.airlock.CabelPannelAirlockMenu;
import net.ssc14.content.menu.pda.PDApassangerGUIMenu;
import net.ssc14.content.menu.food.EggCartonGUIMenu;
import net.ssc14.content.menu.food.MicrowaweGUIMenu;
import net.ssc14.content.menu.storage.BackpackGUIMenu;
import net.ssc14.content.menu.commerce.SaleGUIMenu;
import net.ssc14.content.menu.commerce.OrderGUIMenu;
import net.ssc14.content.menu.commerce.VendingAut1GUIMenu;
import net.ssc14.content.menu.decal.DECALScolorGUIMenu;
import net.ssc14.content.menu.network.NetConfigGUIMenu;
import net.ssc14.content.menu.machinery.MacFrameGUIMenu;
import net.ssc14.content.menu.station.CommunConsoleGUIMenu;
import net.ssc14.content.menu.station.GravGenGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIwallfloorMenu;
import net.ssc14.content.menu.construction.RCDGUIwindowsMenu;

/** Owns menu type registration independently from transitional menu implementations. */
public final class Ssc14MenuTypes {
    private static final Map<String, IContainerFactory<? extends AbstractContainerMenu>>
            FACTORIES = new HashMap<>();
    private static final Set<String> REGISTERED_IDS = new HashSet<>();

    public static final DeferredRegister<MenuType<?>> REGISTRY =
            DeferredRegister.create(Registries.MENU, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<DopCraftMenu>> DOP_CRAFT_MENU = register("dop_craft_menu");
    public static final DeferredHolder<MenuType<?>, MenuType<IDcodeMenu>> I_DCODE = register("i_dcode");
    public static final DeferredHolder<MenuType<?>, MenuType<AccessConfigMENUMenu>> ACCESS_CONFIG_MENU = register("access_config_menu");
    public static final DeferredHolder<MenuType<?>, MenuType<AccessConfigItemMenu>> ACCESS_CONFIG_GUI = register("access_config_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<CabelPannelAirlockMenu>> CABEL_PANNEL_AIRLOCK = register("cabel_pannel_airlock");
    public static final DeferredHolder<MenuType<?>, MenuType<GravGenGUIMenu>> GRAV_GEN_GUI = register("grav_gen_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<MacFrameGUIMenu>> MAC_FRAME_GUI = register("mac_frame_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<PDApassangerGUIMenu>> PD_APASSANGER_GUI = register("pd_apassanger_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<RCDGUIMenu>> RCDGUI = register("rcdgui");
    public static final DeferredHolder<MenuType<?>, MenuType<RCDGUIwallfloorMenu>> RCDGU_IWALLFLOOR = register("rcdgu_iwallfloor");
    public static final DeferredHolder<MenuType<?>, MenuType<HeadsetMenu>> HEADSET_GU_IKOSTIL = register("headset_gu_ikostil");
    public static final DeferredHolder<MenuType<?>, MenuType<CommunConsoleGUIMenu>> COMMUN_CONSOLE_GUI = register("commun_console_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<VendingAut1GUIMenu>> VENDING_AUT_1_GUI = register("vending_aut_1_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<RCDGUIwindowsMenu>> RCDGU_IWINDOWS = register("rcdgu_iwindows");
    public static final DeferredHolder<MenuType<?>, MenuType<NetConfigGUIMenu>> NET_CONFIG_GUI = register("net_config_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<EggCartonGUIMenu>> EGG_CARTON_GUI = register("egg_carton_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<MicrowaweGUIMenu>> MICROWAWE_GUI = register("microwawe_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<DECALScolorGUIMenu>> DECAL_SCOLOR_GUI = register("decal_scolor_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<BackpackGUIMenu>> BACKPACK_GUI = register("backpack_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<OrderGUIMenu>> ORDER_GUI = register("order_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<SaleGUIMenu>> SALE_GUI = register("sale_gui");

    static {
        bindFactory("dop_craft_menu", DopCraftMenu::new);
        bindFactory("i_dcode", IDcodeMenu::new);
        bindFactory("access_config_menu", AccessConfigMENUMenu::new);
        bindFactory("access_config_gui", AccessConfigItemMenu::new);
        bindFactory("cabel_pannel_airlock", CabelPannelAirlockMenu::new);
        bindFactory("grav_gen_gui", GravGenGUIMenu::new);
        bindFactory("mac_frame_gui", MacFrameGUIMenu::new);
        bindFactory("pd_apassanger_gui", PDApassangerGUIMenu::new);
        bindFactory("rcdgui", RCDGUIMenu::new);
        bindFactory("rcdgu_iwallfloor", RCDGUIwallfloorMenu::new);
        bindFactory("headset_gu_ikostil", HeadsetMenu::new);
        bindFactory("commun_console_gui", CommunConsoleGUIMenu::new);
        bindFactory("vending_aut_1_gui", VendingAut1GUIMenu::new);
        bindFactory("rcdgu_iwindows", RCDGUIwindowsMenu::new);
        bindFactory("net_config_gui", NetConfigGUIMenu::new);
        bindFactory("egg_carton_gui", EggCartonGUIMenu::new);
        bindFactory("microwawe_gui", MicrowaweGUIMenu::new);
        bindFactory("decal_scolor_gui", DECALScolorGUIMenu::new);
        bindFactory("backpack_gui", BackpackGUIMenu::new);
        bindFactory("order_gui", OrderGUIMenu::new);
        bindFactory("sale_gui", SaleGUIMenu::new);
        validateFactoryBindings();
    }

    private Ssc14MenuTypes() { }

    public static <T extends AbstractContainerMenu> void bindFactory(
            String id, IContainerFactory<T> factory) {
        if (!REGISTERED_IDS.contains(id)) {
            throw new IllegalArgumentException("Unknown menu type: " + id);
        }
        if (FACTORIES.putIfAbsent(id, factory) != null) {
            throw new IllegalStateException("Menu factory already bound: " + id);
        }
    }

    public static void validateFactoryBindings() {
        if (!FACTORIES.keySet().equals(REGISTERED_IDS)) {
            Set<String> missing = new HashSet<>(REGISTERED_IDS);
            missing.removeAll(FACTORIES.keySet());
            throw new IllegalStateException("Missing menu factories: " + missing);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractContainerMenu>
            DeferredHolder<MenuType<?>, MenuType<T>> register(String id) {
        if (!REGISTERED_IDS.add(id)) {
            throw new IllegalStateException("Menu type already registered: " + id);
        }
        return (DeferredHolder<MenuType<?>, MenuType<T>>) (DeferredHolder<?, ?>) REGISTRY.register(id, () -> IMenuTypeExtension.create(
                (containerId, inventory, data) -> factory(id).create(
                        containerId, inventory, data)));
    }

    private static IContainerFactory<? extends AbstractContainerMenu> factory(String id) {
        IContainerFactory<? extends AbstractContainerMenu> factory = FACTORIES.get(id);
        if (factory == null) {
            throw new IllegalStateException("Menu factory is not bound: " + id);
        }
        return factory;
    }
}
