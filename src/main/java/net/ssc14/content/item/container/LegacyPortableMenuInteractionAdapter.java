package net.ssc14.content.item.container;

import io.netty.buffer.Unpooled;
import net.ssc14.content.menu.access.AccessConfigItemMenu;
import net.ssc14.content.menu.storage.BackpackGUIMenu;
import net.ssc14.content.menu.food.EggCartonGUIMenu;
import net.ssc14.content.menu.pda.PDApassangerGUIMenu;
import net.ssc14.content.menu.pda.PortableMenuCondition;
import net.ssc14.content.menu.pda.PdaGenerationService;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

/** Compatibility adapter retained until portable menu contracts are migrated. */
public final class LegacyPortableMenuInteractionAdapter
        implements PortableMenuInteractionService.Adapter {
    public static final LegacyPortableMenuInteractionAdapter INSTANCE =
            new LegacyPortableMenuInteractionAdapter();

    private LegacyPortableMenuInteractionAdapter() { }

    @Override
    public void open(PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        player.openMenu(menuProvider(kind, player, hand), buffer -> writeBinding(buffer, player, hand));
    }

    @Override
    public boolean canOpen(PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        return kind != PortableMenuItemKind.PASSENGER_PDA
                || PortableMenuCondition.execute(player, player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(PortableMenuItemKind kind, ItemStack stack, ServerLevel level,
            Entity entity, @Nullable EquipmentSlot equipmentSlot) {
        switch (kind) {
            case PASSENGER_PDA -> PdaGenerationService.execute(
                    level, entity.getX(), entity.getY(), entity.getZ(), entity, stack);
            case EGG_CARTON -> EggGenerationService.execute(stack);
            default -> { }
        }
    }

    private static MenuProvider menuProvider(PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() { return Component.literal(kind.menuTitle()); }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player menuPlayer) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                writeBinding(buffer, player, hand);
                return switch (kind) {
                    case ACCESS_CONFIG -> new AccessConfigItemMenu(id, inventory, buffer);
                    case BACKPACK -> new BackpackGUIMenu(id, inventory, buffer);
                    case PASSENGER_PDA -> new PDApassangerGUIMenu(id, inventory, buffer);
                    case EGG_CARTON -> new EggCartonGUIMenu(id, inventory, buffer);
                };
            }
        };
    }

    private static void writeBinding(FriendlyByteBuf buffer, ServerPlayer player, InteractionHand hand) {
        buffer.writeBlockPos(player.blockPosition());
        buffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
    }
}
