package net.mcreator.ssc;

import io.netty.buffer.Unpooled;
import net.ssc14.content.menu.access.AccessConfigItemMenu;
import net.ssc14.content.menu.storage.BackpackGUIMenu;
import net.ssc14.content.menu.food.EggCartonGUIMenu;
import net.mcreator.ssc.world.inventory.PDApassangerGUIMenu;
import net.mcreator.ssc.procedures.EggCarton_EggsGenerateProcedure;
import net.mcreator.ssc.procedures.PDAGUIOpeningConditionsProcedure;
import net.mcreator.ssc.procedures.PDA_generateProcedure;
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
import net.ssc14.content.item.container.PortableMenuInteractionService;
import net.ssc14.content.item.container.PortableMenuItemKind;

import javax.annotation.Nullable;

/** Adapter retained until portable inventory menus move into canonical packages. */
final class LegacyPortableMenuInteractionAdapter
        implements PortableMenuInteractionService.Adapter {
    static final LegacyPortableMenuInteractionAdapter INSTANCE =
            new LegacyPortableMenuInteractionAdapter();

    private LegacyPortableMenuInteractionAdapter() { }

    @Override
    public void open(
            PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        player.openMenu(menuProvider(kind, player, hand),
                buffer -> writeBinding(buffer, player, hand));
    }

    @Override
    public boolean canOpen(
            PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        return kind != PortableMenuItemKind.PASSENGER_PDA
                || PDAGUIOpeningConditionsProcedure.execute(
                        player, player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(
            PortableMenuItemKind kind,
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        switch (kind) {
            case PASSENGER_PDA -> PDA_generateProcedure.execute(
                    level, entity.getX(), entity.getY(), entity.getZ(), entity, stack);
            case EGG_CARTON -> EggCarton_EggsGenerateProcedure.execute(stack);
            default -> { }
        }
    }

    private static MenuProvider menuProvider(
            PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal(kind.menuTitle());
            }

            @Override
            public AbstractContainerMenu createMenu(
                    int id, Inventory inventory, Player menuPlayer) {
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

    private static void writeBinding(
            FriendlyByteBuf buffer, ServerPlayer player, InteractionHand hand) {
        buffer.writeBlockPos(player.blockPosition());
        buffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
    }
}
