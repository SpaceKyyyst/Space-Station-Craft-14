package net.ssc14.content.block.floor;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Randomized floor tile with five visual states. */
public final class FiveVariantFloorTileBlock extends RandomizedFloorTileBlock {
    public static final IntegerProperty STATE =
            IntegerProperty.create("state", MINIMUM_STATE, 5);

    public FiveVariantFloorTileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty stateProperty() {
        return STATE;
    }
}
