package net.ssc14.content.block.floor;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Randomized floor tile with four visual states. */
public final class FourVariantFloorTileBlock extends RandomizedFloorTileBlock {
    public static final IntegerProperty STATE =
            IntegerProperty.create("state", MINIMUM_STATE, 4);

    public FourVariantFloorTileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty stateProperty() {
        return STATE;
    }
}
