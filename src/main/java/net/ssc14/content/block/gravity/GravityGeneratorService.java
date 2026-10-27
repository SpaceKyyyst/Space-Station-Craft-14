package net.ssc14.content.block.gravity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.content.particle.Ssc14ParticleTypes;
import net.ssc14.content.gravity.StationGravityState;

/** Charge simulation and throttled visual synchronization for a loaded generator. */
public final class GravityGeneratorService {
    private static final int MAX_CHARGE = 2000;
    private static final int CHARGE_PER_TICK = 5;
    private static final int VISUAL_INTERVAL_TICKS = 4;
    private static final String CHARGE_TAG = "b_energy";
    private static final String ENABLED_TAG = "active";
    private GravityGeneratorService() { }

    public static void tick(ServerLevel level, BlockPos position) {
        var blockEntity = level.getBlockEntity(position);
        if (blockEntity == null) return;
        var state = blockEntity.getBlockState();
        if (!(state.getBlock() instanceof GravityGeneratorBlock)) return;
        var data = blockEntity.getPersistentData();
        double stored = data.getDoubleOr(CHARGE_TAG, 0);
        int charge = Double.isFinite(stored) ? (int) Math.clamp(stored, 0, MAX_CHARGE) : 0;
        boolean enabled = data.getBooleanOr(ENABLED_TAG, false);
        int nextCharge = Math.clamp(charge + (enabled ? CHARGE_PER_TICK : -CHARGE_PER_TICK), 0, MAX_CHARGE);
        boolean gravity = state.getValue(GravityGeneratorBlock.GRAVITY);
        if (nextCharge == 0) gravity = false;
        else if (nextCharge == MAX_CHARGE && enabled) gravity = true;
        int indicator = nextCharge == 0 ? 0 : nextCharge == MAX_CHARGE && enabled ? 1 : state.getValue(GravityGeneratorBlock.BLOCKSTATE);
        var nextState = state.setValue(GravityGeneratorBlock.GRAVITY, gravity).setValue(GravityGeneratorBlock.BLOCKSTATE, indicator);
        boolean changed = stored != nextCharge;
        if (changed) {
            data.putDouble(CHARGE_TAG, nextCharge);
            blockEntity.setChanged();
        }
        if (nextState != state) level.setBlock(position, nextState, 3);
        else if (changed && level.getGameTime() % VISUAL_INTERVAL_TICKS == 0) level.sendBlockUpdated(position, state, state, 3);
        StationGravityState.setEnabled(gravity);
        if (level.getGameTime() % VISUAL_INTERVAL_TICKS == 0) emitParticle(level, position, nextCharge);
    }

    private static void emitParticle(ServerLevel level, BlockPos position, int charge) {
        if (charge <= 1) return;
        ParticleOptions particle;
        double speed = 0;
        if (charge > MAX_CHARGE * 3 / 4) particle = Ssc14ParticleTypes.GRAV_GEN_ACTIVE_PARTICLE.get();
        else if (charge > MAX_CHARGE / 2) particle = Ssc14ParticleTypes.GRAV_GEN_PARTICLE_2.get();
        else if (charge > MAX_CHARGE / 4) {
            particle = Ssc14ParticleTypes.GRAV_GEN_PARTICLE_3.get();
            speed = 0.01;
        } else {
            particle = Ssc14ParticleTypes.GRAV_GEN_PARTICLE_4.get();
            speed = 0.1;
        }
        level.sendParticles(particle, position.getX() + 0.5, position.getY() + 1.5, position.getZ() + 0.5,
                1, 0, 0, 0, speed);
    }
}
