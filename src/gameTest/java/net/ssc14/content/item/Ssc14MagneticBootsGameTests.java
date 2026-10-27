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
import net.ssc14.content.item.equipment.MagneticBootsItem;
import net.ssc14.content.item.equipment.MagneticBootsState;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MagneticBootsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_magnetic_boots_contract");
    private static final List<ExpectedBoots> BOOTS = List.of(
            new ExpectedBoots(Ssc14Items.MAGNETIC_BOOTS_ITEM,
                    "magnetic_boots_item", MagneticBootsState.INACTIVE),
            new ExpectedBoots(Ssc14Items.MAGNETIC_BOOTS_ACTIVE_ITEM,
                    "magnetic_boots_active_item", MagneticBootsState.ACTIVE));

    private Ssc14MagneticBootsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MagneticBootsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_magnetic_boots_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedBoots expected : BOOTS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof MagneticBootsItem,
                    expected.id() + " no longer uses MagneticBootsItem");
            helper.assertTrue(((MagneticBootsItem) item).state() == expected.state(),
                    expected.id() + " state changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == MagneticBootsItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");
            helper.assertTrue(item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expected.id() + " fire resistance changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBoots(
            DeferredItem<? extends Item> holder, String id, MagneticBootsState state) { }
}
