package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
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

public class Nippers_UseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR.get()) {
			if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity6.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity10.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity13.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity17.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity20.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity24.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity27.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity31.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity34.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity38.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR.get()
														&& 0 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip44 ? blockstate.getValue(_getip44) : -1)) {
													if (entity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity45.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
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
														if (entity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity49.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity54.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_FLOOR.get()
														&& 0 < (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip60 ? blockstate.getValue(_getip60) : -1)) {
													if (1 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip62 ? blockstate.getValue(_getip62) : -1)) {
														if (entity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity63.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 0;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity67 && _livingEntity67.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity67.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if (2 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip70 ? blockstate.getValue(_getip70) : -1)) {
														if (entity instanceof LivingEntity _livingEntity71 && _livingEntity71.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity71.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 0;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity75 && _livingEntity75.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity75.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if (3 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip78 ? blockstate.getValue(_getip78) : -1)) {
														if (entity instanceof LivingEntity _livingEntity79 && _livingEntity79.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity79.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 0;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity83 && _livingEntity83.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity83.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if (4 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip86 ? blockstate.getValue(_getip86) : -1)) {
														if (entity instanceof LivingEntity _livingEntity87 && _livingEntity87.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity87.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 2;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity91 && _livingEntity91.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity91.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if (5 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip94 ? blockstate.getValue(_getip94) : -1)) {
														if (entity instanceof LivingEntity _livingEntity95 && _livingEntity95.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity95.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 3;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity99 && _livingEntity99.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity99.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if (6 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip102 ? blockstate.getValue(_getip102) : -1)) {
														if (entity instanceof LivingEntity _livingEntity103 && _livingEntity103.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity103.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 3;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity107 && _livingEntity107.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity107.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													} else if (7 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip110 ? blockstate.getValue(_getip110) : -1)) {
														if (entity instanceof LivingEntity _livingEntity111 && _livingEntity111.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity111.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(1, () -> {
															{
																int _value = 6;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
															if (entity instanceof LivingEntity _livingEntity115 && _livingEntity115.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity115.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														});
													}
												} else if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity120 && _livingEntity120.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity120.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_UP_FLOOR.get()) {
													if (entity instanceof LivingEntity _livingEntity125 && _livingEntity125.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity125.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
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
														if (entity instanceof LivingEntity _livingEntity129 && _livingEntity129.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity129.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity131 && _livingEntity131.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity131.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity133 && _livingEntity133.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity133.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
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
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_UP_FLOOR.get()) {
			if (entity instanceof LivingEntity _livingEntity143 && _livingEntity143.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity143.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity147 && _livingEntity147.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity147.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity150 && _livingEntity150.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity150.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity154 && _livingEntity154.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity154.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity157 && _livingEntity157.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity157.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity161 && _livingEntity161.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity161.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity164 && _livingEntity164.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity164.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity168 && _livingEntity168.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity168.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity171 && _livingEntity171.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity171.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity175 && _livingEntity175.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity175.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
													if (entity instanceof LivingEntity _livingEntity178 && _livingEntity178.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity178.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity182 && _livingEntity182.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity182.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ROD_UP_FLOOR.get()
																&& 0 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip188 ? blockstate.getValue(_getip188) : -1)) {
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
															if (entity instanceof LivingEntity _livingEntity192 && _livingEntity192.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity192.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity193 && _livingEntity193.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity193.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity195 && _livingEntity195.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity195.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity197 && _livingEntity197.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity197.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity199 && _livingEntity199.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity199.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity201 && _livingEntity201.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity201.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity203 && _livingEntity203.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity203.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip208 ? blockstate.getValue(_getip208) : -1) > 0
				&& (entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(6)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection()) == Direction.UP) {
			if (entity instanceof LivingEntity _livingEntity211 && _livingEntity211.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity211.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity215 && _livingEntity215.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity215.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity218 && _livingEntity218.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity218.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity222 && _livingEntity222.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity222.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity225 && _livingEntity225.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity225.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity229 && _livingEntity229.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity229.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity232 && _livingEntity232.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity232.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity236 && _livingEntity236.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity236.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity239 && _livingEntity239.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity239.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity243 && _livingEntity243.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity243.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
													if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SHEATHING.get()) {
														if (1 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip249 ? blockstate.getValue(_getip249) : -1)) {
															if (entity instanceof LivingEntity _livingEntity250 && _livingEntity250.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity250.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 0;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity254 && _livingEntity254.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity254.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														} else if (2 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip257 ? blockstate.getValue(_getip257) : -1)) {
															if (entity instanceof LivingEntity _livingEntity258 && _livingEntity258.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity258.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 0;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity262 && _livingEntity262.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity262.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														} else if (3 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip265 ? blockstate.getValue(_getip265) : -1)) {
															if (entity instanceof LivingEntity _livingEntity266 && _livingEntity266.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity266.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 0;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity270 && _livingEntity270.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity270.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														} else if (4 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip273 ? blockstate.getValue(_getip273) : -1)) {
															if (entity instanceof LivingEntity _livingEntity274 && _livingEntity274.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity274.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 2;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity278 && _livingEntity278.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity278.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														} else if (5 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip281 ? blockstate.getValue(_getip281) : -1)) {
															if (entity instanceof LivingEntity _livingEntity282 && _livingEntity282.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity282.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 3;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity286 && _livingEntity286.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity286.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														} else if (6 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip289 ? blockstate.getValue(_getip289) : -1)) {
															if (entity instanceof LivingEntity _livingEntity290 && _livingEntity290.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity290.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 3;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity294 && _livingEntity294.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity294.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														} else if (7 == (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip297 ? blockstate.getValue(_getip297) : -1)) {
															if (entity instanceof LivingEntity _livingEntity298 && _livingEntity298.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity298.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
															Ssc14Mod.queueServerWork(1, () -> {
																{
																	int _value = 6;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
																if (entity instanceof LivingEntity _livingEntity302 && _livingEntity302.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity302.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															});
														}
													}
												} else {
													if (entity instanceof LivingEntity _livingEntity304 && _livingEntity304.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity304.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity306 && _livingEntity306.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity306.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity308 && _livingEntity308.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity308.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity310 && _livingEntity310.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity310.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity312 && _livingEntity312.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity312.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip317 ? blockstate.getValue(_getip317) : -1) == 0) {
			if (entity instanceof LivingEntity _livingEntity318 && _livingEntity318.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity318.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity322 && _livingEntity322.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity322.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity325 && _livingEntity325.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity325.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity329 && _livingEntity329.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity329.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity332 && _livingEntity332.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity332.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity336 && _livingEntity336.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity336.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity339 && _livingEntity339.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity339.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity343 && _livingEntity343.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity343.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity346 && _livingEntity346.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity346.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity350 && _livingEntity350.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity350.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip356 ? blockstate.getValue(_getip356) : -1) == 0) {
													if (entity instanceof LivingEntity _livingEntity357 && _livingEntity357.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity357.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														{
															int _value = 1;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														if (entity instanceof LivingEntity _livingEntity360 && _livingEntity360.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity360.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity362 && _livingEntity362.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity362.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity364 && _livingEntity364.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity364.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity366 && _livingEntity366.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity366.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity368 && _livingEntity368.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity368.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity370 && _livingEntity370.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity370.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip375 ? blockstate.getValue(_getip375) : -1) == 8) {
			if (entity instanceof LivingEntity _livingEntity376 && _livingEntity376.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity376.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(2, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity380 && _livingEntity380.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity380.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity383 && _livingEntity383.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity383.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity387 && _livingEntity387.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity387.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity390 && _livingEntity390.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity390.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(2, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity394 && _livingEntity394.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity394.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity397 && _livingEntity397.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity397.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity401 && _livingEntity401.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity401.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity404 && _livingEntity404.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity404.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(2, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity408 && _livingEntity408.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity408.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip414 ? blockstate.getValue(_getip414) : -1) == 8) {
													if (entity instanceof LivingEntity _livingEntity415 && _livingEntity415.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity415.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															BlockPos _bp = BlockPos.containing(x, y, z);
															BlockState _bs = Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get().defaultBlockState();
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
															ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()), new ItemStack(Ssc14ModItems.PLASTEEL.get()));
															entityToSpawn.setPickUpDelay(1);
															entityToSpawn.setUnlimitedLifetime();
															_level.addFreshEntity(entityToSpawn);
														}
														if (entity instanceof LivingEntity _livingEntity422 && _livingEntity422.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity422.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity424 && _livingEntity424.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity424.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity426 && _livingEntity426.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity426.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity428 && _livingEntity428.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity428.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity430 && _livingEntity430.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity430.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity432 && _livingEntity432.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity432.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get()) {
			if (entity instanceof LivingEntity _livingEntity436 && _livingEntity436.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity436.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity440 && _livingEntity440.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity440.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity443 && _livingEntity443.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity443.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity447 && _livingEntity447.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity447.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity450 && _livingEntity450.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity450.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity454 && _livingEntity454.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity454.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity457 && _livingEntity457.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity457.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity461 && _livingEntity461.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity461.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity464 && _livingEntity464.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity464.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity468 && _livingEntity468.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity468.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get()) {
													if (entity instanceof LivingEntity _livingEntity473 && _livingEntity473.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity473.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
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
															ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()), new ItemStack(Ssc14ModItems.PLASTEEL.get()));
															entityToSpawn.setPickUpDelay(1);
															entityToSpawn.setUnlimitedLifetime();
															_level.addFreshEntity(entityToSpawn);
														}
														if (entity instanceof LivingEntity _livingEntity480 && _livingEntity480.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity480.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity482 && _livingEntity482.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity482.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity484 && _livingEntity484.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity484.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity486 && _livingEntity486.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity486.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity488 && _livingEntity488.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity488.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity490 && _livingEntity490.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity490.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.GRILLE.get()) {
			if (entity instanceof LivingEntity _livingEntity494 && _livingEntity494.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity494.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity498 && _livingEntity498.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity498.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity501 && _livingEntity501.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity501.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(2, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity505 && _livingEntity505.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity505.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity508 && _livingEntity508.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity508.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity512 && _livingEntity512.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity512.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity515 && _livingEntity515.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity515.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(2, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity519 && _livingEntity519.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity519.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity522 && _livingEntity522.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity522.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity526 && _livingEntity526.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity526.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
													if (entity instanceof LivingEntity _livingEntity529 && _livingEntity529.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity529.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(2, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity533 && _livingEntity533.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity533.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.GRILLE.get()) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
															if (world instanceof ServerLevel _level) {
																ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.1), (z + 0.5), new ItemStack(Ssc14ModItems.ROOD.get()));
																entityToSpawn.setPickUpDelay(1);
																entityToSpawn.setUnlimitedLifetime();
																_level.addFreshEntity(entityToSpawn);
															}
															if (entity instanceof LivingEntity _livingEntity541 && _livingEntity541.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity541.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity542 && _livingEntity542.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity542.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity544 && _livingEntity544.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity544.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity546 && _livingEntity546.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity546.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity548 && _livingEntity548.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity548.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity550 && _livingEntity550.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity550.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity552 && _livingEntity552.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity552.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BROKEN_GRILLE.get()) {
			if (entity instanceof LivingEntity _livingEntity556 && _livingEntity556.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity556.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(1, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity560 && _livingEntity560.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity560.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
					if (entity instanceof LivingEntity _livingEntity563 && _livingEntity563.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity563.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(2, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity567 && _livingEntity567.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity567.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
							if (entity instanceof LivingEntity _livingEntity570 && _livingEntity570.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity570.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(1, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity574 && _livingEntity574.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity574.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
									if (entity instanceof LivingEntity _livingEntity577 && _livingEntity577.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity577.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(2, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity581 && _livingEntity581.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity581.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
											if (entity instanceof LivingEntity _livingEntity584 && _livingEntity584.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity584.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(1, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity588 && _livingEntity588.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity588.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()) {
													if (entity instanceof LivingEntity _livingEntity591 && _livingEntity591.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity591.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(2, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity595 && _livingEntity595.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity595.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.NIPPERS.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BROKEN_GRILLE.get()) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:nippers_use")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
															if (entity instanceof LivingEntity _livingEntity602 && _livingEntity602.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity602.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity603 && _livingEntity603.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity603.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity605 && _livingEntity605.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity605.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity607 && _livingEntity607.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity607.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity609 && _livingEntity609.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity609.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity611 && _livingEntity611.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity611.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity613 && _livingEntity613.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity613.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}
}