package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class CommunicationServers_TIC_PR_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) != 0) {
			if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip3 ? blockstate.getValue(_getip3) : -1) == 1) {
				Ssc14ModVariables.MapVariables.get(world).Base_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip7 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip7) : -1) == 1)) {
						Ssc14ModVariables.MapVariables.get(world).Base_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip10 ? blockstate.getValue(_getip10) : -1) == 2) {
				Ssc14ModVariables.MapVariables.get(world).Command_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip14 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip14) : -1) == 2)) {
						Ssc14ModVariables.MapVariables.get(world).Command_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip17 ? blockstate.getValue(_getip17) : -1) == 3) {
				Ssc14ModVariables.MapVariables.get(world).Engeneer_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip21 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip21) : -1) == 3)) {
						Ssc14ModVariables.MapVariables.get(world).Engeneer_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip24 ? blockstate.getValue(_getip24) : -1) == 4) {
				Ssc14ModVariables.MapVariables.get(world).Medical_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip28 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip28) : -1) == 4)) {
						Ssc14ModVariables.MapVariables.get(world).Medical_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip31 ? blockstate.getValue(_getip31) : -1) == 5) {
				Ssc14ModVariables.MapVariables.get(world).RND_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip35 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip35) : -1) == 5)) {
						Ssc14ModVariables.MapVariables.get(world).RND_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip38 ? blockstate.getValue(_getip38) : -1) == 6) {
				Ssc14ModVariables.MapVariables.get(world).Sec_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip42 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip42) : -1) == 6)) {
						Ssc14ModVariables.MapVariables.get(world).Sec_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip45 ? blockstate.getValue(_getip45) : -1) == 7) {
				Ssc14ModVariables.MapVariables.get(world).Serv_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip49 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip49) : -1) == 7)) {
						Ssc14ModVariables.MapVariables.get(world).Serv_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
			} else if ((getPropertyByName(blockstate, "type") instanceof IntegerProperty _getip52 ? blockstate.getValue(_getip52) : -1) == 8) {
				Ssc14ModVariables.MapVariables.get(world).Cargo_Radio_Frequency = true;
				Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
				Ssc14Mod.queueServerWork(20, () -> {
					if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Ssc14ModBlocks.COMMUNICATION_SERVERS.get()
							|| (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "type") instanceof IntegerProperty _getip56 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip56) : -1) == 8)) {
						Ssc14ModVariables.MapVariables.get(world).Cargo_Radio_Frequency = false;
						Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
					}
				});
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