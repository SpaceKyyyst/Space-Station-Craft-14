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
import net.ssc14.content.item.combat.AmmunitionItem;
import net.ssc14.content.item.material.MaterialItem;
import net.ssc14.content.item.utility.UtilityItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14InertItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_inert_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.ROOD, "rood", MaterialItem.class,
                    MaterialItem.MAX_STACK_SIZE, true),
            item(Ssc14Items.WOOD, "wood", MaterialItem.class,
                    MaterialItem.MAX_STACK_SIZE, true),
            item(Ssc14Items.PEN, "pen", UtilityItem.class,
                    UtilityItem.MAX_STACK_SIZE, true),
            item(Ssc14Items.HEALTH_ANALYZER, "health_analyzer", UtilityItem.class,
                    UtilityItem.MAX_STACK_SIZE, true),
            item(Ssc14Items.BULLET_35ITEM, "bullet_35item", AmmunitionItem.class,
                    AmmunitionItem.MAX_STACK_SIZE, false),
            item(Ssc14Items.FOAM_DART, "foam_dart", AmmunitionItem.class,
                    AmmunitionItem.MAX_STACK_SIZE, false),
            item(Ssc14Items.BULLET_35_CARTRIDGE, "bullet_35_cartridge",
                    AmmunitionItem.class, AmmunitionItem.MAX_STACK_SIZE, false));

    private Ssc14InertItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14InertItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_inert_environment"));
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
            helper.assertTrue(item.getDefaultMaxStackSize() == expected.maxStackSize(),
                    expected.id() + " stack size changed");
            boolean fireResistant = item.getDefaultInstance()
                    .has(DataComponents.DAMAGE_RESISTANT);
            helper.assertTrue(fireResistant == expected.fireResistant(),
                    expected.id() + " fire resistance changed");
        }
        helper.succeed();
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            Class<? extends Item> type,
            int maxStackSize,
            boolean fireResistant) {
        return new ExpectedItem(holder, id, type, maxStackSize, fireResistant);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            Class<? extends Item> type,
            int maxStackSize,
            boolean fireResistant) { }
}
