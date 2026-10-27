
package net.ssc14.content.energy;

import net.minecraft.core.BlockPos;
import java.util.HashSet;
import java.util.Set;

public class EnergyNetwork {
    private final CableType type;
    private final java.util.function.BooleanSupplier generationReady;
    private boolean topologyValid = true;
    private boolean shutdownProcessed;
    private final Set<BlockPos> cables = new HashSet<>();
    private final java.util.Map<Long, java.util.List<BlockPos>> cablesBySection = new java.util.HashMap<>();
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
        this(type, () -> true);
    }

    public EnergyNetwork(CableType type, java.util.function.BooleanSupplier generationReady) {
        this.type = type;
        this.generationReady = generationReady;
    }

    public boolean isTopologyReady() { return topologyValid && generationReady.getAsBoolean(); }
    public void invalidateTopology() { topologyValid = false; resetTickStats(); }

    public void acknowledgeShutdown() { shutdownProcessed = true; }
    public boolean isShutdownProcessed() { return shutdownProcessed; }

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
    public void addCable(BlockPos pos) {
        var immutable = pos.immutable();
        if (cables.add(immutable)) {
            long section = net.minecraft.core.SectionPos.asLong(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
            cablesBySection.computeIfAbsent(section, ignored -> new java.util.ArrayList<>()).add(immutable);
        }
    }

    /** Exact radius check over nearby sections, independent of distant cable count. */
    public boolean hasCableWithin(BlockPos position, int radius) {
        for (int x = (position.getX() - radius) >> 4; x <= (position.getX() + radius) >> 4; x++) {
            for (int y = (position.getY() - radius) >> 4; y <= (position.getY() + radius) >> 4; y++) {
                for (int z = (position.getZ() - radius) >> 4; z <= (position.getZ() + radius) >> 4; z++) {
                    var nearby = cablesBySection.get(net.minecraft.core.SectionPos.asLong(x, y, z));
                    if (nearby == null) continue;
                    for (var cable : nearby) if (position.closerThan(cable, radius)) return true;
                }
            }
        }
        return false;
    }
    public int getCableCount() { return this.cables.size(); }
}
