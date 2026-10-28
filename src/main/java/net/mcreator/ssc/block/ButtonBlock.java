package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.control.NetworkButtonBlock;

/** @deprecated canonical implementation lives in {@code net.ssc14}. */
@Deprecated
public class ButtonBlock extends NetworkButtonBlock implements net.ssc14.common.network.NetworkTrigger {
    public ButtonBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
