package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical open upper airlock plug block entity. */
public class OpenAirlockUpperPlugBlockEntity extends AbstractAirlockUpperPlugBlockEntity {
    public OpenAirlockUpperPlugBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14AirlockBlockEntities.AIRLOCK_UP_PLUG_OPEN.get(),
                "airlock_up_plug_open", pos, state);
    }
}
