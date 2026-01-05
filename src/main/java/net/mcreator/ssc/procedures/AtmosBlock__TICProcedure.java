package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class AtmosBlock__TICProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double o2 = 0;
		double Divided_Part = 0;
		double N_blocks = 0;
		N_blocks = 0;
		if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
			N_blocks = N_blocks + 1;
		}
		if ((world.getBlockState(BlockPos.containing(x - 1, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
			N_blocks = N_blocks + 1;
		}
		if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
			N_blocks = N_blocks + 1;
		}
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
			N_blocks = N_blocks + 1;
		}
		if ((world.getBlockState(BlockPos.containing(x, y, z + 1))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
			N_blocks = N_blocks + 1;
		}
		if ((world.getBlockState(BlockPos.containing(x, y, z - 1))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
			N_blocks = N_blocks + 1;
		}
		Divided_Part = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") / N_blocks;
		if (((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
				|| (world.getBlockState(BlockPos.containing(x, y - 2, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
				|| (world.getBlockState(BlockPos.containing(x, y - 3, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
				|| (world.getBlockState(BlockPos.containing(x, y - 4, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
				|| (world.getBlockState(BlockPos.containing(x, y - 5, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks"))))
				&& ((world.getBlockState(BlockPos.containing(x, y + 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
						|| (world.getBlockState(BlockPos.containing(x, y + 2, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
						|| (world.getBlockState(BlockPos.containing(x, y + 3, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
						|| (world.getBlockState(BlockPos.containing(x, y + 4, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks")))
						|| (world.getBlockState(BlockPos.containing(x, y + 5, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:germetic_blocks"))))) {
			if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Blocks.AIR) {
				world.setBlock(BlockPos.containing(x + 1, y, z), Ssc14ModBlocks.ATMOS_BLOCK.get().defaultBlockState(), 3);
			}
			if ((world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Blocks.AIR) {
				world.setBlock(BlockPos.containing(x - 1, y, z), Ssc14ModBlocks.ATMOS_BLOCK.get().defaultBlockState(), 3);
			}
			if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR) {
				world.setBlock(BlockPos.containing(x, y + 1, z), Ssc14ModBlocks.ATMOS_BLOCK.get().defaultBlockState(), 3);
			}
			if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.AIR) {
				world.setBlock(BlockPos.containing(x, y - 1, z), Ssc14ModBlocks.ATMOS_BLOCK.get().defaultBlockState(), 3);
			}
			if ((world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Blocks.AIR) {
				world.setBlock(BlockPos.containing(x, y, z + 1), Ssc14ModBlocks.ATMOS_BLOCK.get().defaultBlockState(), 3);
			}
			if ((world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Blocks.AIR) {
				world.setBlock(BlockPos.containing(x, y, z - 1), Ssc14ModBlocks.ATMOS_BLOCK.get().defaultBlockState(), 3);
			}
			if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
				if (getBlockNBTNumber(world, BlockPos.containing(x + 1, y, z), "O2") < Divided_Part) {
					o2 = Math.round((getBlockNBTNumber(world, BlockPos.containing(x + 1, y, z), "O2") + Divided_Part) / 2);
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x + 1, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x + 1, y, z), "O2") + o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") - o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			}
			if ((world.getBlockState(BlockPos.containing(x - 1, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
				if (getBlockNBTNumber(world, BlockPos.containing(x - 1, y, z), "O2") < Divided_Part) {
					o2 = Math.round((getBlockNBTNumber(world, BlockPos.containing(x - 1, y, z), "O2") + Divided_Part) / 2);
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x - 1, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x - 1, y, z), "O2") + o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") - o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			}
			if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
				if (getBlockNBTNumber(world, BlockPos.containing(x, y + 1, z), "O2") < Divided_Part) {
					o2 = Math.round((getBlockNBTNumber(world, BlockPos.containing(x, y + 1, z), "O2") + Divided_Part) / 2);
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y + 1, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y + 1, z), "O2") + o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") - o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			}
			if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
				if (getBlockNBTNumber(world, BlockPos.containing(x, y - 1, z), "O2") < Divided_Part) {
					o2 = Math.round((getBlockNBTNumber(world, BlockPos.containing(x, y - 1, z), "O2") + Divided_Part) / 2);
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y - 1, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y - 1, z), "O2") + o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") - o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			}
			if ((world.getBlockState(BlockPos.containing(x, y, z + 1))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
				if (getBlockNBTNumber(world, BlockPos.containing(x, y, z + 1), "O2") < Divided_Part) {
					o2 = Math.round((getBlockNBTNumber(world, BlockPos.containing(x, y, z + 1), "O2") + Divided_Part) / 2);
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z + 1);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z + 1), "O2") + o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") - o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			}
			if ((world.getBlockState(BlockPos.containing(x, y, z - 1))).is(BlockTags.create(ResourceLocation.parse("ssc14:permeable_to_gases")))) {
				if (getBlockNBTNumber(world, BlockPos.containing(x, y, z - 1), "O2") < Divided_Part) {
					o2 = Math.round((getBlockNBTNumber(world, BlockPos.containing(x, y, z - 1), "O2") + Divided_Part) / 2);
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z - 1);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z - 1), "O2") + o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null) {
							_blockEntity.getPersistentData().putDouble("O2", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "O2") - o2));
						}
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
				}
			}
		} else {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}