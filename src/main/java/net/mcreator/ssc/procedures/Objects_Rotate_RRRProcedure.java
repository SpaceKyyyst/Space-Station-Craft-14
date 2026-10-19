package net.mcreator.ssc.procedures;

import net.mcreator.ssc.rotation.RotateCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

/** Generated compatibility facade; all checks and mutation belong to the server command. */
public class Objects_Rotate_RRRProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity instanceof ServerPlayer player && world == player.level()) {
			RotateCommand.execute(player, 0);
		}
	}
}
