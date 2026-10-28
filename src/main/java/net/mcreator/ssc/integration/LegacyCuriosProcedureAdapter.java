package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.MagBootsCAPIBaubleIsEquippedProcedure;
import net.mcreator.ssc.procedures.OuterclothingEvaCAPIBaubleIsEquippedProcedure;
import net.mcreator.ssc.procedures.OuterclothingSalvageCAPIBaubleIsEquippedProcedure;
import net.mcreator.ssc.procedures.OuterclothingSalvageCAPIBaubleIsUnequippedProcedure;
import net.minecraft.world.entity.Entity;

/** Transitional boundary for generated Curios lifecycle procedures. */
public final class LegacyCuriosProcedureAdapter {
    private LegacyCuriosProcedureAdapter() {
    }

    public static void onMagBootsEquip(Entity entity) {
        MagBootsCAPIBaubleIsEquippedProcedure.execute(entity);
    }

    public static void onEvaEquip(Entity entity) {
        OuterclothingEvaCAPIBaubleIsEquippedProcedure.execute(
                entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    public static void onSalvageEquip(Entity entity) {
        OuterclothingSalvageCAPIBaubleIsEquippedProcedure.execute(
                entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    public static void onSalvageUnequip(Entity entity) {
        OuterclothingSalvageCAPIBaubleIsUnequippedProcedure.execute(entity);
    }
}
