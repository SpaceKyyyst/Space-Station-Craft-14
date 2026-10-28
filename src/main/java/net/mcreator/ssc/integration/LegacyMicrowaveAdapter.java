package net.mcreator.ssc.integration;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.ssc14.content.menu.food.MicrowaveControlService;
import net.ssc14.content.menu.food.MicrowaveRecipeService;
import net.mcreator.ssc.init.Ssc14ModItems;

/** Transitional boundary for the generated microwave runtime. */
public final class LegacyMicrowaveAdapter {
    private LegacyMicrowaveAdapter() {
    }

    public static void start(LevelAccessor level, double x, double y, double z, Entity entity) {
        if (!(level instanceof Level world) || world.isClientSide()) return;
        BlockPos pos = BlockPos.containing(x, y, z);
        var resourceHandler = world.getCapability(Capabilities.Item.BLOCK, pos, null);
        IItemHandler handler = resourceHandler == null ? null : IItemHandler.of(resourceHandler);
        if (handler == null) return;
        if (!(handler instanceof IItemHandlerModifiable inventory)) return;
        MicrowaveRecipeService.RecipeCatalog catalog = new MicrowaveRecipeService.RecipeCatalog(
                Ssc14ModItems.POTATO.get(), Ssc14ModItems.SPACE_FRIES.get(), Ssc14ModItems.BUN.get(),
                Ssc14ModItems.CHEESE.get(), Ssc14ModItems.MEAT.get(), Ssc14ModItems.CHEESEBURGER.get(),
                Ssc14ModItems.TOFU_SLICE.get(), Ssc14ModItems.TOFU_BURGER.get(), Ssc14ModItems.DOUGH.get(),
                Ssc14ModItems.TOFU_BREAD.get(), Ssc14ModItems.MEATLOAF.get(), Ssc14ModItems.BREAD_SLICE.get(),
                Ssc14ModItems.FLY_AMANITA.get(), Ssc14ModItems.MOLDY_BREAD_PIECE.get(), Ssc14ModItems.BREAD.get(),
                Ssc14ModItems.DOUGH_PIECE.get());
        MicrowaveRecipeService.cook(world, pos, inventory, MicrowaveControlService.timerSeconds(world, pos),
                catalog, stack -> stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem);
    }
}
