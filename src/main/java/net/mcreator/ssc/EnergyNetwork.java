
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import java.util.HashSet;
import java.util.Set;

public class EnergyNetwork {
    private final CableType type;
    private final Set<BlockPos> cables = new HashSet<>();
    private final Set<BlockPos> sources = new HashSet<>();
    private final Set<BlockPos> consumers = new HashSet<>();

    // --- СТАТИСТИКА ДЛЯ МУЛЬТИТУЛА ---
    public long currentPower = 0;       // Текущее питание (Ватт)
    public long batteryPower = 0;       // От батарей (Ватт)
    public long theoreticalSupply = 0;  // Теоретическое снабжение (Ватт)
    public long idealConsumption = 0;   // Идеальное потребление (Ватт)
    public long inputStored = 0;        // Входной запас (Джоули источников)
    public long inputMax = 0;           // Макс. входной запас
    public long outputStored = 0;       // Выходной запас (Джоули потребителей)
    public long outputMax = 0;          // Макс. выходной запас

    public EnergyNetwork(CableType type) {
        this.type = type;
    }

    public void resetTickStats() {
        this.currentPower = 0;
        this.batteryPower = 0;
        this.theoreticalSupply = 0;
        this.idealConsumption = 0;
        this.inputStored = 0;
        this.inputMax = 0;
        this.outputStored = 0;
        this.outputMax = 0;
    }

    public CableType getType() { return type; }
    public Set<BlockPos> getCables() { return cables; }
    public Set<BlockPos> getSources() { return sources; }
    public Set<BlockPos> getConsumers() { return consumers; }
    public void addCable(BlockPos pos) { this.cables.add(pos.immutable()); }
    public int getCableCount() { return this.cables.size(); }
}
