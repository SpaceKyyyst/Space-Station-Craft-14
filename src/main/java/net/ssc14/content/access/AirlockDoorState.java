package net.ssc14.content.access;

import net.minecraft.world.level.block.state.BlockState;

/** Shared structural predicate for SSC14 airlock door blocks. */
public final class AirlockDoorState {
    private static final String VARIANT_PROPERTY = "arl_variat";

    private AirlockDoorState() {
    }

    public static boolean isDoor(BlockState state) {
        return state.getBlock().getStateDefinition().getProperty(VARIANT_PROPERTY) != null;
    }
}
