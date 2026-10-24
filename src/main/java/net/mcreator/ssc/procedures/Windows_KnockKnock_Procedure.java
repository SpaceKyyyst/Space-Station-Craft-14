package net.mcreator.ssc.procedures;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.window.WindowKnockService;
/** @deprecated Use {@link WindowKnockService}. */
@Deprecated(forRemoval = false)
public final class Windows_KnockKnock_Procedure {
    private Windows_KnockKnock_Procedure() { }
    public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        return WindowKnockService.knock(world, x, y, z, entity);
    }
}
