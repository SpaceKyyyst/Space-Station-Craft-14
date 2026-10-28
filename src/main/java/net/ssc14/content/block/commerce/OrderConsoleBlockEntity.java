package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical 21-slot order console container. */
public class OrderConsoleBlockEntity extends AbstractCommerceConsoleBlockEntity {
    public static final int SLOT_COUNT = 21;
    public static final int MAX_STACK_SIZE = 64;

    public OrderConsoleBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14CommerceBlockEntities.ORDER_CONSOLE.get(), pos, state,
                CommerceConsoleIntegrationService.ConsoleKind.ORDER,
                "order_console", "Order console", SLOT_COUNT, MAX_STACK_SIZE);
    }
}
