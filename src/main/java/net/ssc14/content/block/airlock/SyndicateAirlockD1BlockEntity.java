package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SyndicateAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public SyndicateAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.SYNDICATE_AIRLOCK_D_1.get(), pos, state,
                "syndicate_airlock_d_1", "Syndicate Airlock");
    }
}
