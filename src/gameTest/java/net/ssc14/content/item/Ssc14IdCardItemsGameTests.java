package net.ssc14.content.item;

import java.util.List;
import net.mcreator.ssc.access.IdCardRegistry;
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
import net.ssc14.content.item.access.IdCardItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14IdCardItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_id_card_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.ID_CARD_PASSANGER, "id_card_passanger"),
            item(Ssc14Items.ID_CARD_CAPITAN, "id_card_capitan"),
            item(Ssc14Items.ID_CARD_PNT, "id_card_pnt"),
            item(Ssc14Items.ID_CARD_ADUTANT, "id_card_adutant"),
            item(Ssc14Items.ID_CARD_AVD, "id_card_avd"),
            item(Ssc14Items.ID_CARD_OSH, "id_card_osh"),
            item(Ssc14Items.ID_CARD_MIME, "id_card_mime"),
            item(Ssc14Items.ID_CARD_CLOWN, "id_card_clown"),
            item(Ssc14Items.ID_CARD_HO_P, "id_card_ho_p"),
            item(Ssc14Items.ID_CARD_SERVICE_W, "id_card_service_w"),
            item(Ssc14Items.ID_CARD_CLEANER, "id_card_cleaner"),
            item(Ssc14Items.ID_CARD_CHEF, "id_card_chef"),
            item(Ssc14Items.ID_CARD_BOTANIK, "id_card_botanik"),
            item(Ssc14Items.ID_CARD_BARMEN, "id_card_barmen"),
            item(Ssc14Items.ID_CARD_PRIEST, "id_card_priest"),
            item(Ssc14Items.ID_CARD_QM, "id_card_qm"),
            item(Ssc14Items.ID_CARD_UTILIZATOR, "id_card_utilizator"),
            item(Ssc14Items.ID_CARD_LOADER, "id_card_loader"),
            item(Ssc14Items.ID_CARD_REPORTER, "id_card_reporter"),
            item(Ssc14Items.ID_CARD_BOXER, "id_card_boxer"),
            item(Ssc14Items.ID_CARD_BIBLIOTEKAR, "id_card_bibliotekar"),
            item(Ssc14Items.ID_CARD_ZOOTECHNIK, "id_card_zootechnik"),
            item(Ssc14Items.ID_CARD_CE, "id_card_ce"),
            item(Ssc14Items.ID_CARD_VE, "id_card_ve"),
            item(Ssc14Items.ID_CARD_ATMOS, "id_card_atmos"),
            item(Ssc14Items.ID_CARD_ENGENEER, "id_card_engeneer"),
            item(Ssc14Items.ID_CARD_TECH_ASSISTENT, "id_card_tech_assistent"),
            item(Ssc14Items.ID_CARD_MUSICANT, "id_card_musicant"),
            item(Ssc14Items.ID_CARD_HO_S, "id_card_ho_s"),
            item(Ssc14Items.ID_CARD_INSTRUCTOR_S, "id_card_instructor_s"),
            item(Ssc14Items.ID_CARD_WARD, "id_card_ward"),
            item(Ssc14Items.ID_CARD_DETECTIVE, "id_card_detective"),
            item(Ssc14Items.ID_CARD_BRIG_MED, "id_card_brig_med"),
            item(Ssc14Items.ID_CARD_OFICER_S, "id_card_oficer_s"),
            item(Ssc14Items.ID_CARD_KADET_S, "id_card_kadet_s"),
            item(Ssc14Items.ID_CARD_CMO, "id_card_cmo"),
            item(Ssc14Items.ID_CARD_VM, "id_card_vm"),
            item(Ssc14Items.ID_CARD_CHIMIC, "id_card_chimic"),
            item(Ssc14Items.ID_CARD_PARAMED, "id_card_paramed"),
            item(Ssc14Items.ID_CARD_MEDIC, "id_card_medic"),
            item(Ssc14Items.ID_CARD_PSIHOLOG, "id_card_psiholog"),
            item(Ssc14Items.ID_CARD_INTERN, "id_card_intern"),
            item(Ssc14Items.ID_CARD_RD, "id_card_rd"),
            item(Ssc14Items.ID_CARD_VS, "id_card_vs"),
            item(Ssc14Items.ID_CARD_SCIENTIST, "id_card_scientist"),
            item(Ssc14Items.ID_CARD_SCI_ASSISTENT, "id_card_sci_assistent"));

    private Ssc14IdCardItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14IdCardItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_id_card_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof IdCardItem,
                    expected.id() + " no longer uses IdCardItem");
            helper.assertTrue(item.getDefaultMaxStackSize() == IdCardItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");
            helper.assertTrue(IdCardRegistry.defaults(item) != null,
                    expected.id() + " lost its default access mask");
        }
        helper.succeed();
    }

    private static ExpectedItem item(DeferredItem<? extends Item> holder, String id) {
        return new ExpectedItem(holder, id);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(DeferredItem<? extends Item> holder, String id) { }
}
