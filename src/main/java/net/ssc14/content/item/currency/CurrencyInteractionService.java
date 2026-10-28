package net.ssc14.content.item.currency;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.ssc14.common.station.StationBudgetService;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Canonical currency consolidation and station-budget interactions. */
public final class CurrencyInteractionService {
    private static final int DEPOSIT_DELAY_TICKS = 1;

    private CurrencyInteractionService() { }

    public static void use(CurrencyKind kind, Player player) {
        if (player == null || !kind.consolidatesOnUse()) return;
        ItemStack held = player.getMainHandItem();
        if (held.is(Ssc14Items.CREDITS.get()) && held.getCount() == kind.maxStackSize()) {
            held.shrink(held.getCount());
            player.getInventory().placeItemBackInInventory(
                    new ItemStack(Ssc14Items.CREDITS_50.get()));
        }
    }

    public static void useOn(CurrencyKind kind, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || !(context.getLevel() instanceof ServerLevel level)) return;
        boolean isConsole = context.getLevel().getBlockState(context.getClickedPos())
                .is(Ssc14Blocks.ORDER_CONSOLE.get())
                || context.getLevel().getBlockState(context.getClickedPos())
                        .is(Ssc14Blocks.SALE_CONSOLE.get());
        if (!isConsole) {
            use(kind, player);
            return;
        }
        ItemStack interactedStack = context.getItemInHand();
        ServerWorkScheduler.schedule(level, DEPOSIT_DELAY_TICKS,
                () -> deposit(kind, level, player, interactedStack));
    }

    private static void deposit(CurrencyKind kind, ServerLevel level, Player player,
            ItemStack interactedStack) {
        if (kind == CurrencyKind.DEBUG_DELETER) {
            StationBudgetService.reset(level);
            player.sendSystemMessage(Component.literal(
                    "[DEBUG]  БЮДЖЕТ  СТАНЦИИ  ОНУЛИРОВАН"));
            return;
        }
        int count = interactedStack.getCount();
        if (count <= 0 || kind.unitValue() <= 0) return;
        StationBudgetService.credit(level, (double) count * kind.unitValue());
        interactedStack.setCount(0);
    }
}
