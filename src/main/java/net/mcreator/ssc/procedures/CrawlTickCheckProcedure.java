package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class CrawlTickCheckProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player player) {
			boolean isCrawling = player.getPersistentData().getBoolean("ssc14_is_crawling").orElse(false);
			System.out.println("[CRAWL] Tick - isCrawling: " + isCrawling);
			if (isCrawling) {
				// В режиме ползания — устанавливаем позу
				if (player.getPose() != Pose.SWIMMING) {
					player.setPose(Pose.SWIMMING);
					player.refreshDimensions();
					System.out.println("[CRAWL] Set to SWIMMING");
				}
			} else {
				// Проверяем, можно ли встать — точная коллизия с хитбоксом
				AABB standingBox = player.getDimensions(Pose.STANDING).makeBoundingBox(player.getX(), player.getY(), player.getZ());
				boolean canStand = player.level().noCollision(player, standingBox);
				if (canStand && player.getPose() == Pose.SWIMMING) {
					player.setPose(Pose.STANDING);
					player.refreshDimensions();
					System.out.println("[CRAWL] Set to STANDING");
				} else if (!canStand) {
					System.out.println("[CRAWL] Cannot stand - blocks in the way");
					// ВАЖНО: НЕ включаем ползание автоматически!
					// Просто остаёмся в текущей позе
				}
			}
		}
		entity.stopRiding();
	}
}