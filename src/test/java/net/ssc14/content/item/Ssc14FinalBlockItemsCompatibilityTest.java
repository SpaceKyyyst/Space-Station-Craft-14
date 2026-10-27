package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14FinalBlockItemsCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            new Expected("SHEATHING", "sheathing"),
            new Expected("BASE_AIRLOCK_D_1", "base_airlock_d_1"),
            new Expected("CONSOLE_OF_ID", "console_of_id"),
            new Expected("PORTABLE_SHEATER", "portable_sheater"),
            new Expected("APC", "apc"),
            new Expected("PODSTATION", "podstation"),
            new Expected("DEBU_GGENERATOR", "debu_ggenerator"),
            new Expected("LAMP", "lamp"),
            new Expected("SMES", "smes"),
            new Expected("TECH_LAMP", "tech_lamp"),
            new Expected("DEBUG_LAMP", "debug_lamp"),
            new Expected("DEBUG_TECH_LAMP", "debug_tech_lamp"),
            new Expected("GRAVITY_GENERATOR", "gravity_generator"),
            new Expected("C_4", "c_4"),
            new Expected("MACHINE_FRAME", "machine_frame"),
            new Expected("MACHINE_FRAME_2", "machine_frame_2"),
            new Expected("STEEL_TABLE", "steel_table"),
            new Expected("STEEL_CHAIR", "steel_chair"),
            new Expected("WOOD_CHAIR", "wood_chair"),
            new Expected("OFICE_BLACK_CHAIR", "ofice_black_chair"),
            new Expected("OFICE_WHITE_CHAIR", "ofice_white_chair"),
            new Expected("BAR_CHAIR", "bar_chair"),
            new Expected("KOSTILNIBLOK", "kostilniblok"),
            new Expected("DEBUG_GRAV_GEN", "debug_grav_gen"),
            new Expected("NUCLEAR_WARHEAD", "nuclear_warhead"),
            new Expected("COMPUTER", "computer"),
            new Expected("CLOSET_SECURE", "closet_secure"),
            new Expected("CLOSET", "closet"),
            new Expected("DEBUG_13", "debug_13"),
            new Expected("EXTINGUISHER_CABINET", "extinguisher_cabinet"),
            new Expected("COMMUNICATION_SERVERS", "communication_servers"),
            new Expected("WOOD_TABLE", "wood_table"),
            new Expected("COMMUNICATION_CONSOLE", "communication_console"),
            new Expected("TECH_AIRLOCK_D_1", "tech_airlock_d_1"),
            new Expected("GAS_PRODUCER_O_2", "gas_producer_o_2"),
            new Expected("VENDING_AUT_1", "vending_aut_1"),
            new Expected("SHUTTERS", "shutters"),
            new Expected("BUTTON", "button"),
            new Expected("MICROWAWE", "microwawe"),
            new Expected("ATMOSPHERICS_AIRLOCK_D_1", "atmospherics_airlock_d_1"),
            new Expected("CARGO_AIRLOCK_D_1", "cargo_airlock_d_1"),
            new Expected("CENT_COM_AIRLOCK_D_1", "cent_com_airlock_d_1"),
            new Expected("CHEMISTRY_AIRLOCK_D_1", "chemistry_airlock_d_1"),
            new Expected("COMMAND_AIRLOCK_D_1", "command_airlock_d_1"),
            new Expected("ENGINEERING_AIRLOCK_D_1", "engineering_airlock_d_1"),
            new Expected("HYDROPONICS_AIRLOCK_D_1", "hydroponics_airlock_d_1"),
            new Expected("MEDICAL_AIRLOCK_D_1", "medical_airlock_d_1"),
            new Expected("SALVAGE_AIRLOCK_D_1", "salvage_airlock_d_1"),
            new Expected("SCIENCE_AIRLOCK_D_1", "science_airlock_d_1"),
            new Expected("SECURITY_AIRLOCK_D_1", "security_airlock_d_1"),
            new Expected("SYNDICATE_AIRLOCK_D_1", "syndicate_airlock_d_1"),
            new Expected("VIROLOGY_AIRLOCK_D_1", "virology_airlock_d_1"),
            new Expected("CLOSET_EMERG", "closet_emerg"),
            new Expected("CLOSET_FIRE", "closet_fire"),
            new Expected("CLOSET_BLACK", "closet_black"),
            new Expected("CLOSET_BLUE", "closet_blue"),
            new Expected("CLOSET_GRAY", "closet_gray"),
            new Expected("CLOSET_GREEN", "closet_green"),
            new Expected("CLOSET_MIXED", "closet_mixed"),
            new Expected("CLOSET_N_2", "closet_n_2"),
            new Expected("CLOSET_ORANGE", "closet_orange"),
            new Expected("CLOSET_PINK", "closet_pink"),
            new Expected("GLASS_TABLE", "glass_table"),
            new Expected("GLASS_AIRLOCK_D_1", "glass_airlock_d_1"),
            new Expected("BASE_GLASS_AIRLOCK_D_1", "base_glass_airlock_d_1"),
            new Expected("FUEL_TANK", "fuel_tank"),
            new Expected("ORDER_CONSOLE", "order_console"),
            new Expected("ORDER_PALLET", "order_pallet"),
            new Expected("SALE_PALLET", "sale_pallet"),
            new Expected("SALE_CONSOLE", "sale_console"),
            new Expected("ARM_CHAIR_BASE", "arm_chair_base"),
            new Expected("ARM_CHAIR_BROWN", "arm_chair_brown"),
            new Expected("ARM_CHAIR_CARP", "arm_chair_carp"),
            new Expected("EMERGENCY_LIGHT", "emergency_light"));

    @Test
    void canonicalRegistryOwnsFinalBlockItemsAndLegacyHoldersRemainAliases()
            throws ReflectiveOperationException {
        for (Expected expected : EXPECTED) {
            Field field = Ssc14Items.class.getField(expected.field());
            Object canonical = field.get(null);
            assertSame(canonical, Ssc14ModItems.class.getField(expected.field()).get(null));
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private record Expected(String field, String path) { }
}
