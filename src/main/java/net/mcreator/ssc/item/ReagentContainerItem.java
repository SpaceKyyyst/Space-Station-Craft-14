
package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.reagent.Ssc14ReagentContainers;

/**
 * @deprecated Use {@link net.ssc14.content.item.reagent.ReagentContainerItem};
 *     retained for source compatibility.
 */
@Deprecated(forRemoval = false)
public class ReagentContainerItem
        extends net.ssc14.content.item.reagent.ReagentContainerItem {
    public ReagentContainerItem(Item.Properties properties) {
        this(properties, Ssc14ReagentContainers.DEFAULT_CAPACITY);
    }

    protected ReagentContainerItem(Item.Properties properties, int capacity) {
        super(properties, capacity);
    }
}
