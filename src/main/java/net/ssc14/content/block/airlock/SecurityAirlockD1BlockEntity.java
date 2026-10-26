package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SecurityAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public SecurityAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.SECURITY_AIRLOCK_D_1.get(), pos, state,
                "security_airlock_d_1", "Security Airlock");
    }
}
