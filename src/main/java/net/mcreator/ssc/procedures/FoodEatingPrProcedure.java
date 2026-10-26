package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.food.EdibleFoodItem;
import net.ssc14.content.item.food.FoodConsumptionService;

/** @deprecated Canonical food items call {@link FoodConsumptionService} directly. */
@Deprecated(forRemoval = false)
public final class FoodEatingPrProcedure {
    private FoodEatingPrProcedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z,
            Entity entity, ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) {
            return;
        }
        if (itemStack.getItem() instanceof EdibleFoodItem food) {
            FoodConsumptionService.consume(
                    world, x, y, z, entity, itemStack, food.definition());
        }
    }
}
