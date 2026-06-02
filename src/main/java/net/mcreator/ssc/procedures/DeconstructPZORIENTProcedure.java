
package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;

public class DeconstructPZORIENTProcedure {
    public static Vec3 execute() {
        // Для частиц на гранях NORTH и SOUTH
        // SOUTH не требует поворота, NORTH требует 180 градусов
        // Но так как мы спавним обе грани с одним типом частицы,
        // используем 0 (для SOUTH), а NORTH будет выглядеть правильно
        // благодаря двойному рендеру (перевёрнутой стороне)
        return new Vec3(0, 0, 0); // 0 градусов (без поворота)
    }
}
