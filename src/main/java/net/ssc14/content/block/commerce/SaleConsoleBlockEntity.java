package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical zero-slot sale console container. */
public class SaleConsoleBlockEntity extends AbstractCommerceConsoleBlockEntity {
    public static final int SLOT_COUNT = 0;
    public static final int MAX_STACK_SIZE = 1;

    public SaleConsoleBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14CommerceBlockEntities.SALE_CONSOLE.get(), pos, state,
                CommerceConsoleIntegrationService.ConsoleKind.SALE,
                "sale_console", "Sale console", SLOT_COUNT, MAX_STACK_SIZE);
    }
}
