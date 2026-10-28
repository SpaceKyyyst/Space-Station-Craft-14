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
import net.ssc14.content.item.food.FoodPackagingWasteItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14FoodPackagingWasteItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_food_packaging_waste_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.ENERGY_BAR_TRASH, Ssc14Items.CHEESE_HONKERS_TRASH,
            Ssc14Items.CHIPS_TRASH, Ssc14Items.BORITOS_TRASH, Ssc14Items.POPCORN_TRASH);
    private static final List<String> IDS = List.of(
            "energy_bar_trash", "cheese_honkers_trash", "chips_trash", "boritos_trash",
            "popcorn_trash");

    private Ssc14FoodPackagingWasteItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14FoodPackagingWasteItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_food_packaging_waste_environment"));
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
            helper.assertTrue(item instanceof FoodPackagingWasteItem,
                    expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                    expectedId + " stack size changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
