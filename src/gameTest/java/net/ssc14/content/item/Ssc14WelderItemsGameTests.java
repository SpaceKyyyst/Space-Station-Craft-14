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
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.tool.WelderItem;
import net.ssc14.content.item.tool.WelderState;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14WelderItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_welder_contract");
    private static final List<ExpectedWelder> ITEMS = List.of(
            new ExpectedWelder(Ssc14Items.WELDING, "welding", WelderState.INACTIVE),
            new ExpectedWelder(Ssc14Items.ACTIVE_WELDER,
                    "active_welder", WelderState.ACTIVE));

    private Ssc14WelderItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14WelderItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_welder_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedWelder expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof WelderItem,
                    expected.id() + " no longer uses WelderItem");
            helper.assertTrue(((WelderItem) item).state() == expected.state(),
                    expected.id() + " state changed");
            helper.assertTrue(item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expected.id() + " fire resistance changed");
            helper.assertTrue(!item.getDefaultInstance().has(DataComponents.MAX_DAMAGE),
                    expected.id() + " regained durability damage");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedWelder(
            DeferredItem<? extends Item> holder, String id, WelderState state) { }
}
