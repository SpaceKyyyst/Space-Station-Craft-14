package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class ExtinguisherCabinet_Click_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		if (false == (getPropertyByName(blockstate, "open") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1))) {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
			}
		} else if (true == (getPropertyByName(blockstate, "open") instanceof BooleanProperty _getbp4 && blockstate.getValue(_getbp4))) {
			if (entity.isShiftKeyDown()) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
			} else {
				if (false == (getPropertyByName(blockstate, "empty") instanceof BooleanProperty _getbp8 && blockstate.getValue(_getbp8))
						&& ItemStack.EMPTY.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
					{
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("empty") instanceof BooleanProperty _booleanProp)
							world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
					}
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack13 = new ItemStack(Ssc14ModItems.EXTINGUISHER.get()).copy();
						_setstack13.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack13);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				} else if (true == (getPropertyByName(blockstate, "empty") instanceof BooleanProperty _getbp15 && blockstate.getValue(_getbp15))
						&& (Ssc14ModItems.EXTINGUISHER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
								|| Ssc14ModItems.EXTINGUISHER_OPEN.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem())) {
					(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
					{
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("empty") instanceof BooleanProperty _booleanProp)
							world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
					}
				} else {
					{
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("open") instanceof BooleanProperty _booleanProp)
							world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
					}
				}
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