package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CentComAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public CentComAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.CENT_COM_AIRLOCK_D_1.get(), pos, state,
                "cent_com_airlock_d_1", "CentComm Airlock");
    }
}
