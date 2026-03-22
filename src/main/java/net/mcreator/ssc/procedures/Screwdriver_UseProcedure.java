package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.ProblemReporter;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Screwdriver_UseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_WINDOW.get()
				&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("window_disassembly") instanceof IntegerProperty _getip7
						? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip7)
						: -1) == 0) {
			if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity8.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(4, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity12.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity15.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(4, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity19.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(4, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity26.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity29.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(4, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity33.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity36.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(4, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity40.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
													if (entity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity43.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(4, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity47.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_WINDOW.get()
																&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("window_disassembly") instanceof IntegerProperty _getip53
																		? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip53)
																		: -1) == 0) {
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
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
																int _value = 1;
																BlockPos _pos = BlockPos.containing(x, y, z);
																BlockState _bs = world.getBlockState(_pos);
																if (_bs.getBlock().getStateDefinition().getProperty("window_disassembly") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																	world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
															}
															if (entity instanceof LivingEntity _livingEntity57 && _livingEntity57.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity57.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														} else {
															if (entity instanceof LivingEntity _livingEntity58 && _livingEntity58.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity58.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity60 && _livingEntity60.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity60.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity62 && _livingEntity62.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity62.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity64 && _livingEntity64.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity64.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity66 && _livingEntity66.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity66.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity68 && _livingEntity68.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity68.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip73 ? blockstate.getValue(_getip73) : -1) == 1) {
			if (entity instanceof LivingEntity _livingEntity74 && _livingEntity74.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity74.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity78 && _livingEntity78.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity78.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity81 && _livingEntity81.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity81.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity85 && _livingEntity85.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity85.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity88 && _livingEntity88.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity88.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity92 && _livingEntity92.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity92.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity95 && _livingEntity95.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity95.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity99 && _livingEntity99.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity99.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity102 && _livingEntity102.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity102.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity106 && _livingEntity106.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity106.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip112 ? blockstate.getValue(_getip112) : -1) == 1) {
													if (entity instanceof LivingEntity _livingEntity113 && _livingEntity113.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity113.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 2;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity116 && _livingEntity116.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity116.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity118 && _livingEntity118.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity118.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity120 && _livingEntity120.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity120.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity122 && _livingEntity122.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity122.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity124 && _livingEntity124.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity124.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity126 && _livingEntity126.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity126.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip131 ? blockstate.getValue(_getip131) : -1) == 7) {
			if (entity instanceof LivingEntity _livingEntity132 && _livingEntity132.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity132.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(2, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity136 && _livingEntity136.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity136.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity139 && _livingEntity139.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity139.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity143 && _livingEntity143.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity143.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity146 && _livingEntity146.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity146.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(2, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity150 && _livingEntity150.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity150.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity153 && _livingEntity153.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity153.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity157 && _livingEntity157.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity157.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity160 && _livingEntity160.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity160.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(2, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity164 && _livingEntity164.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity164.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PLASTEEL_WALL.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip170 ? blockstate.getValue(_getip170) : -1) == 7) {
													if (entity instanceof LivingEntity _livingEntity171 && _livingEntity171.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity171.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 8;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity174 && _livingEntity174.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity174.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity176 && _livingEntity176.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity176.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
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
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get()) {
			if (entity instanceof LivingEntity _livingEntity190 && _livingEntity190.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity190.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity194 && _livingEntity194.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity194.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity197 && _livingEntity197.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity197.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity201 && _livingEntity201.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity201.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity204 && _livingEntity204.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity204.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity208 && _livingEntity208.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity208.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity211 && _livingEntity211.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity211.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity215 && _livingEntity215.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity215.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity218 && _livingEntity218.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity218.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity222 && _livingEntity222.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity222.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
														&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get())
														&& (blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp230 && blockstate.getValue(_getbp230)) == false) {
													if (entity instanceof LivingEntity _livingEntity231 && _livingEntity231.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity231.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _booleanProp)
																world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
														}
														if (world instanceof Level _level)
															_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
														if (entity instanceof LivingEntity _livingEntity235 && _livingEntity235.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity235.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity240 && _livingEntity240.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity240.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
														&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get()
																|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get())
														&& (blockstate.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _getbp248 && blockstate.getValue(_getbp248)) == true) {
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty _booleanProp)
																world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
														}
														if (world instanceof Level _level)
															_level.updateNeighborsAt(BlockPos.containing(x, y, z), _level.getBlockState(BlockPos.containing(x, y, z)).getBlock());
														if (entity instanceof LivingEntity _livingEntity252 && _livingEntity252.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity252.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														BaseAirlockD1_PutProcedure.execute(world, x, y, z, blockstate);
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
				} else {
					if (entity instanceof LivingEntity _livingEntity262 && _livingEntity262.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity262.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip267 ? blockstate.getValue(_getip267) : -1) == 1) {
			if (entity instanceof LivingEntity _livingEntity268 && _livingEntity268.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity268.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(2, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity272 && _livingEntity272.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity272.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity275 && _livingEntity275.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity275.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(1, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity279 && _livingEntity279.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity279.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity282 && _livingEntity282.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity282.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(2, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity286 && _livingEntity286.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity286.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity289 && _livingEntity289.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity289.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(1, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity293 && _livingEntity293.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity293.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity296 && _livingEntity296.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity296.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(2, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity300 && _livingEntity300.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity300.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip306 ? blockstate.getValue(_getip306) : -1) == 1) {
													if (entity instanceof LivingEntity _livingEntity307 && _livingEntity307.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity307.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(1, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 2;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity310 && _livingEntity310.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity310.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity312 && _livingEntity312.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity312.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity314 && _livingEntity314.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity314.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity316 && _livingEntity316.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity316.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity318 && _livingEntity318.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity318.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity320 && _livingEntity320.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity320.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
				&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip325 ? blockstate.getValue(_getip325) : -1) == 4) {
			if (entity instanceof LivingEntity _livingEntity326 && _livingEntity326.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity326.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(3, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity330 && _livingEntity330.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity330.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity333 && _livingEntity333.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity333.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(3, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity337 && _livingEntity337.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity337.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity340 && _livingEntity340.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity340.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(3, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity344 && _livingEntity344.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity344.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity347 && _livingEntity347.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity347.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(3, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity351 && _livingEntity351.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity351.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity354 && _livingEntity354.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity354.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(3, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity358 && _livingEntity358.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity358.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
														&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.ARMORED_WINDOW.get()
														&& (blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip364 ? blockstate.getValue(_getip364) : -1) == 4) {
													if (entity instanceof LivingEntity _livingEntity365 && _livingEntity365.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity365.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(3, () -> {
														if (world instanceof Level _level) {
															if (!_level.isClientSide()) {
																_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
															} else {
																_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
															}
														}
														{
															int _value = 5;
															BlockPos _pos = BlockPos.containing(x, y, z);
															BlockState _bs = world.getBlockState(_pos);
															if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
																world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
														}
														if (entity instanceof LivingEntity _livingEntity368 && _livingEntity368.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity368.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity370 && _livingEntity370.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity370.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity372 && _livingEntity372.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity372.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity374 && _livingEntity374.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity374.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity376 && _livingEntity376.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity376.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity378 && _livingEntity378.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity378.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.MACHINE_FRAME_2.get()
				&& true == (blockstate.getBlock().getStateDefinition().getProperty("ready") instanceof BooleanProperty _getbp383 && blockstate.getValue(_getbp383))) {
			if (entity instanceof LivingEntity _livingEntity384 && _livingEntity384.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity384.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(4, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity388 && _livingEntity388.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity388.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity391 && _livingEntity391.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity391.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(4, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity395 && _livingEntity395.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity395.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity398 && _livingEntity398.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity398.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(4, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity402 && _livingEntity402.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity402.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity405 && _livingEntity405.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity405.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(4, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity409 && _livingEntity409.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity409.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity412 && _livingEntity412.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity412.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(4, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity416 && _livingEntity416.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity416.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
													if (entity instanceof LivingEntity _livingEntity419 && _livingEntity419.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity419.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(4, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity423 && _livingEntity423.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity423.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()
																&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.MACHINE_FRAME_2.get()
																&& true == (blockstate.getBlock().getStateDefinition().getProperty("ready") instanceof BooleanProperty _getbp429 && blockstate.getValue(_getbp429))) {
															if (entity instanceof LivingEntity _livingEntity430 && _livingEntity430.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity430.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															if (world instanceof Level _level) {
																if (!_level.isClientSide()) {
																	_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
																} else {
																	_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
																}
															}
															if (!world.isClientSide()) {
																BlockPos _bp = BlockPos.containing(x, y, z);
																BlockEntity _blockEntity = world.getBlockEntity(_bp);
																BlockState _bs = world.getBlockState(_bp);
																if (_blockEntity != null) {
																	_blockEntity.getPersistentData().putDouble("plug", 0);
																}
																if (world instanceof Level _level)
																	_level.sendBlockUpdated(_bp, _bs, _bs, 3);
															}
															if (Ssc14ModItems.SUBSTATION_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
																for (int index0 = 0; index0 < 9; index0++) {
																	if (world instanceof ILevelExtension _ext
																			&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable)
																		_itemHandlerModifiable.setStackInSlot((int) getBlockNBTNumber(world, BlockPos.containing(x, y, z), "plug"), ItemStack.EMPTY);
																	if (!world.isClientSide()) {
																		BlockPos _bp = BlockPos.containing(x, y, z);
																		BlockEntity _blockEntity = world.getBlockEntity(_bp);
																		BlockState _bs = world.getBlockState(_bp);
																		if (_blockEntity != null) {
																			_blockEntity.getPersistentData().putDouble("plug", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "plug") + 1));
																		}
																		if (world instanceof Level _level)
																			_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																	}
																}
																{
																	BlockPos _bp = BlockPos.containing(x, y, z);
																	BlockState _bs = Ssc14ModBlocks.PODSTATION.get().defaultBlockState();
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
															} else if (Ssc14ModItems.SMES_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
																for (int index1 = 0; index1 < 9; index1++) {
																	if (world instanceof ILevelExtension _ext
																			&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable)
																		_itemHandlerModifiable.setStackInSlot((int) getBlockNBTNumber(world, BlockPos.containing(x, y, z), "plug"), ItemStack.EMPTY);
																	if (!world.isClientSide()) {
																		BlockPos _bp = BlockPos.containing(x, y, z);
																		BlockEntity _blockEntity = world.getBlockEntity(_bp);
																		BlockState _bs = world.getBlockState(_bp);
																		if (_blockEntity != null) {
																			_blockEntity.getPersistentData().putDouble("plug", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "plug") + 1));
																		}
																		if (world instanceof Level _level)
																			_level.sendBlockUpdated(_bp, _bs, _bs, 3);
																	}
																}
																{
																	BlockPos _bp = BlockPos.containing(x, y, z);
																	BlockState _bs = Ssc14ModBlocks.SMES.get().defaultBlockState();
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
															}
														} else {
															if (entity instanceof LivingEntity _livingEntity447 && _livingEntity447.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity447.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity449 && _livingEntity449.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity449.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity451 && _livingEntity451.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity451.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity453 && _livingEntity453.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity453.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity455 && _livingEntity455.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity455.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity457 && _livingEntity457.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity457.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PODSTATION.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SMES.get()) {
			if (entity instanceof LivingEntity _livingEntity463 && _livingEntity463.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
				_livingEntity463.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
			Ssc14Mod.queueServerWork(6, () -> {
				if (entity.getX() + entity.getZ()
						+ entity.getY() == (entity instanceof LivingEntity _livingEntity467 && _livingEntity467.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity467.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
						&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
					if (entity instanceof LivingEntity _livingEntity470 && _livingEntity470.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity470.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
					Ssc14Mod.queueServerWork(6, () -> {
						if (entity.getX() + entity.getZ() + entity
								.getY() == (entity instanceof LivingEntity _livingEntity474 && _livingEntity474.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity474.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
								&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
							if (entity instanceof LivingEntity _livingEntity477 && _livingEntity477.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity477.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
							Ssc14Mod.queueServerWork(6, () -> {
								if (entity.getX() + entity.getZ()
										+ entity.getY() == (entity instanceof LivingEntity _livingEntity481 && _livingEntity481.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
												? _livingEntity481.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
												: 0)
										&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
									if (entity instanceof LivingEntity _livingEntity484 && _livingEntity484.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity484.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
									Ssc14Mod.queueServerWork(6, () -> {
										if (entity.getX() + entity.getZ()
												+ entity.getY() == (entity instanceof LivingEntity _livingEntity488 && _livingEntity488.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
														? _livingEntity488.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
														: 0)
												&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
											if (entity instanceof LivingEntity _livingEntity491 && _livingEntity491.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity491.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
											Ssc14Mod.queueServerWork(6, () -> {
												if (entity.getX() + entity.getZ()
														+ entity.getY() == (entity instanceof LivingEntity _livingEntity495 && _livingEntity495.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																? _livingEntity495.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																: 0)
														&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
													if (entity instanceof LivingEntity _livingEntity498 && _livingEntity498.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity498.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
													Ssc14Mod.queueServerWork(6, () -> {
														if (entity.getX() + entity.getZ()
																+ entity.getY() == (entity instanceof LivingEntity _livingEntity502 && _livingEntity502.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																		? _livingEntity502.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																		: 0)
																&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.SCREWDRIVER.get()) {
															if (entity instanceof LivingEntity _livingEntity505 && _livingEntity505.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity505.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.PODSTATION.get()) {
																if (world instanceof Level _level) {
																	if (!_level.isClientSide()) {
																		_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
																	} else {
																		_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
																	}
																}
																{
																	BlockPos _bp = BlockPos.containing(x, y, z);
																	BlockState _bs = Ssc14ModBlocks.MACHINE_FRAME_2.get().defaultBlockState();
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
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.SUBSTATION_BOARD.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(0, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get()).copy();
																	_setstack.setCount(5);
																	_itemHandlerModifiable.setStackInSlot(1, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get()).copy();
																	_setstack.setCount(5);
																	_itemHandlerModifiable.setStackInSlot(2, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.LOW_BATTERIE.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(3, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.CAPACITOR.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(4, _setstack);
																}
															} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.SMES.get()) {
																if (world instanceof Level _level) {
																	if (!_level.isClientSide()) {
																		_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1);
																	} else {
																		_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:screwdriver")), SoundSource.NEUTRAL, 1, 1, false);
																	}
																}
																{
																	BlockPos _bp = BlockPos.containing(x, y, z);
																	BlockState _bs = Ssc14ModBlocks.MACHINE_FRAME_2.get().defaultBlockState();
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
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.SMES_BOARD.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(0, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get()).copy();
																	_setstack.setCount(10);
																	_itemHandlerModifiable.setStackInSlot(1, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.CAPACITOR.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(2, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.LOW_BATTERIE.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(3, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.LOW_BATTERIE.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(4, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.LOW_BATTERIE.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(5, _setstack);
																}
																if (world instanceof ILevelExtension _ext
																		&& _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
																	ItemStack _setstack = new ItemStack(Ssc14ModItems.LOW_BATTERIE.get()).copy();
																	_setstack.setCount(1);
																	_itemHandlerModifiable.setStackInSlot(6, _setstack);
																}
															}
														} else {
															if (entity instanceof LivingEntity _livingEntity526 && _livingEntity526.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																_livingEntity526.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
														}
													});
												} else {
													if (entity instanceof LivingEntity _livingEntity528 && _livingEntity528.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
														_livingEntity528.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
												}
											});
										} else {
											if (entity instanceof LivingEntity _livingEntity530 && _livingEntity530.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
												_livingEntity530.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
										}
									});
								} else {
									if (entity instanceof LivingEntity _livingEntity532 && _livingEntity532.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
										_livingEntity532.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
								}
							});
						} else {
							if (entity instanceof LivingEntity _livingEntity534 && _livingEntity534.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
								_livingEntity534.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
						}
					});
				} else {
					if (entity instanceof LivingEntity _livingEntity536 && _livingEntity536.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
						_livingEntity536.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
				}
			});
		}
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
			if (itemHandler != null)
				return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}