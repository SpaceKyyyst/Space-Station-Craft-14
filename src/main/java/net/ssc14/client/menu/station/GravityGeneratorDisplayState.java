package net.ssc14.client.menu.station;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Immutable client projection of the gravity generator state used by its screen. */
public record GravityGeneratorDisplayState(int energy, boolean active, boolean showsRod) {
    public static final int MAX_ENERGY = 2000;
    private static final int TICKS_PER_PERCENT = 20;
    private static final int MAX_DISPLAY_SECONDS = 100;

    public enum Status {
        FULL,
        ACTIVE,
        INACTIVE,
        OFF,
        UNKNOWN
    }

    public static GravityGeneratorDisplayState capture(LevelAccessor level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        int energy = -1;
        boolean active = false;
        if (blockEntity != null) {
            energy = blockEntity.getPersistentData().getInt("b_energy").orElse(0);
            active = blockEntity.getPersistentData().getBoolean("active").orElse(false);
        }

        var state = level.getBlockState(pos);
        boolean showsRod = state.getProperties().stream()
                .filter(property -> property.getName().equals("gravity"))
                .filter(BooleanProperty.class::isInstance)
                .map(BooleanProperty.class::cast)
                .anyMatch(state::getValue);
        return new GravityGeneratorDisplayState(energy, active, showsRod);
    }

    public Status status() {
        if (energy == MAX_ENERGY) {
            return Status.FULL;
        }
        if (energy == 0) {
            return Status.OFF;
        }
        if (energy > 0 && energy < MAX_ENERGY) {
            return active ? Status.ACTIVE : Status.INACTIVE;
        }
        return Status.UNKNOWN;
    }

    public String etaText() {
        int boundedEnergy = Math.max(0, Math.min(MAX_ENERGY, energy));
        if (boundedEnergy == 0 || boundedEnergy == MAX_ENERGY) {
            return "N/D";
        }
        int elapsed = active
                ? (MAX_ENERGY - boundedEnergy) / TICKS_PER_PERCENT
                : boundedEnergy / TICKS_PER_PERCENT;
        int boundedSeconds = Math.max(0, Math.min(MAX_DISPLAY_SECONDS, elapsed));
        return String.format("%02d:%02d", boundedSeconds / 60, boundedSeconds % 60);
    }

    public String percentText() {
        return Math.round(energy / (double) TICKS_PER_PERCENT) + " %";
    }
}
