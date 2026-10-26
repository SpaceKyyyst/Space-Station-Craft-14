package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BaseGlassAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public BaseGlassAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.BASE_GLASS_AIRLOCK_D_1.get(), pos, state,
                "base_glass_airlock_d_1", "Base Glass Airlock");
    }
}
