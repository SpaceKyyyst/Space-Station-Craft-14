package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class BaseAirlockOpenCloseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
			public BlockState with(BlockState _bs, String _property, int _newValue) {
				Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
				return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
			}
		}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 0)).getBlock() && (blockstate.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _getbp4 && blockstate.getValue(_getbp4)) == false) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_open")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_open")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
			Ssc14Mod.queueServerWork(2, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
					public BlockState with(BlockState _bs, String _property, int _newValue) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
						return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
					}
				}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 0)).getBlock()) {
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					Ssc14Mod.queueServerWork(2, () -> {
						if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
							public BlockState with(BlockState _bs, String _property, int _newValue) {
								Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
								return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
							}
						}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 1)).getBlock()) {
							{
								int _value = 2;
								BlockPos _pos = BlockPos.containing(x, y, z);
								BlockState _bs = world.getBlockState(_pos);
								if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
									world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
							}
							Ssc14Mod.queueServerWork(2, () -> {
								if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
									public BlockState with(BlockState _bs, String _property, int _newValue) {
										Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
										return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
									}
								}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 2)).getBlock()) {
									{
										int _value = 3;
										BlockPos _pos = BlockPos.containing(x, y, z);
										BlockState _bs = world.getBlockState(_pos);
										if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
											world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
									}
									Ssc14Mod.queueServerWork(2, () -> {
										if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
											public BlockState with(BlockState _bs, String _property, int _newValue) {
												Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
												return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
											}
										}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 3)).getBlock()) {
											{
												int _value = 4;
												BlockPos _pos = BlockPos.containing(x, y, z);
												BlockState _bs = world.getBlockState(_pos);
												if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
													world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
											}
											Ssc14Mod.queueServerWork(2, () -> {
												if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
													public BlockState with(BlockState _bs, String _property, int _newValue) {
														Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
														return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
													}
												}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 4)).getBlock()) {
													{
														int _value = 5;
														BlockPos _pos = BlockPos.containing(x, y, z);
														BlockState _bs = world.getBlockState(_pos);
														if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
															world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
													}
													{
														BlockPos _pos = BlockPos.containing(x, y, z);
														BlockState _bs = world.getBlockState(_pos);
														if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
															world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
													}
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
			public BlockState with(BlockState _bs, String _property, int _newValue) {
				Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
				return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
			}
		}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 5)).getBlock() && (blockstate.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _getbp36 && blockstate.getValue(_getbp36)) == true
				&& !(!world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 1), (z + 0.5))).inflate(0.5 / 2d), e -> true).isEmpty())
				&& !(!world.getEntitiesOfClass(Mob.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 1), (z + 0.5))).inflate(0.5 / 2d), e -> true).isEmpty())) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_close")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:airlock_close")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
			Ssc14Mod.queueServerWork(2, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
					public BlockState with(BlockState _bs, String _property, int _newValue) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
						return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
					}
				}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 5)).getBlock()) {
					{
						int _value = 4;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					Ssc14Mod.queueServerWork(2, () -> {
						if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
							public BlockState with(BlockState _bs, String _property, int _newValue) {
								Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
								return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
							}
						}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 4)).getBlock()) {
							{
								int _value = 3;
								BlockPos _pos = BlockPos.containing(x, y, z);
								BlockState _bs = world.getBlockState(_pos);
								if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
									world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
							}
							Ssc14Mod.queueServerWork(2, () -> {
								if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
									public BlockState with(BlockState _bs, String _property, int _newValue) {
										Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
										return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
									}
								}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 3)).getBlock()) {
									{
										int _value = 2;
										BlockPos _pos = BlockPos.containing(x, y, z);
										BlockState _bs = world.getBlockState(_pos);
										if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
											world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
									}
									Ssc14Mod.queueServerWork(2, () -> {
										if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
											public BlockState with(BlockState _bs, String _property, int _newValue) {
												Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
												return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
											}
										}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 2)).getBlock()) {
											{
												int _value = 1;
												BlockPos _pos = BlockPos.containing(x, y, z);
												BlockState _bs = world.getBlockState(_pos);
												if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
													world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
											}
											Ssc14Mod.queueServerWork(2, () -> {
												if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == (new Object() {
													public BlockState with(BlockState _bs, String _property, int _newValue) {
														Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
														return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
													}
												}.with(Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), "blockstate", 1)).getBlock()) {
													{
														int _value = 0;
														BlockPos _pos = BlockPos.containing(x, y, z);
														BlockState _bs = world.getBlockState(_pos);
														if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
															world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
													}
													{
														BlockPos _pos = BlockPos.containing(x, y, z);
														BlockState _bs = world.getBlockState(_pos);
														if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
															world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
													}
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});
		}
	}
}