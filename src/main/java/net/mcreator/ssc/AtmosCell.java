
package net.mcreator.ssc;

public final class AtmosCell {
    private static final float R = 8.314f;
    private static final float CELL_VOLUME = 1f;
    private final float[] moles = new float[GasType.COUNT];
    private float temperatureK = 294.15f;
    private float cachedPressure = -1f;
    private boolean dirty = true;
    private boolean hasReactions = false;

    public float getMoles(int gasIndex) {
        return gasIndex >= 0 && gasIndex < GasType.COUNT ? moles[gasIndex] : 0f;
    }
    
    public void setMoles(int gasIndex, float value) {
        if (gasIndex < 0 || gasIndex >= GasType.COUNT) return;
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
        this.temperatureK = Math.max(1f, tempK);
        dirty = true;
        updateReactionFlag(); // ✅ КРИТИЧЕСКИЙ ФИКС: обновляем флаг реакции при изменении температуры!
    }

    public float getPressure() {
        if (dirty || cachedPressure < 0f) {
            float total = 0f;
            for (float m : moles) total += m;
            cachedPressure = (total * R * temperatureK) / CELL_VOLUME;
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
        hasReactions = false;
        boolean flammable = false, oxidizer = false;
        for (int i = 0; i < GasType.COUNT; i++) {
            if (moles[i] > 0.01f) {
                GasType g = GasType.byIndex(i);
                if (g != null) { 
                    if (g.isFlammable()) flammable = true; 
                    if (g.isOxidizer()) oxidizer = true; 
                }
            }
        }
        hasReactions = flammable && oxidizer && temperatureK >= 373f && temperatureK <= 1643f;
    }
    
    public boolean canReact() { 
        return hasReactions && getTotalMoles() > 0.1f; 
    }

    public float diffuseTo(AtmosCell target, float permeability, float deltaTime) {
        if (permeability <= 0.0f || target == null) return 0f;
        
        float p1 = this.getPressure();
        float p2 = target.getPressure();
        float deltaP = p1 - p2;
        
        if (Math.abs(deltaP) < 1e-6f) return 0f;
        
        float rate = permeability * 0.2f * deltaTime;
        float t1 = this.getTotalMoles();
        float t2 = target.getTotalMoles();
        if (t1 <= 0f && t2 <= 0f) return 0f;
        
        float transferred = 0f;
        for (int i = 0; i < GasType.COUNT; i++) {
            float m1 = this.getMoles(i);
            float m2 = target.getMoles(i);
            float frac = t1 > 0f ? m1 / t1 : 0f;
            float delta = deltaP > 0f ? m1 * rate * frac : -m2 * rate * (t2 > 0f ? m2 / t2 : 0f);
            delta = Math.max(-m2, Math.min(m1, delta));
            
            if (Math.abs(delta) > 1e-6f) {
                this.addMoles(i, -delta);
                target.addMoles(i, delta);
                transferred += Math.abs(delta);
            }
        }
        
        if (transferred > 0.001f) {
            float h1 = t1 * this.getTemperature();
            float h2 = t2 * target.getTemperature();
            float newT = (h1 + h2) / (t1 + t2 + 1e-6f);
            this.setTemperature(newT); 
            target.setTemperature(newT);
            // ✅ Температура изменилась — обновляем флаг реакции
            updateReactionFlag();
            target.updateReactionFlag();
        }
        
        if (transferred > 0.01f) {
            System.out.println("[SSC14 DEBUG] Diffuse: " + String.format("%.3f", transferred) + " mol | dP= " + String.format("%.1f", deltaP) + " Pa ");
        }
        return transferred;
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
                updateReactionFlag(); // ✅ Обновляем после реакции
            }
        }
        return changed;
    }

    public void writeToNBT(net.minecraft.nbt.CompoundTag tag) {
        for (int i = 0; i < GasType.COUNT; i++) if (moles[i] > 0f) tag.putFloat("m_" + i, moles[i]);
        tag.putFloat("temp", temperatureK);
    }
    
	public void readFromNBT(net.minecraft.nbt.CompoundTag tag) {
	    for (int i = 0; i < GasType.COUNT; i++) {
	        String key = "m_" + i;
	        // ✅ ИСПРАВЛЕНО: используем .orElse() для Optional<Float>
	        moles[i] = tag.contains(key) ? tag.getFloat(key).orElse(0f) : 0f;
	    }
	    if (tag.contains("temp")) {
	        // ✅ ИСПРАВЛЕНО: используем .orElse() для Optional<Float>
	        temperatureK = tag.getFloat("temp").orElse(294.15f);
	    }
	    dirty = true; 
	    updateReactionFlag();
	}
}
