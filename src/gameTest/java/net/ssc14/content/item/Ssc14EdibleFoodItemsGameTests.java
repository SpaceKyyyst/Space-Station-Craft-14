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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.item.food.EdibleFoodItem;
import net.ssc14.content.item.food.FoodDefinition;
import net.ssc14.content.item.food.Ssc14FoodDefinitions;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14EdibleFoodItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_edible_food_contract");
    private static final List<ExpectedFood> FOODS = List.of(
            food(Ssc14Items.BREAD, "bread", Ssc14FoodDefinitions.BREAD),
            food(Ssc14Items.BREAD_SLICE, "bread_slice", Ssc14FoodDefinitions.BREAD_SLICE),
            food(Ssc14Items.BUN, "bun", Ssc14FoodDefinitions.BUN),
            food(Ssc14Items.TOFU, "tofu", Ssc14FoodDefinitions.TOFU),
            food(Ssc14Items.TOFU_BURGER, "tofu_burger", Ssc14FoodDefinitions.TOFU_BURGER),
            food(Ssc14Items.TOFU_SLICE, "tofu_slice", Ssc14FoodDefinitions.TOFU_SLICE),
            food(Ssc14Items.CHEESE, "cheese", Ssc14FoodDefinitions.CHEESE),
            food(Ssc14Items.CUTLET, "cutlet", Ssc14FoodDefinitions.CUTLET),
            food(Ssc14Items.FRIED_CUTLET, "fried_cutlet", Ssc14FoodDefinitions.FRIED_CUTLET),
            food(Ssc14Items.MEAT, "meat", Ssc14FoodDefinitions.MEAT),
            food(Ssc14Items.FRIED_MEAT, "fried_meat", Ssc14FoodDefinitions.FRIED_MEAT),
            food(Ssc14Items.ROTTEN_MEAT, "rotten_meat", Ssc14FoodDefinitions.ROTTEN_MEAT));

    private Ssc14EdibleFoodItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14EdibleFoodItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_edible_food_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 160, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedFood expected : FOODS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof EdibleFoodItem,
                    expected.id() + " no longer uses EdibleFoodItem");
            EdibleFoodItem food = (EdibleFoodItem) item;
            helper.assertTrue(food.definition() == expected.definition(),
                    expected.id() + " food descriptor changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == EdibleFoodItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");
        }

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack bun = new ItemStack(Ssc14Items.BUN.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, bun);
        Ssc14Items.BUN.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        int initialProgress = bun.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getInt("EatingProgress").orElse(0);
        helper.assertTrue(initialProgress == 1, "First use did not start timed eating");
        helper.assertTrue(player.getAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB).getValue() == 1.0,
                "First use did not update the progress attribute");

        var multiBitePlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack bread = new ItemStack(Ssc14Items.BREAD.get());
        multiBitePlayer.setItemInHand(InteractionHand.MAIN_HAND, bread);
        Ssc14Items.BREAD.get().use(
                helper.getLevel(), multiBitePlayer, InteractionHand.MAIN_HAND);

        var fullPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack rejectedBun = new ItemStack(Ssc14Items.BUN.get());
        fullPlayer.setItemInHand(InteractionHand.MAIN_HAND, rejectedBun);
        fullPlayer.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).setBaseValue(10.0);
        Ssc14Items.BUN.get().use(helper.getLevel(), fullPlayer, InteractionHand.MAIN_HAND);
        int rejectedProgress = rejectedBun.getOrDefault(
                DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getInt("EatingProgress").orElse(0);
        helper.assertTrue(!rejectedBun.isEmpty() && rejectedProgress == 0,
                "Fullness limit did not reject a new bite");

        helper.succeedWhen(() -> {
            var finalData = bun.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                    .copyTag();
            helper.assertTrue(bun.isEmpty(), "Single-bite food was not consumed; progress="
                    + finalData.getInt("EatingProgress").orElse(0)
                    + ", completedBites=" + finalData.getDoubleOr("Nutrients", 0.0)
                    + ", digestion="
                    + player.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).getValue());
            helper.assertTrue(player.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).getValue() == 1.0,
                    "Completed bite did not increment digestion");
            helper.assertTrue(player.getAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB).getValue() == 0.0,
                    "Completed bite did not reset progress");
            helper.assertTrue(bread.isEmpty(), "Multi-bite food was not fully consumed");
            helper.assertTrue(multiBitePlayer.getAttribute(
                            Ssc14Attributes.DIGESTIVE_PROCESSES).getValue() == 3.0,
                    "Multi-bite food did not complete every bite");
            helper.assertTrue(multiBitePlayer.getAttribute(
                            Ssc14Attributes.PROGRESS_BAR_ATRB).getValue() == 0.0,
                    "Multi-bite food did not reset progress");
        });
    }

    private static ExpectedFood food(
            DeferredItem<? extends Item> holder, String id, FoodDefinition definition) {
        return new ExpectedFood(holder, id, definition);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedFood(
            DeferredItem<? extends Item> holder, String id, FoodDefinition definition) { }
}
