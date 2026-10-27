package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.RCDinterfParticGenerateProcedure;
import net.mcreator.ssc.procedures.RSU_useOnBlock_Procedure;
import net.mcreator.ssc.procedures.RSU_use_Procedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.ssc14.content.item.tool.RsuInteractionService;

/** Connects the canonical RSU item to the generated runtime during migration. */
public final class LegacyRsuInteractionAdapter
        implements RsuInteractionService.Adapter {
    @Override
    public void use(Level level, Player player, InteractionHand hand,
            ItemStack stack) {
        RSU_use_Procedure.execute(level, player.getX(), player.getY(), player.getZ(),
                player, stack);
    }

    @Override
    public void useOn(UseOnContext context) {
        var pos = context.getClickedPos();
        RSU_useOnBlock_Procedure.execute(context.getLevel(), pos.getX(), pos.getY(),
                pos.getZ(), context.getPlayer(), context.getItemInHand());
    }

    @Override
    public void inventoryTick(ServerLevel level, Entity entity, ItemStack stack) {
        RCDinterfParticGenerateProcedure.execute(level, entity.getX(), entity.getY(),
                entity.getZ(), entity, stack);
    }
}
