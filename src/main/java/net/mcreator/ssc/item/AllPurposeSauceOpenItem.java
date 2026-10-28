
package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.reagent.Ssc14ReagentContainers;

/** @deprecated Use the canonical reagent container registry entry. */
@Deprecated(forRemoval = false)
public class AllPurposeSauceOpenItem extends ReagentContainerItem {
    public AllPurposeSauceOpenItem(Item.Properties properties) {
        super(properties, Ssc14ReagentContainers.ALL_PURPOSE_SAUCE_CAPACITY);
    }
}
