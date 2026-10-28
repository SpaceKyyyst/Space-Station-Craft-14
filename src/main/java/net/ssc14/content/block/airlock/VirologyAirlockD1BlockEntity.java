package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class VirologyAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public VirologyAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.VIROLOGY_AIRLOCK_D_1.get(), pos, state,
                "virology_airlock_d_1", "Virology Airlock");
    }
}
