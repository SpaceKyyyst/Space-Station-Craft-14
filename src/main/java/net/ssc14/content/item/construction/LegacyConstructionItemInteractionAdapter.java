package net.ssc14.content.item.construction;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.construction.ConstructionItemInteractionService;
import net.ssc14.content.item.construction.ConstructionItemKind;
import net.ssc14.content.item.construction.ConstructionAssemblyService;
import net.ssc14.content.block.energy.SheathingCableService;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.item.Ssc14Items;

/** Adapter retained until construction procedures move into the canonical namespace. */
public final class LegacyConstructionItemInteractionAdapter
        implements ConstructionItemInteractionService.Adapter {
    public static final LegacyConstructionItemInteractionAdapter INSTANCE =
            new LegacyConstructionItemInteractionAdapter();

    private LegacyConstructionItemInteractionAdapter() { }

    @Override
    public void use(ConstructionItemKind kind, UseOnContext context) {
        switch (kind) {
            case STEEL_SHEET -> useSteel(context);
            case PLASTEEL_SHEET -> usePlasteel(context);
            case LOW_VOLTAGE_CABLE, MEDIUM_VOLTAGE_CABLE, HIGH_VOLTAGE_CABLE ->
                    SheathingCableService.install(context.getLevel(), context.getClickedPos(), context.getPlayer(),
                            Ssc14Items.LOW_VOLTAGE_CABLE.get(), Ssc14Items.MEDIUM_VOLTAGE_CABLE.get(),
                            Ssc14Items.HIGH_VOLTAGE_CABLE.get());
        }
    }

    private static void useSteel(UseOnContext context) {
        if (context.getPlayer() == null) return;
        ItemStack held = context.getItemInHand();
        if (context.getLevel().getBlockState(context.getClickedPos()).is(Ssc14Blocks.WALL_CARCASE.get())) {
            ConstructionAssemblyService.assembleWall(context.getLevel(), context.getClickedPos(),
                    context.getPlayer(), held, Ssc14Items.STEEL.get(),
                    Ssc14Blocks.WALL_CARCASE.get().defaultBlockState(),
                    Ssc14Blocks.STEEL_WALL.get().defaultBlockState(),
                    Ssc14Attributes.PROGRESS_BAR_ATRB);
        } else {
            ConstructionAssemblyService.replaceBlock(context.getLevel(), context.getClickedPos(),
                    context.getPlayer(), held, Ssc14Items.STEEL.get(),
                    Ssc14Blocks.ROD_FLOOR.get(), Ssc14Blocks.SHEATHING.get());
        }
    }

    private static void usePlasteel(UseOnContext context) {
        if (context.getPlayer() == null) return;
        ItemStack held = context.getItemInHand();
        if (context.getLevel().getBlockState(context.getClickedPos()).is(Ssc14Blocks.WALL_CARCASE.get())) {
            ConstructionAssemblyService.assembleWall(context.getLevel(), context.getClickedPos(),
                    context.getPlayer(), held, Ssc14Items.PLASTEEL.get(),
                    Ssc14Blocks.WALL_CARCASE.get().defaultBlockState(),
                    Ssc14Blocks.PLASTEEL_WALL_CARCASE.get().defaultBlockState(),
                    Ssc14Attributes.PROGRESS_BAR_ATRB);
        } else if (context.getLevel().getBlockState(context.getClickedPos())
                .is(Ssc14Blocks.PLASTEEL_WALL_CARCASE.get())) {
            ConstructionAssemblyService.assembleWall(context.getLevel(), context.getClickedPos(),
                    context.getPlayer(), held, Ssc14Items.PLASTEEL.get(),
                    Ssc14Blocks.PLASTEEL_WALL_CARCASE.get().defaultBlockState(),
                    Ssc14Blocks.PLASTEEL_WALL.get().defaultBlockState(),
                    Ssc14Attributes.PROGRESS_BAR_ATRB);
        }
    }
}
