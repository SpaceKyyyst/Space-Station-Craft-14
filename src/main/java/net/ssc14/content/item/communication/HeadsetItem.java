package net.ssc14.content.item.communication;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Canonical headset item with shared key initialization and interaction behavior. */
public class HeadsetItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    private final HeadsetDefinition definition;

    public HeadsetItem(Properties properties, HeadsetDefinition definition) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
        this.definition = definition;
    }

    public HeadsetDefinition definition() {
        return definition;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        HeadsetKeyService.interact(level, player);
        return result;
    }

    @Override
    public void inventoryTick(
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        HeadsetInitializationService.initialize(stack);
    }
}
