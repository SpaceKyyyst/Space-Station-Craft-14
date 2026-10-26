package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MedicalAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public MedicalAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.MEDICAL_AIRLOCK_D_1.get(), pos, state,
                "medical_airlock_d_1", "Medical Airlock");
    }
}
