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
import net.ssc14.content.item.construction.CableCoilItem;
import net.ssc14.content.item.construction.ConstructionItemKind;
import net.ssc14.content.item.construction.ConstructionSheetItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14ConstructionMaterialItemsGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_construction_material_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.STEEL, "steel", ConstructionSheetItem.class,
                    ConstructionItemKind.STEEL_SHEET, true),
            item(Ssc14Items.PLASTEEL, "plasteel", ConstructionSheetItem.class,
                    ConstructionItemKind.PLASTEEL_SHEET, true),
            item(Ssc14Items.LOW_VOLTAGE_CABLE, "low_voltage_cable", CableCoilItem.class,
                    ConstructionItemKind.LOW_VOLTAGE_CABLE, false),
            item(Ssc14Items.MEDIUM_VOLTAGE_CABLE, "medium_voltage_cable", CableCoilItem.class,
                    ConstructionItemKind.MEDIUM_VOLTAGE_CABLE, false),
            item(Ssc14Items.HIGH_VOLTAGE_CABLE, "high_voltage_cable", CableCoilItem.class,
                    ConstructionItemKind.HIGH_VOLTAGE_CABLE, false));

    private Ssc14ConstructionMaterialItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14ConstructionMaterialItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_construction_material_environment"));
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
            helper.assertTrue(kind(item) == expected.kind(),
                    expected.id() + " construction role changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 30,
                    expected.id() + " stack size changed");
            boolean fireResistant = item.getDefaultInstance()
                    .getOrDefault(DataComponents.DAMAGE_RESISTANT, null) != null;
            helper.assertTrue(fireResistant == expected.fireResistant(),
                    expected.id() + " fire resistance changed");
        }
        helper.succeed();
    }

    private static ConstructionItemKind kind(Item item) {
        if (item instanceof ConstructionSheetItem sheet) {
            return sheet.kind();
        }
        return ((CableCoilItem) item).kind();
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            Class<? extends Item> type,
            ConstructionItemKind kind,
            boolean fireResistant) {
        return new ExpectedItem(holder, id, type, kind, fireResistant);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            Class<? extends Item> type,
            ConstructionItemKind kind,
            boolean fireResistant) { }
}
