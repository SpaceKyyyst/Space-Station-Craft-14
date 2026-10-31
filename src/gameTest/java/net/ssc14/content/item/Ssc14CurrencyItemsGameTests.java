package net.ssc14.content.item;

import java.util.List;
import net.ssc14.integration.neoforge.state.Ssc14WorldVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.currency.CurrencyItem;
import net.ssc14.content.item.currency.CurrencyKind;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14CurrencyItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_currency_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.CREDITS, "credits", CurrencyKind.CREDIT,
                    50, Rarity.COMMON, false),
            item(Ssc14Items.CREDITS_50, "credits_50", CurrencyKind.CREDIT_50,
                    50, Rarity.COMMON, false),
            item(Ssc14Items.DEBU_GCREDITS_DELETER, "debu_gcredits_deleter",
                    CurrencyKind.DEBUG_DELETER, 1, Rarity.EPIC, true));

    private Ssc14CurrencyItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14CurrencyItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_currency_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyDefinitions(helper);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        verifyConsolidation(helper, player);
        verifyBudgetInteractions(helper, player);
    }

    private static void verifyDefinitions(GameTestHelper helper) {
        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof CurrencyItem,
                    expected.id() + " canonical item type changed");
            CurrencyItem currency = (CurrencyItem) item;
            helper.assertTrue(currency.kind() == expected.kind(),
                    expected.id() + " currency role changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == expected.maxStackSize(),
                    expected.id() + " stack size changed");
            helper.assertTrue(item.getDefaultInstance().getRarity() == expected.rarity(),
                    expected.id() + " rarity changed");
            boolean fireResistant = item.getDefaultInstance()
                    .getOrDefault(DataComponents.DAMAGE_RESISTANT, null) != null;
            helper.assertTrue(fireResistant == expected.fireResistant(),
                    expected.id() + " fire resistance changed");
        }
    }

    private static void verifyConsolidation(GameTestHelper helper,
            net.minecraft.world.entity.player.Player player) {
        ItemStack credits = new ItemStack(Ssc14Items.CREDITS.get(), 50);
        player.setItemInHand(InteractionHand.MAIN_HAND, credits);
        Ssc14Items.CREDITS.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(credits.isEmpty(), "fifty single credits were not consumed");
        boolean hasFiftyCredit = false;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            if (player.getInventory().getItem(slot).is(Ssc14Items.CREDITS_50.get())) {
                hasFiftyCredit = true;
                break;
            }
        }
        helper.assertTrue(hasFiftyCredit, "fifty single credits were not consolidated");
        player.getInventory().clearContent();
    }

    private static void verifyBudgetInteractions(GameTestHelper helper,
            net.minecraft.world.entity.player.Player player) {
        BlockPos relativePos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, Ssc14Blocks.ORDER_CONSOLE.get().defaultBlockState());
        var worldVariables = Ssc14WorldVariables.WorldVariables.get(helper.getLevel());
        double previousBudget = worldVariables.Station_Budget;
        worldVariables.Station_Budget = 0;

        ItemStack deposit = new ItemStack(Ssc14Items.CREDITS_50.get(), 2);
        player.setItemInHand(InteractionHand.MAIN_HAND, deposit);
        var hit = new BlockHitResult(
                Vec3.atCenterOf(absolutePos), Direction.UP, absolutePos, false);
        helper.assertTrue(Ssc14Items.CREDITS_50.get().useOn(new UseOnContext(
                        player, InteractionHand.MAIN_HAND, hit)).consumesAction(),
                "fifty-credit deposit interaction changed");

        helper.runAfterDelay(2, () -> {
            helper.assertTrue(worldVariables.Station_Budget == 100 && deposit.isEmpty(),
                    "fifty-credit deposit no longer updates the station budget");
            ItemStack deleter = new ItemStack(Ssc14Items.DEBU_GCREDITS_DELETER.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, deleter);
            Ssc14Items.DEBU_GCREDITS_DELETER.get().useOn(new UseOnContext(
                    player, InteractionHand.MAIN_HAND, hit));
            helper.runAfterDelay(2, () -> {
                helper.assertTrue(worldVariables.Station_Budget == 0,
                        "debug currency tool no longer resets the station budget");
                worldVariables.Station_Budget = previousBudget;
                helper.succeed();
            });
        });
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            CurrencyKind kind,
            int maxStackSize,
            Rarity rarity,
            boolean fireResistant) {
        return new ExpectedItem(holder, id, kind, maxStackSize, rarity, fireResistant);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            CurrencyKind kind,
            int maxStackSize,
            Rarity rarity,
            boolean fireResistant) { }
}
