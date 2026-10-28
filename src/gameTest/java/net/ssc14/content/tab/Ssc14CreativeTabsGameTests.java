package net.ssc14.content.tab;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.Ssc14Items;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14CreativeTabsGameTests {
    private static final Identifier TEST_ID = id("compatibility_creative_tabs");
    private static final List<Expected> EXPECTED = List.of(
            expected("ssc_14_coating", Ssc14CreativeTabs.SSC_14_COATING,
                    Ssc14Items.TITLE_STEEL),
            expected("ssc_14_walls", Ssc14CreativeTabs.SSC_14_WALLS,
                    Ssc14Items.STEEL_WALL),
            expected("ssc_14_objects", Ssc14CreativeTabs.SSC_14_OBJECTS,
                    Ssc14Items.CONSOLE_OF_ID),
            expected("ssc_14_items", Ssc14CreativeTabs.SSC_14_ITEMS,
                    Ssc14Items.GLASS),
            expected("ssc_14mobs", Ssc14CreativeTabs.SSC_14MOBS,
                    Ssc14Items.MOUSE_BROWN_SPAWN_EGG),
            expected("decals", Ssc14CreativeTabs.DECALS,
                    Ssc14Items.DECAL_1SPAWNER));

    private Ssc14CreativeTabsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14CreativeTabsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("creative_tabs_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (Expected expected : EXPECTED) {
            CreativeModeTab tab = expected.tab().get();
            helper.assertTrue(id(expected.path()).equals(
                            BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab)),
                    expected.path() + " tab ID changed");
            Item icon = tab.getIconItem().getItem();
            helper.assertTrue(icon == expected.icon().get(),
                    expected.path() + " icon changed");
        }
        helper.succeed();
    }

    private static Expected expected(String path,
            DeferredHolder<CreativeModeTab, CreativeModeTab> tab,
            DeferredItem<?> icon) {
        return new Expected(path, tab, icon);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String path,
            DeferredHolder<CreativeModeTab, CreativeModeTab> tab,
            DeferredItem<?> icon) { }
}
