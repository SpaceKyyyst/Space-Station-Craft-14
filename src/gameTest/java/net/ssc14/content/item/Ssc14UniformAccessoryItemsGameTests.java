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
import net.ssc14.content.item.clothing.UniformAccessoryItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14UniformAccessoryItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_uniform_accessory_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.TIE_BLACK, Ssc14Items.TIE_RED, Ssc14Items.TIE_SCIENCE,
            Ssc14Items.STOCKINGS_BLUE, Ssc14Items.STOCKINGS_CYAN,
            Ssc14Items.STOCKINGS_D_PINK, Ssc14Items.STOCKINGS_GREEN,
            Ssc14Items.STOCKINGS_L_PINK, Ssc14Items.STOCKINGS_ORANGE,
            Ssc14Items.STOCKINGS_PURPLE, Ssc14Items.STOCKINGS_YELLOW);
    private static final List<String> IDS = List.of(
            "tie_black", "tie_red", "tie_science", "stockings_blue", "stockings_cyan",
            "stockings_d_pink", "stockings_green", "stockings_l_pink", "stockings_orange",
            "stockings_purple", "stockings_yellow");

    private Ssc14UniformAccessoryItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14UniformAccessoryItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_uniform_accessory_environment"));
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
            helper.assertTrue(item instanceof UniformAccessoryItem,
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
