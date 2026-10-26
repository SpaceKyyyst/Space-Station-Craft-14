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
import net.ssc14.content.item.machinery.MachineComponentItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MachineComponentItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_machine_component_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.CAPACITOR, Ssc14Items.MATTER_BIN, Ssc14Items.MICRO_MANIPULATOR,
            Ssc14Items.SUBSTATION_BOARD, Ssc14Items.SMES_BOARD, Ssc14Items.APC_BOARD,
            Ssc14Items.LOW_BATTERIE);
    private static final List<String> IDS = List.of(
            "capacitor", "matter_bin", "micro_manipulator", "substation_board",
            "smes_board", "apc_board", "low_batterie");
    private static final List<Integer> MAX_STACK_SIZES = List.of(30, 30, 30, 1, 1, 1, 1);

    private Ssc14MachineComponentItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MachineComponentItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_machine_component_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (int index = 0; index < ITEMS.size(); index++) {
            Item item = ITEMS.get(index).get();
            String expectedId = IDS.get(index);
            helper.assertTrue(id(expectedId).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expectedId + " item ID changed");
            helper.assertTrue(item instanceof MachineComponentItem,
                    expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == MAX_STACK_SIZES.get(index)
                            && !item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expectedId + " item properties changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
