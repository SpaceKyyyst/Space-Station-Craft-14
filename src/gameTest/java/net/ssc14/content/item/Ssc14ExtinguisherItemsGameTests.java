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
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.safety.ExtinguisherItem;
import net.ssc14.content.item.safety.ExtinguisherState;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14ExtinguisherItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_extinguisher_contract");
    private static final List<ExpectedExtinguisher> ITEMS = List.of(
            new ExpectedExtinguisher(Ssc14Items.EXTINGUISHER,
                    "extinguisher", ExtinguisherState.CLOSED),
            new ExpectedExtinguisher(Ssc14Items.EXTINGUISHER_OPEN,
                    "extinguisher_open", ExtinguisherState.OPEN));

    private Ssc14ExtinguisherItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14ExtinguisherItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_extinguisher_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedExtinguisher expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof ExtinguisherItem,
                    expected.id() + " no longer uses ExtinguisherItem");
            helper.assertTrue(((ExtinguisherItem) item).state() == expected.state(),
                    expected.id() + " state changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == ExtinguisherItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");
            helper.assertTrue(item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expected.id() + " fire resistance changed");
        }

        ExtinguisherItem openItem = (ExtinguisherItem) Ssc14Items.EXTINGUISHER_OPEN.get();
        ItemStack openStack = openItem.getDefaultInstance();
        helper.assertTrue(openItem.getUseAnimation(openStack) == ItemUseAnimation.SPEAR,
                "open extinguisher use animation changed");
        helper.assertTrue(openItem.getUseDuration(openStack, null)
                        == ExtinguisherItem.OPEN_USE_DURATION,
                "open extinguisher use duration changed");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND,
                Ssc14Items.EXTINGUISHER.get().getDefaultInstance());
        Ssc14Items.EXTINGUISHER.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getMainHandItem().is(Ssc14Items.EXTINGUISHER_OPEN.get()),
                "closed extinguisher no longer opens");

        player.setShiftKeyDown(true);
        Ssc14Items.EXTINGUISHER_OPEN.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getMainHandItem().is(Ssc14Items.EXTINGUISHER.get()),
                "shift-use no longer closes the open extinguisher");

        player.setShiftKeyDown(false);
        player.setItemInHand(InteractionHand.MAIN_HAND,
                Ssc14Items.EXTINGUISHER_OPEN.get().getDefaultInstance());
        Ssc14Items.EXTINGUISHER_OPEN.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getMainHandItem().is(Ssc14Items.EXTINGUISHER_OPEN.get()),
                "spraying unexpectedly changes the open extinguisher state");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedExtinguisher(
            DeferredItem<? extends Item> holder, String id, ExtinguisherState state) { }
}
