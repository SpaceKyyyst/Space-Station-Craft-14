
package net.mcreator.ssc.procedures;

import net.minecraft.world.item.context.UseOnContext;
import net.ssc14.content.item.poster.PosterPlacementService;

/** @deprecated Poster placement is owned by {@link PosterPlacementService}. */
@Deprecated(forRemoval = false)
public final class Posters_InstallProcedure {
    private Posters_InstallProcedure() { }

    public static void execute(UseOnContext context) {
        PosterPlacementService.place(context);
    }
}
