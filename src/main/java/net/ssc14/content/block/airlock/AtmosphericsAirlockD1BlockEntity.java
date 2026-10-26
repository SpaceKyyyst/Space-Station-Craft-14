package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AtmosphericsAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public AtmosphericsAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.ATMOSPHERICS_AIRLOCK_D_1.get(), pos, state,
                "atmospherics_airlock_d_1", "Atmospherics Airlock");
    }
}
