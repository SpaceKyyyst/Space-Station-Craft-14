package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.item.tool.AirlockControllerService;

/** @deprecated Use {@link AirlockControllerService}. */
@Deprecated(forRemoval = false)
public final class AirlockController_DEBUG_prProcedure {
    private AirlockController_DEBUG_prProcedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z,
            BlockState state, Entity entity, ItemStack controller) {
        AirlockControllerService.interact(world, x, y, z, state, entity, controller);
    }
}
