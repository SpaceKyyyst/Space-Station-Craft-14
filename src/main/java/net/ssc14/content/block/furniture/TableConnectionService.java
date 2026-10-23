package net.ssc14.content.block.furniture;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Recalculates the connection state of tables around a changed position. */
public final class TableConnectionService {
    private static final String LEGACY_DATA_NAMESPACE = "ssc14";
    private static final TagKey<Block> TABLES = BlockTags.create(
            Identifier.fromNamespaceAndPath(LEGACY_DATA_NAMESPACE, "tables"));
    private static final int[][] REFRESH_OFFSETS = {{0, 0}, {0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private static final int[] STATE_BY_MASK = {0, 1, 2, 5, 3, 9, 6, 12, 4, 8, 10, 13, 7, 11, 14, 15};
    private static final int UPDATE_CLIENTS = 2;

    private TableConnectionService() {
    }

    public static void refresh(LevelAccessor level, double x, double y, double z) {
        refresh(level, BlockPos.containing(x, y, z));
    }

    public static void refresh(LevelAccessor level, BlockPos center) {
        for (int[] offset : REFRESH_OFFSETS) {
            update(level, center.offset(offset[0], 0, offset[1]));
        }
    }

    private static void update(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(TABLES)) {
            return;
        }
        int value = connectionState(
                isTable(level, pos.north()), isTable(level, pos.east()),
                isTable(level, pos.south()), isTable(level, pos.west()));
        IntegerProperty property = (IntegerProperty) state.getBlock()
                .getStateDefinition().getProperty(ConnectedTableBlock.BLOCKSTATE.getName());
        if (property != null && property.getPossibleValues().contains(value)) {
            level.setBlock(pos, state.setValue(property, value), UPDATE_CLIENTS);
        }
    }

    private static boolean isTable(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos).is(TABLES);
    }

    public static int connectionState(boolean north, boolean east, boolean south, boolean west) {
        int mask = (north ? 1 : 0) | (east ? 2 : 0) | (south ? 4 : 0) | (west ? 8 : 0);
        return STATE_BY_MASK[mask];
    }
}
