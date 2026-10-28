package net.ssc14.content.menu.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.ssc14.content.item.reagent.ReagentContainerAccess;

/** Pure recipe execution for the server-side microwave inventory. */
public final class MicrowaveRecipeService {
    public record RecipeCatalog(Item potato, Item spaceFries, Item bun, Item cheese, Item meat,
            Item cheeseburger, Item tofuSlice, Item tofuBurger, Item dough, Item tofuBread,
            Item meatloaf, Item breadSlice, Item flyAmanita, Item moldyBread, Item bread,
            Item doughPiece) { }

    private MicrowaveRecipeService() { }

    public static boolean cook(Level level, BlockPos pos, IItemHandlerModifiable inventory,
            int timer, RecipeCatalog c, Predicate<ItemStack> reagentContainer) {
        List<ItemStack> input = List.of(inventory.getStackInSlot(0), inventory.getStackInSlot(1),
                inventory.getStackInSlot(2), inventory.getStackInSlot(3));
        Map<Item, Integer> output = new HashMap<>();
        if (timer == 15) {
            int potatoes = count(input, c.potato());
            int salt = input.stream().filter(reagentContainer)
                    .mapToInt(stack -> ReagentContainerAccess.contents(stack)
                            .getOrDefault("table_salt", 0)).sum();
            int crafts = Math.min(potatoes, salt / 5);
            if (crafts > 0) {
                remove(input, c.potato(), crafts);
                removeSalt(input, reagentContainer, crafts * 5);
                output.merge(c.spaceFries(), crafts, Integer::sum);
            }
        }
        if (timer == 10) {
            int crafts = Math.min(count(input, c.bun()), Math.min(count(input, c.cheese()), count(input, c.meat())));
            if (crafts > 0) { remove(input, c.bun(), crafts); remove(input, c.cheese(), crafts); remove(input, c.meat(), crafts); output.merge(c.cheeseburger(), crafts, Integer::sum); }
            crafts = Math.min(count(input, c.bun()), count(input, c.tofuSlice()));
            if (crafts > 0) { remove(input, c.bun(), crafts); remove(input, c.tofuSlice(), crafts); output.merge(c.tofuBurger(), crafts, Integer::sum); }
            crafts = count(input, c.dough());
            if (count(input, c.tofuSlice()) > 0) { crafts = Math.min(crafts, count(input, c.tofuSlice())); remove(input, c.dough(), crafts); remove(input, c.tofuSlice(), crafts); output.merge(c.tofuBread(), crafts, Integer::sum); }
            if ((crafts = Math.min(count(input, c.dough()), count(input, c.meat()))) > 0) { remove(input, c.dough(), crafts); remove(input, c.meat(), crafts); output.merge(c.meatloaf(), crafts, Integer::sum); }
            int dough = count(input, c.dough());
            if (dough > 0) { remove(input, c.dough(), dough); output.merge(c.bread(), dough, Integer::sum); }
        }
        if (timer == 5) {
            int crafts = count(input, c.doughPiece());
            if (crafts > 0) { remove(input, c.doughPiece(), crafts); output.merge(c.bun(), crafts, Integer::sum); }
        }
        if (timer == 0) {
            int crafts = Math.min(count(input, c.breadSlice()), count(input, c.flyAmanita()));
            if (crafts > 0) { remove(input, c.breadSlice(), crafts); remove(input, c.flyAmanita(), crafts); output.merge(c.moldyBread(), crafts, Integer::sum); }
        }
        for (Map.Entry<Item, Integer> entry : output.entrySet()) addOutput(inventory, new ItemStack(entry.getKey(), entry.getValue()));
        return !output.isEmpty();
    }

    private static int count(List<ItemStack> stacks, Item item) { return stacks.stream().filter(s -> s.getItem() == item).mapToInt(ItemStack::getCount).sum(); }
    private static void remove(List<ItemStack> stacks, Item item, int amount) { int left = amount; for (ItemStack stack : stacks) if (stack.getItem() == item && left > 0) { int take = Math.min(left, stack.getCount()); stack.shrink(take); left -= take; } }
    private static void removeSalt(List<ItemStack> stacks, Predicate<ItemStack> predicate, int amount) { int left = amount; for (ItemStack stack : stacks) if (predicate.test(stack) && left > 0) { int take = Math.min(left, ReagentContainerAccess.contents(stack).getOrDefault("table_salt", 0)); ReagentContainerAccess.remove(stack, "table_salt", take); left -= take; } }
    private static void addOutput(IItemHandlerModifiable inventory, ItemStack result) { ItemStack existing = inventory.getStackInSlot(4); if (existing.isEmpty()) inventory.setStackInSlot(4, result); else if (ItemStack.isSameItemSameComponents(existing, result)) inventory.setStackInSlot(4, new ItemStack(result.getItem(), Math.min(existing.getMaxStackSize(), existing.getCount() + result.getCount()))); }
}
