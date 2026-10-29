package net.ssc14.content.equipment;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.Identifier;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.content.equipment.SuitEquipmentService;

/** Transitional boundary for generated Curios lifecycle procedures. */
public final class CuriosEquipmentBridge {
    private CuriosEquipmentBridge() {
    }

    public static void onMagBootsEquip(Entity entity) {
        SuitEquipmentService.equipMovementModifier(entity, Identifier.parse("ssc_14:magboots"), 0.2,
                entity.level(), entity.getX(), entity.getY(), entity.getZ());
    }

    public static void onEvaEquip(Entity entity) {
        SuitEquipmentService.equipMovementModifier(entity, Identifier.parse("ssc_14:outerclothing"), 0.2,
                entity.level(), entity.getX(), entity.getY(), entity.getZ());
    }

    public static void onSalvageEquip(Entity entity) {
        SuitEquipmentService.equipMovementModifier(entity, Identifier.parse("ssc_14:salvage"), 0.25,
                entity.level(), entity.getX(), entity.getY(), entity.getZ());
    }

    public static void onSalvageUnequip(Entity entity) {
        if (entity instanceof net.minecraft.server.level.ServerPlayer player) {
            SuitEquipmentService.removeSalvageSuit(player, Ssc14ModItems.HARDSUIT_SALVAGE_HELMET.get(),
                    Identifier.parse("ssc_14:salvage"));
        }
    }
}
