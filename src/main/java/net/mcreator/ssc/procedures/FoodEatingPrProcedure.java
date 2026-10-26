package net.mcreator.ssc.procedures;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.content.item.food.FoodConsumptionService;
import net.ssc14.content.item.food.FoodDefinition;

/** @deprecated Canonical food items call {@link FoodConsumptionService} directly. */
@Deprecated(forRemoval = false)
public final class FoodEatingPrProcedure {
    private record LegacyFood(Supplier<Item> item, FoodDefinition definition) { }

    private static final List<LegacyFood> FOODS = List.of(
            food(Ssc14ModItems.ENERGY_BAR_2, 2,
                    "Nom. На вкус орехово, волокнисто, сладко и как овёс."),
            food(Ssc14ModItems.CATBURGER, 3, "Nom. На вкус как хлеб, мясо и... МЯУ."),
            food(Ssc14ModItems.CHEESEBURGER, 3, "Nom. На вкус как хлеб, мясо и сыр."),
            foodWithTrash(Ssc14ModItems.CHEESE_HONKERS, 1, "Nom. На вкус сырно.",
                    Ssc14ModItems.CHEESE_HONKERS_TRASH),
            foodWithTrash(Ssc14ModItems.CHIPS, 1, "Nom. На вкус хрустяще.",
                    Ssc14ModItems.CHIPS_TRASH),
            foodWithTrash(Ssc14ModItems.BORITOS, 1, "Nom. На вкус хрустяще.",
                    Ssc14ModItems.BORITOS_TRASH),
            foodWithTrash(Ssc14ModItems.POPCORN, 1, "Nom. На вкус воздушно.",
                    Ssc14ModItems.POPCORN_TRASH));

    private FoodEatingPrProcedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z,
            Entity entity, ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) {
            return;
        }
        for (LegacyFood food : FOODS) {
            if (food.item().get() == itemStack.getItem()) {
                FoodConsumptionService.consume(
                        world, x, y, z, entity, itemStack, food.definition());
                return;
            }
        }
    }

    private static LegacyFood food(Supplier<Item> item, int maxBites, String tasteMessage) {
        return new LegacyFood(item, FoodDefinition.withoutTrash(maxBites, tasteMessage));
    }

    private static LegacyFood foodWithTrash(Supplier<Item> item, int maxBites,
            String tasteMessage, Supplier<? extends Item> trash) {
        return new LegacyFood(item, new FoodDefinition(maxBites, tasteMessage, trash));
    }
}
