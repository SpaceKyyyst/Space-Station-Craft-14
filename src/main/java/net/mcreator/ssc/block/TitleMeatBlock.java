package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.ssc14.content.block.floor.RandomizedFloorTileBlock;

/** @deprecated Use {@link RandomizedFloorTileBlock} through the canonical registry. */
@Deprecated
public class TitleMeatBlock extends RandomizedFloorTileBlock {
    public static final IntegerProperty STATE = IntegerProperty.create("state", 1, 4);

    public TitleMeatBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty stateProperty() {
        return STATE;
    }
}
