package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ChemistryAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public ChemistryAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.CHEMISTRY_AIRLOCK_D_1.get(), pos, state,
                "chemistry_airlock_d_1", "Chemistry Airlock");
    }
}
