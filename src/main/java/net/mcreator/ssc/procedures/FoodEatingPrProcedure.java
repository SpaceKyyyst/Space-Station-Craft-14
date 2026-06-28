package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class FoodEatingPrProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (false == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("eating", false)) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity2.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			{
				final String _tagName = "eating";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			Ssc14Mod.queueServerWork(5, () -> {
				if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
						&& true == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("eating", false)) {
					if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity10.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(5, () -> {
						if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
								&& true == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("eating", false)) {
							if (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity16.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(5, () -> {
								if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
										&& true == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("eating", false)) {
									if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(5, () -> {
										if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
												&& true == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("eating", false)) {
											if (entity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity28.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(5, () -> {
												if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
														&& true == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("eating", false)) {
													if (entity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity34.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(5, () -> {
														{
															final String _tagName = "eating";
															final boolean _tagValue = false;
															CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
														}
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:eating_sounds")), SoundSource.NEUTRAL,
																		(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:eating_sounds")), SoundSource.NEUTRAL,
																		(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
															}
														}
														if ((entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																? _livingEntity40.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																: 0) < 10) {
															if (itemstack.getItem() == Ssc14ModItems.ENERGY_BAR_2.get()) {
																if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0) < 1) {
																	{
																		final String _tagName = "Nutrients";
																		final double _tagValue = (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0));
																		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
																	}
																	if (entity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity50.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity49.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																} else {
																	itemstack.shrink(1);
																	if (entity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity54.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity53.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																}
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal(
																			"Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043E\u0440\u0435\u0445\u043E\u0432\u043E, \u0432\u043E\u043B\u043E\u043A\u043D\u0438\u0441\u0442\u043E, \u0441\u043B\u0430\u0434\u043A\u043E \u0438 \u043A\u0430\u043A \u043E\u0432\u0451\u0441."),
																			true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.CATBURGER.get()) {
																if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0) < 2) {
																	{
																		final String _tagName = "Nutrients";
																		final double _tagValue = (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0));
																		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
																	}
																	if (entity instanceof LivingEntity _livingEntity65 && _livingEntity65.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity65.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity64 && _livingEntity64.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity64.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																} else {
																	itemstack.shrink(1);
																	if (entity instanceof LivingEntity _livingEntity69 && _livingEntity69.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity69.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity68 && _livingEntity68.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity68.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																}
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(
																			Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0445\u043B\u0435\u0431, \u043C\u044F\u0441\u043E \u0438... \u041C\u042F\u0423."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.CHEESEBURGER.get()) {
																if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0) < 2) {
																	{
																		final String _tagName = "Nutrients";
																		final double _tagValue = (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0));
																		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
																	}
																	if (entity instanceof LivingEntity _livingEntity80 && _livingEntity80.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity80.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity79 && _livingEntity79.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity79.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																} else {
																	itemstack.shrink(1);
																	if (entity instanceof LivingEntity _livingEntity84 && _livingEntity84.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity84.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity83 && _livingEntity83.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity83.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																}
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(
																			Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0445\u043B\u0435\u0431, \u043C\u044F\u0441\u043E \u0438 \u0441\u044B\u0440."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.BREAD.get()) {
																if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0) < 2) {
																	{
																		final String _tagName = "Nutrients";
																		final double _tagValue = (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0));
																		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
																	}
																	if (entity instanceof LivingEntity _livingEntity95 && _livingEntity95.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity95.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity94 && _livingEntity94.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity94.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																} else {
																	itemstack.shrink(1);
																	if (entity instanceof LivingEntity _livingEntity99 && _livingEntity99.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity99.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity98 && _livingEntity98.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity98.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																}
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0445\u043B\u0435\u0431."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.TOFU.get()) {
																if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0) < 2) {
																	{
																		final String _tagName = "Nutrients";
																		final double _tagValue = (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Nutrients", 0));
																		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
																	}
																	if (entity instanceof LivingEntity _livingEntity110 && _livingEntity110.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity110.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity109 && _livingEntity109.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity109.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																} else {
																	itemstack.shrink(1);
																	if (entity instanceof LivingEntity _livingEntity114 && _livingEntity114.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																		_livingEntity114.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																				.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity113 && _livingEntity113.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																						? _livingEntity113.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																						: 0)));
																}
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(
																			Component.literal(
																					"Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043F\u0440\u0435\u0441\u043D\u043E \u0438 \u043D\u0435\u043C\u043D\u043E\u0433\u043E \u0441\u043E\u0435\u0432\u043E."),
																			true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.CHEESE_HONKERS.get()) {
																if (world instanceof ServerLevel _level) {
																	ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Ssc14ModItems.CHEESE_HONKERS_TRASH.get()));
																	entityToSpawn.setPickUpDelay(1);
																	entityToSpawn.setUnlimitedLifetime();
																	_level.addFreshEntity(entityToSpawn);
																}
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity122 && _livingEntity122.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity122.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity121 && _livingEntity121.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity121.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u0441\u044B\u0440\u043D\u043E."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.CHIPS.get()) {
																if (world instanceof ServerLevel _level) {
																	ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Ssc14ModItems.CHIPS_TRASH.get()));
																	entityToSpawn.setPickUpDelay(1);
																	entityToSpawn.setUnlimitedLifetime();
																	_level.addFreshEntity(entityToSpawn);
																}
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity130 && _livingEntity130.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity130.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity129 && _livingEntity129.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity129.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u0445\u0440\u0443\u0441\u0442\u044F\u0449\u0435."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.BORITOS.get()) {
																if (world instanceof ServerLevel _level) {
																	ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Ssc14ModItems.BORITOS_TRASH.get()));
																	entityToSpawn.setPickUpDelay(1);
																	entityToSpawn.setUnlimitedLifetime();
																	_level.addFreshEntity(entityToSpawn);
																}
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity138 && _livingEntity138.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity138.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity137 && _livingEntity137.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity137.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u0445\u0440\u0443\u0441\u0442\u044F\u0449\u0435."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.POPCORN.get()) {
																if (world instanceof ServerLevel _level) {
																	ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Ssc14ModItems.POPCORN_TRASH.get()));
																	entityToSpawn.setPickUpDelay(1);
																	entityToSpawn.setUnlimitedLifetime();
																	_level.addFreshEntity(entityToSpawn);
																}
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity146 && _livingEntity146.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity146.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity145 && _livingEntity145.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity145.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u0432\u043E\u0437\u0434\u0443\u0448\u043D\u043E."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.BUN.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity153 && _livingEntity153.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity153.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity152 && _livingEntity152.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity152.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0445\u043B\u0435\u0431."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.BREAD_SLICE.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity160 && _livingEntity160.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity160.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity159 && _livingEntity159.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity159.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0445\u043B\u0435\u0431."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.TOFU_SLICE.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity167 && _livingEntity167.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity167.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity166 && _livingEntity166.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity166.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(
																			Component.literal(
																					"Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043F\u0440\u0435\u0441\u043D\u043E \u0438 \u043D\u0435\u043C\u043D\u043E\u0433\u043E \u0441\u043E\u0435\u0432\u043E."),
																			true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.TOFU_BURGER.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity174 && _livingEntity174.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity174.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity173 && _livingEntity173.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity173.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0445\u043B\u0435\u0431 \u0441 \u0442\u043E\u0444\u0443."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.CHEESE.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity181 && _livingEntity181.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity181.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity180 && _livingEntity180.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity180.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u0441\u044B\u0440\u043D\u043E."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.CUTLET.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity188 && _livingEntity188.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity188.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity187 && _livingEntity187.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity187.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal(
																			"Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0441\u044B\u0440\u043E\u0435 \u043F\u0435\u0440\u0435\u043C\u043E\u043B\u043E\u0442\u043E\u0435 \u043C\u044F\u0441\u043E."),
																			true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.FRIED_CUTLET.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity195 && _livingEntity195.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity195.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity194 && _livingEntity194.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity194.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u043A\u043E\u0442\u043B\u0435\u0442\u0430."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.MEAT.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity202 && _livingEntity202.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity202.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity201 && _livingEntity201.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity201.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0441\u044B\u0440\u043E\u0435 \u043C\u044F\u0441\u043E."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.FRIED_MEAT.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity209 && _livingEntity209.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity209.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity208 && _livingEntity208.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity208.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0441\u0442\u0435\u0439\u043A."), true);
																Digestion20PrProcedure.execute(world, entity);
															} else if (itemstack.getItem() == Ssc14ModItems.ROTTEN_MEAT.get()) {
																itemstack.shrink(1);
																if (entity instanceof LivingEntity _livingEntity216 && _livingEntity216.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
																	_livingEntity216.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																			.setBaseValue((1 + (entity instanceof LivingEntity _livingEntity215 && _livingEntity215.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES)
																					? _livingEntity215.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue()
																					: 0)));
																if (entity instanceof Player _player && !_player.level().isClientSide())
																	_player.displayClientMessage(
																			Component.literal("Nom. \u041D\u0430 \u0432\u043A\u0443\u0441 \u043A\u0430\u043A \u0433\u043D\u0438\u043B\u044C \u0438 \u043F\u043B\u0435\u0441\u0435\u043D\u044C."), true);
																Digestion20PrProcedure.execute(world, entity);
															}
														} else {
															if (entity instanceof Player _player && !_player.level().isClientSide())
																_player.displayClientMessage(Component.literal("\u0412 \u0432\u0430\u0441 \u0431\u043E\u043B\u044C\u0448\u0435 \u043D\u0435 \u0432\u043B\u0435\u0437\u0435\u0442!"), true);
														}
														if (entity instanceof LivingEntity _livingEntity219 && _livingEntity219.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity219.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity221 && _livingEntity221.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity221.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity223 && _livingEntity223.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity223.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity225 && _livingEntity225.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity225.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity227 && _livingEntity227.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity227.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity229 && _livingEntity229.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity229.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else {
			{
				final String _tagName = "eating";
				final boolean _tagValue = false;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
		}
	}
}