package net.mcreator.ssc.integration;

import javax.annotation.Nullable;
import net.ssc14.content.firearm.MagazineDescriptionService;
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
public final class LegacyFirearmInteractionAdapter implements FirearmInteractionService.Adapter {
    public static final LegacyFirearmInteractionAdapter INSTANCE =
            new LegacyFirearmInteractionAdapter();

    private LegacyFirearmInteractionAdapter() { }

    @Override
    public String tooltip(FirearmState state, ItemStack stack) {
        return state instanceof Mk58State
                ? MagazineDescriptionService.mk58(stack)
				: MagazineDescriptionService.execute(stack);
    }

    @Override
    public void use(FirearmState state, Level level, Player player, ItemStack stack) {
        if (state instanceof Mk58State) {
            FirearmInteractionService.toggleMk58(level, player, stack,
                    net.mcreator.ssc.init.Ssc14ModItems.MK_58.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.MK_58OPEN.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.MK_58NOMAGZ.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.MK_58OPEN_NO_MAGZ.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.MAGAZINE_PISTOL_35.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.BULLET_35ITEM.get());
        } else {
            FirearmInteractionService.usePistolMagazine(level, player, stack,
                    net.mcreator.ssc.init.Ssc14ModItems.MAGAZINE_PISTOL_35.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get(),
                    net.mcreator.ssc.init.Ssc14ModItems.BULLET_35ITEM.get());
        }
    }

    @Override
    public void swing(Mk58State state, LivingEntity entity, ItemStack stack) {
        FirearmInteractionService.fireMk58(entity.level(), entity, stack,
                net.mcreator.ssc.init.Ssc14ModItems.MK_58.get(),
                net.mcreator.ssc.init.Ssc14ModItems.MK_58NOMAGZ.get(),
                net.mcreator.ssc.init.Ssc14ModItems.MK_58OPEN.get(),
                net.mcreator.ssc.init.Ssc14ModItems.BULLET_35_CARTRIDGE.get(),
                net.ssc14.content.entity.Ssc14EntityTypes.BULLET_35.get());
    }

    @Override
    public void inventoryTick(
            FirearmState state,
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        if (state == Mk58State.CLOSED_WITH_MAGAZINE) {
            FirearmInteractionService.initializeMk58(stack);
        } else if (state == PistolMagazineState.LOADED) {
            FirearmInteractionService.initializePistolMagazine(stack);
        }
    }
}
