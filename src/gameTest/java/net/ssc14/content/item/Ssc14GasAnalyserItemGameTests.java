package net.ssc14.content.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.tool.GasAnalyserItem;
import net.ssc14.content.item.tool.GasAnalyserOverlayService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14GasAnalyserItemGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_gas_analyser_contract");

    private Ssc14GasAnalyserItemGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14GasAnalyserItemGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_gas_analyser_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.GAS_ANALYSER.get();
        helper.assertTrue(id("gas_analyser").equals(BuiltInRegistries.ITEM.getKey(item)),
                "gas analyser item ID changed");
        helper.assertTrue(item instanceof GasAnalyserItem,
                "gas analyser no longer uses canonical GasAnalyserItem");
        helper.assertTrue(item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                "gas analyser fire resistance changed");
        helper.assertTrue(!item.getDefaultInstance().has(DataComponents.MAX_DAMAGE),
                "gas analyser regained durability damage");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, item.getDefaultInstance());
        item.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(GasAnalyserOverlayService.isVisible(player),
                "using the gas analyser no longer enables its overlay");

        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        player.setItemInHand(InteractionHand.OFF_HAND, item.getDefaultInstance());
        GasAnalyserOverlayService.refreshHeldState(player);
        helper.assertTrue(GasAnalyserOverlayService.isVisible(player),
                "offhand gas analyser no longer keeps its overlay visible");

        player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        GasAnalyserOverlayService.refreshHeldState(player);
        helper.assertTrue(!GasAnalyserOverlayService.isVisible(player),
                "gas analyser overlay remains enabled after unequipping the item");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
