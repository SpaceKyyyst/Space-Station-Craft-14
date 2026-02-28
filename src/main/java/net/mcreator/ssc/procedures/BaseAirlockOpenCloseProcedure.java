package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

import io.netty.buffer.Unpooled;

public class BaseAirlockOpenCloseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACCESS_CONFIG.get() && !entity.isShiftKeyDown()) {
			if (entity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
				_ent.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("AccessConfigMENU");
					}

					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new AccessConfigMENUMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		} else {
			if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp8 && blockstate.getValue(_getbp8)) == false
					|| (blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp10 && blockstate.getValue(_getbp10)) == true && entity.isShiftKeyDown()) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("bolted") instanceof BooleanProperty _getbp13 && blockstate.getValue(_getbp13)) == false) {
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putBoolean("Opening", true);
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if ((blockstate.getBlock().getStateDefinition().getProperty("emergency_acs") instanceof BooleanProperty _getbp16 && blockstate.getValue(_getbp16)) == false) {
						if (entity.getPersistentData().getBooleanOr("Technical", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Technical") == true) {
							if (!world.isClientSide()) {
								BlockPos _bp = BlockPos.containing(x, y, z);
								BlockEntity _blockEntity = world.getBlockEntity(_bp);
								BlockState _bs = world.getBlockState(_bp);
								if (_blockEntity != null) {
									_blockEntity.getPersistentData().putBoolean("Opening", false);
								}
								if (world instanceof Level _level)
									_level.sendBlockUpdated(_bp, _bs, _bs, 3);
							}
						} else {
							if (entity.getPersistentData().getBooleanOr("Service", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Service") == true) {
								if (!world.isClientSide()) {
									BlockPos _bp = BlockPos.containing(x, y, z);
									BlockEntity _blockEntity = world.getBlockEntity(_bp);
									BlockState _bs = world.getBlockState(_bp);
									if (_blockEntity != null) {
										_blockEntity.getPersistentData().putBoolean("Opening", false);
									}
									if (world instanceof Level _level)
										_level.sendBlockUpdated(_bp, _bs, _bs, 3);
								}
							} else {
								if (entity.getPersistentData().getBooleanOr("Out", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Out") == true) {
									if (!world.isClientSide()) {
										BlockPos _bp = BlockPos.containing(x, y, z);
										BlockEntity _blockEntity = world.getBlockEntity(_bp);
										BlockState _bs = world.getBlockState(_bp);
										if (_blockEntity != null) {
											_blockEntity.getPersistentData().putBoolean("Opening", false);
										}
										if (world instanceof Level _level)
											_level.sendBlockUpdated(_bp, _bs, _bs, 3);
									}
								} else {
									if (entity.getPersistentData().getBooleanOr("gun_room", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "gun_room") == true) {
										if (!world.isClientSide()) {
											BlockPos _bp = BlockPos.containing(x, y, z);
											BlockEntity _blockEntity = world.getBlockEntity(_bp);
											BlockState _bs = world.getBlockState(_bp);
											if (_blockEntity != null) {
												_blockEntity.getPersistentData().putBoolean("Opening", false);
											}
											if (world instanceof Level _level)
												_level.sendBlockUpdated(_bp, _bs, _bs, 3);
										}
									} else {
										if (entity.getPersistentData().getBooleanOr("HoS", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "HoS") == true) {
											if (!world.isClientSide()) {
												BlockPos _bp = BlockPos.containing(x, y, z);
												BlockEntity _blockEntity = world.getBlockEntity(_bp);
												BlockState _bs = world.getBlockState(_bp);
												if (_blockEntity != null) {
													_blockEntity.getPersistentData().putBoolean("Opening", false);
												}
												if (world instanceof Level _level)
													_level.sendBlockUpdated(_bp, _bs, _bs, 3);
											}
										} else {
											if (entity.getPersistentData().getBooleanOr("Brig", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Brig") == true) {
												if (!world.isClientSide()) {
													BlockPos _bp = BlockPos.containing(x, y, z);
													BlockEntity _blockEntity = world.getBlockEntity(_bp);
													BlockState _bs = world.getBlockState(_bp);
													if (_blockEntity != null) {
														_blockEntity.getPersistentData().putBoolean("Opening", false);
													}
													if (world instanceof Level _level)
														_level.sendBlockUpdated(_bp, _bs, _bs, 3);
												}
											} else {
												if (entity.getPersistentData().getBooleanOr("Medical", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Medical") == true) {
													if (!world.isClientSide()) {
														BlockPos _bp = BlockPos.containing(x, y, z);
														BlockEntity _blockEntity = world.getBlockEntity(_bp);
														BlockState _bs = world.getBlockState(_bp);
														if (_blockEntity != null) {
															_blockEntity.getPersistentData().putBoolean("Opening", false);
														}
														if (world instanceof Level _level)
															_level.sendBlockUpdated(_bp, _bs, _bs, 3);
													}
												} else {
													if (entity.getPersistentData().getBooleanOr("Crio", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Crio") == true) {
														if (!world.isClientSide()) {
															BlockPos _bp = BlockPos.containing(x, y, z);
															BlockEntity _blockEntity = world.getBlockEntity(_bp);
															BlockState _bs = world.getBlockState(_bp);
															if (_blockEntity != null) {
																_blockEntity.getPersistentData().putBoolean("Opening", false);
															}
															if (world instanceof Level _level)
																_level.sendBlockUpdated(_bp, _bs, _bs, 3);
														}
													} else {
														if (entity.getPersistentData().getBooleanOr("Security", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Security") == true) {
															if (!world.isClientSide()) {
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockEntity _blockEntity = world.getBlockEntity(_bp);
																BlockState _bs = world.getBlockState(_bp);
																if (_blockEntity != null) {
																	_blockEntity.getPersistentData().putBoolean("Opening", false);
																}
																if (world instanceof Level _level)
																	_level.sendBlockUpdated(_bp, _bs, _bs, 3);
															}
														} else {
															if (entity.getPersistentData().getBooleanOr("Ingeneer", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Ingeneer") == true) {
																if (!world.isClientSide()) {
																	BlockPos _bp = BlockPos.containing(x, y, z);
																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																	BlockState _bs = world.getBlockState(_bp);
																	if (_blockEntity != null) {
																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																	}
																	if (world instanceof Level _level)
																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																}
															} else {
																if (entity.getPersistentData().getBooleanOr("Command", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Command") == true) {
																	if (!world.isClientSide()) {
																		BlockPos _bp = BlockPos.containing(x, y, z);
																		BlockEntity _blockEntity = world.getBlockEntity(_bp);
																		BlockState _bs = world.getBlockState(_bp);
																		if (_blockEntity != null) {
																			_blockEntity.getPersistentData().putBoolean("Opening", false);
																		}
																		if (world instanceof Level _level)
																			_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																	}
																} else {
																	if (entity.getPersistentData().getBooleanOr("Detective", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Detective") == true) {
																		if (!world.isClientSide()) {
																			BlockPos _bp = BlockPos.containing(x, y, z);
																			BlockEntity _blockEntity = world.getBlockEntity(_bp);
																			BlockState _bs = world.getBlockState(_bp);
																			if (_blockEntity != null) {
																				_blockEntity.getPersistentData().putBoolean("Opening", false);
																			}
																			if (world instanceof Level _level)
																				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																		}
																	} else {
																		if (entity.getPersistentData().getBooleanOr("PNT", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "PNT") == true) {
																			if (!world.isClientSide()) {
																				BlockPos _bp = BlockPos.containing(x, y, z);
																				BlockEntity _blockEntity = world.getBlockEntity(_bp);
																				BlockState _bs = world.getBlockState(_bp);
																				if (_blockEntity != null) {
																					_blockEntity.getPersistentData().putBoolean("Opening", false);
																				}
																				if (world instanceof Level _level)
																					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																			}
																		} else {
																			if (entity.getPersistentData().getBooleanOr("Scientist", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Scientist") == true) {
																				if (!world.isClientSide()) {
																					BlockPos _bp = BlockPos.containing(x, y, z);
																					BlockEntity _blockEntity = world.getBlockEntity(_bp);
																					BlockState _bs = world.getBlockState(_bp);
																					if (_blockEntity != null) {
																						_blockEntity.getPersistentData().putBoolean("Opening", false);
																					}
																					if (world instanceof Level _level)
																						_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																				}
																			} else {
																				if (entity.getPersistentData().getBooleanOr("Supply_Deportament", false) == false
																						&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Supply_Deportament") == true) {
																					if (!world.isClientSide()) {
																						BlockPos _bp = BlockPos.containing(x, y, z);
																						BlockEntity _blockEntity = world.getBlockEntity(_bp);
																						BlockState _bs = world.getBlockState(_bp);
																						if (_blockEntity != null) {
																							_blockEntity.getPersistentData().putBoolean("Opening", false);
																						}
																						if (world instanceof Level _level)
																							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																					}
																				} else {
																					if (entity.getPersistentData().getBooleanOr("Atmos", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Atmos") == true) {
																						if (!world.isClientSide()) {
																							BlockPos _bp = BlockPos.containing(x, y, z);
																							BlockEntity _blockEntity = world.getBlockEntity(_bp);
																							BlockState _bs = world.getBlockState(_bp);
																							if (_blockEntity != null) {
																								_blockEntity.getPersistentData().putBoolean("Opening", false);
																							}
																							if (world instanceof Level _level)
																								_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																						}
																					} else {
																						if (entity.getPersistentData().getBooleanOr("Kitchen", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Kitchen") == true) {
																							if (!world.isClientSide()) {
																								BlockPos _bp = BlockPos.containing(x, y, z);
																								BlockEntity _blockEntity = world.getBlockEntity(_bp);
																								BlockState _bs = world.getBlockState(_bp);
																								if (_blockEntity != null) {
																									_blockEntity.getPersistentData().putBoolean("Opening", false);
																								}
																								if (world instanceof Level _level)
																									_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																							}
																						} else {
																							if (entity.getPersistentData().getBooleanOr("Uridic", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Uridic") == true) {
																								if (!world.isClientSide()) {
																									BlockPos _bp = BlockPos.containing(x, y, z);
																									BlockEntity _blockEntity = world.getBlockEntity(_bp);
																									BlockState _bs = world.getBlockState(_bp);
																									if (_blockEntity != null) {
																										_blockEntity.getPersistentData().putBoolean("Opening", false);
																									}
																									if (world instanceof Level _level)
																										_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																								}
																							} else {
																								if (entity.getPersistentData().getBooleanOr("Gidroponic", false) == false
																										&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Gidroponic") == true) {
																									if (!world.isClientSide()) {
																										BlockPos _bp = BlockPos.containing(x, y, z);
																										BlockEntity _blockEntity = world.getBlockEntity(_bp);
																										BlockState _bs = world.getBlockState(_bp);
																										if (_blockEntity != null) {
																											_blockEntity.getPersistentData().putBoolean("Opening", false);
																										}
																										if (world instanceof Level _level)
																											_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																									}
																								} else {
																									if (entity.getPersistentData().getBooleanOr("Teatre", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Teatre") == true) {
																										if (!world.isClientSide()) {
																											BlockPos _bp = BlockPos.containing(x, y, z);
																											BlockEntity _blockEntity = world.getBlockEntity(_bp);
																											BlockState _bs = world.getBlockState(_bp);
																											if (_blockEntity != null) {
																												_blockEntity.getPersistentData().putBoolean("Opening", false);
																											}
																											if (world instanceof Level _level)
																												_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																										}
																									} else {
																										if (entity.getPersistentData().getBooleanOr("Bar", false) == false && getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Bar") == true) {
																											if (!world.isClientSide()) {
																												BlockPos _bp = BlockPos.containing(x, y, z);
																												BlockEntity _blockEntity = world.getBlockEntity(_bp);
																												BlockState _bs = world.getBlockState(_bp);
																												if (_blockEntity != null) {
																													_blockEntity.getPersistentData().putBoolean("Opening", false);
																												}
																												if (world instanceof Level _level)
																													_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																											}
																										} else {
																											if (entity.getPersistentData().getBooleanOr("Cleaner", false) == false
																													&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Cleaner") == true) {
																												if (!world.isClientSide()) {
																													BlockPos _bp = BlockPos.containing(x, y, z);
																													BlockEntity _blockEntity = world.getBlockEntity(_bp);
																													BlockState _bs = world.getBlockState(_bp);
																													if (_blockEntity != null) {
																														_blockEntity.getPersistentData().putBoolean("Opening", false);
																													}
																													if (world instanceof Level _level)
																														_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																												}
																											} else {
																												if (entity.getPersistentData().getBooleanOr("Utilizat", false) == false
																														&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Utilizat") == true) {
																													if (!world.isClientSide()) {
																														BlockPos _bp = BlockPos.containing(x, y, z);
																														BlockEntity _blockEntity = world.getBlockEntity(_bp);
																														BlockState _bs = world.getBlockState(_bp);
																														if (_blockEntity != null) {
																															_blockEntity.getPersistentData().putBoolean("Opening", false);
																														}
																														if (world instanceof Level _level)
																															_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																													}
																												} else {
																													if (entity.getPersistentData().getBooleanOr("Chemistry", false) == false
																															&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Chemistry") == true) {
																														if (!world.isClientSide()) {
																															BlockPos _bp = BlockPos.containing(x, y, z);
																															BlockEntity _blockEntity = world.getBlockEntity(_bp);
																															BlockState _bs = world.getBlockState(_bp);
																															if (_blockEntity != null) {
																																_blockEntity.getPersistentData().putBoolean("Opening", false);
																															}
																															if (world instanceof Level _level)
																																_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																														}
																													} else {
																														if (entity.getPersistentData().getBooleanOr("Church", false) == false
																																&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Church") == true) {
																															if (!world.isClientSide()) {
																																BlockPos _bp = BlockPos.containing(x, y, z);
																																BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																BlockState _bs = world.getBlockState(_bp);
																																if (_blockEntity != null) {
																																	_blockEntity.getPersistentData().putBoolean("Opening", false);
																																}
																																if (world instanceof Level _level)
																																	_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																															}
																														} else {
																															if (entity.getPersistentData().getBooleanOr("CE", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "CE") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																															if (entity.getPersistentData().getBooleanOr("Qm", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Qm") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																															if (entity.getPersistentData().getBooleanOr("CMO", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "CMO") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																															if (entity.getPersistentData().getBooleanOr("RD", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "RD") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																															if (entity.getPersistentData().getBooleanOr("HoP", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "HoP") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																															if (entity.getPersistentData().getBooleanOr("Capitan", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Capitan") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																															if (entity.getPersistentData().getBooleanOr("Blue_Sh", false) == false
																																	&& getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Blue_Sh") == true) {
																																if (!world.isClientSide()) {
																																	BlockPos _bp = BlockPos.containing(x, y, z);
																																	BlockEntity _blockEntity = world.getBlockEntity(_bp);
																																	BlockState _bs = world.getBlockState(_bp);
																																	if (_blockEntity != null) {
																																		_blockEntity.getPersistentData().putBoolean("Opening", false);
																																	}
																																	if (world instanceof Level _level)
																																		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																																}
																															}
																														}
																													}
																												}
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					} else {
						if (!world.isClientSide()) {
							BlockPos _bp = BlockPos.containing(x, y, z);
							BlockEntity _blockEntity = world.getBlockEntity(_bp);
							BlockState _bs = world.getBlockState(_bp);
							if (_blockEntity != null) {
								_blockEntity.getPersistentData().putBoolean("Opening", true);
							}
							if (world instanceof Level _level)
								_level.sendBlockUpdated(_bp, _bs, _bs, 3);
						}
					}
					Ssc14Mod.queueServerWork(1, () -> {
						if ((blockstate.getBlock().getStateDefinition().getProperty("emergency_acs") instanceof BooleanProperty _getbp115 && blockstate.getValue(_getbp115)) == true
								|| getBlockNBTLogic(world, BlockPos.containing(x, y, z), "Opening") == true) {
							if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get()
									&& ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip120 ? blockstate.getValue(_getip120) : -1) == 0
											|| (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip122 ? blockstate.getValue(_getip122) : -1) == 6
											|| (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip124 ? blockstate.getValue(_getip124) : -1) == 7
											|| (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip126 ? blockstate.getValue(_getip126) : -1) == 8)) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_open")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_open")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
								{
									int _value = 1;
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
											int _value = 3;
											BlockPos _pos = BlockPos.containing(x, y, z);
											BlockState _bs = world.getBlockState(_pos);
											if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
												world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
										}
										Ssc14Mod.queueServerWork(2, () -> {
											{
												int _value = 4;
												BlockPos _pos = BlockPos.containing(x, y, z);
												BlockState _bs = world.getBlockState(_pos);
												if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
													world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
											}
											Ssc14Mod.queueServerWork(2, () -> {
												{
													BlockPos _bp = BlockPos.containing(x, y, z);
													// Сохраняем PersistentData ДО замены блока
													CompoundTag persistentData = new CompoundTag();
													BlockEntity oldBe = world.getBlockEntity(_bp);
													if (oldBe != null) {
														persistentData = oldBe.getPersistentData().copy(); // ← КЛЮЧЕВОЙ МОМЕНТ
													}
													// Подготавливаем новый блок с теми же свойствами
													BlockState _bs = Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get().defaultBlockState();
													BlockState _bso = world.getBlockState(_bp);
													for (Property<?> _propertyOld : _bso.getProperties()) {
														Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
														if (_propertyNew != null) {
															try {
																_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
															} catch (Exception e) {
																// ignore
															}
														}
													}
													// Устанавливаем новый блок
													world.setBlock(_bp, _bs, 3);
													// Восстанавливаем PersistentData в новом BlockEntity
													BlockEntity newBe = world.getBlockEntity(_bp);
													if (newBe != null && !persistentData.isEmpty()) {
														newBe.getPersistentData().merge(persistentData);
													}
												}
												{
													BlockPos _bp = BlockPos.containing(x, y + 1, z);
													BlockState _bs = Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get().defaultBlockState();
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
											});
										});
									});
								});
							} else if (!(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.4), (z + 0.5))).inflate(0.4 / 2d), e -> true).isEmpty())
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
							}
						} else {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_no_access")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_no_access")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
						}
					});
				}
			} else if ((blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp156 && blockstate.getValue(_getbp156)) == true && !entity.isShiftKeyDown()) {
				if (entity instanceof ServerPlayer _ent) {
					BlockPos _bpos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
					_ent.openMenu(new MenuProvider() {
						@Override
						public Component getDisplayName() {
							return Component.literal("CabelPannelAirlock");
						}

						@Override
						public boolean shouldTriggerClientSideContainerClosingOnOpen() {
							return false;
						}

						@Override
						public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
							return new CabelPannelAirlockMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
						}
					}, _bpos);
				}
			}
		}
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}