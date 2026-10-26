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
import net.ssc14.content.item.material.MaterialItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MaterialItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_material_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.SILVER, Ssc14Items.SSC_14_GOLD, Ssc14Items.URAN,
            Ssc14Items.PLASMA, Ssc14Items.PLASTIC, Ssc14Items.GLASS,
            Ssc14Items.ARM_GLASS, Ssc14Items.RAW_IRON, Ssc14Items.RAW_QUARTZ,
            Ssc14Items.RAW_COAL, Ssc14Items.RAW_GOLD, Ssc14Items.RAW_PLASMA,
            Ssc14Items.RAW_SILVER, Ssc14Items.RAW_URAN, Ssc14Items.RAW_DIAMOND);
    private static final List<String> IDS = List.of(
            "silver", "ssc_14_gold", "uran", "plasma", "plastic", "glass",
            "arm_glass", "raw_iron", "raw_quartz", "raw_coal", "raw_gold",
            "raw_plasma", "raw_silver", "raw_uran", "raw_diamond");

    private Ssc14MaterialItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MaterialItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_material_environment"));
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
            helper.assertTrue(item instanceof MaterialItem,
                    expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 30
                            && item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expectedId + " item properties changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
