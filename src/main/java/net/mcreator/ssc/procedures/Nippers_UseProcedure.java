package net.mcreator.ssc.procedures;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.ProblemReporter;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Nippers_UseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_UP_FLOOR.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_LVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_MVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_HVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_L_MVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_L_HVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_M_HVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_LM_HVC.get()) {
			if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity26.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity29.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity33.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity36.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity40.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity43.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity47.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity50.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity54.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR.get()) {
													if (entity instanceof LivingEntity _livingEntity59 && _livingEntity59.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity59.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
														if (world instanceof ServerLevel _level) {
															ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.ROOD.get()));
															entityToSpawn.setPickUpDelay(1);
															entityToSpawn.setUnlimitedLifetime();
															_level.addFreshEntity(entityToSpawn);
														}
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														if (entity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity63.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity68 && _livingEntity68.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity68.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
													if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_LVC.get()) {
														if (entity instanceof LivingEntity _livingEntity73 && _livingEntity73.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity73.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity77 && _livingEntity77.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity77.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_MVC.get()) {
														if (entity instanceof LivingEntity _livingEntity81 && _livingEntity81.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity81.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity85 && _livingEntity85.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity85.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_HVC.get()) {
														if (entity instanceof LivingEntity _livingEntity89 && _livingEntity89.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity89.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity93 && _livingEntity93.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity93.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_L_MVC.get()) {
														if (entity instanceof LivingEntity _livingEntity97 && _livingEntity97.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity97.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_MVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity101 && _livingEntity101.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity101.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_L_HVC.get()) {
														if (entity instanceof LivingEntity _livingEntity105 && _livingEntity105.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity105.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_HVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity109 && _livingEntity109.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity109.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_M_HVC.get()) {
														if (entity instanceof LivingEntity _livingEntity113 && _livingEntity113.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity113.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_HVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity117 && _livingEntity117.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity117.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR_LM_HVC.get()) {
														if (entity instanceof LivingEntity _livingEntity121 && _livingEntity121.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity121.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_M_HVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity125 && _livingEntity125.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity125.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													}
													if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_UP_FLOOR.get()) {
														if (entity instanceof LivingEntity _livingEntity129 && _livingEntity129.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity129.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.5), (z + 0.5), new ItemStack(Ssc14ModItems.ROOD.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity133 && _livingEntity133.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity133.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													}
												} else {
													if (entity instanceof LivingEntity _livingEntity135 && _livingEntity135.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity135.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity137 && _livingEntity137.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity137.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity139 && _livingEntity139.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity139.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity141 && _livingEntity141.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity141.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity143 && _livingEntity143.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity143.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
		if (((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_HVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LHVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MHVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMHVC.get())
				&& (entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(6)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection()) == Direction.UP) {
			if (entity instanceof LivingEntity _livingEntity161 && _livingEntity161.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity161.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity165 && _livingEntity165.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity165.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity168 && _livingEntity168.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity168.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity172 && _livingEntity172.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity172.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity175 && _livingEntity175.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity175.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity179 && _livingEntity179.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity179.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity182 && _livingEntity182.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity182.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity186 && _livingEntity186.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity186.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity189 && _livingEntity189.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity189.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity193 && _livingEntity193.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity193.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
													if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LVC.get()) {
														if (entity instanceof LivingEntity _livingEntity198 && _livingEntity198.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity198.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity202 && _livingEntity202.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity202.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MVC.get()) {
														if (entity instanceof LivingEntity _livingEntity206 && _livingEntity206.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity206.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity210 && _livingEntity210.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity210.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_HVC.get()) {
														if (entity instanceof LivingEntity _livingEntity214 && _livingEntity214.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity214.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity218 && _livingEntity218.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity218.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMVC.get()) {
														if (entity instanceof LivingEntity _livingEntity222 && _livingEntity222.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity222.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING_MVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity226 && _livingEntity226.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity226.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LHVC.get()) {
														if (entity instanceof LivingEntity _livingEntity230 && _livingEntity230.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity230.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING_HVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity234 && _livingEntity234.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity234.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MHVC.get()) {
														if (entity instanceof LivingEntity _livingEntity238 && _livingEntity238.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity238.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING_HVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity242 && _livingEntity242.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity242.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMHVC.get()) {
														if (entity instanceof LivingEntity _livingEntity246 && _livingEntity246.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity246.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.SHEATHING_MHVC.get().defaultBlockState();
																BlockState _bso = world.getBlockState(_bp);
																for (Property<?> _propertyOld : _bso.getProperties()) {
																	Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
																	if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
																		try {
																			_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
																		} catch (Exception e) {
																		}
																}
																BlockEntity _be = world.getBlockEntity(_bp);
																CompoundTag _bnbt = null;
																if (_be != null) {
																	_bnbt = _be.saveWithFullMetadata(world.registryAccess());
																	_be.setRemoved();
																}
																world.setBlock(_bp, _bs, 3);
																if (_bnbt != null) {
																	_be = world.getBlockEntity(_bp);
																	if (_be != null) {
																		try {
																			_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
																		} catch (Exception ignored) {
																		}
																	}
																}
															}
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity250 && _livingEntity250.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity250.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													}
												} else {
													if (entity instanceof LivingEntity _livingEntity252 && _livingEntity252.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity252.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity254 && _livingEntity254.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity254.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity256 && _livingEntity256.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity256.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity258 && _livingEntity258.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity258.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity260 && _livingEntity260.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity260.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}
}