package net.mcreator.ssc.procedures;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class BrokeGrilleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		{
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockState _bs = Ssc14ModBlocks.BROKEN_GRILLE.get().defaultBlockState();
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
			Direction _dir = Direction.getRandom(RandomSource.create());
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty _dp && _dp.getPossibleValues().contains(_dir)) {
				world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
			} else if (_bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis())) {
				world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
			}
		}
	}
}