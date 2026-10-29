package net.ssc14.content.item.tool;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.ssc14.content.item.tool.RsuInteractionService;
import net.ssc14.content.item.tool.RsuDeconstructionService;
import net.ssc14.content.item.tool.RsuConstructionService;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.item.Ssc14Items;

/** Connects the canonical RSU item to the generated runtime during migration. */
public final class LegacyRsuInteractionAdapter
        implements RsuInteractionService.Adapter {
    @Override
    public void use(Level level, Player player, InteractionHand hand,
            ItemStack stack) {
        RsuInteractionService.useLegacy(level, player, stack,
                Ssc14Items.RC_DCARTRIDGE.get());
    }

    @Override
    public void useOn(UseOnContext context) {
        var pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        double mode = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("Mode", 0);
        if (mode == 1 && context.getPlayer() != null
                && RsuDeconstructionService.start(context.getLevel(), pos, context.getPlayer(),
                stack, Ssc14Items.RSU.get(),
                Ssc14Attributes.PROGRESS_BAR_ATRB)) {
            return;
        }
        if (mode >= 2 && mode <= 10 && context.getPlayer() != null
                && RsuConstructionService.start(context.getLevel(), pos, context.getClickedFace(),
                context.getPlayer(), stack, Ssc14Items.RSU.get(), (int) mode)) {
            return;
        }
    }

    @Override
    public void inventoryTick(ServerLevel level, Entity entity, ItemStack stack) {
        net.ssc14.content.item.tool.RsuAimingParticleService.execute(level, entity.getX(), entity.getY(),
                entity.getZ(), entity, stack, Ssc14Items.RSU.get());
    }
}
