package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CargoAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public CargoAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.CARGO_AIRLOCK_D_1.get(), pos, state,
                "cargo_airlock_d_1", "Cargo Airlock");
    }
}
