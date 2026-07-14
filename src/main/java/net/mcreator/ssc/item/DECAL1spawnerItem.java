
package net.mcreator.ssc.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.mcreator.ssc.procedures.DECAL1spawner_USE_Procedure;
import java.util.function.Consumer;

public class DECAL1spawnerItem extends Item {
    public DECAL1spawnerItem(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        DECAL1spawner_USE_Procedure.execute(
            context.getLevel(),
            context.getPlayer(),
            context.getClickedPos(),
            context.getClickedFace(),
            context.getItemInHand() // Передаем стек предмета
        );
        return InteractionResult.SUCCESS;
    }
}
