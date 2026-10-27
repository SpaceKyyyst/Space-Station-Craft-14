package net.ssc14.integration.neoforge.player;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

/** Persisted SSC14 player damage state. */
public final class Ssc14PlayerVariables implements ValueIOSerializable {
    private boolean syncDirty;
    public double sscCustomHealth;
    public double ssc14_dmg_blunt;
    public double ssc14_dmg_slash;
    public double ssc14_dmg_piercing;
    public double ssc14_dmg_heat;
    public double ssc14_dmg_shock;
    public double ssc14_dmg_cold;
    public double ssc14_dmg_caustic;
    public double ssc14_dmg_poison;
    public double ssc14_dmg_radiation;
    public double ssc14_dmg_asphyx;
    public double ssc14_dmg_bloodloss;
    public double ssc14_dmg_cellular;

    @Override
    public void serialize(ValueOutput output) {
        output.putDouble("sscCustomHealth", sscCustomHealth);
        output.putDouble("ssc14_dmg_blunt", ssc14_dmg_blunt);
        output.putDouble("ssc14_dmg_slash", ssc14_dmg_slash);
        output.putDouble("ssc14_dmg_piercing", ssc14_dmg_piercing);
        output.putDouble("ssc14_dmg_heat", ssc14_dmg_heat);
        output.putDouble("ssc14_dmg_shock", ssc14_dmg_shock);
        output.putDouble("ssc14_dmg_cold", ssc14_dmg_cold);
        output.putDouble("ssc14_dmg_caustic", ssc14_dmg_caustic);
        output.putDouble("ssc14_dmg_poison", ssc14_dmg_poison);
        output.putDouble("ssc14_dmg_radiation", ssc14_dmg_radiation);
        output.putDouble("ssc14_dmg_asphyx", ssc14_dmg_asphyx);
        output.putDouble("ssc14_dmg_bloodloss", ssc14_dmg_bloodloss);
        output.putDouble("ssc14_dmg_cellular", ssc14_dmg_cellular);
    }

    @Override
    public void deserialize(ValueInput input) {
        sscCustomHealth = input.getDoubleOr("sscCustomHealth", 0);
        ssc14_dmg_blunt = input.getDoubleOr("ssc14_dmg_blunt", 0);
        ssc14_dmg_slash = input.getDoubleOr("ssc14_dmg_slash", 0);
        ssc14_dmg_piercing = input.getDoubleOr("ssc14_dmg_piercing", 0);
        ssc14_dmg_heat = input.getDoubleOr("ssc14_dmg_heat", 0);
        ssc14_dmg_shock = input.getDoubleOr("ssc14_dmg_shock", 0);
        ssc14_dmg_cold = input.getDoubleOr("ssc14_dmg_cold", 0);
        ssc14_dmg_caustic = input.getDoubleOr("ssc14_dmg_caustic", 0);
        ssc14_dmg_poison = input.getDoubleOr("ssc14_dmg_poison", 0);
        ssc14_dmg_radiation = input.getDoubleOr("ssc14_dmg_radiation", 0);
        ssc14_dmg_asphyx = input.getDoubleOr("ssc14_dmg_asphyx", 0);
        ssc14_dmg_bloodloss = input.getDoubleOr("ssc14_dmg_bloodloss", 0);
        ssc14_dmg_cellular = input.getDoubleOr("ssc14_dmg_cellular", 0);
    }

    public void markSyncDirty() {
        syncDirty = true;
    }

    public boolean isSyncDirty() {
        return syncDirty;
    }

    public void clearSyncDirty() {
        syncDirty = false;
    }
}
