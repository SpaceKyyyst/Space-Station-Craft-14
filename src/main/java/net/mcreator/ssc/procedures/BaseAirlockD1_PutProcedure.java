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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class BaseAirlockD1_PutProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (!((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_U_1.get())) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get()) {
				world.setBlock(BlockPos.containing(x, y + 1, z), Ssc14ModBlocks.BASE_AIRLOCK_U_1.get().defaultBlockState(), 3);
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
			} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get()) {
				world.setBlock(BlockPos.containing(x, y + 1, z), Ssc14ModBlocks.BASE_AIRLOCK_U_1OPEN.get().defaultBlockState(), 3);
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
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip15 ? blockstate.getValue(_getip15) : -1) == 0
				|| (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip17 ? blockstate.getValue(_getip17) : -1) > 4) {
			if ((blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp19 && blockstate.getValue(_getbp19)) == true) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp21 && blockstate.getValue(_getbp21)) == true) {
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
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("emergency_acs") instanceof BooleanProperty _getbp25 && blockstate.getValue(_getbp25)) == true) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp27 && blockstate.getValue(_getbp27)) == true) {
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
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp31 && blockstate.getValue(_getbp31)) == true) {
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
		}
		if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			if (true == (blockstate.getBlock().getStateDefinition().getProperty("timer") instanceof BooleanProperty _getbp37 && blockstate.getValue(_getbp37))
					&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp39 && blockstate.getValue(_getbp39))) {
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList()
							.broadcastSystemMessage(Component.literal("\u041D\u0430\u0447\u0430\u043B\u043E \u043E\u0442\u0441\u0447\u0451\u0442\u0430 \u0442\u0430\u0439\u043C\u0435\u0440\u0430").withStyle(ChatFormatting.BOLD), false);
				}
				Ssc14Mod.queueServerWork(20, () -> {
					if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
							&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp44 && blockstate.getValue(_getbp44))) {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("3...").withColor(0x7f7f7f).withStyle(ChatFormatting.ITALIC), false);
						}
						Ssc14Mod.queueServerWork(20, () -> {
							if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
									&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp49 && blockstate.getValue(_getbp49))) {
								if (world instanceof ServerLevel _level) {
									_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("2...").withColor(0x7f7f7f).withStyle(ChatFormatting.ITALIC), false);
								}
								Ssc14Mod.queueServerWork(20, () -> {
									if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
											&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp54 && blockstate.getValue(_getbp54))) {
										if (world instanceof ServerLevel _level) {
											_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("1...").withColor(0x7f7f7f).withStyle(ChatFormatting.ITALIC), false);
										}
										Ssc14Mod.queueServerWork(20, () -> {
											if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
													&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp59 && blockstate.getValue(_getbp59))) {
												if (!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
														&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())) {
													if (world instanceof ServerLevel _level) {
														_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0417\u0430\u043A\u0440\u044B\u0442\u0438\u0435 \u0448\u043B\u044E\u0437\u0430...").withColor(0x27cc27), false);
													}
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
													if (world instanceof ServerLevel _level) {
														_level.getServer().getPlayerList()
																.broadcastSystemMessage(Component.literal("\u0428\u043B\u044E\u0437 \u0447\u0442\u043E \u0442\u043E \u0431\u043B\u043E\u043A\u0438\u0440\u0443\u0435\u0442").withColor(0xcc2828), false);
													}
													if (world instanceof ServerLevel _level) {
														_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u041E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u0435 \u0448\u043B\u044E\u0437\u0430...").withColor(0xccbc27),
																false);
													}
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
			} else if (false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp81 && blockstate.getValue(_getbp81))) {
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0422\u0430\u0439\u043C\u0435\u0440 \u043D\u0435 \u0440\u0430\u0431\u043E\u0442\u0430\u0435\u0442").withColor(0xcc27cc), false);
				}
				Ssc14Mod.queueServerWork(20, () -> {
					if (!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
							&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
							&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get()) {
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0417\u0430\u043A\u0440\u044B\u0442\u0438\u0435 \u0448\u043B\u044E\u0437\u0430...").withColor(0x28cccc), false);
						}
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
						if (world instanceof ServerLevel _level) {
							_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0428\u043B\u044E\u0437 \u0447\u0442\u043E \u0442\u043E \u0431\u043B\u043E\u043A\u0438\u0440\u0443\u0435\u0442").withColor(0x6027cc), false);
						}
						BaseAirlockD1_PutProcedure.execute(world, x, y, z, blockstate);
					}
				});
			}
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
				// Генерация [1,1,2,3,4,5,6,7]
				List<Integer> values = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4, 5, 6, 7));
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
}