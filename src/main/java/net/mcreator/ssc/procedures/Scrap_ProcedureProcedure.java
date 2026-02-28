package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Scrap_ProcedureProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))
				&& (entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection()) == Direction.UP) {
			if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity8.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity12.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity15.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity19.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity26.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity29.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity33.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity36.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity40.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
													if (entity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity45.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														if (world instanceof ServerLevel _level) {
															ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), (new ItemStack((world.getBlockState(BlockPos.containing(x, y, z))).getBlock())));
															entityToSpawn.setPickUpDelay(1);
															entityToSpawn.setUnlimitedLifetime();
															_level.addFreshEntity(entityToSpawn);
														}
														world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														if (entity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity51.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity53.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity55 && _livingEntity55.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity55.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity57 && _livingEntity57.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity57.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
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
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
			if (entity instanceof LivingEntity _livingEntity65 && _livingEntity65.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity65.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(33, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity69 && _livingEntity69.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity69.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity72 && _livingEntity72.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity72.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(33, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity76 && _livingEntity76.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity76.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity79 && _livingEntity79.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity79.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(33, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity83 && _livingEntity83.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity83.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity86 && _livingEntity86.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity86.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(33, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity90 && _livingEntity90.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity90.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity93 && _livingEntity93.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity93.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(33, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity97 && _livingEntity97.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity97.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
													if (entity instanceof LivingEntity _livingEntity102 && _livingEntity102.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity102.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(33, () -> {
														if (world instanceof ServerLevel _level) {
															ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), (new ItemStack((world.getBlockState(BlockPos.containing(x, y, z))).getBlock())));
															entityToSpawn.setPickUpDelay(1);
															entityToSpawn.setUnlimitedLifetime();
															_level.addFreshEntity(entityToSpawn);
														}
														world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														if (entity instanceof LivingEntity _livingEntity108 && _livingEntity108.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity108.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity110 && _livingEntity110.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity110.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity112 && _livingEntity112.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity112.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity114 && _livingEntity114.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity114.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity116 && _livingEntity116.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity116.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity118 && _livingEntity118.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity118.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip123 ? blockstate.getValue(_getip123) : -1) == 3) {
			if (entity instanceof LivingEntity _livingEntity124 && _livingEntity124.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity124.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity128 && _livingEntity128.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity128.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity131 && _livingEntity131.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity131.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity135 && _livingEntity135.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity135.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity138 && _livingEntity138.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity138.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity142 && _livingEntity142.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity142.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity145 && _livingEntity145.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity145.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity149 && _livingEntity149.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity149.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity152 && _livingEntity152.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity152.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity156 && _livingEntity156.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity156.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip162 ? blockstate.getValue(_getip162) : -1) == 3) {
													if (entity instanceof LivingEntity _livingEntity163 && _livingEntity163.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity163.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 4;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity166 && _livingEntity166.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity166.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity168 && _livingEntity168.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity168.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity170 && _livingEntity170.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity170.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity172 && _livingEntity172.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity172.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity174 && _livingEntity174.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity174.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity176 && _livingEntity176.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity176.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip181 ? blockstate.getValue(_getip181) : -1) == 6) {
			if (entity instanceof LivingEntity _livingEntity182 && _livingEntity182.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity182.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity186 && _livingEntity186.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity186.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity189 && _livingEntity189.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity189.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity193 && _livingEntity193.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity193.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity196 && _livingEntity196.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity196.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity200 && _livingEntity200.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity200.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity203 && _livingEntity203.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity203.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity207 && _livingEntity207.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity207.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity210 && _livingEntity210.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity210.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity214 && _livingEntity214.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity214.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip220 ? blockstate.getValue(_getip220) : -1) == 6) {
													if (entity instanceof LivingEntity _livingEntity221 && _livingEntity221.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity221.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 7;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity224 && _livingEntity224.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity224.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity226 && _livingEntity226.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity226.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity228 && _livingEntity228.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity228.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity230 && _livingEntity230.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity230.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity232 && _livingEntity232.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity232.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity234 && _livingEntity234.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity234.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip239 ? blockstate.getValue(_getip239) : -1) == 2) {
			if (entity instanceof LivingEntity _livingEntity240 && _livingEntity240.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity240.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity244 && _livingEntity244.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity244.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity247 && _livingEntity247.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity247.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity251 && _livingEntity251.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity251.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity254 && _livingEntity254.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity254.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity258 && _livingEntity258.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity258.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity261 && _livingEntity261.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity261.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity265 && _livingEntity265.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity265.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity268 && _livingEntity268.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity268.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity272 && _livingEntity272.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity272.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip278 ? blockstate.getValue(_getip278) : -1) == 2) {
													if (entity instanceof LivingEntity _livingEntity279 && _livingEntity279.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity279.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 3;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity282 && _livingEntity282.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity282.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity284 && _livingEntity284.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity284.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity286 && _livingEntity286.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity286.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity288 && _livingEntity288.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity288.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity290 && _livingEntity290.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity290.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity292 && _livingEntity292.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity292.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}
}