package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.entity.C4CrutchEntEntity;
import net.mcreator.ssc.Ssc14Mod;

public class PlstWallExplosionBreakdownProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Ssc14Mod.queueServerWork(1, () -> {
			if (!world.getEntitiesOfClass(C4CrutchEntEntity.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.5), (z + 0.5))).inflate(2 / 2d), e -> true).isEmpty()) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
					world.destroyBlock(_pos, false);
				}
			} else if (!world.getEntitiesOfClass(C4CrutchEntEntity.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), (y + 0.5), (z + 0.5))).inflate(10 / 2d), e -> true).isEmpty()) {
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
			} else {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
					world.destroyBlock(_pos, false);
				}
			}
		});
	}
}