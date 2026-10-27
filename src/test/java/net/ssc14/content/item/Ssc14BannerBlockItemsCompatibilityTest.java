package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14BannerBlockItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED = expected();

    @Test
    void canonicalRegistryOwnsBannerItemsAndLegacyHoldersRemainAliases()
            throws ReflectiveOperationException {
        for (Map.Entry<String, String> entry : EXPECTED.entrySet()) {
            Field field = Ssc14Items.class.getField(entry.getKey());
            Object canonical = field.get(null);
            assertSame(canonical, Ssc14ModItems.class.getField(entry.getKey()).get(null));
            assertEquals("ssc_14:" + entry.getValue(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private static Map<String, String> expected() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("BANNER_NT", "banner_nt");
        result.put("BANNER_MED", "banner_med");
        result.put("BANNER_RN_D", "banner_rn_d");
        result.put("BANNER_SEC", "banner_sec");
        result.put("BANNER_CARGO", "banner_cargo");
        result.put("BANNER_ENG", "banner_eng");
        result.put("BANNER_SYND", "banner_synd");
        return Map.copyOf(result);
    }
}
