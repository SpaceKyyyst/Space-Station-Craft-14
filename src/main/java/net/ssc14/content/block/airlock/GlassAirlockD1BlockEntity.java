package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GlassAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public GlassAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.GLASS_AIRLOCK_D_1.get(), pos, state,
                "glass_airlock_d_1", "Glass Airlock");
    }
}
