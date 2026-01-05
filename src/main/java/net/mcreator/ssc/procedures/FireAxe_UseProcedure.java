package net.mcreator.ssc.procedures;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
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
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class FireAxe_UseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_HVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LHVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MHVC.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMHVC.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.UPER_SHEATHING.get()) {
			if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(10, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity26.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.FIRE_AXE.get()) {
					if (entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity29.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(10, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity33.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.FIRE_AXE.get()) {
							if (entity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity36.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(10, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity40.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.FIRE_AXE.get()) {
									if (entity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity43.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(10, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity47.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.FIRE_AXE.get()) {
											if (entity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity50.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(10, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity54.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.FIRE_AXE.get()
														&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_HVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LHVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MHVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMHVC.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING.get())) {
													if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LVC.get()) {
														if (entity instanceof LivingEntity _livingEntity75 && _livingEntity75.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity75.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_LVC.get().defaultBlockState();
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity79 && _livingEntity79.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity79.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MVC.get()) {
														if (entity instanceof LivingEntity _livingEntity83 && _livingEntity83.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity83.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity87 && _livingEntity87.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity87.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_HVC.get()) {
														if (entity instanceof LivingEntity _livingEntity91 && _livingEntity91.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity91.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity95 && _livingEntity95.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity95.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMVC.get()) {
														if (entity instanceof LivingEntity _livingEntity99 && _livingEntity99.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity99.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_L_MVC.get().defaultBlockState();
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity103 && _livingEntity103.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity103.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LHVC.get()) {
														if (entity instanceof LivingEntity _livingEntity107 && _livingEntity107.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity107.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_L_HVC.get().defaultBlockState();
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity111 && _livingEntity111.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity111.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_MHVC.get()) {
														if (entity instanceof LivingEntity _livingEntity115 && _livingEntity115.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity115.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity119 && _livingEntity119.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity119.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING_LMHVC.get()) {
														if (entity instanceof LivingEntity _livingEntity123 && _livingEntity123.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity123.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
															{
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockState _bs = Ssc14ModBlocks.ROD_FLOOR_LM_HVC.get().defaultBlockState();
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity127 && _livingEntity127.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity127.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING.get()) {
														if (entity instanceof LivingEntity _livingEntity131 && _livingEntity131.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity131.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(10, () -> {
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
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 1), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (entity instanceof LivingEntity _livingEntity135 && _livingEntity135.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity135.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													}
												} else if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity140 && _livingEntity140.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity140.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.FIRE_AXE.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.UPER_SHEATHING.get()) {
													if (entity instanceof LivingEntity _livingEntity145 && _livingEntity145.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity145.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(10, () -> {
														{
															BlockPos _bp = BlockPos.containing(x, y, z);
															BlockState _bs = Ssc14ModBlocks.ROD_UP_FLOOR.get().defaultBlockState();
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
															ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.1), (y + 0.5), (z + 0.1), new ItemStack(Ssc14ModBlocks.TITLE_STEEL.get()));
															entityToSpawn.setPickUpDelay(1);
															entityToSpawn.setUnlimitedLifetime();
															_level.addFreshEntity(entityToSpawn);
														}
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:title_off")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														if (entity instanceof LivingEntity _livingEntity149 && _livingEntity149.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity149.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity151 && _livingEntity151.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity151.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity153 && _livingEntity153.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity153.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity155 && _livingEntity155.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity155.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity157 && _livingEntity157.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity157.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity159 && _livingEntity159.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity159.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}
}