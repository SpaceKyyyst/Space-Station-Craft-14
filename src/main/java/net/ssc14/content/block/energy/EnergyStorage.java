package net.ssc14.content.block.energy;

/** Mutable energy storage and telemetry contract used by the server simulation. */
public interface EnergyStorage {
    long getStoredEnergy();
    void setStoredEnergy(long joules);
    default void setStoredEnergySimulation(long joules) { setStoredEnergy(joules); }
    long getMaxEnergy();
    long getNetworkCurrentPower();
    void setNetworkCurrentPower(long watt);
    long getNetworkTheoreticalSupply();
    void setNetworkTheoreticalSupply(long watt);
    long getNetworkIdealConsumption();
    void setNetworkIdealConsumption(long watt);

    default double getChargePercentage() {
        return getMaxEnergy() == 0 ? 0.0
                : ((double) getStoredEnergy() / getMaxEnergy()) * 100.0;
    }
}
