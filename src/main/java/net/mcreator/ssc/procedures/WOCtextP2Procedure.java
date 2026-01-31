package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class WOCtextP2Procedure {
	public static String execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return "";
		String loc_buf_t = "";
		double X = 0;
		double Y = 0;
		double Z = 0;
		X = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(10)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX();
		Y = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(10)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY();
		Z = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(10)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ();
		if (Ssc14ModBlocks.ROD_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 0 < ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip6 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip6) : -1)
				|| Ssc14ModBlocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 0 < ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip10
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip10)
								: -1)
				|| Ssc14ModBlocks.ROD_UP_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				|| Ssc14ModBlocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (0 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip18
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip18)
								: -1)
								|| 8 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip20
										? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip20)
										: -1))) {
			loc_buf_t = "\u041A\u0443\u0441\u0430\u0447\u043A\u0438";
		} else if ((world.getBlockState(BlockPos.containing(X, Y, Z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles"))) || Ssc14ModBlocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (3 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip26 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip26) : -1)
						|| 6 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip28
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip28)
								: -1))
				|| Ssc14ModBlocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 2 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip32
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip32)
								: -1)) {
			loc_buf_t = "\u041B\u043E\u043C";
		} else if (Ssc14ModBlocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14ModBlocks.UPER_SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041F\u043E\u0436\u0430\u0440\u043D\u044B\u0439 \u0442\u043E\u043F\u043E\u0440";
		} else if (Ssc14ModBlocks.WALL_CARCASE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0421\u043D\u0430\u0447\u0430\u043B\u0430 \u043E\u0442\u043A\u0440\u0443\u0442\u0438\u0442\u0438\u0435";
		} else if (Ssc14ModBlocks.STEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14ModBlocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (2 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip44 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip44) : -1)
						|| 5 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip46
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip46)
								: -1))
				|| Ssc14ModBlocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (0 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip50
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip50)
								: -1)
								|| 3 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip52
										? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip52)
										: -1))) {
			loc_buf_t = "\u0421\u0432\u0430\u0440\u043A\u0430";
		} else if (Ssc14ModBlocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (1 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip56 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip56) : -1)
						|| 7 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip58
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip58)
								: -1))
				|| Ssc14ModBlocks.BASE_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 0 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip62
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip62)
								: -1)
				|| Ssc14ModBlocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (1 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip66
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip66)
								: -1)
								|| 4 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip68
										? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip68)
										: -1))) {
			loc_buf_t = "\u041E\u0442\u0432\u0451\u0440\u0442\u043A\u0430";
		} else if (Ssc14ModBlocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 4 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip72 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip72) : -1)
				|| Ssc14ModBlocks.BASE_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 1 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip76
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip76)
								: -1)
				|| Ssc14ModBlocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 5 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip80
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip80)
								: -1)) {
			loc_buf_t = "\u0413\u0430\u0435\u0447\u043D\u044B\u0439 \u043A\u043B\u044E\u0447";
		}
		return "" + loc_buf_t;
	}
}