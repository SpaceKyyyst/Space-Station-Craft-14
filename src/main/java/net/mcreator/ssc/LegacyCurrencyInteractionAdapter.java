package net.mcreator.ssc;

import net.mcreator.ssc.procedures.Credits_RMCProcedure;
import net.mcreator.ssc.procedures.Credits_USEProcedure;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.ssc14.content.item.currency.CurrencyInteractionService;
import net.ssc14.content.item.currency.CurrencyKind;

/** Adapter retained until the station-budget runtime moves into canonical packages. */
final class LegacyCurrencyInteractionAdapter implements CurrencyInteractionService.Adapter {
    static final LegacyCurrencyInteractionAdapter INSTANCE =
            new LegacyCurrencyInteractionAdapter();

    private LegacyCurrencyInteractionAdapter() { }

    @Override
    public void use(CurrencyKind kind, Player player) {
        Credits_RMCProcedure.execute(player);
    }

    @Override
    public void useOn(CurrencyKind kind, UseOnContext context) {
        Credits_USEProcedure.execute(
                context.getLevel(), context.getClickedPos().getX(),
                context.getClickedPos().getY(), context.getClickedPos().getZ(),
                context.getPlayer(), context.getItemInHand());
    }
}
