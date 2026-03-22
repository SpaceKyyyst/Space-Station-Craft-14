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
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles_up")))) {
			if (entity instanceof LivingEntity _livingEntity65 && _livingEntity65.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity65.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity69 && _livingEntity69.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity69.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity72 && _livingEntity72.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity72.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity76 && _livingEntity76.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity76.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity79 && _livingEntity79.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity79.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity83 && _livingEntity83.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity83.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity86 && _livingEntity86.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity86.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity90 && _livingEntity90.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity90.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity93 && _livingEntity93.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity93.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity97 && _livingEntity97.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity97.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles_up")))) {
													if (entity instanceof LivingEntity _livingEntity102 && _livingEntity102.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity102.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														if (world instanceof ServerLevel _level) {
															ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), y, (z + 0.5), (new ItemStack((world.getBlockState(BlockPos.containing(x, y, z))).getBlock())));
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
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
			if (entity instanceof LivingEntity _livingEntity122 && _livingEntity122.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity122.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(33, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity126 && _livingEntity126.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity126.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity129 && _livingEntity129.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity129.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(33, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity133 && _livingEntity133.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity133.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity136 && _livingEntity136.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity136.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(33, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity140 && _livingEntity140.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity140.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity143 && _livingEntity143.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity143.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(33, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity147 && _livingEntity147.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity147.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity150 && _livingEntity150.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity150.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(33, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity154 && _livingEntity154.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity154.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
													if (entity instanceof LivingEntity _livingEntity159 && _livingEntity159.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity159.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
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
														if (entity instanceof LivingEntity _livingEntity165 && _livingEntity165.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity165.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity167 && _livingEntity167.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity167.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity169 && _livingEntity169.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity169.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity171 && _livingEntity171.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity171.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity173 && _livingEntity173.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity173.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity175 && _livingEntity175.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity175.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip180 ? blockstate.getValue(_getip180) : -1) == 3) {
			if (entity instanceof LivingEntity _livingEntity181 && _livingEntity181.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity181.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity185 && _livingEntity185.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity185.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity188 && _livingEntity188.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity188.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity192 && _livingEntity192.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity192.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity195 && _livingEntity195.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity195.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity199 && _livingEntity199.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity199.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity202 && _livingEntity202.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity202.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity206 && _livingEntity206.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity206.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity209 && _livingEntity209.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity209.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity213 && _livingEntity213.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity213.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip219 ? blockstate.getValue(_getip219) : -1) == 3) {
													if (entity instanceof LivingEntity _livingEntity220 && _livingEntity220.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity220.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
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
														if (entity instanceof LivingEntity _livingEntity223 && _livingEntity223.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity223.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
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
							if (entity instanceof LivingEntity _livingEntity231 && _livingEntity231.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity231.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity233 && _livingEntity233.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity233.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip238 ? blockstate.getValue(_getip238) : -1) == 6) {
			if (entity instanceof LivingEntity _livingEntity239 && _livingEntity239.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity239.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity243 && _livingEntity243.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity243.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity246 && _livingEntity246.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity246.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity250 && _livingEntity250.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity250.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity253 && _livingEntity253.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity253.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity257 && _livingEntity257.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity257.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity260 && _livingEntity260.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity260.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity264 && _livingEntity264.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity264.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity267 && _livingEntity267.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity267.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity271 && _livingEntity271.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity271.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip277 ? blockstate.getValue(_getip277) : -1) == 6) {
													if (entity instanceof LivingEntity _livingEntity278 && _livingEntity278.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity278.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
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
														if (entity instanceof LivingEntity _livingEntity281 && _livingEntity281.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity281.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity283 && _livingEntity283.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity283.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity285 && _livingEntity285.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity285.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity287 && _livingEntity287.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity287.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity289 && _livingEntity289.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity289.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity291 && _livingEntity291.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity291.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip296 ? blockstate.getValue(_getip296) : -1) == 2) {
			if (entity instanceof LivingEntity _livingEntity297 && _livingEntity297.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity297.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity301 && _livingEntity301.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity301.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
					if (entity instanceof LivingEntity _livingEntity304 && _livingEntity304.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity304.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity308 && _livingEntity308.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity308.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
							if (entity instanceof LivingEntity _livingEntity311 && _livingEntity311.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity311.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity315 && _livingEntity315.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity315.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
									if (entity instanceof LivingEntity _livingEntity318 && _livingEntity318.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity318.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity322 && _livingEntity322.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity322.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()) {
											if (entity instanceof LivingEntity _livingEntity325 && _livingEntity325.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity325.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity329 && _livingEntity329.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity329.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.CROWBAR.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip335 ? blockstate.getValue(_getip335) : -1) == 2) {
													if (entity instanceof LivingEntity _livingEntity336 && _livingEntity336.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity336.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
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
														if (entity instanceof LivingEntity _livingEntity339 && _livingEntity339.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity339.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity341 && _livingEntity341.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity341.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity343 && _livingEntity343.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity343.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity345 && _livingEntity345.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity345.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity347 && _livingEntity347.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity347.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity349 && _livingEntity349.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity349.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}
}