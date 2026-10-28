package net.ssc14.content.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.tool.MultitoolItem;
import net.ssc14.content.item.tool.MultitoolModeService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MultitoolItemGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_multitool_contract");

    private Ssc14MultitoolItemGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MultitoolItemGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_multitool_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.MULTITOOL.get();
        helper.assertTrue(id("multitul").equals(BuiltInRegistries.ITEM.getKey(item)),
                "multitool item ID changed");
        helper.assertTrue(item instanceof MultitoolItem,
                "multitool no longer uses canonical implementation");
        ItemStack stack = item.getDefaultInstance();
        helper.assertTrue(stack.getMaxStackSize() == 1,
                "multitool stack limit changed");
        helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT),
                "multitool fire resistance changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        MultitoolModeService.switchMode(player, stack);
        helper.assertTrue(MultitoolModeService.mode(stack) == 1,
                "multitool default mode no longer switches to LV");
        MultitoolModeService.switchMode(player, stack);
        helper.assertTrue(MultitoolModeService.mode(stack) == 2,
                "multitool no longer switches to MV");
        MultitoolModeService.switchMode(player, stack);
        helper.assertTrue(MultitoolModeService.mode(stack) == 3,
                "multitool no longer switches to HV");
        MultitoolModeService.switchMode(player, stack);
        helper.assertTrue(MultitoolModeService.mode(stack) == 1,
                "multitool mode cycle no longer wraps to LV");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
