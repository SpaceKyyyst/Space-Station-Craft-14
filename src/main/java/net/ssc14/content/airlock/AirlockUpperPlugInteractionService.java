package net.ssc14.content.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Handles server-side interaction with the upper airlock plug. */
public final class AirlockUpperPlugInteractionService {
    private static final TagKey<net.minecraft.world.level.block.Block> AIRLOCKS =
            TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("ssc14", "airlocks"));

    private AirlockUpperPlugInteractionService() { }

    public static void execute(Level level, BlockPos plugPos, Entity entity) {
        if (entity == null || level.isClientSide()) return;
        BlockPos airlockPos = plugPos.below();
        BlockState airlockState = level.getBlockState(airlockPos);
        if (!airlockState.is(AIRLOCKS)) return;
        net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure.execute(
                level, airlockPos.getX() + 0.5, airlockPos.getY() + 0.5,
                airlockPos.getZ() + 0.5, airlockState, entity);
    }
}
