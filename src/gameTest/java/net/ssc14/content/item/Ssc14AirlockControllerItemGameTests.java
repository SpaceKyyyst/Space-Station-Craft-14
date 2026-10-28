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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.tool.AirlockControllerItem;
import net.ssc14.content.item.tool.AirlockControllerService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14AirlockControllerItemGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_airlock_controller_contract");

    private Ssc14AirlockControllerItemGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14AirlockControllerItemGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_airlock_controller_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.AIRLOCK_CONTROLLER.get();
        helper.assertTrue(id("airlock_controller")
                        .equals(BuiltInRegistries.ITEM.getKey(item)),
                "airlock controller item ID changed");
        helper.assertTrue(item instanceof AirlockControllerItem,
                "airlock controller no longer uses canonical implementation");
        ItemStack stack = item.getDefaultInstance();
        helper.assertTrue(stack.getMaxStackSize() == 1,
                "airlock controller stack limit changed");
        helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT),
                "airlock controller fire resistance changed");

        helper.assertTrue(AirlockControllerService.mode(stack) == 0,
                "default airlock controller mode changed");
        AirlockControllerService.switchMode(stack);
        helper.assertTrue(AirlockControllerService.mode(stack) == 1,
                "airlock controller no longer switches to emergency mode");
        AirlockControllerService.switchMode(stack);
        helper.assertTrue(AirlockControllerService.mode(stack) == 2,
                "airlock controller no longer switches to open/close mode");
        AirlockControllerService.switchMode(stack);
        helper.assertTrue(AirlockControllerService.mode(stack) == 0,
                "airlock controller mode cycle no longer wraps to bolts");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
