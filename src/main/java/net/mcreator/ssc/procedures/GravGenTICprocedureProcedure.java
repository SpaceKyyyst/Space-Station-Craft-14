package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.Ssc14ModParticleTypes;

public class GravGenTICprocedureProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (0 == getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			{
				int _value = 0;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			if (true == (blockstate.getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _getbp3 && blockstate.getValue(_getbp3))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0413\u0440\u0430\u0432\u0438\u0442\u0430\u0446\u0438\u044F \u0432\u044B\u043A\u043B").withColor(0xcc2828).withStyle(ChatFormatting.BOLD), false);
				}
			}
		} else if (2000 == getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (true == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "active")) {
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				if (false == (blockstate.getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _getbp10 && blockstate.getValue(_getbp10))) {
					{
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _booleanProp)
							world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
					}
					if (world instanceof ServerLevel _level) {
						_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0413\u0440\u0430\u0432\u0438\u0442\u0430\u0446\u0438\u044F \u0432\u043A\u043B").withColor(0x33cc28).withStyle(ChatFormatting.BOLD), false);
					}
				}
			}
		} else if (2000 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("b_energy", 2000);
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
		if (true == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "active") && 2000 > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("b_energy", (5 + getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")));
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		} else if (false == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "active") && 0 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("b_energy", (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") - 5));
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
		if (1500 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.GRAV_GEN_ACTIVE_PARTICLE.get()), (x + 0.5), (y + 1.5), (z + 0.5), 1, 0, 0, 0, 0);
		} else if (1000 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_2.get()), (x + 0.5), (y + 1.5), (z + 0.5), 1, 0, 0, 0, 0);
		} else if (500 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_3.get()), (x + 0.5), (y + 1.5), (z + 0.5), 1, 0, 0, 0, 0.01);
		} else if (1 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.GRAV_GEN_PARTICLE_4.get()), (x + 0.5), (y + 1.5), (z + 0.5), 1, 0, 0, 0, 0.1);
		}
		if (true == (blockstate.getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _getbp32 && blockstate.getValue(_getbp32))) {
			if (false == Ssc14ModVariables.station_gravity) {
				Ssc14ModVariables.station_gravity = true;
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(
							Component.literal("\u041F\u0440\u0430\u0432\u0438\u043B\u043E \u0433\u0440\u0430\u0432\u0438\u0442\u0430\u0446\u0438\u0438 \u0432\u043A\u043B").withColor(0x33cc28).withStyle(ChatFormatting.BOLD), false);
				}
			}
		} else if (false == (blockstate.getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _getbp35 && blockstate.getValue(_getbp35))) {
			if (true == Ssc14ModVariables.station_gravity) {
				Ssc14ModVariables.station_gravity = false;
				if (world instanceof ServerLevel _level) {
					_level.getServer().getPlayerList().broadcastSystemMessage(
							Component.literal("\u041F\u0440\u0430\u0432\u0438\u043B\u043E \u0433\u0440\u0430\u0432\u0438\u0442\u0430\u0446\u0438\u0438 \u0432\u044B\u043A\u043B").withColor(0xcc2828).withStyle(ChatFormatting.BOLD), false);
				}
			}
		}
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("b_energy:  " + getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy"))).withColor(0xfffdcc).withStyle(ChatFormatting.ITALIC), false);
		}
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("active:  " + getBlockNBTLogic(world, BlockPos.containing(x, y, z), "active"))).withColor(0xccffff).withStyle(ChatFormatting.ITALIC), false);
		}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}