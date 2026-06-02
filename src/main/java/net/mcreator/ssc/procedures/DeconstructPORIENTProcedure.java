
package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;

public class DeconstructPORIENTProcedure {
    public static Vec3 execute() {
        // Для частиц на гранях UP и DOWN
        // Возвращаем угол поворота вокруг X оси
        return new Vec3(Math.PI / 2, 0, 0); // 90 градусов (π/2 радиан)
    }
}
