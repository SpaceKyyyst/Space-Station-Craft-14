package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;

/** Handles adding one cable layer to an existing sheathing block. */
public final class SheathingCableService {
    private SheathingCableService() { }

    public static void install(LevelAccessor level, BlockPos pos, Entity entity,
            Item lowVoltage, Item mediumVoltage, Item highVoltage) {
        if (level == null || entity == null || !(entity instanceof LivingEntity living)) return;
        if (living.level().clip(new ClipContext(living.getEyePosition(1.0F),
                living.getEyePosition(1.0F).add(living.getViewVector(1.0F).scale(6.0D)),
                ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, living).getDirection() != Direction.UP) return;
        var current = level.getBlockState(pos);
        if (!(current.getBlock() instanceof SheathingBlock)) return;
        ItemStack held = living.getMainHandItem();
        var property = held.is(lowVoltage) ? SheathingBlock.LV
                : held.is(mediumVoltage) ? SheathingBlock.MV
                : held.is(highVoltage) ? SheathingBlock.HV : null;
        if (property != null && !current.getValue(property)) {
            level.setBlock(pos, current.setValue(property, true), 3);
            held.shrink(1);
            if (!level.isClientSide()) CableTopologyService.invalidate(level, pos);
        }
    }
}
