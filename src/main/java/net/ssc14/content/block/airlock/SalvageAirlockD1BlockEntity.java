package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SalvageAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public SalvageAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.SALVAGE_AIRLOCK_D_1.get(), pos, state,
                "salvage_airlock_d_1", "Salvage Airlock");
    }
}
