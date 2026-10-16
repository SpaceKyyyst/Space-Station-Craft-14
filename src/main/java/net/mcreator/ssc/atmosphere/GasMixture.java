
package net.mcreator.ssc.atmosphere;

import net.mcreator.ssc.GasType;
import net.mcreator.ssc.GasReaction;

public final class GasMixture {
    private static final float R = 8.314f;
    private static final float CELL_VOLUME = 1f;
    private static final double EXCHANGE_RATE_PER_SECOND = 0.2;
    private static final double MAX_EXCHANGE_FRACTION = 0.5;
    private static final double RELATIVE_MOLE_TOLERANCE = 1e-5;
    private static final double ABSOLUTE_MOLE_TOLERANCE = 1e-12;
    private static final float TEMPERATURE_TOLERANCE_K = 0.001f;
    private final float[] moles = new float[GasType.COUNT];
    private float temperatureK = 294.15f;
    private float cachedPressure = -1f;
    private boolean dirty = true;
    private boolean hasReactions = false;

    public float getMoles(int gasIndex) {
        return gasIndex >= 0 && gasIndex < GasType.COUNT ? moles[gasIndex] : 0f;
    }

    public void setMoles(int gasIndex, float value) {
        if (gasIndex < 0 || gasIndex >= GasType.COUNT || !Float.isFinite(value)) return;
        moles[gasIndex] = Math.max(0f, value);
        dirty = true;
        updateReactionFlag();
    }

    public void addMoles(int gasIndex, float delta) {
        if (delta == 0f) return;
        setMoles(gasIndex, getMoles(gasIndex) + delta);
    }

    public float getTemperature() { return temperatureK; }

    public void setTemperature(float tempK) {
        if (!Float.isFinite(tempK)) return;
        this.temperatureK = Math.max(1f, tempK);
        dirty = true;
        updateReactionFlag();
    }

    public float getPressure() {
        if (dirty || cachedPressure < 0f) {
            float total = 0f;
            for (float m : moles) total += m;
            cachedPressure = (float) Math.min(Float.MAX_VALUE, ((double) total * R * temperatureK) / CELL_VOLUME);
            dirty = false;
        }
        return cachedPressure;
    }

    public float getTotalMoles() {
        float sum = 0f;
        for (float m : moles) sum += m;
        return sum;
    }

    private void updateReactionFlag() {
        hasReactions = getTotalMoles() > 0f;
    }

    public boolean canReact() {
        return hasReactions;
    }

    public float diffuseTo(GasMixture target, float permeability, float deltaTime) {
        if (!Float.isFinite(permeability) || !Float.isFinite(deltaTime)
                || permeability <= 0f || deltaTime <= 0f || target == null || target == this) return 0f;
        boolean different = false;
        for (int i = 0; i < GasType.COUNT; i++) {
            double difference = Math.abs((double) moles[i] - target.moles[i]);
            if (difference > Math.max(ABSOLUTE_MOLE_TOLERANCE,
                    Math.max(moles[i], target.moles[i]) * RELATIVE_MOLE_TOLERANCE)) different = true;
        }
        if (!different && (getTotalMoles() == 0f || target.getTotalMoles() == 0f
                || Math.abs(temperatureK - target.temperatureK) < TEMPERATURE_TOLERANCE_K)) return 0f;

        // Exchange equal fractions of both equal-volume cells, including at equal pressure.
        double rate = Math.min(MAX_EXCHANGE_FRACTION,
                Math.min(1f, permeability) * EXCHANGE_RATE_PER_SECOND * deltaTime);
        double energy1 = heatCapacity() * temperatureK;
        double energy2 = target.heatCapacity() * target.temperatureK;
        float transferred = 0f;
        for (int i = 0; i < GasType.COUNT; i++) {
            double delta = ((double) moles[i] - target.moles[i]) * rate;
            moles[i] = (float) (moles[i] - delta);
            target.moles[i] = (float) (target.moles[i] + delta);
            transferred += (float) Math.abs(delta);
        }
        double capacity1 = heatCapacity();
        double capacity2 = target.heatCapacity();
        if (capacity1 > 0) setTemperature((float) ((energy1 * (1 - rate) + energy2 * rate) / capacity1));
        if (capacity2 > 0) target.setTemperature((float) ((energy2 * (1 - rate) + energy1 * rate) / capacity2));
        dirty = true;
        target.dirty = true;
        updateReactionFlag();
        target.updateReactionFlag();
        return transferred;
    }

    private double heatCapacity() {
        double capacity = 0;
        for (int i = 0; i < GasType.COUNT; i++) {
            capacity += (double) moles[i] * GasType.byIndex(i).getHeatCapacity();
        }
        return capacity;
    }

    public boolean tryReactions(GasReaction[] reactions) {
        if (!canReact()) return false;
        boolean changed = false;
        for (GasReaction reaction : reactions) {
            float[] tempRef = new float[]{temperatureK};
            if (reaction.apply(moles, tempRef)) {
                temperatureK = tempRef[0];
                dirty = true;
                changed = true;
                updateReactionFlag();
            }
        }
        return changed;
    }

}
