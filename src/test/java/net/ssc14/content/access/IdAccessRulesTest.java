package net.ssc14.content.access;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

class IdAccessRulesTest {
    @Test
    void returnsOnlyKnownGrantedAccessKeysInCatalogOrder() {
        CompoundTag cardData = new CompoundTag();
        cardData.putBoolean("Security", true);
        cardData.putBoolean("Atmos", true);
        cardData.putBoolean("unknown", true);

        assertEquals(List.of("Security", "Atmos"), IdAccessRules.grantedAccessKeys(cardData));
    }
}
