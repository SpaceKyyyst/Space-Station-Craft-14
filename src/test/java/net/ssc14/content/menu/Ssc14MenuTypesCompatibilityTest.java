package net.ssc14.content.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14MenuTypesCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            expected("DOP_CRAFT_MENU", "dop_craft_menu"),
            expected("I_DCODE", "i_dcode"),
            expected("ACCESS_CONFIG_MENU", "access_config_menu"),
            expected("ACCESS_CONFIG_GUI", "access_config_gui"),
            expected("CABEL_PANNEL_AIRLOCK", "cabel_pannel_airlock"),
            expected("GRAV_GEN_GUI", "grav_gen_gui"),
            expected("MAC_FRAME_GUI", "mac_frame_gui"),
            expected("PD_APASSANGER_GUI", "pd_apassanger_gui"),
            expected("RCDGUI", "rcdgui"),
            expected("RCDGU_IWALLFLOOR", "rcdgu_iwallfloor"),
            expected("HEADSET_GU_IKOSTIL", "headset_gu_ikostil"),
            expected("COMMUN_CONSOLE_GUI", "commun_console_gui"),
            expected("VENDING_AUT_1_GUI", "vending_aut_1_gui"),
            expected("RCDGU_IWINDOWS", "rcdgu_iwindows"),
            expected("NET_CONFIG_GUI", "net_config_gui"),
            expected("EGG_CARTON_GUI", "egg_carton_gui"),
            expected("MICROWAWE_GUI", "microwawe_gui"),
            expected("DECAL_SCOLOR_GUI", "decal_scolor_gui"),
            expected("BACKPACK_GUI", "backpack_gui"),
            expected("ORDER_GUI", "order_gui"),
            expected("SALE_GUI", "sale_gui"));

    @Test
    void canonicalRegistryOwnsAllMenuTypesAndFactoryBindings()
            throws ReflectiveOperationException {
        assertEquals(21, EXPECTED.size());
        for (Expected expected : EXPECTED) {
            Field canonicalField = Ssc14MenuTypes.class.getField(expected.field());
            Object canonical = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
        Ssc14MenuTypes.validateFactoryBindings();
    }

    private static Expected expected(String field, String path) {
        return new Expected(field, path);
    }

    private record Expected(String field, String path) { }
}
