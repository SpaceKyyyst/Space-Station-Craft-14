package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class HydroponicsAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public HydroponicsAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.HYDROPONICS_AIRLOCK_D_1.get(), pos, state,
                "hydroponics_airlock_d_1", "Hydroponics Airlock");
    }
}
