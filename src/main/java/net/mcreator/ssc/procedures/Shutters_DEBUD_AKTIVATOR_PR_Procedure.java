package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class Shutters_DEBUD_AKTIVATOR_PR_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (Ssc14ModItems.DEBUGACTIVATOR.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (false == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "open") instanceof BooleanProperty _getbp3 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp3))) {
				ShuttersOpeningPRProcedure.execute(world, x, y, z, blockstate, entity);
			} else if (true == (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "open") instanceof BooleanProperty _getbp5 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp5))) {
				ShuttersClosingPRProcedure.execute(world, x, y, z, blockstate, entity);
			}
		}
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