package net.ssc14.common.airlock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AirlockAccessPolicyTest {
    @Test
    void preservesDepartmentalAccessMapping() {
        assertEquals("Technical", AirlockAccessPolicy.accessKeyFor("tech_airlock_d_1"));
        assertEquals("Medical", AirlockAccessPolicy.accessKeyFor("virology_airlock_d_1"));
        assertNull(AirlockAccessPolicy.accessKeyFor("base_airlock_d_1"));
    }

    @Test
    void resetContractContainsMappedKeys() {
        assertTrue(AirlockAccessPolicy.accessKeys().contains("Technical"));
        assertTrue(AirlockAccessPolicy.accessKeys().contains("Security"));
        assertTrue(AirlockAccessPolicy.accessKeys().contains("Medical"));
    }
}
