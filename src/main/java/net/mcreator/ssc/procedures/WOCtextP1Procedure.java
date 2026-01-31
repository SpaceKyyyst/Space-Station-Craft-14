package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class WOCtextP1Procedure {
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
		if (Ssc14ModBlocks.ROD_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0412\u043D\u0435\u0448\u043D\u0438\u0439 \u043A\u0430\u0440\u043A\u0430\u0441";
		} else if (Ssc14ModBlocks.ROD_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (1 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip8 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip8) : -1)
						|| 4 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip10
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip10)
								: -1)
						|| 5 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip12
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip12)
								: -1)
						|| 7 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip14
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip14)
								: -1))) {
			loc_buf_t = "\u041D\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if (Ssc14ModBlocks.ROD_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (2 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip18 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip18) : -1)
						|| 6 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip20
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip20)
								: -1))) {
			loc_buf_t = "\u0421\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if (Ssc14ModBlocks.ROD_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 3 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip24
						? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip24)
						: -1)) {
			loc_buf_t = "\u0412\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if (Ssc14ModBlocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 0 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip28
						? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip28)
						: -1)) {
			loc_buf_t = "\u041E\u0431\u0448\u0438\u0432\u043A\u0430";
		} else if (Ssc14ModBlocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (1 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip32 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip32) : -1)
						|| 4 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip34
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip34)
								: -1)
						|| 5 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip36
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip36)
								: -1)
						|| 7 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip38
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip38)
								: -1))) {
			loc_buf_t = "\u041D\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if (Ssc14ModBlocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& (2 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip42 ? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip42) : -1)
						|| 6 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip44
								? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip44)
								: -1))) {
			loc_buf_t = "\u0421\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if (Ssc14ModBlocks.SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()
				&& 3 == ((world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip48
						? (world.getBlockState(BlockPos.containing(X, Y, Z))).getValue(_getip48)
						: -1)) {
			loc_buf_t = "\u0412\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if ((world.getBlockState(BlockPos.containing(X, Y, Z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
			loc_buf_t = "\u041F\u043B\u0438\u0442\u043A\u0430";
		} else if (Ssc14ModBlocks.STEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0421\u0442\u0430\u043B\u044C\u043D\u0430\u044F \u0441\u0442\u0435\u043D\u0430";
		} else if (Ssc14ModBlocks.WALL_CARCASE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041A\u0430\u0440\u043A\u0430\u0441 \u0441\u0442\u0435\u043D\u044B";
		} else if (Ssc14ModBlocks.BASE_AIRLOCK_D_1.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock() || Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0428\u043B\u044E\u0437";
		} else if (Ssc14ModBlocks.UPER_SHEATHING.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041F\u043E\u0442\u043E\u043B\u043E\u0447\u043D\u0430\u044F \u043E\u0431\u0448\u0438\u0432\u043A\u0430";
		} else if (Ssc14ModBlocks.ROD_UP_FLOOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041F\u043E\u0442\u043E\u043B\u043E\u0447\u043D\u044B\u0439 \u0432\u043D\u0435\u0448\u043D\u0438\u0439 \u043A\u0430\u0440\u043A\u0430\u0441";
		} else if (Ssc14ModBlocks.CONSOLE_OF_ID.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041A\u043E\u043D\u0441\u043E\u043B\u044C ID-\u043A\u0430\u0440\u0442";
		} else if (Ssc14ModBlocks.BASE_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041E\u043A\u043D\u043E";
		} else if (Ssc14ModBlocks.PORTABLE_SHEATER.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0422\u0435\u0440\u043C\u043E\u0441\u0442\u0430\u0442";
		} else if (Ssc14ModBlocks.PLASTEEL_WALL.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0423\u043A\u0440\u0435\u043F\u043B\u0451\u043D\u043D\u0430\u044F \u0441\u0442\u0435\u043D\u0430";
		} else if (Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0423\u043A\u0440\u0435\u043F\u043B\u0451\u043D\u043D\u044B\u0439 \u043A\u0430\u0440\u043A\u0430\u0441";
		} else if (Ssc14ModBlocks.APC.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041B\u041A\u041F";
		} else if (Ssc14ModBlocks.PODSTATION.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041F\u043E\u0434\u0441\u0442\u0430\u043D\u0446\u0438\u044F";
		} else if (Ssc14ModBlocks.DEBU_GGENERATOR.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "DEBUG \u0433\u0435\u043D\u0435\u0440\u0430\u0442\u043E\u0440";
		} else if (Ssc14ModBlocks.LAMP.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0421\u0432\u0435\u0442\u0438\u043B\u044C\u043D\u0438\u043A";
		} else if (Ssc14ModBlocks.SMES.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0421\u041C\u042D\u0421";
		} else if (Ssc14ModBlocks.TECH_LAMP.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u041B\u0430\u043C\u043F\u0430 \u043D\u0430\u043A\u0430\u043B\u0438\u0432\u0430\u043D\u0438\u044F";
		} else if (Ssc14ModBlocks.ARMORED_WINDOW.get() == (world.getBlockState(BlockPos.containing(X, Y, Z))).getBlock()) {
			loc_buf_t = "\u0411\u0440\u043E\u043D\u0435\u043E\u043A\u043D\u043E";
		}
		return "" + loc_buf_t;
	}
}