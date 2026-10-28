package net.ssc14.common.airlock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AirlockAccessPolicyTest {
    @Test
    void preservesDepartmentalAccessMapping() {
        assertEquals("Technical", AirlockAccessPolicy.accessKeyFor("tech_airlock_d_1"));
        assertEquals("Medical", AirlockAccessPolicy.accessKeyFor("virology_airlock_d_1"));
        assertNull(AirlockAccessPolicy.accessKeyFor("base_airlock_d_1"));
    }
}
