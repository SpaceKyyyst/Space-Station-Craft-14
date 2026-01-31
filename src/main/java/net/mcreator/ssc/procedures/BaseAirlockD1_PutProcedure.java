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
import net.minecraft.world.entity.Entity;
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

import java.util.*;

public class BaseAirlockD1_PutProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(
					Component.literal((new java.text.SimpleDateFormat("mm-ss-SSS").format(Calendar.getInstance().getTime()) + " - \u041E\u0411\u041D\u041E\u0412\u041B\u0415\u041D\u0418\u0415 \u0428\u041B\u042E\u0417\u0410")), false);
		}
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
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("timer:  " + getBlockNBTLogic(world, BlockPos.containing(x, y, z), "timer"))).withColor(0x00cc00).withStyle(ChatFormatting.ITALIC), false);
		}
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get()
				&& ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip14 ? blockstate.getValue(_getip14) : -1) == 0
						|| (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip16 ? blockstate.getValue(_getip16) : -1) > 4)) {
			if ((blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp18 && blockstate.getValue(_getbp18)) == true) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp20 && blockstate.getValue(_getbp20)) == true) {
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
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("emergency_acs") instanceof BooleanProperty _getbp24 && blockstate.getValue(_getbp24)) == true) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp26 && blockstate.getValue(_getbp26)) == true) {
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
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp30 && blockstate.getValue(_getbp30)) == true) {
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
				if (true == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "timer") && false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp37 && blockstate.getValue(_getbp37))) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList()
								.broadcastSystemMessage(Component.literal("\u041D\u0430\u0447\u0430\u043B\u043E \u043E\u0442\u0441\u0447\u0451\u0442\u0430 \u0442\u0430\u0439\u043C\u0435\u0440\u0430").withStyle(ChatFormatting.BOLD), false);
					}
					Ssc14Mod.queueServerWork(20, () -> {
						if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
								&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp42 && blockstate.getValue(_getbp42))) {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("3...").withColor(0x7f7f7f).withStyle(ChatFormatting.ITALIC), false);
							}
							Ssc14Mod.queueServerWork(20, () -> {
								if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
										&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp47 && blockstate.getValue(_getbp47))) {
									if (world instanceof ServerLevel _level) {
										_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("2...").withColor(0x7f7f7f).withStyle(ChatFormatting.ITALIC), false);
									}
									Ssc14Mod.queueServerWork(20, () -> {
										if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
												&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp52 && blockstate.getValue(_getbp52))) {
											if (world instanceof ServerLevel _level) {
												_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("1...").withColor(0x7f7f7f).withStyle(ChatFormatting.ITALIC), false);
											}
											Ssc14Mod.queueServerWork(20, () -> {
												if (Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()
														&& false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp57 && blockstate.getValue(_getbp57))) {
													if (!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
															&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
															|| false == (blockstate.getBlock().getStateDefinition().getProperty("safe") instanceof BooleanProperty _getbp61 && blockstate.getValue(_getbp61))) {
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
																		if ((findEntityInWorldRange(world, Player.class, (x + 0.5), (y + 0.6), (z + 0.5), 0.3)) instanceof Player _player && !_player.level().isClientSide())
																			_player.displayClientMessage(Component.literal("\u041D\u0410\u041D\u0415\u0421\u0401\u041D \u0423\u0420\u041E\u041D \u0428\u041B\u042E\u0417\u041E\u041C"), true);
																	});
																});
															});
														});
													} else {
														if (world instanceof ServerLevel _level) {
															_level.getServer().getPlayerList().broadcastSystemMessage(
																	Component.literal("\u0428\u043B\u044E\u0437 \u0447\u0442\u043E \u0442\u043E \u0431\u043B\u043E\u043A\u0438\u0440\u0443\u0435\u0442").withColor(0xcc2828), false);
														}
														if (world instanceof ServerLevel _level) {
															_level.getServer().getPlayerList()
																	.broadcastSystemMessage(Component.literal("\u041E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u0435 \u0448\u043B\u044E\u0437\u0430...").withColor(0xccbc27), false);
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
				} else if (false == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "timer") && false == (blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp85 && blockstate.getValue(_getbp85))) {
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0422\u0430\u0439\u043C\u0435\u0440 \u043D\u0435 \u0440\u0430\u0431\u043E\u0442\u0430\u0435\u0442").withColor(0xcc27cc), false);
					}
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("timer:  " + getBlockNBTLogic(world, BlockPos.containing(x, y, z), "timer"))).withColor(0x990099).withStyle(ChatFormatting.ITALIC), false);
					}
					Ssc14Mod.queueServerWork(20, () -> {
						if ((!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
								&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
								|| false == (blockstate.getBlock().getStateDefinition().getProperty("safe") instanceof BooleanProperty _getbp92 && blockstate.getValue(_getbp92)))
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
											if ((findEntityInWorldRange(world, Player.class, (x + 0.5), (y + 0.6), (z + 0.5), 0.3)) instanceof Player _player && !_player.level().isClientSide())
												_player.displayClientMessage(Component.literal("\u041D\u0410\u041D\u0415\u0421\u0401\u041D \u0423\u0420\u041E\u041D \u0428\u041B\u042E\u0417\u041E\u041C"), true);
										});
									});
								});
							});
						} else {
							if (world instanceof ServerLevel _level) {
								_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0428\u043B\u044E\u0437 \u0447\u0442\u043E \u0442\u043E \u0431\u043B\u043E\u043A\u0438\u0440\u0443\u0435\u0442").withColor(0x6027cc),
										false);
							}
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
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("NBT \u0443\u0441\u0442\u0430\u043D\u043E\u0432\u043B\u0435\u043D\u044B").withColor(0xff00ff).withStyle(ChatFormatting.BOLD), false);
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

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}
}