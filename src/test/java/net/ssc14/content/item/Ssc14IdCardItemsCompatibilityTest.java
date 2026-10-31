package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14IdCardItemsCompatibilityTest {
    private static final List<ExpectedCard> EXPECTED_CARDS = List.of(
            card("ID_CARD_PASSANGER", "id_card_passanger", "IDCardPassangerItem"),
            card("ID_CARD_CAPITAN", "id_card_capitan", "IDCardCapitanItem"),
            card("ID_CARD_PNT", "id_card_pnt", "IDCardPNTItem"),
            card("ID_CARD_ADUTANT", "id_card_adutant", "IDCardAdutantItem"),
            card("ID_CARD_AVD", "id_card_avd", "IDCardAVDItem"),
            card("ID_CARD_OSH", "id_card_osh", "IDCardOSHItem"),
            card("ID_CARD_MIME", "id_card_mime", "IDCardMimeItem"),
            card("ID_CARD_CLOWN", "id_card_clown", "IDCardClownItem"),
            card("ID_CARD_HO_P", "id_card_ho_p", "IDCardHoPItem"),
            card("ID_CARD_SERVICE_W", "id_card_service_w", "IDCardServiceWItem"),
            card("ID_CARD_CLEANER", "id_card_cleaner", "IDCardCleanerItem"),
            card("ID_CARD_CHEF", "id_card_chef", "IDCardChefItem"),
            card("ID_CARD_BOTANIK", "id_card_botanik", "IDCardBotanikItem"),
            card("ID_CARD_BARMEN", "id_card_barmen", "IDCardBarmenItem"),
            card("ID_CARD_PRIEST", "id_card_priest", "IDCardPriestItem"),
            card("ID_CARD_QM", "id_card_qm", "IDCardQmItem"),
            card("ID_CARD_UTILIZATOR", "id_card_utilizator", "IDCardUtilizatorItem"),
            card("ID_CARD_LOADER", "id_card_loader", "IDCardLoaderItem"),
            card("ID_CARD_REPORTER", "id_card_reporter", "IDCardReporterItem"),
            card("ID_CARD_BOXER", "id_card_boxer", "IDCardBoxerItem"),
            card("ID_CARD_BIBLIOTEKAR", "id_card_bibliotekar", "IDCardBibliotekarItem"),
            card("ID_CARD_ZOOTECHNIK", "id_card_zootechnik", "IDCardZootechnikItem"),
            card("ID_CARD_CE", "id_card_ce", "IDCardCEItem"),
            card("ID_CARD_VE", "id_card_ve", "IDCardVEItem"),
            card("ID_CARD_ATMOS", "id_card_atmos", "IDCardAtmosItem"),
            card("ID_CARD_ENGENEER", "id_card_engeneer", "IDCardEngeneerItem"),
            card("ID_CARD_TECH_ASSISTENT", "id_card_tech_assistent", "IDCardTechAssistentItem"),
            card("ID_CARD_MUSICANT", "id_card_musicant", "IDCardMusicantItem"),
            card("ID_CARD_HO_S", "id_card_ho_s", "IDCardHoSItem"),
            card("ID_CARD_INSTRUCTOR_S", "id_card_instructor_s", "IDCardInstructorSItem"),
            card("ID_CARD_WARD", "id_card_ward", "IDCardWardItem"),
            card("ID_CARD_DETECTIVE", "id_card_detective", "IDCardDetectiveItem"),
            card("ID_CARD_BRIG_MED", "id_card_brig_med", "IDCardBrigMedItem"),
            card("ID_CARD_OFICER_S", "id_card_oficer_s", "IDCardOficerSItem"),
            card("ID_CARD_KADET_S", "id_card_kadet_s", "IDCardKadetSItem"),
            card("ID_CARD_CMO", "id_card_cmo", "IDCardCMOItem"),
            card("ID_CARD_VM", "id_card_vm", "IDCardVMItem"),
            card("ID_CARD_CHIMIC", "id_card_chimic", "IDCardChimicItem"),
            card("ID_CARD_PARAMED", "id_card_paramed", "IDCardParamedItem"),
            card("ID_CARD_MEDIC", "id_card_medic", "IDCardMedicItem"),
            card("ID_CARD_PSIHOLOG", "id_card_psiholog", "IDCardPsihologItem"),
            card("ID_CARD_INTERN", "id_card_intern", "IDCardInternItem"),
            card("ID_CARD_RD", "id_card_rd", "IDCardRDItem"),
            card("ID_CARD_VS", "id_card_vs", "IDCardVSItem"),
            card("ID_CARD_SCIENTIST", "id_card_scientist", "IDCardScientistItem"),
            card("ID_CARD_SCI_ASSISTENT", "id_card_sci_assistent", "IDCardSciAssistentItem"));

    @Test
    void canonicalRegistryOwnsAllIdCardIdsAndPreservesCanonicalIds() throws Exception {
        assertEquals(46, EXPECTED_CARDS.size());
        for (ExpectedCard expected : EXPECTED_CARDS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.registryName(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(),
                    expected.fieldName());
        }
    }

    @Test
    void legacyIdCardTypesDelegateToCanonicalImplementation() throws Exception {
        Class<?> canonicalType = Class.forName("net.ssc14.content.item.access.IdCardItem");
        for (ExpectedCard expected : EXPECTED_CARDS) {
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType),
                    expected.legacyClassName());
        }
    }

    private static ExpectedCard card(
            String fieldName, String registryName, String legacyClassName) {
        return new ExpectedCard(fieldName, registryName, legacyClassName);
    }

    private record ExpectedCard(
            String fieldName, String registryName, String legacyClassName) { }
}
