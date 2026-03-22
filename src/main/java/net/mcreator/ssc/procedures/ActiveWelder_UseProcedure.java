package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
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
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if (entity.getX() + entity.getZ()
				+ entity.getY() == (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity7.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
				&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
			if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity10.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
				}
			}
			Ssc14Mod.queueServerWork(20, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity17.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
						&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
					if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					Ssc14Mod.queueServerWork(20, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity29.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
								&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
							if (entity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity34.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							if (world instanceof ServerLevel _level)
								_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
											(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2),
											false);
								}
							}
							Ssc14Mod.queueServerWork(20, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity41 && _livingEntity41.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity41.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
										&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
									if (entity instanceof LivingEntity _livingEntity46 && _livingEntity46.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity46.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									if (world instanceof ServerLevel _level)
										_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
													(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
										} else {
											_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
													(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
										}
									}
									Ssc14Mod.queueServerWork(20, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity53.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
												&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
											if (entity instanceof LivingEntity _livingEntity58 && _livingEntity58.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity58.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											if (world instanceof ServerLevel _level)
												_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
											if (world instanceof Level _level) {
												if (!_level.isClientSide()) {
													_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
															(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
												} else {
													_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
															(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
												}
											}
											Ssc14Mod.queueServerWork(20, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity65 && _livingEntity65.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity65.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
													if (entity instanceof LivingEntity _livingEntity70 && _livingEntity70.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity70.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													if (world instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
													if (world instanceof Level _level) {
														if (!_level.isClientSide()) {
															_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
																	(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
														} else {
															_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welding_work")), SoundSource.NEUTRAL, (float) 0.2,
																	(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
														}
													}
													Ssc14Mod.queueServerWork(20, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity77 && _livingEntity77.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity77.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel",
																		0) > 450
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
															if (world instanceof ServerLevel _level)
																_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPARK.get()), (x + 0.5), (y + 0.5), (z + 0.5), 20, 0.2, 0.2, 0.2, 0.2);
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.WALL_CARCASE.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																world.setBlock(_bp, _bs, 3);
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.5), (z + 0.5), new ItemStack(Ssc14ModItems.STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															{
																final String _tagName = "fuel";
																final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
																		.getDoubleOr("fuel", 0) - 350);
																CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
															}
															if (entity instanceof LivingEntity _livingEntity92 && _livingEntity92.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity92.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity93 && _livingEntity93.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity93.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity95 && _livingEntity95.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity95.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity97 && _livingEntity97.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity97.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity99 && _livingEntity99.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity99.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity101 && _livingEntity101.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity101.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity103 && _livingEntity103.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity103.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if (entity.getX() + entity.getZ()
				+ entity.getY() == (entity instanceof LivingEntity _livingEntity108 && _livingEntity108.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity108.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
				&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip112 ? blockstate.getValue(_getip112) : -1) == 2) {
			if (entity instanceof LivingEntity _livingEntity113 && _livingEntity113.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity113.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(13, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity117 && _livingEntity117.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity117.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
						&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
					if (entity instanceof LivingEntity _livingEntity122 && _livingEntity122.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity122.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(13, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity126 && _livingEntity126.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity126.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
								&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
							if (entity instanceof LivingEntity _livingEntity131 && _livingEntity131.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity131.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(13, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity135 && _livingEntity135.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity135.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
										&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
									if (entity instanceof LivingEntity _livingEntity140 && _livingEntity140.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity140.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(13, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity144 && _livingEntity144.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity144.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
												&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
											if (entity instanceof LivingEntity _livingEntity149 && _livingEntity149.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity149.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(13, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity153 && _livingEntity153.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity153.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
													if (entity instanceof LivingEntity _livingEntity158 && _livingEntity158.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity158.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(13, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity162 && _livingEntity162.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity162.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel",
																		0) > 450
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
																&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip170 ? blockstate.getValue(_getip170) : -1) == 2) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															{
																int _value = 3;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
															}
															{
																final String _tagName = "fuel";
																final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
																		.getDoubleOr("fuel", 0) - 350);
																CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
															}
															if (entity instanceof LivingEntity _livingEntity177 && _livingEntity177.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity177.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity178 && _livingEntity178.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity178.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity180 && _livingEntity180.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity180.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity182 && _livingEntity182.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity182.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity184 && _livingEntity184.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity184.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity186 && _livingEntity186.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity186.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity188 && _livingEntity188.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity188.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if (entity.getX() + entity.getZ()
				+ entity.getY() == (entity instanceof LivingEntity _livingEntity193 && _livingEntity193.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity193.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
				&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip197 ? blockstate.getValue(_getip197) : -1) == 5) {
			if (entity instanceof LivingEntity _livingEntity198 && _livingEntity198.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity198.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(27, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity202 && _livingEntity202.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity202.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
						&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
					if (entity instanceof LivingEntity _livingEntity207 && _livingEntity207.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity207.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(27, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity211 && _livingEntity211.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity211.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
								&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
							if (entity instanceof LivingEntity _livingEntity216 && _livingEntity216.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity216.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(27, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity220 && _livingEntity220.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity220.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
										&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
									if (entity instanceof LivingEntity _livingEntity225 && _livingEntity225.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity225.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(27, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity229 && _livingEntity229.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity229.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
												&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
											if (entity instanceof LivingEntity _livingEntity234 && _livingEntity234.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity234.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(27, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity238 && _livingEntity238.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity238.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
													if (entity instanceof LivingEntity _livingEntity243 && _livingEntity243.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity243.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(27, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity247 && _livingEntity247.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity247.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel",
																		0) > 450
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
																&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip255 ? blockstate.getValue(_getip255) : -1) == 5) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															{
																int _value = 6;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
															}
															{
																final String _tagName = "fuel";
																final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
																		.getDoubleOr("fuel", 0) - 350);
																CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
															}
															if (entity instanceof LivingEntity _livingEntity262 && _livingEntity262.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity262.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity263 && _livingEntity263.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity263.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity265 && _livingEntity265.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity265.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity267 && _livingEntity267.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity267.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity269 && _livingEntity269.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity269.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity271 && _livingEntity271.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity271.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity273 && _livingEntity273.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity273.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if (entity.getX() + entity.getZ()
				+ entity.getY() == (entity instanceof LivingEntity _livingEntity278 && _livingEntity278.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity278.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
				&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip282 ? blockstate.getValue(_getip282) : -1) == 0) {
			if (entity instanceof LivingEntity _livingEntity283 && _livingEntity283.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity283.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(20, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity287 && _livingEntity287.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity287.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
						&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
					if (entity instanceof LivingEntity _livingEntity292 && _livingEntity292.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity292.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(20, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity296 && _livingEntity296.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity296.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
								&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
							if (entity instanceof LivingEntity _livingEntity301 && _livingEntity301.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity301.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(20, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity305 && _livingEntity305.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity305.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
										&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
									if (entity instanceof LivingEntity _livingEntity310 && _livingEntity310.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity310.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(20, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity314 && _livingEntity314.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity314.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
												&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
											if (entity instanceof LivingEntity _livingEntity319 && _livingEntity319.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity319.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(20, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity323 && _livingEntity323.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity323.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
													if (entity instanceof LivingEntity _livingEntity328 && _livingEntity328.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity328.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(20, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity332 && _livingEntity332.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity332.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel",
																		0) > 450
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
																&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip340 ? blockstate.getValue(_getip340) : -1) == 0) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															{
																int _value = 1;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
															}
															{
																final String _tagName = "fuel";
																final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
																		.getDoubleOr("fuel", 0) - 350);
																CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
															}
															if (entity instanceof LivingEntity _livingEntity347 && _livingEntity347.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity347.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity348 && _livingEntity348.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity348.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity350 && _livingEntity350.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity350.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity352 && _livingEntity352.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity352.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity354 && _livingEntity354.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity354.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity356 && _livingEntity356.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity356.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity358 && _livingEntity358.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity358.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if (entity.getX() + entity.getZ()
				+ entity.getY() == (entity instanceof LivingEntity _livingEntity363 && _livingEntity363.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity363.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
				&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip367 ? blockstate.getValue(_getip367) : -1) == 3) {
			if (entity instanceof LivingEntity _livingEntity368 && _livingEntity368.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity368.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(27, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity372 && _livingEntity372.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity372.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
						&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
					if (entity instanceof LivingEntity _livingEntity377 && _livingEntity377.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity377.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(27, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity381 && _livingEntity381.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity381.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
								&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
							if (entity instanceof LivingEntity _livingEntity386 && _livingEntity386.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity386.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(27, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity390 && _livingEntity390.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity390.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
										&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
									if (entity instanceof LivingEntity _livingEntity395 && _livingEntity395.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity395.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(27, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity399 && _livingEntity399.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity399.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
												&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
											if (entity instanceof LivingEntity _livingEntity404 && _livingEntity404.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity404.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(27, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity408 && _livingEntity408.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity408.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
													if (entity instanceof LivingEntity _livingEntity413 && _livingEntity413.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity413.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(27, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity417 && _livingEntity417.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity417.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel",
																		0) > 450
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
																&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip425 ? blockstate.getValue(_getip425) : -1) == 3) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															{
																int _value = 4;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
															}
															{
																final String _tagName = "fuel";
																final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
																		.getDoubleOr("fuel", 0) - 350);
																CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
															}
															if (entity instanceof LivingEntity _livingEntity432 && _livingEntity432.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity432.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity433 && _livingEntity433.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity433.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity435 && _livingEntity435.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity435.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity437 && _livingEntity437.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity437.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity439 && _livingEntity439.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity439.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity441 && _livingEntity441.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity441.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity443 && _livingEntity443.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity443.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}
}