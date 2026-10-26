package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical closed upper airlock plug block entity. */
public class ClosedAirlockUpperPlugBlockEntity extends AbstractAirlockUpperPlugBlockEntity {
    public ClosedAirlockUpperPlugBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG.get(),
                "airlock_up_plug", pos, state);
    }
}
