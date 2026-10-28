package net.ssc14.content.item.tool;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

/** Canonical network configurator item. */
public class NetworkConfiguratorItem extends Item {
    public NetworkConfiguratorItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        NetworkConfiguratorService.use(context.getLevel(), context.getClickedPos(),
                context.getPlayer(), context.getItemInHand());
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {
        NetworkConfiguratorService.reset(stack);
        return true;
    }
}
