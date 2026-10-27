package net.mcreator.ssc;

import net.ssc14.common.atmosphere.model.GasType;
import net.mcreator.ssc.atmosphere.GasMixture;
import net.minecraft.nbt.CompoundTag;

/** Minecraft persistence adapter; the public cell API and NBT keys remain stable. */
public final class AtmosCell {
    private final GasMixture mixture;

    public AtmosCell() { mixture = new GasMixture(); }
    AtmosCell(Runnable changed) { mixture = new GasMixture(changed); }

    public float getMoles(int gasIndex) { return mixture.getMoles(gasIndex); }
    public void setMoles(int gasIndex, float value) { mixture.setMoles(gasIndex, value); }
    public void addMoles(int gasIndex, float delta) { mixture.addMoles(gasIndex, delta); }
    public float getTemperature() { return mixture.getTemperature(); }
    public void setTemperature(float tempK) { mixture.setTemperature(tempK); }
    public float getPressure() { return mixture.getPressure(); }
    public float getTotalMoles() { return mixture.getTotalMoles(); }
    public boolean canReact() { return mixture.canReact(); }

    public float diffuseTo(AtmosCell target, float permeability, float deltaTime) {
        return mixture.diffuseTo(target == null ? null : target.mixture, permeability, deltaTime);
    }

    public boolean tryReactions(GasReaction[] reactions) { return mixture.tryReactions(reactions); }

    public void writeToNBT(CompoundTag tag) {
        for (int i = 0; i < GasType.COUNT; i++) {
            String key = "m_" + i;
            if (getMoles(i) > 0) tag.putFloat(key, getMoles(i));
            else tag.remove(key);
        }
        tag.putFloat("temp", getTemperature());
    }

    public void readFromNBT(CompoundTag tag) {
        for (int i = 0; i < GasType.COUNT; i++) {
            float amount = tag.getFloat("m_" + i).orElse(0f);
            setMoles(i, Float.isFinite(amount) ? amount : 0f);
        }
        float temperature = tag.getFloat("temp").orElse(GasMixture.DEFAULT_TEMPERATURE_K);
        setTemperature(Float.isFinite(temperature) ? temperature : GasMixture.DEFAULT_TEMPERATURE_K);
    }
}
