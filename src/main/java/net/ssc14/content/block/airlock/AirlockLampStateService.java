package net.ssc14.content.block.airlock;

import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.common.airlock.AirlockWireState;

public final class AirlockLampStateService {
    private AirlockLampStateService() {
    }
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState ignored) {
        if (!(world instanceof ServerLevel level)) return;
        BlockPos pos = BlockPos.containing(x, y, z);
        if (!level.hasChunkAt(pos)) return;
        BlockState before = level.getBlockState(pos);
        if (before.getBlock().getStateDefinition().getProperty("arl_variat") == null) return;
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return;
        CompoundTag nbt = be.getPersistentData();
        boolean oldPower = nbt.getBooleanOr("powered", true);
        boolean oldBolted = nbt.getBooleanOr("bolted", false);
        boolean oldBoltCut = nbt.getBooleanOr("_ssc14_bolt_wire_cut", false);
        // Existing GUI renders a wire when cabel_N == false: true means CUT.
        boolean[] intact = {true, true, true, true, true, true, true, true};
        for (int i = 1; i <= 8; i++) {
            int function = nbt.getIntOr("log_cab_" + i, -1);
            if (function >= 0 && function < intact.length)
                intact[function] = !nbt.getBooleanOr("cabel_" + i, false)
                        && !AirlockWireState.active(nbt, function, level.getGameTime());
        }
        boolean changed = false;
        changed |= setFlag(nbt, "energy_cabel_1", intact[0]);
        changed |= setFlag(nbt, "energy_cabel_2", intact[1]);
        boolean powerPulse = AirlockWireState.active(nbt, 0, level.getGameTime())
                || AirlockWireState.active(nbt, 1, level.getGameTime());
        boolean powered = nbt.getBooleanOr("_ssc14_supply_powered", oldPower)
                && (intact[0] || intact[1]) && !powerPulse;
        // Remember the supply separately: mending the wires must restore cable power.
        if (!nbt.contains("_ssc14_supply_powered")) {
            nbt.putBoolean("_ssc14_supply_powered", oldPower);
            changed = true;
        }
        changed |= setFlag(nbt, "powered", powered);
        changed |= setFlag(nbt, "timer", intact[3]);
        changed |= setFlag(nbt, "ai_access", intact[4]);
        if (nbt.getBooleanOr("logs", true) && !intact[5]) AirlockEventLogService.clear(be);
        changed |= setFlag(nbt, "logs", intact[5]);
        changed |= setFlag(nbt, "diods", intact[6]);
        changed |= setFlag(nbt, "safe", intact[7]);
        boolean boltCut = !intact[2];
        changed |= setFlag(nbt, "_ssc14_bolt_wire_cut", boltCut);
        // Cutting drops powered bolts; mending does not lift them. Do not drop
        // the bolts merely because an unrelated wire was changed.
        if (boltCut && !oldBoltCut && powered && !oldBolted) {
            nbt.putBoolean("bolted", true);
            changed = true;
            BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.parse("ssc_14:airlock_bolt_on"))
                    .ifPresent(sound -> level.playSound(null, pos, sound, SoundSource.BLOCKS, 1f, 1f));
        }
        if (!changed) return;
        be.setChanged();
        AirlockStateInitializer.execute(level, x, y, z, before);
        if (oldPower != powered || oldBolted != nbt.getBooleanOr("bolted", false))
            BaseAirlockOpenCloseProcedure.refreshAutoClose(level, pos);
        BlockState after = level.getBlockState(pos);
        if (before == after) level.sendBlockUpdated(pos, after, after, 3);
    }

    private static boolean setFlag(CompoundTag nbt, String key, boolean value) {
        if (nbt.contains(key) && nbt.getBooleanOr(key, !value) == value) return false;
        nbt.putBoolean(key, value);
        return true;
    }
}
