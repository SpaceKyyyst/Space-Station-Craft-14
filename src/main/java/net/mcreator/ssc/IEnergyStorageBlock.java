
package net.mcreator.ssc;

public interface IEnergyStorageBlock {
    long getStoredEnergy();
    void setStoredEnergy(long joules);
    default void setStoredEnergySimulation(long joules) {
        setStoredEnergy(joules);
    }
    long getMaxEnergy();
    
    // Новые методы для костыля мультитула
    long getNetworkCurrentPower();
    void setNetworkCurrentPower(long watt);
    
    long getNetworkTheoreticalSupply();
    void setNetworkTheoreticalSupply(long watt);
    
    long getNetworkIdealConsumption();
    void setNetworkIdealConsumption(long watt);

    default double getChargePercentage() {
        return getMaxEnergy() == 0 ? 0.0 : ((double) getStoredEnergy() / getMaxEnergy()) * 100.0;
    }
}
