package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class ActiveWelder_UseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		double fuel = 0;
		// (костыль для MC)
		if (getPropertyByName(blockstate, "12345") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1) && entity.isInWater()) {
			if (world instanceof ServerLevel _level) {
				_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("123").withColor(0x000000), false);
			}
			if (world instanceof Level _level)
				_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
		}
		// === ШАГ 0: БАЗОВАЯ ПРОВЕРКА НА ОШИБКИ ===
		// Если сущность (игрок) не существует — выходим, чтобы не было краша
		if (entity == null)
			return;
		// 🔧 КАК ИЗМЕНИТЬ ПОД СЕБЯ:
		// - Замени "12345" на имя нужного свойства блока
		// - Замени Component.literal("123") на свой текст
		// - Удали весь блок, если он не нужен
		// === ШАГ 2: ПРОВЕРКА, ЧТО ЭТО ЖИВОЕ СУЩЕСТВО (ИГРОК/МОБ) ===
		// Приводим entity к LivingEntity (чтобы работать с инвентарём, атрибутами)
		if (!(entity instanceof LivingEntity livingEntity))
			return;
		// Проверяем, что у игрока есть нужные атрибуты (созданные в твоём моде)
		// Если атрибута нет — выходим, чтобы не было ошибки
		if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			return;
		if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
			return;
		// === ШАГ 3: ПОДГОТОВКА ПЕРЕМЕННЫХ ===
		// Преобразуем double-координаты в BlockPos (целочисленные координаты блока)
		BlockPos pos = BlockPos.containing(x, y, z);
		// Создаём "хеш" позиции игрока (сумма координат) — чтобы проверять, не сдвинулся ли он
		// Это дешевле, чем сравнивать 3 координаты отдельно каждый тик
		double posHash = entity.getX() + entity.getY() + entity.getZ();
		// Получаем тип блока, по которому кликнули (без свойств, только сам блок)
		Block targetBlock = blockstate.getBlock();
		// === ШАГ 4: НАСТРОЙКА СВАРКИ ЧЕРЕЗ RECORD (Java 21) ===
		/**
		* record — это специальный тип в Java 21 для хранения неизменяемых данных.
		* Мы упаковываем все настройки сварки в один объект, чтобы удобно передавать их
		* во внутренний класс WeldProcess (там переменные должны быть final).
		*
		* Параметры WeldConfig:
		* - delay: задержка между шагами прогресса (в тиках, 20 тиков = 1 секунда)
		* - resultBlock: блок, в который превратится исходный (если нужна полная замена)
		* - dropItem: предмет, который выпадет при завершении (если нужен)
		* - targetState: новое значение свойства блока (если меняем свойство, а не весь блок)
		* - stateProp: само свойство блока, которое меняем (например, "blockstate")
		*/
		record WeldConfig(int delay, Block resultBlock, net.minecraft.world.item.Item dropItem, int targetState, IntegerProperty stateProp) {
		}
		// Объявляем переменную config как final — обязательно для использования внутри внутреннего класса!
		final WeldConfig config;
		// === ОПРЕДЕЛЯЕМ НАСТРОЙКИ В ЗАВИСИМОСТИ ОТ ТИПА БЛОКА ===
		// СЛУЧАЙ 1: Стальная стена (STEEL_WALL) — полная замена блока
		if (targetBlock == Ssc14ModBlocks.STEEL_WALL.get()) {
			// Создаём конфиг: 20 тиков задержка, результат — WALL_CARCASE, дроп — STEEL
			config = new WeldConfig(20, Ssc14ModBlocks.WALL_CARCASE.get(), Ssc14ModItems.STEEL.get(), -1, null);
			// СЛУЧАЙ 2: Пластисталь или Бронированное окно — меняем свойство блока
		} else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get() || targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
			// Ищем в свойствах блока свойство с именем "blockstate" типа IntegerProperty
			IntegerProperty stateProp = null;
			for (var prop : blockstate.getProperties()) {
				if (prop.getName().equals("blockstate") && prop instanceof IntegerProperty ip) {
					stateProp = ip; // нашли!
					break;
				}
			}
			// Если свойство найдено — проверяем его текущее значение
			if (stateProp != null) {
				int state = blockstate.getValue(stateProp); // текущее значение свойства
				// Для PLASTEEL_WALL:
				if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
					if (state == 2) // если свойство = 2 → меняем на 3
						config = new WeldConfig(13, null, null, 3, stateProp);
					else if (state == 5) // если свойство = 5 → меняем на 6
						config = new WeldConfig(27, null, null, 6, stateProp);
					else
						return; // другое значение — не обрабатываем
				}
				// Для ARMORED_WINDOW:
				else if (targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
					if (state == 0)
						config = new WeldConfig(20, null, null, 1, stateProp);
					else if (state == 3)
						config = new WeldConfig(27, null, null, 4, stateProp);
					else
						return;
				} else
					return;
			} else
				return; // свойство не найдено — выходим
		} else
			return; // блок не подходит для сварки — выходим
		// === ШАГ 5: ЛОКАЛЬНЫЙ КЛАСС "WeldProcess" — ДВИЖОК ПРОГРЕССА ===
		/**
		* Этот класс отвечает за пошаговый процесс сварки:
		* - Проверяет, не сдвинулся ли игрок
		* - Проигрывает частицы и звуки
		* - Обновляет прогресс-бар
		* - По завершении меняет блок и тратит топливо
		*
		* Почему внутренний класс? Чтобы иметь доступ к переменным execute (pos, config, livingEntity и т.д.)
		*/
		class WeldProcess {
			/**
			* run(int step) — главный метод, который вызывает сам себя рекурсивно
			* @param step: текущий шаг прогресса (от 1 до 6)
			*/
			void run(int step) {
				// === ПРОВЕРКИ НА КАЖДОМ ШАГЕ (если что-то не так — сбрасываем прогресс) ===
				// 1. Игрок сдвинулся? (сравниваем хеш позиции)
				if (entity.getX() + entity.getY() + entity.getZ() != posHash) {
					reset();
					return;
				}
				// 2. В главной руке больше нет сварочного аппарата?
				if (!livingEntity.getMainHandItem().is(Ssc14ModItems.ACTIVE_WELDER.get())) {
					reset();
					return;
				}
				// 3. Блок на позиции изменился или исчез?
				if (!world.getBlockState(pos).is(targetBlock)) {
					reset();
					return;
				}
				// === ВИЗУАЛЬНЫЕ ЭФФЕКТЫ (частицы + звук) ===
				// Отправляем частицы искр (только на сервере, чтобы синхронизировать с клиентами)
				if (world instanceof ServerLevel sl)
					sl.sendParticles(Ssc14ModParticleTypes.SPARK.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, // центр блока
							20, // количество частиц
							0.2, 0.2, 0.2, // разброс по X, Y, Z
							0.2); // скорость частиц
				// Настраиваем звук: случайная высота тона (0.8 - 1.2) для естественности
				ResourceLocation sfx = ResourceLocation.parse("ssc_14:welding_work");
				float pitch = 0.8F + (world instanceof Level l ? l.random : RandomSource.create()).nextFloat() * 0.4F;
				// Проигрываем звук через вспомогательный метод (см. ниже)
				playLevelSound(world, pos, sfx, 0.2F, pitch);
				// === ЛОГИКА ПРОГРЕССА ===
				// Если ещё не 6 шагов — продолжаем процесс
				if (step < 6) {
					// Обновляем значение атрибута прогресс-бара (для отображения в интерфейсе)
					livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);
					// Планируем следующий шаг через config.delay() тиков
					// () -> run(step + 1) — это лямбда-выражение: "вызови run со следующим шагом"
					Ssc14Mod.queueServerWork(config.delay(), () -> run(step + 1));
				} else {
					// === ФИНАЛЬНЫЙ ШАГ (шаг 6) — ЗАВЕРШЕНИЕ СВАРКИ ===
					// Получаем предмет в главной руке для работы с его данными (топливо)
					ItemStack stack = livingEntity.getMainHandItem();
					// Читаем значение "fuel" из кастомных данных предмета (NBT-тег)
					double fuel = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 0);
					// Проверяем, достаточно ли топлива (больше 450)
					if (fuel > 450) {
						// Проигрываем финальный звук успеха
						playLevelSound(world, pos, ResourceLocation.parse("ssc_14:welder_use"), 1F, 1F);
						// === МЕНЯЕМ БЛОК ===
						// Вариант А: полная замена блока (если config.resultBlock задан)
						if (config.resultBlock() != null) {
							world.setBlock(pos, config.resultBlock().defaultBlockState(), 3);
							// Флаг 3 = обновить блок + отправить обновление клиентам + пересчитать свет
						}
						// Вариант Б: меняем только свойство блока (если задано stateProp)
						else if (config.stateProp() != null) {
							world.setBlock(pos, world.getBlockState(pos).setValue(config.stateProp(), config.targetState()), 3);
						}
						// === ВЫПАДЕНИЕ ПРЕДМЕТА (если задан dropItem) ===
						if (config.dropItem() != null && world instanceof ServerLevel sLevel) {
							// Создаём сущность предмета в центре блока
							ItemEntity item = new ItemEntity(sLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(config.dropItem()));
							item.setPickUpDelay(1); // задержка 1 тик, чтобы игрок не подобрал мгновенно
							item.setUnlimitedLifetime(); // предмет не исчезнет со временем
							sLevel.addFreshEntity(item); // добавляем в мир
						}
						// === ТРАТИМ ТОПЛИВО ===
						// Обновляем тег "fuel" в предмете: отнимаем 350
						CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("fuel", fuel - 350));
					}
					// Сбрасываем прогресс-бар в 0 (независимо от успеха)
					livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			}

			/**
			* reset() — сбрасывает прогресс, если что-то пошло не так
			* Вызывается при прерывании процесса (игрок сдвинулся, сменил предмет и т.п.)
			*/
			void reset() {
				livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
			}
		}
		// === ЗАПУСКАЕМ ПРОЦЕСС С ПЕРВОГО ШАГА ===
		new WeldProcess().run(1);
	} // ← КОНЕЦ МЕТОДА execute
		// ============================================================================
		// ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ (вне execute, но внутри класса)
		// ============================================================================

	/**
	* playLevelSound — универсальный метод для проигрывания звука
	* Автоматически определяет, сервер это или клиент, и использует правильный метод
	*
	* @param world: мир
	* @param pos: позиция блока
	* @param soundLoc: идентификатор звука (например, "ssc_14:welding_work")
	* @param volume: громкость (1.0 = 100%)
	* @param pitch: высота тона (1.0 = нормальная)
	*/
	private static void playLevelSound(LevelAccessor world, BlockPos pos, ResourceLocation soundLoc, float volume, float pitch) {
		// Работаем только если world — это полноценный Level (а не LevelAccessor)
		if (world instanceof Level lvl) {
			// Получаем объект SoundEvent по его идентификатору
			net.minecraft.sounds.SoundEvent sound = BuiltInRegistries.SOUND_EVENT.getValue(soundLoc);
			// Если звук найден в реестре
			if (sound != null) {
				// На сервере: проигрываем звук всем игрокам вокруг
				if (!lvl.isClientSide())
					lvl.playSound(null, pos, sound, SoundSource.NEUTRAL, volume, pitch);
				// На клиенте: проигрываем локально (false = не передавать другим игрокам)
				else
					lvl.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.NEUTRAL, volume, pitch, false);
			}
		}
		/**
		* getPropertyByName — ищет свойство блока по имени
		* Нужен, потому что в MCreator неудобно работать с динамическими свойствами
		*
		* @param state: состояние блока
		* @param name: имя свойства (например, "blockstate", "open", "powered")
		* @return: объект Property, если найден, или null
		*/
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}