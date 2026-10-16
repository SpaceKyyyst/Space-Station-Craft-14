package net.mcreator.ssc;

import net.mcreator.ssc.atmosphere.GasMixture;
import net.minecraft.nbt.CompoundTag;

/** Compatibility facade for simulation callers and the persisted cell format. */
public final class AtmosCell {
    private final GasMixture mixture = new GasMixture();

    public float getMoles(int index) { return mixture.getMoles(index); }
    public void setMoles(int index, float value) { mixture.setMoles(index, value); }
    public void addMoles(int index, float delta) { mixture.addMoles(index, delta); }
    public float getTemperature() { return mixture.getTemperature(); }
    public void setTemperature(float value) { mixture.setTemperature(value); }
    public float getPressure() { return mixture.getPressure(); }
    public float getTotalMoles() { return mixture.getTotalMoles(); }
    public boolean canReact() { return mixture.canReact(); }
    public boolean tryReactions(GasReaction[] reactions) { return mixture.tryReactions(reactions); }
    public float diffuseTo(AtmosCell target, float permeability, float deltaTime) {
        return mixture.diffuseTo(target == null ? null : target.mixture, permeability, deltaTime);
    }

    public void writeToNBT(CompoundTag tag) {
        for (int index = 0; index < GasType.COUNT; index++) {
            tag.putFloat("m_" + index, getMoles(index));
        }
        tag.putFloat("temp", getTemperature());
    }

    public void readFromNBT(CompoundTag tag) {
        for (int index = 0; index < GasType.COUNT; index++) {
            setMoles(index, 0);
            setMoles(index, tag.getFloat("m_" + index).orElse(0f));
        }
        setTemperature(294.15f);
        setTemperature(tag.getFloat("temp").orElse(294.15f));
    }
}
