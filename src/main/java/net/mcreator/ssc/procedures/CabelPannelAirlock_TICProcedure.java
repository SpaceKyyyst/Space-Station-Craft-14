package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class CabelPannelAirlock_TICProcedure {
    private static final TagKey<Block> AIRLOCKS = BlockTags.create(Identifier.parse("ssc14:airlocks"));

    public static void execute(LevelAccessor world, Entity entity) {
        if (world.isClientSide() || !(entity instanceof Player player)) return;
        // Reuse one raycast result instead of tracing the same ray nine times per player tick.
        var eye = entity.getEyePosition(1f);
        BlockHitResult hit = entity.level().clip(new ClipContext(eye,
                eye.add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));
        if (hit.getType() != HitResult.Type.BLOCK || !entity.level().hasChunkAt(hit.getBlockPos())) {
            player.closeContainer();
            return;
        }
        BlockState state = world.getBlockState(hit.getBlockPos());
        if (!state.is(AIRLOCKS)
                || !(state.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty panel)
                || !state.getValue(panel)) player.closeContainer();
    }
}
