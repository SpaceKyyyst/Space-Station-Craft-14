package net.ssc14.content.energy;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.ssc.block.LampBlock;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

/** One world read per distinct lamp per tick; shared lamps are charged only once. */
public final class LampTickBatch {
    private static final class Lamp {
        final BlockState state;
        boolean powered;
        Lamp(BlockState state) { this.state = state; }
        boolean eligible() {
            return state != null && state.getValue(LampBlock.HAVE_LAMP) && !state.getValue(LampBlock.BROKEN);
        }
    }
    private final ServerLevel level;
    private final Map<BlockPos, Lamp> lamps = new HashMap<>();

    public LampTickBatch(ServerLevel level) { this.level = level; }

    private Lamp lamp(BlockPos pos) {
        return lamps.computeIfAbsent(pos, key -> {
            var state = level.isLoaded(key) ? level.getBlockState(key) : null;
            return new Lamp(state != null && state.is(Ssc14ModBlocks.LAMP.get()) ? state : null);
        });
    }

    public boolean track(BlockPos pos) { return lamp(pos).state != null; }
    public boolean needsPower(BlockPos pos) {
        var lamp = lamp(pos);
        return lamp.eligible() && !lamp.powered;
    }
    public void power(BlockPos pos) {
        var lamp = lamp(pos);
        if (lamp.eligible()) lamp.powered = true;
    }
    public void apply() {
        for (var entry : lamps.entrySet()) {
            var lamp = entry.getValue();
            if (lamp.state == null || lamp.state.getValue(LampBlock.ACTIVE) == lamp.powered) continue;
            // Earlier neighbor notifications may have changed another lamp in this batch.
            if (!level.isLoaded(entry.getKey())) continue;
            var current = level.getBlockState(entry.getKey());
            if (!current.is(Ssc14ModBlocks.LAMP.get())) continue;
            boolean powered = lamp.powered && current.getValue(LampBlock.HAVE_LAMP) && !current.getValue(LampBlock.BROKEN);
            if (current.getValue(LampBlock.ACTIVE) != powered) {
                level.setBlock(entry.getKey(), current.setValue(LampBlock.ACTIVE, powered), 3);
            }
        }
    }
}
