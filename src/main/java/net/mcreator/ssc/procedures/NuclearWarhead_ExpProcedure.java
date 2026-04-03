package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.Ssc14Mod;

public class NuclearWarhead_ExpProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0414\u0435\u0442\u043E\u043D\u0430\u0446\u0438\u044F \u0447\u0435\u0440\u0435\u0437"), false);
		}
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("3...").withColor(0xff7f7f), false);
		}
		Ssc14Mod.queueServerWork(20, () -> {
			if (world instanceof ServerLevel _level) {
				_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("2...").withColor(0xff3f3f), false);
			}
			Ssc14Mod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("1...").withColor(0xff0000), false);
				}
				Ssc14Mod.queueServerWork(20, () -> {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0411\u0410\u0425").withColor(0xff0000), false);
					}
					world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
					// Проверяем, что мир — это серверный уровень (взрывы только на сервере!)
					if (!(world instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
						return;
					}
					// Параметры эллипсоидальной ударной волны
					final int totalRings = 10; // Количество колец
					final double ringSpacing = 10.0; // Базовое расстояние между кольцами
					final float power = 25.0f; // Мощность взрыва
					final int baseExplosions = 15; // Количество взрывов на первом кольце
					// Пропорции эллипсоида: горизонталь (XZ) : вертикаль (Y) = 10 : 5 = 2 : 1
					final double horizontalScale = 1.0; // Масштаб по осям X и Z (100%)
					final double verticalScale = 0.5; // Масштаб по оси Y (50%)
					// Золотое сечение для равномерного распределения точек на поверхности
					final double goldenRatio = (1 + Math.sqrt(5)) / 2;
					// Создаём все 20 эллипсоидальных колец
					for (int ring = 0; ring < totalRings; ring++) {
						// Текущий базовый радиус (10, 20, 30, 40...)
						double baseRadius = ringSpacing * (ring + 1);
						// Количество взрывов для текущего кольца (растёт на 30% каждый раз)
						int explosionsThisRing = (int) Math.round(baseExplosions * Math.pow(1.3, ring));
						// Распределяем точки равномерно по поверхности эллипсоида
						for (int i = 0; i < explosionsThisRing; i++) {
							// Сферические координаты (золотая спираль)
							double theta = 2 * Math.PI * i / goldenRatio;
							double phi = Math.acos(1 - 2 * (i + 0.5) / explosionsThisRing);
							// Преобразуем в декартовы координаты с учётом эллипсоидального масштаба
							// Горизонталь (X, Z): полный радиус
							// Вертикаль (Y): половина радиуса (пропорция 10:5)
							double ex = x + baseRadius * horizontalScale * Math.sin(phi) * Math.cos(theta);
							double ey = y + baseRadius * verticalScale * Math.cos(phi);
							double ez = z + baseRadius * horizontalScale * Math.sin(phi) * Math.sin(theta);
							// Создаём взрыв
							serverLevel.explode(null, ex, ey, ez, power, false, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
						}
					}
				});
			});
		});
	}
}