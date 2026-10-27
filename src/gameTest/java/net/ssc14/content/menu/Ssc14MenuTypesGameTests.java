package net.ssc14.content.menu;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MenuTypesGameTests {
    private static final Identifier TEST_ID = id("compatibility_menu_types");
    private static final List<Expected> EXPECTED = List.of(
            expected("dop_craft_menu", Ssc14MenuTypes.DOP_CRAFT_MENU),
            expected("i_dcode", Ssc14MenuTypes.I_DCODE),
            expected("access_config_menu", Ssc14MenuTypes.ACCESS_CONFIG_MENU),
            expected("access_config_gui", Ssc14MenuTypes.ACCESS_CONFIG_GUI),
            expected("cabel_pannel_airlock", Ssc14MenuTypes.CABEL_PANNEL_AIRLOCK),
            expected("grav_gen_gui", Ssc14MenuTypes.GRAV_GEN_GUI),
            expected("mac_frame_gui", Ssc14MenuTypes.MAC_FRAME_GUI),
            expected("pd_apassanger_gui", Ssc14MenuTypes.PD_APASSANGER_GUI),
            expected("rcdgui", Ssc14MenuTypes.RCDGUI),
            expected("rcdgu_iwallfloor", Ssc14MenuTypes.RCDGU_IWALLFLOOR),
            expected("headset_gu_ikostil", Ssc14MenuTypes.HEADSET_GU_IKOSTIL),
            expected("commun_console_gui", Ssc14MenuTypes.COMMUN_CONSOLE_GUI),
            expected("vending_aut_1_gui", Ssc14MenuTypes.VENDING_AUT_1_GUI),
            expected("rcdgu_iwindows", Ssc14MenuTypes.RCDGU_IWINDOWS),
            expected("net_config_gui", Ssc14MenuTypes.NET_CONFIG_GUI),
            expected("egg_carton_gui", Ssc14MenuTypes.EGG_CARTON_GUI),
            expected("microwawe_gui", Ssc14MenuTypes.MICROWAWE_GUI),
            expected("decal_scolor_gui", Ssc14MenuTypes.DECAL_SCOLOR_GUI),
            expected("backpack_gui", Ssc14MenuTypes.BACKPACK_GUI),
            expected("order_gui", Ssc14MenuTypes.ORDER_GUI),
            expected("sale_gui", Ssc14MenuTypes.SALE_GUI));

    private Ssc14MenuTypesGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MenuTypesGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("menu_types_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(EXPECTED.size() == 21, "menu type count changed");
        for (Expected expected : EXPECTED) {
            MenuType<?> menuType = expected.holder().get();
            helper.assertTrue(id(expected.path()).equals(
                            BuiltInRegistries.MENU.getKey(menuType)),
                    expected.path() + " menu type ID changed");
        }
        helper.succeed();
    }

    private static Expected expected(String path,
            DeferredHolder<MenuType<?>, ? extends MenuType<?>> holder) {
        return new Expected(path, holder);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String path,
            DeferredHolder<MenuType<?>, ? extends MenuType<?>> holder) { }
}
