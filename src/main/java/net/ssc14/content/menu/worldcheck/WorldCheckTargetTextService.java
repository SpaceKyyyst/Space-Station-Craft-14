package net.ssc14.content.menu.worldcheck;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

import net.ssc14.content.block.Ssc14Blocks;

public final class WorldCheckTargetTextService {
	public static boolean hasTarget(Entity entity) {
		if (entity == null) return false;
		return entity.level().clip(new ClipContext(entity.getEyePosition(1f),
				entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)),
				ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.BLOCK;
	}

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
		if (Ssc14Blocks.ROD_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 0 < (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip6 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip6) : -1)
				|| Ssc14Blocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 0 < (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip10 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip10) : -1)
				|| Ssc14Blocks.ROD_UP_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14Blocks.PLASTEEL_WALL_CARCASE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				|| Ssc14Blocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (0 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip18 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip18) : -1)
								|| 8 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip20 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip20) : -1))
				|| Ssc14Blocks.GRILLE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14Blocks.BROKEN_GRILLE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041A\u0443\u0441\u0430\u0447\u043A\u0438";
		} else if ((world.getBlockState(BlockPos.containing(X, Y, Z))).is(BlockTags.create(Identifier.parse("ssc14:tiles")))
				|| Ssc14Blocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (3 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip30 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip30) : -1)
								|| 6 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip32 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip32) : -1))
				|| Ssc14Blocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 2 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip36 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip36) : -1)) {
			loc_buf_t = "\u041B\u043E\u043C";
		} else if (Ssc14Blocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14Blocks.UPER_SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041F\u043E\u0436\u0430\u0440\u043D\u044B\u0439 \u0442\u043E\u043F\u043E\u0440";
		} else if (Ssc14Blocks.WALL_CARCASE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0421\u043D\u0430\u0447\u0430\u043B\u0430 \u043E\u0442\u043A\u0440\u0443\u0442\u0438\u0442\u0438\u0435";
		} else if (Ssc14Blocks.STEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				|| Ssc14Blocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (2 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip48 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip48) : -1)
								|| 5 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip50 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip50) : -1))
				|| Ssc14Blocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (0 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip54 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip54) : -1)
								|| 3 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip56 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip56) : -1))) {
			loc_buf_t = "\u0421\u0432\u0430\u0440\u043A\u0430";
		} else if (Ssc14Blocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (1 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip60 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip60) : -1)
						|| 7 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip62 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip62) : -1))
				|| Ssc14Blocks.BASE_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 0 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip66 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip66) : -1)
				|| Ssc14Blocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& (1 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip70 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip70) : -1)
								|| 4 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip72 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip72) : -1))) {
			loc_buf_t = "\u041E\u0442\u0432\u0451\u0440\u0442\u043A\u0430";
		} else if (Ssc14Blocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 4 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip76 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip76) : -1)
				|| Ssc14Blocks.BASE_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 1 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip80 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip80) : -1)
				|| Ssc14Blocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
						&& 5 == (getPropertyByName((world.getBlockState(BlockPos.containing(X, Y, Z))), "blockstate") instanceof IntegerProperty _getip84 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip84) : -1)) {
			loc_buf_t = "\u0413\u0430\u0435\u0447\u043D\u044B\u0439 \u043A\u043B\u044E\u0447";
		}
		return "" + loc_buf_t;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}
