
package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;

public class DeconstructPXORIENTProcedure {
    public static Vec3 execute() {
        // Для частиц на гранях EAST и WEST
        // Возвращаем угол поворота вокруг Y оси
        return new Vec3(0, Math.PI / 2, 0); // 90 градусов (π/2 радиан)
    }
}
