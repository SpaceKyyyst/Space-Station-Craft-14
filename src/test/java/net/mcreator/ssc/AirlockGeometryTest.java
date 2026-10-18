package net.mcreator.ssc;

import net.mcreator.ssc.access.AirlockGeometry;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AirlockGeometryTest {
    @Test
    void missingWidthUsesStandardDoor() {
        var data = new CompoundTag();
        assertEquals(1, AirlockGeometry.width(data));
        AirlockGeometry.initialize(data);
        assertEquals(1, data.getIntOr(AirlockGeometry.WIDTH_KEY, 0));
    }

    @Test
    void persistedWidthIsClampedToSupportedRange() {
        var data = new CompoundTag();
        data.putInt(AirlockGeometry.WIDTH_KEY, 0);
        assertEquals(1, AirlockGeometry.width(data));
        data.putInt(AirlockGeometry.WIDTH_KEY, 2);
        assertEquals(2, AirlockGeometry.width(data));
        data.putInt(AirlockGeometry.WIDTH_KEY, 99);
        assertEquals(3, AirlockGeometry.width(data));
    }
}
