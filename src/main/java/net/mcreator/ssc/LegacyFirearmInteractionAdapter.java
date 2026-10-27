package net.mcreator.ssc;

import javax.annotation.Nullable;
import net.mcreator.ssc.procedures.MK58_Breech_Open_Procedure;
import net.mcreator.ssc.procedures.MK58_DescriptionProcedure;
import net.mcreator.ssc.procedures.MK58_Fire_Procedure;
import net.mcreator.ssc.procedures.MK58_GENERATE_Procedure;
import net.mcreator.ssc.procedures.MagazinePistol35_Description_Procedure;
import net.mcreator.ssc.procedures.MagazinePistol35_GENERATE_Procedure;
import net.mcreator.ssc.procedures.MagazinePistol35_USE_Procedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ssc14.content.item.weapon.FirearmInteractionService;
import net.ssc14.content.item.weapon.FirearmState;
import net.ssc14.content.item.weapon.Mk58State;
import net.ssc14.content.item.weapon.PistolMagazineState;

/** Adapter retained until the MK-58 procedures move into canonical packages. */
final class LegacyFirearmInteractionAdapter implements FirearmInteractionService.Adapter {
    static final LegacyFirearmInteractionAdapter INSTANCE =
            new LegacyFirearmInteractionAdapter();

    private LegacyFirearmInteractionAdapter() { }

    @Override
    public String tooltip(FirearmState state, ItemStack stack) {
        return state instanceof Mk58State
                ? MK58_DescriptionProcedure.execute(stack)
                : MagazinePistol35_Description_Procedure.execute(stack);
    }

    @Override
    public void use(FirearmState state, Level level, Player player, ItemStack stack) {
        if (state instanceof Mk58State) {
            MK58_Breech_Open_Procedure.execute(level, player, stack);
        } else {
            MagazinePistol35_USE_Procedure.execute(level, player, stack);
        }
    }

    @Override
    public void swing(Mk58State state, LivingEntity entity, ItemStack stack) {
        MK58_Fire_Procedure.execute(entity.level(), entity, stack);
    }

    @Override
    public void inventoryTick(
            FirearmState state,
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        if (state == Mk58State.CLOSED_WITH_MAGAZINE) {
            MK58_GENERATE_Procedure.execute(stack);
        } else if (state == PistolMagazineState.LOADED) {
            MagazinePistol35_GENERATE_Procedure.execute(stack);
        }
    }
}
