package net.mcreator.ssc;

import net.mcreator.ssc.procedures.PlaSteelUseProcedure;
import net.mcreator.ssc.procedures.Sheathing_Cab_InstalProcedure;
import net.mcreator.ssc.procedures.SteeluseProcedure;
import net.minecraft.world.item.context.UseOnContext;
import net.ssc14.content.item.construction.ConstructionItemInteractionService;
import net.ssc14.content.item.construction.ConstructionItemKind;

/** Adapter retained until construction procedures move into the canonical namespace. */
public final class LegacyConstructionItemInteractionAdapter
        implements ConstructionItemInteractionService.Adapter {
    public static final LegacyConstructionItemInteractionAdapter INSTANCE =
            new LegacyConstructionItemInteractionAdapter();

    private LegacyConstructionItemInteractionAdapter() { }

    @Override
    public void use(ConstructionItemKind kind, UseOnContext context) {
        switch (kind) {
            case STEEL_SHEET -> SteeluseProcedure.execute(
                    context.getLevel(), context.getClickedPos().getX(),
                    context.getClickedPos().getY(), context.getClickedPos().getZ(),
                    context.getPlayer());
            case PLASTEEL_SHEET -> PlaSteelUseProcedure.execute(
                    context.getLevel(), context.getClickedPos().getX(),
                    context.getClickedPos().getY(), context.getClickedPos().getZ(),
                    context.getPlayer());
            case LOW_VOLTAGE_CABLE, MEDIUM_VOLTAGE_CABLE, HIGH_VOLTAGE_CABLE ->
                    Sheathing_Cab_InstalProcedure.execute(
                            context.getLevel(), context.getClickedPos().getX(),
                            context.getClickedPos().getY(), context.getClickedPos().getZ(),
                            context.getLevel().getBlockState(context.getClickedPos()),
                            context.getPlayer());
        }
    }
}
