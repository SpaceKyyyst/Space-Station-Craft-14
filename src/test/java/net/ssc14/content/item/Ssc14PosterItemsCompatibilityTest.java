package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.poster.PosterItem;
import org.junit.jupiter.api.Test;

class Ssc14PosterItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("POSTER_RANDOM_ANYTHING", "poster_random_anything"),
            Map.entry("POSTER_ANOMALY", "poster_anomaly"),
            Map.entry("POSTER_ARMORY", "poster_armory"),
            Map.entry("POSTER_ATMOS", "poster_atmos"),
            Map.entry("POSTER_BAR", "poster_bar"),
            Map.entry("POSTER_BRIDGE", "poster_bridge"),
            Map.entry("POSTER_CARGO", "poster_cargo"),
            Map.entry("POSTER_CHAPEL", "poster_chapel"),
            Map.entry("POSTER_CHEM", "poster_chem"),
            Map.entry("POSTER_COMMANDER", "poster_commander"),
            Map.entry("POSTER_DEATHSPOSAL", "poster_deathsposal"),
            Map.entry("POSTER_DETECTIVE", "poster_detective"),
            Map.entry("POSTER_DRAMA", "poster_drama"),
            Map.entry("POSTER_ENG", "poster_eng"),
            Map.entry("POSTER_EVA", "poster_eva"),
            Map.entry("POSTER_GRAVI", "poster_gravi"),
            Map.entry("POSTER_HYDRO", "poster_hydro"),
            Map.entry("POSTER_JANITOR", "poster_janitor"),
            Map.entry("POSTER_KITCHEN", "poster_kitchen"),
            Map.entry("POSTER_LAW", "poster_law"),
            Map.entry("POSTER_MAIL", "poster_mail"),
            Map.entry("POSTER_MEDBAY", "poster_medbay"),
            Map.entry("POSTER_MEDIUM_SECURE", "poster_medium_secure"),
            Map.entry("POSTER_PODS", "poster_pods"),
            Map.entry("POSTER_PRISON", "poster_prison"),
            Map.entry("POSTER_RND", "poster_rnd"),
            Map.entry("POSTER_SALVAGE", "poster_salvage"),
            Map.entry("POSTER_SCI", "poster_sci"),
            Map.entry("POSTER_SECURITY", "poster_security"),
            Map.entry("POSTER_SPACE", "poster_space"),
            Map.entry("POSTER_SURGERY", "poster_surgery"),
            Map.entry("POSTER_TELECOMS", "poster_telecoms"),
            Map.entry("POSTER_VAULT", "poster_vault"),
            Map.entry("POSTER_VIROLOGY", "poster_virology"),
            Map.entry("POSTER_XENOARCH", "poster_xenoarch"));

    @Test
    void canonicalRegistryOwnsPosterIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyPosterTypesDelegateToCanonicalImplementation() {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(PosterItem.class.isAssignableFrom(legacyType), legacyType.getName());
        }
    }

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.PosterRandomAnythingItem.class,
            net.mcreator.ssc.item.PosterAnomalyItem.class,
            net.mcreator.ssc.item.PosterArmoryItem.class,
            net.mcreator.ssc.item.PosterAtmosItem.class,
            net.mcreator.ssc.item.PosterBarItem.class,
            net.mcreator.ssc.item.PosterBridgeItem.class,
            net.mcreator.ssc.item.PosterCargoItem.class,
            net.mcreator.ssc.item.PosterChapelItem.class,
            net.mcreator.ssc.item.PosterChemItem.class,
            net.mcreator.ssc.item.PosterCommanderItem.class,
            net.mcreator.ssc.item.PosterDeathsposalItem.class,
            net.mcreator.ssc.item.PosterDetectiveItem.class,
            net.mcreator.ssc.item.PosterDramaItem.class,
            net.mcreator.ssc.item.PosterEngItem.class,
            net.mcreator.ssc.item.PosterEvaItem.class,
            net.mcreator.ssc.item.PosterGraviItem.class,
            net.mcreator.ssc.item.PosterHydroItem.class,
            net.mcreator.ssc.item.PosterJanitorItem.class,
            net.mcreator.ssc.item.PosterKitchenItem.class,
            net.mcreator.ssc.item.PosterLawItem.class,
            net.mcreator.ssc.item.PosterMailItem.class,
            net.mcreator.ssc.item.PosterMedbayItem.class,
            net.mcreator.ssc.item.PosterMediumSecureItem.class,
            net.mcreator.ssc.item.PosterPodsItem.class,
            net.mcreator.ssc.item.PosterPrisonItem.class,
            net.mcreator.ssc.item.PosterRndItem.class,
            net.mcreator.ssc.item.PosterSalvageItem.class,
            net.mcreator.ssc.item.PosterSciItem.class,
            net.mcreator.ssc.item.PosterSecurityItem.class,
            net.mcreator.ssc.item.PosterSpaceItem.class,
            net.mcreator.ssc.item.PosterSurgeryItem.class,
            net.mcreator.ssc.item.PosterTelecomsItem.class,
            net.mcreator.ssc.item.PosterVaultItem.class,
            net.mcreator.ssc.item.PosterVirologyItem.class,
            net.mcreator.ssc.item.PosterXenoarchItem.class
    };
}
