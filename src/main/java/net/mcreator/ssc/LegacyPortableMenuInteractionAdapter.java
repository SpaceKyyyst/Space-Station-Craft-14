package net.mcreator.ssc;

import io.netty.buffer.Unpooled;
import net.mcreator.ssc.world.inventory.AccessConfigGUIMenu;
import net.mcreator.ssc.world.inventory.BackpackGUIMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.ssc14.content.item.container.PortableMenuInteractionService;
import net.ssc14.content.item.container.PortableMenuItemKind;

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
                    case ACCESS_CONFIG -> new AccessConfigGUIMenu(id, inventory, buffer);
                    case BACKPACK -> new BackpackGUIMenu(id, inventory, buffer);
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
