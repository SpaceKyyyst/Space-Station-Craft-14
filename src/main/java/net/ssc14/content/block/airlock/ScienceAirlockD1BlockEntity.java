package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ScienceAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public ScienceAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.SCIENCE_AIRLOCK_D_1.get(), pos, state,
                "science_airlock_d_1", "Science Airlock");
    }
}
