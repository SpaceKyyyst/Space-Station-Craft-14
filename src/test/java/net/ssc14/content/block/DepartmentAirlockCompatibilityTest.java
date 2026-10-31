package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.airlock.Ssc14AirlockBlockEntities;
import org.junit.jupiter.api.Test;

class DepartmentAirlockCompatibilityTest {
    private static final List<AirlockContract> TYPES = List.of(
            contract("Tech", "TECH_AIRLOCK_D_1", "tech_airlock_d_1"),
            contract("Atmospherics", "ATMOSPHERICS_AIRLOCK_D_1", "atmospherics_airlock_d_1"),
            contract("Cargo", "CARGO_AIRLOCK_D_1", "cargo_airlock_d_1"),
            contract("CentCom", "CENT_COM_AIRLOCK_D_1", "cent_com_airlock_d_1"),
            contract("Chemistry", "CHEMISTRY_AIRLOCK_D_1", "chemistry_airlock_d_1"),
            contract("Command", "COMMAND_AIRLOCK_D_1", "command_airlock_d_1"),
            contract("Engineering", "ENGINEERING_AIRLOCK_D_1", "engineering_airlock_d_1"),
            contract("Hydroponics", "HYDROPONICS_AIRLOCK_D_1", "hydroponics_airlock_d_1"),
            contract("Medical", "MEDICAL_AIRLOCK_D_1", "medical_airlock_d_1"),
            contract("Salvage", "SALVAGE_AIRLOCK_D_1", "salvage_airlock_d_1"),
            contract("Science", "SCIENCE_AIRLOCK_D_1", "science_airlock_d_1"),
            contract("Security", "SECURITY_AIRLOCK_D_1", "security_airlock_d_1"),
            contract("Syndicate", "SYNDICATE_AIRLOCK_D_1", "syndicate_airlock_d_1"),
            contract("Virology", "VIROLOGY_AIRLOCK_D_1", "virology_airlock_d_1"),
            contract("Glass", "GLASS_AIRLOCK_D_1", "glass_airlock_d_1"),
            contract("BaseGlass", "BASE_GLASS_AIRLOCK_D_1", "base_glass_airlock_d_1"));

    @Test
    void everyDepartmentAirlockHasCanonicalBlockAndBlockEntity()
            throws Exception {
        for (AirlockContract contract : TYPES) {
            String type = contract.type();
            Class<?> canonicalBlock = Class.forName(
                    "net.ssc14.content.block.airlock." + type + "AirlockD1Block");
            Class<?> legacyBlock = Class.forName(
                    "net.mcreator.ssc.block." + type + "AirlockD1Block");
            assertTrue(canonicalBlock.isAssignableFrom(legacyBlock), type + " block facade");
            legacyBlock.getConstructor(BlockBehaviour.Properties.class);

            Class<?> canonicalEntity = Class.forName(
                    "net.ssc14.content.block.airlock." + type + "AirlockD1BlockEntity");
            Class<?> legacyEntity = Class.forName(
                    "net.mcreator.ssc.block.entity." + type + "AirlockD1BlockEntity");
            assertTrue(canonicalEntity.isAssignableFrom(legacyEntity),
                    type + " block entity facade");

            Object canonicalBlockHolder = Ssc14Blocks.class.getField(contract.field()).get(null);
            assertEquals("ssc_14:" + contract.id(), canonicalBlockHolder.getClass()
                    .getMethod("getId").invoke(canonicalBlockHolder).toString());
            Object canonicalEntityHolder = Ssc14AirlockBlockEntities.class
                    .getField(contract.field()).get(null);
            assertEquals("ssc_14:" + contract.id(), canonicalEntityHolder.getClass()
                    .getMethod("getId").invoke(canonicalEntityHolder).toString());
        }
    }

    private static AirlockContract contract(String type, String field, String id) {
        return new AirlockContract(type, field, id);
    }

    private record AirlockContract(String type, String field, String id) { }
}
