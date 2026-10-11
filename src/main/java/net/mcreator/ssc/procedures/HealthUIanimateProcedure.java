
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;

public class HealthUIanimateProcedure {

	public static double execute(Entity entity) {
		if (entity == null) return 0.0;

		// Получаем официальное ванильное время тиков мира
		long gameTime = entity.level().getGameTime();

		// УМНОЖАЕМ НА 2.8: Это ускоряет «внутренние часы» анимации, 
		// чтобы весь цикл из 28 спрайтов прокручивался ровно за ~2 секунды!
		double acceleratedTime = (double) (gameTime * 2.8);

		// Делаем красивый зацикленный таймер от 0 до 112 с помощью остатка от деления
		double loopTimer = acceleratedTime % 113.0;

		// Возвращаем номер спрайта для твоего UI, делённый на 4, строго по твоей оригинальной логике
		return Math.round(loopTimer / 4.0);
	}
}
