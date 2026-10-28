package net.ssc14.content.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.food.EdibleFoodItem;
import net.ssc14.content.item.food.FoodDefinition;
import net.ssc14.content.item.food.FoodConsumptionService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14PackagedFoodItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_packaged_food_contract");
    private static final List<ExpectedFood> FOODS = List.of(
            food(Ssc14Items.ENERGY_BAR_2, "energy_bar_2", 2,
                    "Nom. На вкус орехово, волокнисто, сладко и как овёс.", null),
            food(Ssc14Items.CATBURGER, "catburger", 3,
                    "Nom. На вкус как хлеб, мясо и... МЯУ.", null),
            food(Ssc14Items.CHEESEBURGER, "cheeseburger", 3,
                    "Nom. На вкус как хлеб, мясо и сыр.", null),
            food(Ssc14Items.CHEESE_HONKERS, "cheese_honkers", 1,
                    "Nom. На вкус сырно.", Ssc14Items.CHEESE_HONKERS_TRASH),
            food(Ssc14Items.CHIPS, "chips", 1,
                    "Nom. На вкус хрустяще.", Ssc14Items.CHIPS_TRASH),
            food(Ssc14Items.BORITOS, "boritos", 1,
                    "Nom. На вкус хрустяще.", Ssc14Items.BORITOS_TRASH),
            food(Ssc14Items.POPCORN, "popcorn", 1,
                    "Nom. На вкус воздушно.", Ssc14Items.POPCORN_TRASH));

    private Ssc14PackagedFoodItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14PackagedFoodItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_packaged_food_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 160, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyDescriptors(helper);
        verifyCanonicalConsumptionAndTrash(helper);
    }

    private static void verifyDescriptors(GameTestHelper helper) {
        for (ExpectedFood expected : FOODS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof EdibleFoodItem,
                    expected.id() + " no longer uses EdibleFoodItem");
            FoodDefinition definition = ((EdibleFoodItem) item).definition();
            helper.assertTrue(definition.maxBites() == expected.maxBites(),
                    expected.id() + " bite count changed");
            helper.assertTrue(definition.tasteMessage().equals(expected.tasteMessage()),
                    expected.id() + " taste message changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == EdibleFoodItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");
            if (expected.trash() == null) {
                helper.assertTrue(definition.trash() == null,
                        expected.id() + " unexpectedly creates packaging waste");
            } else {
                helper.assertTrue(definition.trash() != null
                                && definition.trash().get() == expected.trash().get(),
                        expected.id() + " packaging waste mapping changed");
            }
        }
    }

    private static void verifyCanonicalConsumptionAndTrash(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack chips = new ItemStack(Ssc14Items.CHIPS.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, chips);
        BlockPos dropPos = helper.absolutePos(new BlockPos(2, 2, 2));
        player.setPos(dropPos.getX() + 5.0, dropPos.getY(), dropPos.getZ());

        FoodConsumptionService.consume(helper.getLevel(), dropPos.getX(), dropPos.getY(),
                dropPos.getZ(), player, chips, ((EdibleFoodItem) chips.getItem()).definition());

        helper.succeedWhen(() -> {
            helper.assertTrue(chips.isEmpty(),
                    "Canonical food service did not consume the descriptor");
            boolean hasTrash = helper.getLevel()
                    .getEntitiesOfClass(ItemEntity.class, new AABB(dropPos).inflate(1.0))
                    .stream().anyMatch(entity -> entity.getItem().is(Ssc14Items.CHIPS_TRASH.get()));
            helper.assertTrue(hasTrash, "Packaged food did not create its compatibility trash item");
        });
    }

    private static ExpectedFood food(DeferredItem<? extends Item> holder, String id,
            int maxBites, String tasteMessage, DeferredItem<? extends Item> trash) {
        return new ExpectedFood(holder, id, maxBites, tasteMessage, trash);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedFood(
            DeferredItem<? extends Item> holder,
            String id,
            int maxBites,
            String tasteMessage,
            DeferredItem<? extends Item> trash) { }
}
