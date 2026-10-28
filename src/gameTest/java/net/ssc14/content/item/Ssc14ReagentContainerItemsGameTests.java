package net.ssc14.content.item;

import java.util.List;
import java.util.Map;
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
import net.ssc14.content.item.reagent.ReagentContainerAccess;
import net.ssc14.content.item.reagent.ReagentContainerItem;
import net.ssc14.content.item.reagent.SealedReagentContainerItem;
import net.ssc14.content.item.reagent.Ssc14ReagentContainers;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14ReagentContainerItemsGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_reagent_container_contract");
    private static final List<ExpectedOpenContainer> OPEN_CONTAINERS = List.of(
            open(Ssc14Items.REAGENT_CONTAINER, "reagent_container",
                    Ssc14ReagentContainers.DEFAULT_CAPACITY),
            open(Ssc14Items.FLOUR_OPEN, "flour_open",
                    Ssc14ReagentContainers.FLOUR_CAPACITY),
            open(Ssc14Items.SUGAR_OPEN, "sugar_open",
                    Ssc14ReagentContainers.SUGAR_CAPACITY),
            open(Ssc14Items.MILK_OPEN, "milk_open",
                    Ssc14ReagentContainers.MILK_CAPACITY),
            open(Ssc14Items.ALL_PURPOSE_SAUCE_OPEN, "all_purpose_sauce_open",
                    Ssc14ReagentContainers.ALL_PURPOSE_SAUCE_CAPACITY));
    private static final List<ExpectedSealedContainer> SEALED_CONTAINERS = List.of(
            sealed(Ssc14Items.FLOUR, "flour", Ssc14Items.FLOUR_OPEN,
                    "flour", Ssc14ReagentContainers.FLOUR_CAPACITY),
            sealed(Ssc14Items.SUGAR, "sugar", Ssc14Items.SUGAR_OPEN,
                    "sugar", Ssc14ReagentContainers.SUGAR_CAPACITY),
            sealed(Ssc14Items.MILK, "milk", Ssc14Items.MILK_OPEN,
                    "milk", Ssc14ReagentContainers.MILK_CAPACITY),
            sealed(Ssc14Items.ALL_PURPOSE_SAUCE, "all_purpose_sauce",
                    Ssc14Items.ALL_PURPOSE_SAUCE_OPEN, "enzyme",
                    Ssc14ReagentContainers.ALL_PURPOSE_SAUCE_CAPACITY));

    private Ssc14ReagentContainerItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14ReagentContainerItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_reagent_container_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyOpenContainers(helper);
        verifySealedContainers(helper);
        helper.succeed();
    }

    private static void verifyOpenContainers(GameTestHelper helper) {
        for (ExpectedOpenContainer expected : OPEN_CONTAINERS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof ReagentContainerItem container,
                    expected.id() + " canonical type changed");
            ReagentContainerItem container = (ReagentContainerItem) item;
            helper.assertTrue(container.getMaxCapacity() == expected.capacity(),
                    expected.id() + " capacity changed");
            helper.assertTrue(item.getDefaultMaxStackSize()
                    == ReagentContainerItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");
        }
    }

    private static void verifySealedContainers(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (ExpectedSealedContainer expected : SEALED_CONTAINERS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof SealedReagentContainerItem,
                    expected.id() + " canonical type changed");

            ItemStack sealedStack = new ItemStack(item);
            player.setItemInHand(InteractionHand.MAIN_HAND, sealedStack);
            item.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);

            ItemStack openedStack = player.getMainHandItem();
            helper.assertTrue(openedStack.is(expected.openHolder().get()),
                    expected.id() + " opened into the wrong item");
            helper.assertTrue(Map.of(expected.reagentId(), expected.amount())
                            .equals(ReagentContainerAccess.contents(openedStack)),
                    expected.id() + " opened with the wrong reagent contents");
        }
    }

    private static ExpectedOpenContainer open(
            DeferredItem<? extends Item> holder, String id, int capacity) {
        return new ExpectedOpenContainer(holder, id, capacity);
    }

    private static ExpectedSealedContainer sealed(
            DeferredItem<? extends Item> holder,
            String id,
            DeferredItem<? extends Item> openHolder,
            String reagentId,
            int amount) {
        return new ExpectedSealedContainer(holder, id, openHolder, reagentId, amount);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedOpenContainer(
            DeferredItem<? extends Item> holder, String id, int capacity) { }

    private record ExpectedSealedContainer(
            DeferredItem<? extends Item> holder,
            String id,
            DeferredItem<? extends Item> openHolder,
            String reagentId,
            int amount) { }
}
