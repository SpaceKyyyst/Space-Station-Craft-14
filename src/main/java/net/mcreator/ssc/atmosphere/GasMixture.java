package net.mcreator.ssc.atmosphere;

import java.util.Objects;
import net.mcreator.ssc.GasReaction;
import net.mcreator.ssc.GasType;

/** One cubic metre of gas. All mutations notify the owning simulation. */
public final class GasMixture {
    public static final float DEFAULT_TEMPERATURE_K = 294.15f;
    private static final double GAS_CONSTANT = 8.314;
    private static final double DIFFUSION_RATE = 0.2;
    private static final double TRANSFER_EPSILON = 1e-6;
    private final float[] moles = new float[GasType.COUNT];
    private final Runnable changed;
    private float temperature = DEFAULT_TEMPERATURE_K;

    public GasMixture() { this(() -> { }); }

    public GasMixture(Runnable changed) {
        this.changed = Objects.requireNonNull(changed, "changed");
    }

    public float getMoles(int index) { return validIndex(index) ? moles[index] : 0f; }

    /** Invalid external numbers are ignored; negative finite amounts clamp to vacuum. */
    public void setMoles(int index, float amount) {
        if (!validIndex(index) || !Float.isFinite(amount)) return;
        float next = Math.max(0f, amount);
        if (moles[index] != next) {
            moles[index] = next;
            changed.run();
        }
    }

    public void addMoles(int index, float amount) {
        if (Float.isFinite(amount)) setMoles(index, getMoles(index) + amount);
    }

    public float getTemperature() { return temperature; }

    public void setTemperature(float kelvin) {
        if (!Float.isFinite(kelvin)) return;
        float next = Math.max(1f, kelvin);
        if (temperature != next) {
            temperature = next;
            changed.run();
        }
    }

    public float getTotalMoles() { return finiteFloat(totalMoles()); }
    public float getPressure() { return finiteFloat(totalMoles() * GAS_CONSTANT * temperature); }

    private double totalMoles() {
        double total = 0;
        for (float amount : moles) total += amount;
        return total;
    }

    private double heatCapacity() {
        double capacity = 0;
        for (int i = 0; i < GasType.COUNT; i++) {
            capacity += (double) moles[i] * GasType.byIndex(i).getHeatCapacity();
        }
        return capacity;
    }

    /** Each reaction owns its actual prerequisites, including non-combustion reactions. */
    public boolean canReact() { return totalMoles() > 0; }

    public boolean tryReactions(GasReaction[] reactions) {
        Objects.requireNonNull(reactions, "reactions");
        boolean reacted = false;
        float[] temperatureRef = {temperature};
        for (GasReaction reaction : reactions) {
            if (reaction.apply(moles, temperatureRef)) reacted = true;
        }
        if (reacted) {
            temperature = temperatureRef[0];
            changed.run();
        }
        return reacted;
    }

    /**
     * Exchanges partial pressures in O(G) time/space. Species and transported heat
     * are conserved within float rounding; equal-pressure mixtures still mix.
     * The rate is bounded at equilibrium even for long time steps.
     */
    public float diffuseTo(GasMixture target, float permeability, float seconds) {
        if (target == null || target == this || !Float.isFinite(permeability)
                || !Float.isFinite(seconds) || permeability <= 0 || seconds <= 0) return 0;
        double rate = Math.min(1, Math.min(1, permeability) * DIFFUSION_RATE * seconds);
        double sourceHeat = heatCapacity() * temperature;
        double targetHeat = target.heatCapacity() * target.temperature;
        double sourceCapacity = 0;
        double targetCapacity = 0;
        double transferred = 0;
        float[] sourceNext = moles.clone();
        float[] targetNext = target.moles.clone();
        for (int i = 0; i < GasType.COUNT; i++) {
            double delta = ((double) moles[i] * temperature - (double) target.moles[i] * target.temperature)
                    / ((double) temperature + target.temperature) * rate;
            if (Math.abs(delta) < TRANSFER_EPSILON) delta = 0;
            sourceNext[i] = (float) (moles[i] - delta);
            targetNext[i] = (float) (target.moles[i] + delta);
            if (!Float.isFinite(sourceNext[i]) || !Float.isFinite(targetNext[i])) return 0;
            double capacityPerMole = GasType.byIndex(i).getHeatCapacity();
            double transportedHeat = delta * capacityPerMole * (delta > 0 ? temperature : target.temperature);
            sourceHeat -= transportedHeat;
            targetHeat += transportedHeat;
            sourceCapacity += sourceNext[i] * capacityPerMole;
            targetCapacity += targetNext[i] * capacityPerMole;
            transferred += Math.abs(delta);
        }
        if (transferred == 0) return 0;
        float sourceTemperature = sourceCapacity > 0 ? (float) (sourceHeat / sourceCapacity) : temperature;
        float targetTemperature = targetCapacity > 0 ? (float) (targetHeat / targetCapacity) : target.temperature;
        if (!Float.isFinite(sourceTemperature) || !Float.isFinite(targetTemperature)) return 0;
        System.arraycopy(sourceNext, 0, moles, 0, GasType.COUNT);
        System.arraycopy(targetNext, 0, target.moles, 0, GasType.COUNT);
        temperature = Math.max(1f, sourceTemperature);
        target.temperature = Math.max(1f, targetTemperature);
        changed.run();
        target.changed.run();
        return finiteFloat(transferred);
    }

    private static boolean validIndex(int index) { return index >= 0 && index < GasType.COUNT; }
    private static float finiteFloat(double value) { return (float) Math.min(Float.MAX_VALUE, value); }
}
