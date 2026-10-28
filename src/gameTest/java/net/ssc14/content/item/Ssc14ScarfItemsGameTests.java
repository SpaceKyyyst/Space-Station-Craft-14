package net.ssc14.content.item;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.clothing.ScarfItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14ScarfItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_scarf_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.RED_SCARF, Ssc14Items.BLACK_SCARF, Ssc14Items.BLACK_GREEN_SCARF,
            Ssc14Items.BLUE_SCARF, Ssc14Items.BROWN_SCARF, Ssc14Items.GREEN_SCARF,
            Ssc14Items.LIGHT_BLUE_SCARF, Ssc14Items.LIGHT_BLUE_WHITE_SCARF,
            Ssc14Items.ORANGE_SCARF, Ssc14Items.PURPLE_SCARF, Ssc14Items.SINDY_SCARF,
            Ssc14Items.TRNS_SCARF, Ssc14Items.YELLOW_SCARF);
    private static final List<String> IDS = List.of(
            "red_scarf", "black_scarf", "black_green_scarf", "blue_scarf", "brown_scarf",
            "green_scarf", "light_blue_scarf", "light_blue_white_scarf", "orange_scarf",
            "purple_scarf", "sindy_scarf", "trns_scarf", "yellow_scarf");

    private Ssc14ScarfItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14ScarfItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_scarf_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (int index = 0; index < ITEMS.size(); index++) {
            Item item = ITEMS.get(index).get();
            String expectedId = IDS.get(index);
            helper.assertTrue(id(expectedId).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expectedId + " item ID changed");
            helper.assertTrue(item instanceof ScarfItem, expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                    expectedId + " stack size changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
