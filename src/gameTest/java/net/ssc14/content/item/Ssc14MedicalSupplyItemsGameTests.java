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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.medical.MedicalSupplyItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MedicalSupplyItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_medical_supply_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.BRUTEPACK,
            Ssc14Items.BLOODPACK,
            Ssc14Items.GAUZE,
            Ssc14Items.OINTMENT);
    private static final List<String> IDS = List.of("brutepack", "bloodpack", "gauze", "ointment");

    private Ssc14MedicalSupplyItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MedicalSupplyItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_medical_supply_environment"));
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
            helper.assertTrue(item instanceof MedicalSupplyItem,
                    expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 30,
                    expectedId + " stack size changed");
            helper.assertTrue(item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expectedId + " fire resistance changed");
            helper.assertTrue(item.getDestroySpeed(ItemStack.EMPTY, Blocks.STONE.defaultBlockState()) == 0.0F,
                    expectedId + " destroy speed changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
