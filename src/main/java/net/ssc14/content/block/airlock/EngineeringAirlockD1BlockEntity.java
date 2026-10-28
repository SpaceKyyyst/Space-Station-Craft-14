package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EngineeringAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public EngineeringAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.ENGINEERING_AIRLOCK_D_1.get(), pos, state,
                "engineering_airlock_d_1", "Engineering Airlock");
    }
}
