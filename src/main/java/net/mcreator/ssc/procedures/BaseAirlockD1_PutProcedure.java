package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.RandomSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class BaseAirlockD1_PutProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (!((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Ssc14ModBlocks.AIRLOCK_UP_PLUG.get() || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get())) {
			world.setBlock(BlockPos.containing(x, y + 1, z), Ssc14ModBlocks.AIRLOCK_UP_PLUG.get().defaultBlockState(), 3);
			{
				Direction _dir = (getDirectionFromBlockState(blockstate));
				BlockPos _pos = BlockPos.containing(x, y + 1, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty _dp && _dp.getPossibleValues().contains(_dir)) {
					world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
				} else if (_bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis())) {
					world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
				}
			}
		}
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get()
				&& ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip11 ? blockstate.getValue(_getip11) : -1) == 0
						|| (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip13 ? blockstate.getValue(_getip13) : -1) > 4)) {
			if ((blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp15 && blockstate.getValue(_getbp15)) == true) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp17 && blockstate.getValue(_getbp17)) == true) {
					{
						int _value = 9;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				} else {
					{
						int _value = 5;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				}
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("emergency_acs") instanceof BooleanProperty _getbp21 && blockstate.getValue(_getbp21)) == true) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp23 && blockstate.getValue(_getbp23)) == true) {
					{
						int _value = 8;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				} else {
					{
						int _value = 6;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				}
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp27 && blockstate.getValue(_getbp27)) == true) {
				{
					int _value = 7;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			} else {
				{
					int _value = 0;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			}
		} else if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			Ssc14Mod.queueServerWork(1, () -> {
				if (true == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "timer") && false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp34 && blockstate.getValue(_getbp34))) {
					Ssc14Mod.queueServerWork(20, () -> {
						if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
								&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp38 && blockstate.getValue(_getbp38))) {
							Ssc14Mod.queueServerWork(20, () -> {
								if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
										&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp42 && blockstate.getValue(_getbp42))) {
									Ssc14Mod.queueServerWork(20, () -> {
										if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
												&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp46 && blockstate.getValue(_getbp46))) {
											Ssc14Mod.queueServerWork(20, () -> {
												if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
														&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp50 && blockstate.getValue(_getbp50))) {
													if (!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
															&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())) {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_close")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_close")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															BlockPos _bp = BlockPos.containing(x, y, z);
															BlockState _bs = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState();
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
														{
															BlockPos _bp = BlockPos.containing(x, y + 1, z);
															BlockState _bs = Ssc14ModBlocks.AIRLOCK_UP_PLUG.get().defaultBlockState();
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
														{
															int _value = 4;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														Ssc14Mod.queueServerWork(2, () -> {
															{
																int _value = 3;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
															}
															Ssc14Mod.queueServerWork(2, () -> {
																{
																	int _value = 2;
																	BlockPos _pos = BlockPos.containing(x, y, z);
																	BlockState _bs = world.getBlockState(_pos);
																	if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																		world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
																}
																Ssc14Mod.queueServerWork(2, () -> {
																	{
																		int _value = 1;
																		BlockPos _pos = BlockPos.containing(x, y, z);
																		BlockState _bs = world.getBlockState(_pos);
																		if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																			world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
																	}
																	Ssc14Mod.queueServerWork(2, () -> {
																		{
																			int _value = 0;
																			BlockPos _pos = BlockPos.containing(x, y, z);
																			BlockState _bs = world.getBlockState(_pos);
																			if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																				world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
																		}
																	});
																});
															});
														});
													} else {
														BaseAirlockD1_PutProcedure.execute(world, x, y, z, blockstate);
													}
												}
											});
										}
									});
								}
							});
						}
					});
				} else if (false == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "timer") && false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp71 && blockstate.getValue(_getbp71))) {
					Ssc14Mod.queueServerWork(20, () -> {
						if (!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
								&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
								&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_close")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_close")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
							{
								BlockPos _bp = BlockPos.containing(x, y, z);
								BlockState _bs = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState();
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
							{
								BlockPos _bp = BlockPos.containing(x, y + 1, z);
								BlockState _bs = Ssc14ModBlocks.AIRLOCK_UP_PLUG.get().defaultBlockState();
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
							{
								int _value = 4;
								BlockPos _pos = BlockPos.containing(x, y, z);
								BlockState _bs = world.getBlockState(_pos);
								if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
									world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
							}
							Ssc14Mod.queueServerWork(2, () -> {
								{
									int _value = 3;
									BlockPos _pos = BlockPos.containing(x, y, z);
									BlockState _bs = world.getBlockState(_pos);
									if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
										world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
								}
								Ssc14Mod.queueServerWork(2, () -> {
									{
										int _value = 2;
										BlockPos _pos = BlockPos.containing(x, y, z);
										BlockState _bs = world.getBlockState(_pos);
										if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
											world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
									}
									Ssc14Mod.queueServerWork(2, () -> {
										{
											int _value = 1;
											BlockPos _pos = BlockPos.containing(x, y, z);
											BlockState _bs = world.getBlockState(_pos);
											if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
												world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
										}
										Ssc14Mod.queueServerWork(2, () -> {
											{
												int _value = 0;
												BlockPos _pos = BlockPos.containing(x, y, z);
												BlockState _bs = world.getBlockState(_pos);
												if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
													world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
											}
										});
									});
								});
							});
						} else {
							BaseAirlockD1_PutProcedure.execute(world, x, y, z, blockstate);
						}
					});
				}
			});
		}
		if (false == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Blocking_13")) {
			Ssc14Mod.queueServerWork(2, () -> {
				if (false == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Blocking_13")) {
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putBoolean("Blocking_13", true);
							_blockEntity.getPersistentData().putBoolean("energy_cabel_1", true);
							_blockEntity.getPersistentData().putBoolean("energy_cabel_2", true);
							_blockEntity.getPersistentData().putBoolean("timer", true);
							_blockEntity.getPersistentData().putBoolean("ai_access", true);
							_blockEntity.getPersistentData().putBoolean("logs", true);
							_blockEntity.getPersistentData().putBoolean("diods", true);
							_blockEntity.getPersistentData().putBoolean("safe", true);
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			});
		}
		BlockPos _bp = BlockPos.containing(x, y, z);
		boolean alreadyGenerated = false;
		BlockEntity _beCheck = world.getBlockEntity(_bp);
		if (_beCheck != null) {
			alreadyGenerated = _beCheck.getPersistentData().getBoolean("random_cab").orElse(false);
		}
		if (!alreadyGenerated && !world.isClientSide()) {
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null) {
				_blockEntity.getPersistentData().putBoolean("random_cab", true);
				// Генерация [0,1,2,3,4,5,6,7]
				List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
				RandomSource randomSource = world.getRandom();
				Random random = new Random(randomSource.nextLong());
				Collections.shuffle(values, random);
				for (int i = 0; i < 8; i++) {
					_blockEntity.getPersistentData().putInt("log_cab_" + (i + 1), values.get(i));
				}
			}
			if (world instanceof Level _level) {
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		if (blockState.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class)
			return (Direction) blockState.getValue(ep);
		if (blockState.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class)
			return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}