package net.mcreator.ssc;

import net.ssc14.common.atmosphere.model.GasType;
import net.ssc14.integration.neoforge.atmosphere.AtmosCell;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.ssc14.common.atmosphere.reaction.StandardGasReactions;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AtmosPersistenceTest {
    @Test
    void regionSerializationKeepsCoordinateCellAndGasKeys() {
        CompoundTag region = savedRegion(-3, 1, 5, GasType.OXYGEN, 7, 300);
        assertEquals(Set.of("x", "y", "z", "cells"), region.keySet());
        CompoundTag cell = region.getList("cells").orElseThrow().getCompound(0).orElseThrow();
        assertEquals(Set.of("idx", "temp", "m_0"), cell.keySet());
    }

    @Test
    void cellRoundTripPreservesStableGasKeysAndClearsReusedTag() {
        AtmosCell cell = new AtmosCell();
        CompoundTag tag = new CompoundTag();
        cell.setMoles(0, 3);
        cell.setMoles(4, 0.005f);
        cell.setTemperature(500);
        cell.writeToNBT(tag);
        assertEquals(3f, tag.getFloat("m_0").orElseThrow());
        AtmosCell restored = new AtmosCell();
        restored.readFromNBT(tag);
        assertEquals(3f, restored.getMoles(0));
        assertEquals(0.005f, restored.getMoles(4));
        assertEquals(500, restored.getTemperature());
        cell.setMoles(0, 0);
        cell.writeToNBT(tag);
        assertFalse(tag.contains("m_0"));
    }

    @Test
    void invalidSavedNumbersBecomeSafeDefaultsAndMissingFieldsResetOldState() {
        AtmosCell cell = new AtmosCell();
        cell.setMoles(0, 100);
        cell.setTemperature(1000);
        CompoundTag tag = new CompoundTag();
        tag.putFloat("m_0", Float.NaN);
        tag.putFloat("m_1", -1);
        tag.putFloat("temp", Float.POSITIVE_INFINITY);
        cell.readFromNBT(tag);
        assertEquals(0, cell.getTotalMoles());
        assertEquals(294.15f, cell.getTemperature());
        cell.setTemperature(1000);
        cell.readFromNBT(new CompoundTag());
        assertEquals(294.15f, cell.getTemperature());
    }

    @Test
    void negativeCoordinatesRoundTripAndTinyAmountsAreSaved() {
        AtmosRegion region = new AtmosRegion(-1, -2, -3);
        BlockPos pos = new BlockPos(-1, -17, -33);
        assertEquals(pos, region.unpackCellIndex(region.packCellIndex(pos), region.getRegionOrigin()));
        region.getCellAt(pos).setMoles(0, 0.001f);
        CompoundTag saved = new CompoundTag();
        region.writeToNBT(saved);
        AtmosRegion restored = new AtmosRegion(-1, -2, -3);
        restored.readFromNBT(saved);
        assertEquals(0.001f, restored.getCellAt(pos).getMoles(0));
        assertTrue(restored.isActive());
        restored.readFromNBT(new CompoundTag());
        assertEquals(0, restored.getCellAt(pos).getTotalMoles());
        assertFalse(restored.isActive());
    }

    @Test
    void restoredReactionAndSubsequentTicksContinueWithoutManualDirtyMarking() {
        AtmosRegion region = new AtmosRegion(0, 0, 0);
        AtmosCell cell = region.getCellAt(BlockPos.ZERO);
        cell.setMoles(GasType.NITROUS_OXIDE.ordinal(), 1);
        cell.setTemperature(1000);
        CompoundTag saved = new CompoundTag();
        region.writeToNBT(saved);
        AtmosRegion restored = new AtmosRegion(0, 0, 0);
        restored.readFromNBT(saved);
        restored.tick(0.2f, StandardGasReactions.create());
        assertEquals(0.95f, restored.getCellAt(BlockPos.ZERO).getMoles(GasType.NITROUS_OXIDE.ordinal()));
        restored.tick(0.2f, StandardGasReactions.create());
        assertEquals(0.9f, restored.getCellAt(BlockPos.ZERO).getMoles(GasType.NITROUS_OXIDE.ordinal()), 0.00001);
    }

    @Test
    void invalidCellIndicesAreSkippedWithoutLosingValidCells() {
        CompoundTag saved = new CompoundTag();
        ListTag cells = new ListTag();
        for (int index : new int[]{-1, 4096, 0}) {
            CompoundTag cell = new CompoundTag();
            cell.putInt("idx", index);
            cell.putFloat("m_0", 1);
            cells.add(cell);
        }
        saved.put("cells", cells);
        AtmosRegion region = new AtmosRegion(0, 0, 0);
        region.readFromNBT(saved);
        assertEquals(1, region.getCellAt(BlockPos.ZERO).getMoles(0));
    }

    @Test
    void regionBudgetDefersWorkRatherThanDiscardingHigherIndices() {
        AtmosRegion region = new AtmosRegion(0, 0, 0);
        for (int index = 0; index < 4096; index++) {
            AtmosCell cell = region.getCellAt(region.unpackCellIndex(index, BlockPos.ZERO));
            cell.setMoles(GasType.NITROUS_OXIDE.ordinal(), 1);
            cell.setTemperature(1000);
        }
        region.tick(0.2f, StandardGasReactions.create());
        assertEquals(1f, region.getCellAt(new BlockPos(15, 15, 15)).getMoles(GasType.NITROUS_OXIDE.ordinal()));
        for (int step = 1; step < 16; step++) region.tick(0.2f, StandardGasReactions.create());
        assertEquals(0.95f, region.getCellAt(new BlockPos(15, 15, 15)).getMoles(GasType.NITROUS_OXIDE.ordinal()));
    }

    @Test
    void savedDataRoundTripPreservesUnvisitedRegionAndModifiedLiveRegion() {
        CompoundTag dormant = savedRegion(-3, 1, 5, GasType.NITROGEN, 7, 300);
        dormant.putString("legacyExtra", "preserved");
        AtmosphereSavedData data = savedData(savedRegion(0, 0, 0, GasType.OXYGEN, 3, 400), dormant);
        long liveKey = AtmosphereManager.packRegionKey(0, 0, 0);
        long dormantKey = AtmosphereManager.packRegionKey(-3, 1, 5);
        AtmosRegion live = new AtmosRegion(0, 0, 0);
        live.readFromNBT(data.restore(liveKey));
        data.track(liveKey, live);
        live.getCellAt(BlockPos.ZERO).setMoles(GasType.OXYGEN.ordinal(), 8);
        live.getCellAt(BlockPos.ZERO).setTemperature(500);

        CompoundTag encoded = data.encode();
        assertEquals(Set.of("regions"), encoded.keySet());
        assertEquals(2, encoded.getList("regions").orElseThrow().size());
        AtmosphereSavedData restored = AtmosphereSavedData.decode(encoded);
        assertEquals(dormant, restored.restore(dormantKey), "An unvisited region must survive unchanged");
        AtmosRegion restoredLive = new AtmosRegion(0, 0, 0);
        restoredLive.readFromNBT(restored.restore(liveKey));
        assertEquals(8, restoredLive.getCellAt(BlockPos.ZERO).getMoles(GasType.OXYGEN.ordinal()));
        assertEquals(500, restoredLive.getCellAt(BlockPos.ZERO).getTemperature());
        assertTrue(restoredLive.isActive(), "Restored live gas must resume simulation");
        assertNull(restored.restore(liveKey), "A restored region must not retain a stale stored copy");
    }

    @Test
    void emptiedLiveRegionDoesNotResurrectItsOldGasAfterSaving() {
        long key = AtmosphereManager.packRegionKey(0, 0, 0);
        AtmosphereSavedData data = savedData(savedRegion(0, 0, 0, GasType.OXYGEN, 3, 294.15f));
        AtmosRegion live = new AtmosRegion(0, 0, 0);
        live.readFromNBT(data.restore(key));
        data.track(key, live);
        live.getCellAt(BlockPos.ZERO).setMoles(GasType.OXYGEN.ordinal(), 0);

        AtmosphereSavedData restored = AtmosphereSavedData.decode(data.encode());
        assertNull(restored.restore(key));
        assertFalse(restored.savedRegionKeys().iterator().hasNext());
    }

    private static CompoundTag savedRegion(int x, int y, int z, GasType gas, float amount, float temperature) {
        AtmosRegion region = new AtmosRegion(x, y, z);
        AtmosCell cell = region.getCellAt(region.getRegionOrigin());
        cell.setMoles(gas.ordinal(), amount);
        cell.setTemperature(temperature);
        CompoundTag tag = new CompoundTag();
        region.writeToNBT(tag);
        return tag;
    }

    private static AtmosphereSavedData savedData(CompoundTag... regions) {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        for (CompoundTag region : regions) list.add(region);
        tag.put("regions", list);
        return AtmosphereSavedData.decode(tag);
    }
}
