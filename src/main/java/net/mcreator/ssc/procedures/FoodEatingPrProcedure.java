
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModAttributes;

import java.util.List;
import java.util.function.Supplier;

public class FoodEatingPrProcedure {

	private static class FoodProperties {
		final Supplier<Item> item;
		final int maxBites;         
		final String tasteMessage;  
		final Supplier<Item> trash; 

		FoodProperties(Supplier<Item> item, int maxBites, String tasteMessage, Supplier<Item> trash) {
			this.item = item;
			this.maxBites = maxBites;
			this.tasteMessage = tasteMessage;
			this.trash = trash;
		}
	}

	// ==========================================================================================
	// 📜 РЕЕСТР ВСЕЙ ЕДЫ МОДА (Добавляйте новые строки сюда)
	// ==========================================================================================
	private static final List<FoodProperties> FOODS = List.of(
		new FoodProperties(Ssc14ModItems.ENERGY_BAR_2, 2, "Nom. На вкус орехово, волокнисто, сладко и как овёс.", null),
		new FoodProperties(Ssc14ModItems.CATBURGER, 3, "Nom. На вкус как хлеб, мясо и... МЯУ.", null),
		new FoodProperties(Ssc14ModItems.CHEESEBURGER, 3, "Nom. На вкус как хлеб, мясо и сыр.", null),
		new FoodProperties(Ssc14ModItems.BREAD, 3, "Nom. На вкус как хлеб.", null),
		new FoodProperties(Ssc14ModItems.TOFU, 3, "Nom. На вкус пресно и немного соево.", null),
		new FoodProperties(Ssc14ModItems.BUN, 1, "Nom. На вкус как хлеб.", null),
		new FoodProperties(Ssc14ModItems.BREAD_SLICE, 1, "Nom. На вкус как хлеб.", null),
		new FoodProperties(Ssc14ModItems.TOFU_SLICE, 1, "Nom. На вкус пресно и немного соево.", null),
		new FoodProperties(Ssc14ModItems.TOFU_BURGER, 1, "Nom. На вкус как хлеб с тофу.", null),
		new FoodProperties(Ssc14ModItems.CHEESE, 1, "Nom. На вкус сырно.", null),
		new FoodProperties(Ssc14ModItems.CUTLET, 1, "Nom. На вкус как сырое перемолотое мясо.", null),
		new FoodProperties(Ssc14ModItems.FRIED_CUTLET, 1, "Nom. На вкус как котлета.", null),
		new FoodProperties(Ssc14ModItems.MEAT, 1, "Nom. На вкус как сырое мясо.", null),
		new FoodProperties(Ssc14ModItems.FRIED_MEAT, 1, "Nom. На вкус как стейк.", null),
		new FoodProperties(Ssc14ModItems.ROTTEN_MEAT, 1, "Nom. На вкус как гниль и плесень.", null),
		
		new FoodProperties(Ssc14ModItems.CHEESE_HONKERS, 1, "Nom. На вкус сырно.", Ssc14ModItems.CHEESE_HONKERS_TRASH),
		new FoodProperties(Ssc14ModItems.CHIPS, 1, "Nom. На вкус хрустяще.", Ssc14ModItems.CHIPS_TRASH),
		new FoodProperties(Ssc14ModItems.BORITOS, 1, "Nom. На вкус хрустяще.", Ssc14ModItems.BORITOS_TRASH),
		new FoodProperties(Ssc14ModItems.POPCORN, 1, "Nom. На вкус воздушно.", Ssc14ModItems.POPCORN_TRASH)
	);

	
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null || itemstack == null || itemstack.isEmpty()) return;
		if (!(entity instanceof LivingEntity livingEntity)) return;

		// Поиск предмета в реестре еды мода
		FoodProperties foundFood = null;
		for (FoodProperties prop : FOODS) {
			if (prop.item.get() == itemstack.getItem()) {
				foundFood = prop;
				break;
			}
		}
		if (foundFood == null) return;
		final FoodProperties food = foundFood;

		CompoundTag nbt = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		int currentProgress = nbt.getInt("EatingProgress").orElse(0);

		// Проверка сытости: контролируем желудок только перед началом укуса
		if (currentProgress == 0) {
			double digestion = livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
					? livingEntity.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue() : 0;
			if (digestion >= 10) {
				if (entity instanceof ServerPlayer player) {
					player.sendSystemMessage(Component.literal("В вас больше не влезет!"), true);
				}
				resetProgressBar(livingEntity);
				return;
			}
		}

		// 🔥 АВТОМАТИКА И ОТМЕНА: Проверяем стадию прогресс-бара
		if (currentProgress > 0) {
			// Если процедура вызвана повторным кликом ПКМ во время заполнения — это ОТМЕНА
			if (nbt.getBoolean("ButtonTriggered").orElse(false)) {
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> {
					tag.putInt("EatingProgress", 0);
					tag.putBoolean("ButtonTriggered", false);
				});
				resetProgressBar(livingEntity);
				return;
			}
		} else {
			// Самый первый клик ПКМ: ставим маркер триггера кнопки
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean("ButtonTriggered", true));
		}

		// Проверяем, держит ли игрок ту же самую стопку еды в руке
		ItemStack handItem = livingEntity.getMainHandItem();
		if (handItem.isEmpty() || handItem.getItem() != itemstack.getItem()) {
			resetProgressBar(livingEntity);
			return; 
		}

		// Запускаем автоматические шаги прогресс-бара от 1 до 6
		if (currentProgress < 6) {
			currentProgress++;
			int nextProg = currentProgress;
			CustomData.update(DataComponents.CUSTOM_DATA, handItem, tag -> {
				tag.putInt("EatingProgress", nextProg);
				tag.putBoolean("ButtonTriggered", false); // Очищаем триггер клика для шагов таймера
			});
			
			if (livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) {
				livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(currentProgress);
			}

			// Вызываем следующий автоматический шаг через 5 тиков
			net.mcreator.ssc.Ssc14Mod.queueServerWork(5, () -> {
				execute(world, x, y, z, entity, handItem);
			});
		} else {
			// Прогресс равен 6 — завершаем укус, сбрасываем бары
			double currentDigestion = livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
					? livingEntity.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue() : 0;
			finishBite(world, x, y, z, livingEntity, handItem, food, currentDigestion);
		}
	}
	private static void finishBite(LevelAccessor world, double x, double y, double z, LivingEntity livingEntity, ItemStack itemstack, FoodProperties food, double digestion) {
		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> {
			tag.putInt("EatingProgress", 0);
			tag.putBoolean("ButtonTriggered", false);
		});

		if (world instanceof Level level) {
			if (!level.isClientSide()) {
				level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:eating_sounds")), SoundSource.NEUTRAL,
						(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
			} else {
				level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:eating_sounds")), SoundSource.NEUTRAL,
						(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
			}
		}

		if (livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)) {
			livingEntity.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(digestion + 1);
		}

		if (livingEntity instanceof ServerPlayer player) {
			player.sendSystemMessage(Component.literal(food.tasteMessage), true);
		}

		Digestion20PrProcedure.execute(world, livingEntity);

		CompoundTag nbt = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		double currentBites = nbt.getDoubleOr("Nutrients", 0) + 1;

		if (currentBites < food.maxBites) {
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("Nutrients", currentBites));
			
			// Автоматический укус по второму кругу, если порции остались
			execute(world, x, y, z, livingEntity, itemstack);
		} else {
			if (food.trash != null && world instanceof ServerLevel serverLevel) {
				ItemEntity trashEntity = new ItemEntity(serverLevel, x, y, z, new ItemStack(food.trash.get()));
				trashEntity.setPickUpDelay(1);
				trashEntity.setUnlimitedLifetime();
				serverLevel.addFreshEntity(trashEntity);
			}
			itemstack.shrink(1);
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("Nutrients", 0));
			resetProgressBar(livingEntity);
		}
	}

	private static void resetProgressBar(LivingEntity livingEntity) {
		if (livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) {
			livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
		}
	}
}
