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
import net.ssc14.content.item.clothing.UniformJumpsuitItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14UniformJumpsuitItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_uniform_jumpsuit_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.SECURITY_JUMPSUIT,
            Ssc14Items.PASSANGER_JUMPSUIT,
            Ssc14Items.ATMOS_JUMPSUIT,
            Ssc14Items.BOTANIST_JUMPSUIT,
            Ssc14Items.CAPTAIN_JUMPSUIT,
            Ssc14Items.CARGO_JUMPSUIT,
            Ssc14Items.CE_JUMPSUIT,
            Ssc14Items.CHEMIST_JUMPSUIT,
            Ssc14Items.CMO_JUMPSUIT,
            Ssc14Items.ENGENEER_JUMPSUIT,
            Ssc14Items.HO_P_JUMPSUIT,
            Ssc14Items.HO_S_JUMPSUIT,
            Ssc14Items.MEDICAL_JUMPSUIT,
            Ssc14Items.QM_JUMPSUIT,
            Ssc14Items.RD_JUMPSUIT,
            Ssc14Items.RND_JUMPSUIT,
            Ssc14Items.WARDEN_JUMPSUIT,
            Ssc14Items.BARTENDER_JUMPSUIT,
            Ssc14Items.BRIGMEDIC_JUMPSUIT,
            Ssc14Items.CAPFORMAL_JUMPSUIT,
            Ssc14Items.CASUAL_JUMPSUIT,
            Ssc14Items.C_ETURTLE_JUMPSUIT,
            Ssc14Items.CENTCOM_AGENT_JUMPSUIT,
            Ssc14Items.CENTCOM_OFFICER_JUMPSUIT,
            Ssc14Items.CENTCOM_OFFICIAL_JUMPSUIT,
            Ssc14Items.CHAPLAIN_JUMPSUIT,
            Ssc14Items.CHEF_JUMPSUIT,
            Ssc14Items.CM_OTURTLE_JUMPSUIT,
            Ssc14Items.HO_SPARADE_JUMPSUIT,
            Ssc14Items.MAID_JUMPSUIT);
    private static final List<String> IDS = List.of(
            "security_jumpsuit",
            "passanger_jumpsuit",
            "atmos_jumpsuit",
            "botanist_jumpsuit",
            "captain_jumpsuit",
            "cargo_jumpsuit",
            "ce_jumpsuit",
            "chemist_jumpsuit",
            "cmo_jumpsuit",
            "engeneer_jumpsuit",
            "ho_p_jumpsuit",
            "ho_s_jumpsuit",
            "medical_jumpsuit",
            "qm_jumpsuit",
            "rd_jumpsuit",
            "rnd_jumpsuit",
            "warden_jumpsuit",
            "bartender_jumpsuit",
            "brigmedic_jumpsuit",
            "capformal_jumpsuit",
            "casual_jumpsuit",
            "c_eturtle_jumpsuit",
            "centcom_agent_jumpsuit",
            "centcom_officer_jumpsuit",
            "centcom_official_jumpsuit",
            "chaplain_jumpsuit",
            "chef_jumpsuit",
            "cm_oturtle_jumpsuit",
            "ho_sparade_jumpsuit",
            "maid_jumpsuit");

    private Ssc14UniformJumpsuitItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14UniformJumpsuitItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_uniform_jumpsuit_environment"));
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
            helper.assertTrue(item instanceof UniformJumpsuitItem,
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
