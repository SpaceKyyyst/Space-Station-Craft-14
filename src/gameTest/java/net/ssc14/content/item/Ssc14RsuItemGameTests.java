package net.ssc14.content.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.tool.RsuItem;
import net.ssc14.content.item.tool.RsuStateService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14RsuItemGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_rsu_contract");

    private Ssc14RsuItemGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14RsuItemGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_rsu_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.RSU.get();
        helper.assertTrue(id("rsu").equals(BuiltInRegistries.ITEM.getKey(item)),
                "RSU ID changed");
        helper.assertTrue(item instanceof RsuItem,
                "RSU no longer uses canonical implementation");
        var stack = item.getDefaultInstance();
        helper.assertTrue(stack.getMaxStackSize() == 1, "RSU stack limit changed");

        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble("Charges", 17));
        helper.assertTrue(RsuStateService.charges(stack) == 17,
                "RSU Charges data contract changed");
        helper.assertTrue("Заряды: 17.0".equals(RsuStateService.tooltip(stack)),
                "RSU tooltip contract changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
