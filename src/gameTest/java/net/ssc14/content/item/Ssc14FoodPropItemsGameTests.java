package net.ssc14.content.item;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.item.food.FoodPropItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14FoodPropItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_food_prop_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.BAGEL, "bagel"),
            item(Ssc14Items.BAGUETTE, "baguette"),
            item(Ssc14Items.BANANA_NUT_BREAD, "banana_nut_bread"),
            item(Ssc14Items.CHEESE_WHEEL, "cheese_wheel"),
            item(Ssc14Items.CREAM_CHEESE_BREAD, "cream_cheese_bread"),
            item(Ssc14Items.FLY_AMANITA, "fly_amanita"),
            item(Ssc14Items.MEATLOAF, "meatloaf"),
            item(Ssc14Items.MOLDY_BREAD_PIECE, "moldy_bread_piece"),
            item(Ssc14Items.POPPY_SEED_BAGEL, "poppy_seed_bagel"),
            item(Ssc14Items.POTATO, "potato"),
            item(Ssc14Items.SAUSAGE_BREAD, "sausage_bread"),
            item(Ssc14Items.SPACE_FRIES, "space_fries"),
            item(Ssc14Items.TACO_FLATBREAD, "taco_flatbread"),
            item(Ssc14Items.TOAST_WITH_BUTTER, "toast_with_butter"),
            item(Ssc14Items.TOFU_BREAD, "tofu_bread"));

    private Ssc14FoodPropItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14FoodPropItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_food_prop_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        double initialDigestion =
                player.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).getValue();
        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof FoodPropItem,
                    expected.id() + " no longer uses FoodPropItem");
            helper.assertTrue(item.getDefaultMaxStackSize() == FoodPropItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");

            ItemStack stack = new ItemStack(item);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            item.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertTrue(stack.getCount() == 1 && !stack.has(DataComponents.CUSTOM_DATA),
                    expected.id() + " unexpectedly changed item state on use");
        }
        helper.assertTrue(
                player.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).getValue()
                        == initialDigestion,
                "Food props unexpectedly changed digestion");
        helper.assertTrue(player.getAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB).getValue() == 0.0,
                "Food props unexpectedly started a consumption progress bar");
        helper.succeed();
    }

    private static ExpectedItem item(DeferredItem<? extends Item> holder, String id) {
        return new ExpectedItem(holder, id);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(DeferredItem<? extends Item> holder, String id) { }
}
