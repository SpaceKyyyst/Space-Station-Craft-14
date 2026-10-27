package net.ssc14.content.item.weapon;

import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

/** Canonical .35 pistol magazine implementation shared by loaded and empty IDs. */
public class PistolMagazineItem extends Item {
    private final PistolMagazineState state;

    public PistolMagazineItem(Properties properties, PistolMagazineState state) {
        super(properties.stacksTo(1).fireResistant());
        this.state = Objects.requireNonNull(state, "state");
    }

    public PistolMagazineState state() {
        return state;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> components,
            TooltipFlag flag) {
        super.appendHoverText(stack, context, display, components, flag);
        Mk58Item.appendTooltip(
                components, FirearmInteractionService.tooltip(state, stack));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        FirearmInteractionService.use(state, level, player, player.getItemInHand(hand));
        return result;
    }

    @Override
    public void inventoryTick(
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        FirearmInteractionService.inventoryTick(
                state, stack, level, entity, equipmentSlot);
    }
}
