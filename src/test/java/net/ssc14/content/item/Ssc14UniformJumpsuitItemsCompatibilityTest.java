package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.clothing.UniformJumpsuitItem;
import org.junit.jupiter.api.Test;

class Ssc14UniformJumpsuitItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("SECURITY_JUMPSUIT", "security_jumpsuit"),
            Map.entry("PASSANGER_JUMPSUIT", "passanger_jumpsuit"),
            Map.entry("ATMOS_JUMPSUIT", "atmos_jumpsuit"),
            Map.entry("BOTANIST_JUMPSUIT", "botanist_jumpsuit"),
            Map.entry("CAPTAIN_JUMPSUIT", "captain_jumpsuit"),
            Map.entry("CARGO_JUMPSUIT", "cargo_jumpsuit"),
            Map.entry("CE_JUMPSUIT", "ce_jumpsuit"),
            Map.entry("CHEMIST_JUMPSUIT", "chemist_jumpsuit"),
            Map.entry("CMO_JUMPSUIT", "cmo_jumpsuit"),
            Map.entry("ENGENEER_JUMPSUIT", "engeneer_jumpsuit"),
            Map.entry("HO_P_JUMPSUIT", "ho_p_jumpsuit"),
            Map.entry("HO_S_JUMPSUIT", "ho_s_jumpsuit"),
            Map.entry("MEDICAL_JUMPSUIT", "medical_jumpsuit"),
            Map.entry("QM_JUMPSUIT", "qm_jumpsuit"),
            Map.entry("RD_JUMPSUIT", "rd_jumpsuit"),
            Map.entry("RND_JUMPSUIT", "rnd_jumpsuit"),
            Map.entry("WARDEN_JUMPSUIT", "warden_jumpsuit"),
            Map.entry("BARTENDER_JUMPSUIT", "bartender_jumpsuit"),
            Map.entry("BRIGMEDIC_JUMPSUIT", "brigmedic_jumpsuit"),
            Map.entry("CAPFORMAL_JUMPSUIT", "capformal_jumpsuit"),
            Map.entry("CASUAL_JUMPSUIT", "casual_jumpsuit"),
            Map.entry("C_ETURTLE_JUMPSUIT", "c_eturtle_jumpsuit"),
            Map.entry("CENTCOM_AGENT_JUMPSUIT", "centcom_agent_jumpsuit"),
            Map.entry("CENTCOM_OFFICER_JUMPSUIT", "centcom_officer_jumpsuit"),
            Map.entry("CENTCOM_OFFICIAL_JUMPSUIT", "centcom_official_jumpsuit"),
            Map.entry("CHAPLAIN_JUMPSUIT", "chaplain_jumpsuit"),
            Map.entry("CHEF_JUMPSUIT", "chef_jumpsuit"),
            Map.entry("CM_OTURTLE_JUMPSUIT", "cm_oturtle_jumpsuit"),
            Map.entry("HO_SPARADE_JUMPSUIT", "ho_sparade_jumpsuit"),
            Map.entry("MAID_JUMPSUIT", "maid_jumpsuit"));

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.SecurityJumpsuitItem.class,
            net.mcreator.ssc.item.PassangerJumpsuitItem.class,
            net.mcreator.ssc.item.AtmosJumpsuitItem.class,
            net.mcreator.ssc.item.BotanistJumpsuitItem.class,
            net.mcreator.ssc.item.CaptainJumpsuitItem.class,
            net.mcreator.ssc.item.CargoJumpsuitItem.class,
            net.mcreator.ssc.item.CEJumpsuitItem.class,
            net.mcreator.ssc.item.ChemistJumpsuitItem.class,
            net.mcreator.ssc.item.CMOJumpsuitItem.class,
            net.mcreator.ssc.item.EngeneerJumpsuitItem.class,
            net.mcreator.ssc.item.HoPJumpsuitItem.class,
            net.mcreator.ssc.item.HoSJumpsuitItem.class,
            net.mcreator.ssc.item.MedicalJumpsuitItem.class,
            net.mcreator.ssc.item.QMJumpsuitItem.class,
            net.mcreator.ssc.item.RDJumpsuitItem.class,
            net.mcreator.ssc.item.RNDJumpsuitItem.class,
            net.mcreator.ssc.item.WardenJumpsuitItem.class,
            net.mcreator.ssc.item.BartenderJumpsuitItem.class,
            net.mcreator.ssc.item.BrigmedicJumpsuitItem.class,
            net.mcreator.ssc.item.CapformalJumpsuitItem.class,
            net.mcreator.ssc.item.CasualJumpsuitItem.class,
            net.mcreator.ssc.item.CEturtleJumpsuitItem.class,
            net.mcreator.ssc.item.CentcomAgentJumpsuitItem.class,
            net.mcreator.ssc.item.CentcomOfficerJumpsuitItem.class,
            net.mcreator.ssc.item.CentcomOfficialJumpsuitItem.class,
            net.mcreator.ssc.item.ChaplainJumpsuitItem.class,
            net.mcreator.ssc.item.ChefJumpsuitItem.class,
            net.mcreator.ssc.item.CMOturtleJumpsuitItem.class,
            net.mcreator.ssc.item.HoSparadeJumpsuitItem.class,
            net.mcreator.ssc.item.MaidJumpsuitItem.class
    };

    @Test
    void canonicalRegistryOwnsJumpsuitIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyJumpsuitTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(UniformJumpsuitItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
