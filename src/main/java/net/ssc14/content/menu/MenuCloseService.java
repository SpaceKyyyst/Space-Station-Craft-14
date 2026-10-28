package net.ssc14.content.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public final class MenuCloseService {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}
