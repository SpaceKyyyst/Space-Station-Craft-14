package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.utility.DebugBroadcastService;

/** @deprecated Use {@link DebugBroadcastService}; retained for generated callers. */
@Deprecated(forRemoval = false)
public class DEBUG_item_PR_Procedure {
	public static void execute(LevelAccessor world) {
		DebugBroadcastService.broadcast(world);
	}
}
