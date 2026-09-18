
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class CPlogLampPrProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null)
			return;
		CompoundTag nbt = be.getPersistentData();
		if (world.isClientSide())
			return;
		boolean needsUpdate = false;
		for (int cableIndex = 0; cableIndex < 8; cableIndex++) {
			String cableTag = "cabel_" + (cableIndex + 1);
			String logTag = "log_cab_" + (cableIndex + 1);
			boolean isCableCut = !nbt.getBooleanOr(cableTag, false);
			int logValue = (int) nbt.getDoubleOr(logTag, 0);
			if (logValue < 0 || logValue > 7)
				continue;
			if (isCableCut) {
				switch (logValue) {
					case 0 :
						if (!nbt.getBooleanOr("energy_cabel_1", false)) {
							nbt.putBoolean("energy_cabel_1", true);
							needsUpdate = true;
						}
						break;
					case 1 :
						if (!getBooleanProperty(blockstate, "energy_cabel_2", false) && !nbt.getBooleanOr("energy_cabel_2", false)) {
							nbt.putBoolean("energy_cabel_2", true);
							needsUpdate = true;
						}
						break;
					case 2 :
						if (!nbt.getBooleanOr("bolted", false)) {
							nbt.putBoolean("bolted", true);
							needsUpdate = true;
							Property<?> boltedProp = world.getBlockState(pos).getBlock().getStateDefinition().getProperty("bolted");
							if (boltedProp instanceof BooleanProperty bp) {
								world.setBlock(pos, world.getBlockState(pos).setValue(bp, true), 3);
							}
							// ИСПРАВЛЕНО: Безопасный вызов звука болтов шлюза для 26.1.2 через фабричный метод
							BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.fromNamespaceAndPath("ssc_14", "airlock_bolt_on")).ifPresent(sound -> ((Level) world).playSound(null, pos, sound, SoundSource.NEUTRAL, 1f, 1f));
							BaseAirlockD1PutProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
						}
						break;
					case 3 :
						if (!nbt.getBooleanOr("timer", false)) {
							nbt.putBoolean("timer", true);
							needsUpdate = true;
						}
						break;
					case 4 :
						if (!nbt.getBooleanOr("ai_access", false)) {
							nbt.putBoolean("ai_access", true);
							needsUpdate = true;
						}
						break;
					case 5 :
						if (!nbt.getBooleanOr("logs", false)) {
							nbt.putBoolean("logs", true);
							needsUpdate = true;
						}
						break;
					case 6 :
						if (!nbt.getBooleanOr("diods", false)) {
							nbt.putBoolean("diods", true);
							needsUpdate = true;
						}
						break;
					case 7 :
						if (!nbt.getBooleanOr("safe", false)) {
							nbt.putBoolean("safe", true);
							needsUpdate = true;
						}
						break;
				}
			} else {
				switch (logValue) {
					case 0 :
						if (nbt.getBooleanOr("energy_cabel_1", true)) {
							nbt.putBoolean("energy_cabel_1", false);
							needsUpdate = true;
						}
						break;
					case 1 :
						if (nbt.getBooleanOr("energy_cabel_2", true)) {
							nbt.putBoolean("energy_cabel_2", false);
							needsUpdate = true;
						}
						break;
					case 3 :
						if (nbt.getBooleanOr("timer", true)) {
							nbt.putBoolean("timer", false);
							needsUpdate = true;
						}
						break;
					case 4 :
						if (nbt.getBooleanOr("ai_access", true)) {
							nbt.putBoolean("ai_access", false);
							needsUpdate = true;
						}
						break;
					case 5 :
						if (nbt.getBooleanOr("logs", true)) {
							nbt.putBoolean("logs", false);
							needsUpdate = true;
						}
						break;
					case 6 :
						if (nbt.getBooleanOr("diods", true)) {
							nbt.putBoolean("diods", false);
							needsUpdate = true;
						}
						break;
					case 7 :
						if (nbt.getBooleanOr("safe", true)) {
							nbt.putBoolean("safe", false);
							needsUpdate = true;
						}
						break;
				}
			}
		}
		if (needsUpdate && world instanceof Level level) {
			level.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}

	private static boolean getBooleanProperty(BlockState state, String propName, boolean defaultValue) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(propName);
		if (prop instanceof BooleanProperty bp) {
			return state.getValue(bp);
		}
		return defaultValue;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity be = world.getBlockEntity(pos);
		return be != null && be.getPersistentData().getBooleanOr(tag, false);
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity be = world.getBlockEntity(pos);
		return be != null ? be.getPersistentData().getDoubleOr(tag, 0) : -1;
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
