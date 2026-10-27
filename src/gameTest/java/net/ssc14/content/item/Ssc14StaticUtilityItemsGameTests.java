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
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.utility.DebugActivatorItem;
import net.ssc14.content.item.utility.RcdCartridgeItem;
import net.ssc14.content.item.utility.TrafficConeItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14StaticUtilityItemsGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_static_utility_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.RC_DCARTRIDGE, "rc_dcartridge", RcdCartridgeItem.class,
                    RcdCartridgeItem.MAX_STACK_SIZE, Rarity.COMMON),
            item(Ssc14Items.CONE, "cone", TrafficConeItem.class,
                    TrafficConeItem.MAX_STACK_SIZE, Rarity.COMMON),
            item(Ssc14Items.DEBUGACTIVATOR, "debugactivator", DebugActivatorItem.class,
                    DebugActivatorItem.MAX_STACK_SIZE, DebugActivatorItem.RARITY));

    private Ssc14StaticUtilityItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14StaticUtilityItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_static_utility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(expected.type().isInstance(item),
                    expected.id() + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == expected.maxStackSize(),
                    expected.id() + " stack size changed");
            helper.assertTrue(item.getDefaultInstance().getRarity() == expected.rarity(),
                    expected.id() + " rarity changed");
        }
        helper.succeed();
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            Class<? extends Item> type,
            int maxStackSize,
            Rarity rarity) {
        return new ExpectedItem(holder, id, type, maxStackSize, rarity);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            Class<? extends Item> type,
            int maxStackSize,
            Rarity rarity) { }
}
