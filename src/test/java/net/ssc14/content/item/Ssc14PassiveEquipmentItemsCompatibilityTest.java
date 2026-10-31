package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;
import org.junit.jupiter.api.Test;

class Ssc14PassiveEquipmentItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("ISOLATED_GLOVES", "isolated_gloves"),
            Map.entry("COMBAT_BOOTS_ITEM", "combat_boots_item"),
            Map.entry("HELMET_SECURITY", "helmet_security"),
            Map.entry("BELT_SECURITY", "belt_security"),
            Map.entry("HELMETS_EVA", "helmets_eva"),
            Map.entry("GAS_MASK", "gas_mask"),
            Map.entry("WORK_BOOTS", "work_boots"),
            Map.entry("COAT_ATMOS", "coat_atmos"),
            Map.entry("COAT_SECURITY", "coat_security"),
            Map.entry("GLASS_GLASSES", "glass_glasses"),
            Map.entry("BLACK_GLOVES", "black_gloves"),
            Map.entry("HELMET_COSMONAUT", "helmet_cosmonaut"),
            Map.entry("BELT_SECURITY_WEBBING", "belt_security_webbing"),
            Map.entry("BELT_INSTRUMENTAL", "belt_instrumental"),
            Map.entry("MASK_GAS_SECURITY", "mask_gas_security"),
            Map.entry("HARDSUIT_SALVAGE", "hardsuit_salvage"),
            Map.entry("HARDSUIT_SALVAGE_HELMET", "hardsuit_salvage_helmet"),
            Map.entry("BLACK_SOLES_ITEM", "black_soles_item"),
            Map.entry("CLOAKS_CAP", "cloaks_cap"),
            Map.entry("MANTLES_CAP", "mantles_cap"),
            Map.entry("ARMOR_SECURITY", "armor_security"),
            Map.entry("OUTERCLOTHING_EVA", "outerclothing_eva"),
            Map.entry("MALE_BOXERS", "male_boxers"),
            Map.entry("GLASS_SECURITY", "glass_security"),
            Map.entry("CLOAKS_HO_S", "cloaks_ho_s"),
            Map.entry("MANTLES_HO_S_SHOULDER_CAP", "mantles_ho_s_shoulder_cap"),
            Map.entry("MANTLES_HO_S", "mantles_ho_s"),
            Map.entry("GLASS_DARK", "glass_dark"),
            Map.entry("WHITE_KNEE", "white_knee"),
            Map.entry("BEE_THIGH", "bee_thigh"));

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.IsolatedGlovesItem.class,
            net.mcreator.ssc.item.CombatBootsItemItem.class,
            net.mcreator.ssc.item.HelmetSecurityItem.class,
            net.mcreator.ssc.item.BeltSecurityItem.class,
            net.mcreator.ssc.item.HelmetsEvaItem.class,
            net.mcreator.ssc.item.GasMaskItem.class,
            net.mcreator.ssc.item.WorkBootsItem.class,
            net.mcreator.ssc.item.CoatAtmosItem.class,
            net.mcreator.ssc.item.CoatSecurityItem.class,
            net.mcreator.ssc.item.GlassGlassesItem.class,
            net.mcreator.ssc.item.BlackGlovesItem.class,
            net.mcreator.ssc.item.HelmetCosmonautItem.class,
            net.mcreator.ssc.item.BeltSecurityWebbingItem.class,
            net.mcreator.ssc.item.BeltInstrumentalItem.class,
            net.mcreator.ssc.item.MaskGasSecurityItem.class,
            net.mcreator.ssc.item.HardsuitSalvageItem.class,
            net.mcreator.ssc.item.HardsuitSalvageHelmetItem.class,
            net.mcreator.ssc.item.BlackSolesItemItem.class,
            net.mcreator.ssc.item.CloaksCapItem.class,
            net.mcreator.ssc.item.MantlesCapItem.class,
            net.mcreator.ssc.item.ArmorSecurityItem.class,
            net.mcreator.ssc.item.OuterclothingEvaItem.class,
            net.mcreator.ssc.item.MaleBoxersItem.class,
            net.mcreator.ssc.item.GlassSecurityItem.class,
            net.mcreator.ssc.item.CloaksHoSItem.class,
            net.mcreator.ssc.item.MantlesHoSShoulderCapItem.class,
            net.mcreator.ssc.item.MantlesHoSItem.class,
            net.mcreator.ssc.item.GlassDarkItem.class,
            net.mcreator.ssc.item.WhiteKneeItem.class,
            net.mcreator.ssc.item.BeeThighItem.class
    };

    @Test
    void canonicalRegistryOwnsPassiveEquipmentIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyPassiveEquipmentTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(PassiveEquipmentItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
