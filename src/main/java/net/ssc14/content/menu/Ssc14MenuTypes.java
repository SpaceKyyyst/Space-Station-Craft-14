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

/** Owns menu type registration independently from transitional menu implementations. */
public final class Ssc14MenuTypes {
    private static final Map<String, IContainerFactory<? extends AbstractContainerMenu>>
            FACTORIES = new HashMap<>();
    private static final Set<String> REGISTERED_IDS = new HashSet<>();

    public static final DeferredRegister<MenuType<?>> REGISTRY =
            DeferredRegister.create(Registries.MENU, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> DOP_CRAFT_MENU = register("dop_craft_menu");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> I_DCODE = register("i_dcode");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> ACCESS_CONFIG_MENU = register("access_config_menu");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> ACCESS_CONFIG_GUI = register("access_config_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> CABEL_PANNEL_AIRLOCK = register("cabel_pannel_airlock");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> GRAV_GEN_GUI = register("grav_gen_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> MAC_FRAME_GUI = register("mac_frame_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> PD_APASSANGER_GUI = register("pd_apassanger_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> RCDGUI = register("rcdgui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> RCDGU_IWALLFLOOR = register("rcdgu_iwallfloor");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> HEADSET_GU_IKOSTIL = register("headset_gu_ikostil");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> COMMUN_CONSOLE_GUI = register("commun_console_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> VENDING_AUT_1_GUI = register("vending_aut_1_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> RCDGU_IWINDOWS = register("rcdgu_iwindows");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> NET_CONFIG_GUI = register("net_config_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> EGG_CARTON_GUI = register("egg_carton_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> MICROWAWE_GUI = register("microwawe_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> DECAL_SCOLOR_GUI = register("decal_scolor_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> BACKPACK_GUI = register("backpack_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> ORDER_GUI = register("order_gui");
    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> SALE_GUI = register("sale_gui");

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

    private static DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>>
            register(String id) {
        if (!REGISTERED_IDS.add(id)) {
            throw new IllegalStateException("Menu type already registered: " + id);
        }
        return REGISTRY.register(id, () -> IMenuTypeExtension.create(
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
