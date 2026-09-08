
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

public class AirlockUpPlug_ClickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide()) return;

        BlockPos airlockPos = BlockPos.containing(x, y - 1, z);
        BlockState airlockState = world.getBlockState(airlockPos);

        if (airlockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("ssc14", "airlocks")))) {
            if (world instanceof Level level) {
                BaseAirlockOpenCloseProcedure.execute(
                    level,
                    airlockPos.getX() + 0.5,
                    airlockPos.getY() + 0.5,
                    airlockPos.getZ() + 0.5,
                    airlockState,
                    entity
                );
            }
        }
    }
}
