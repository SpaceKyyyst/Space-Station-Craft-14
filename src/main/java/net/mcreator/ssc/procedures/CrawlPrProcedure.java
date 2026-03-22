package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class CrawlPrProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player player) {
			boolean isCrawling = player.getPersistentData().getBoolean("ssc14_is_crawling").orElse(false);
			player.getPersistentData().putBoolean("ssc14_is_crawling", !isCrawling);
		}
		entity.stopRiding();
	}
}