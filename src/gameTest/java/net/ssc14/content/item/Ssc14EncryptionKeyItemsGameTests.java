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
import net.ssc14.content.item.communication.RadioEncryptionKeyItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14EncryptionKeyItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_encryption_key_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.ENCRYPTION_KEY_PASSANGER, Ssc14Items.ENCRYPTION_KEY_COMMAND,
            Ssc14Items.ENCRYPTION_KEY_ENGENEER, Ssc14Items.ENCRYPTION_KEY_MEDICAL,
            Ssc14Items.ENCRYPTION_KEY_RND, Ssc14Items.ENCRYPTION_KEY_SECURITY,
            Ssc14Items.ENCRYPTION_KEY_SERVICE, Ssc14Items.ENCRYPTION_KEY_CARGO);
    private static final List<String> IDS = List.of(
            "encryption_key_passanger", "encryption_key_command", "encryption_key_engeneer",
            "encryption_key_medical", "encryption_key_rnd", "encryption_key_security",
            "encryption_key_service", "encryption_key_cargo");

    private Ssc14EncryptionKeyItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14EncryptionKeyItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_encryption_key_environment"));
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
            helper.assertTrue(item instanceof RadioEncryptionKeyItem,
                    expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1
                            && item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expectedId + " item properties changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
