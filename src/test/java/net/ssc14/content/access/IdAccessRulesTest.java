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

    @Test
    void requiresEveryCatalogAccessKey() {
        CompoundTag cardData = new CompoundTag();
        for (String key : IdJobCatalog.accessKeys()) {
            cardData.putBoolean(key, true);
        }

        assertEquals(true, IdAccessRules.hasAllAccess(cardData));
        cardData.putBoolean("Security", false);
        assertEquals(false, IdAccessRules.hasAllAccess(cardData));
    }
}
