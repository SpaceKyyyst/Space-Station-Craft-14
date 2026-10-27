package net.ssc14.content.item;

import java.util.List;
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
import net.ssc14.content.item.clothing.PassiveEquipmentItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14PassiveEquipmentItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_passive_equipment_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.ISOLATED_GLOVES,
            Ssc14Items.COMBAT_BOOTS_ITEM,
            Ssc14Items.HELMET_SECURITY,
            Ssc14Items.BELT_SECURITY,
            Ssc14Items.HELMETS_EVA,
            Ssc14Items.GAS_MASK,
            Ssc14Items.WORK_BOOTS,
            Ssc14Items.COAT_ATMOS,
            Ssc14Items.COAT_SECURITY,
            Ssc14Items.GLASS_GLASSES,
            Ssc14Items.BLACK_GLOVES,
            Ssc14Items.HELMET_COSMONAUT,
            Ssc14Items.BELT_SECURITY_WEBBING,
            Ssc14Items.BELT_INSTRUMENTAL,
            Ssc14Items.MASK_GAS_SECURITY,
            Ssc14Items.HARDSUIT_SALVAGE,
            Ssc14Items.HARDSUIT_SALVAGE_HELMET,
            Ssc14Items.BLACK_SOLES_ITEM,
            Ssc14Items.CLOAKS_CAP,
            Ssc14Items.MANTLES_CAP,
            Ssc14Items.ARMOR_SECURITY,
            Ssc14Items.OUTERCLOTHING_EVA,
            Ssc14Items.MALE_BOXERS,
            Ssc14Items.GLASS_SECURITY,
            Ssc14Items.CLOAKS_HO_S,
            Ssc14Items.MANTLES_HO_S_SHOULDER_CAP,
            Ssc14Items.MANTLES_HO_S,
            Ssc14Items.GLASS_DARK,
            Ssc14Items.WHITE_KNEE,
            Ssc14Items.BEE_THIGH);
    private static final List<String> IDS = List.of(
            "isolated_gloves",
            "combat_boots_item",
            "helmet_security",
            "belt_security",
            "helmets_eva",
            "gas_mask",
            "work_boots",
            "coat_atmos",
            "coat_security",
            "glass_glasses",
            "black_gloves",
            "helmet_cosmonaut",
            "belt_security_webbing",
            "belt_instrumental",
            "mask_gas_security",
            "hardsuit_salvage",
            "hardsuit_salvage_helmet",
            "black_soles_item",
            "cloaks_cap",
            "mantles_cap",
            "armor_security",
            "outerclothing_eva",
            "male_boxers",
            "glass_security",
            "cloaks_ho_s",
            "mantles_ho_s_shoulder_cap",
            "mantles_ho_s",
            "glass_dark",
            "white_knee",
            "bee_thigh");

    private Ssc14PassiveEquipmentItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14PassiveEquipmentItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_passive_equipment_environment"));
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
            helper.assertTrue(item instanceof PassiveEquipmentItem,
                    expectedId + " canonical item type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                    expectedId + " stack size changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
