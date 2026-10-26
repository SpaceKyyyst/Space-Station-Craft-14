package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TechAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public TechAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.TECH_AIRLOCK_D_1.get(), pos, state,
                "tech_airlock_d_1", "Tech Airlock");
    }
}
