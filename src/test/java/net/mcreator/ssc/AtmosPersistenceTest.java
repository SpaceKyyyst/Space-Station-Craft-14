package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.mcreator.ssc.atmosphere.StandardGasReactions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AtmosPersistenceTest {
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
        region.tick(0.2f, StandardGasReactions.create());
        assertEquals(0.95f, region.getCellAt(new BlockPos(15, 15, 15)).getMoles(GasType.NITROUS_OXIDE.ordinal()));
    }
}
