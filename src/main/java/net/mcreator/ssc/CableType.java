
package net.mcreator.ssc;

import net.mcreator.ssc.block.SheathingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public enum CableType {
    LV(SheathingBlock.LV),
    MV(SheathingBlock.MV),
    HV(SheathingBlock.HV);

    private final BooleanProperty property;

    CableType(BooleanProperty property) {
        this.property = property;
    }

    public BooleanProperty getProperty() {
        return this.property;
    }

    public boolean hasCable(BlockState state) {
        return state.hasProperty(this.property) && state.getValue(this.property);
    }
}
