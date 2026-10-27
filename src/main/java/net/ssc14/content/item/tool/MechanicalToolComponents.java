package net.ssc14.content.item.tool;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.Ssc14Items;

/** Preserves the generated tools' intentionally unbreakable default components. */
@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class MechanicalToolComponents {
    private MechanicalToolComponents() { }

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        for (Item item : List.of(
                Ssc14Items.CROWBAR.get(),
                Ssc14Items.NIPPERS.get(),
                Ssc14Items.FIRE_AXE.get(),
                Ssc14Items.SCREWDRIVER.get(),
                Ssc14Items.SPANNER.get())) {
            event.modify(item,
                    (builder, _, _) -> builder.set(DataComponents.MAX_DAMAGE, null));
        }
    }
}
