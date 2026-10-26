package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CommandAirlockD1BlockEntity extends NamedAirlockBlockEntity {
    public CommandAirlockD1BlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.COMMAND_AIRLOCK_D_1.get(), pos, state,
                "command_airlock_d_1", "Command Airlock");
    }
}
