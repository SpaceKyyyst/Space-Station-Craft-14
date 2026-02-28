package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
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
															}
														} else {
															if (entity instanceof Player _player && !_player.level().isClientSide())
																_player.displayClientMessage(Component.literal("\u0412 \u0432\u0430\u0441 \u0431\u043E\u043B\u044C\u0448\u0435 \u043D\u0435 \u0432\u043B\u0435\u0437\u0435\u0442!"), true);
														}
														if (entity instanceof LivingEntity _livingEntity57 && _livingEntity57.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity57.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity59 && _livingEntity59.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity59.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity61 && _livingEntity61.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity61.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity63.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity65 && _livingEntity65.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity65.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity67 && _livingEntity67.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity67.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
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