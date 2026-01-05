package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class ActiveWelder_UseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double fuel = 0;
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER))
			_livingEntity3.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).setBaseValue((entity.getX() + entity.getZ() + entity.getY()));
		if (entity.isShiftKeyDown()) {
			if (entity.getX() + entity.getZ()
					+ entity.getY() == (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity8.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
					&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
				if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
					_livingEntity13.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(1);
				Ssc14Mod.queueServerWork(20, () -> {
					if (entity.getX() + entity.getZ() + entity
							.getY() == (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER) ? _livingEntity17.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue() : 0)
							&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
							&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
						if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
							_livingEntity22.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(2);
						Ssc14Mod.queueServerWork(20, () -> {
							if (entity.getX() + entity.getZ()
									+ entity.getY() == (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
											? _livingEntity26.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
											: 0)
									&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
									&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
								if (entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
									_livingEntity31.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(3);
								Ssc14Mod.queueServerWork(20, () -> {
									if (entity.getX() + entity.getZ()
											+ entity.getY() == (entity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
													? _livingEntity35.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
													: 0)
											&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
											&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
										if (entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
											_livingEntity40.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(4);
										Ssc14Mod.queueServerWork(20, () -> {
											if (entity.getX() + entity.getZ()
													+ entity.getY() == (entity instanceof LivingEntity _livingEntity44 && _livingEntity44.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
															? _livingEntity44.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
															: 0)
													&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
													&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
												if (entity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
													_livingEntity49.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(5);
												Ssc14Mod.queueServerWork(20, () -> {
													if (entity.getX() + entity.getZ()
															+ entity.getY() == (entity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																	? _livingEntity53.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																	: 0)
															&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
															&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
														if (entity instanceof LivingEntity _livingEntity58 && _livingEntity58.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity58.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(6);
														Ssc14Mod.queueServerWork(20, () -> {
															if (entity.getX() + entity.getZ()
																	+ entity.getY() == (entity instanceof LivingEntity _livingEntity62 && _livingEntity62.getAttributes().hasAttribute(Ssc14ModAttributes.XY_ZPLAYER)
																			? _livingEntity62.getAttribute(Ssc14ModAttributes.XY_ZPLAYER).getValue()
																			: 0)
																	&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel",
																			0) > 450
																	&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()
																	&& (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.STEEL_WALL.get()) {
																if (world instanceof Level _level) {
																	if (!_level.isClientSide()) {
																		_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1);
																	} else {
																		_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_use")), SoundSource.NEUTRAL, 1, 1, false);
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
																	ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.5), (z + 0.5), new ItemStack(Ssc14ModItems.STEEL.get()));
																	entityToSpawn.setPickUpDelay(1);
																	entityToSpawn.setUnlimitedLifetime();
																	_level.addFreshEntity(entityToSpawn);
																}
																{
																	final String _tagName = "fuel";
																	final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
																			.getDoubleOr("fuel", 0) - 350);
																	CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
																}
																if (entity instanceof LivingEntity _livingEntity76 && _livingEntity76.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity76.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															} else {
																if (entity instanceof LivingEntity _livingEntity77 && _livingEntity77.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
																	_livingEntity77.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
															}
														});
													} else {
														if (entity instanceof LivingEntity _livingEntity79 && _livingEntity79.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
															_livingEntity79.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
													}
												});
											} else {
												if (entity instanceof LivingEntity _livingEntity81 && _livingEntity81.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
													_livingEntity81.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
											}
										});
									} else {
										if (entity instanceof LivingEntity _livingEntity83 && _livingEntity83.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
											_livingEntity83.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
									}
								});
							} else {
								if (entity instanceof LivingEntity _livingEntity85 && _livingEntity85.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
									_livingEntity85.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
							}
						});
					} else {
						if (entity instanceof LivingEntity _livingEntity87 && _livingEntity87.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB))
							_livingEntity87.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
					}
				});
			}
		} else {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.WELDING.get()
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 0) > 600) {
				fuel = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 0);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack95 = new ItemStack(Ssc14ModItems.ACTIVE_WELDER.get()).copy();
					_setstack95.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack95);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_on")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_on")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				{
					final String _tagName = "fuel";
					final double _tagValue = (fuel - 350);
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.ACTIVE_WELDER.get()) {
				fuel = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 0);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack103 = new ItemStack(Ssc14ModItems.WELDING.get()).copy();
					_setstack103.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack103);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_off")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:welder_off")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				{
					final String _tagName = "fuel";
					final double _tagValue = fuel;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
			}
		}
	}
}