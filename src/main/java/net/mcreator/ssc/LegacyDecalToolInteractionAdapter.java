package net.mcreator.ssc;

import io.netty.buffer.Unpooled;
import net.mcreator.ssc.procedures.DECAL1deletion_USE_Procedure;
import net.mcreator.ssc.procedures.DECAL1spawner_USE_Procedure;
import net.ssc14.content.decal.DecalToolPermissionService;
import net.ssc14.content.menu.decal.DECALScolorGUIMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.UseOnContext;
import net.ssc14.content.item.decal.DecalToolInteractionService;
import net.ssc14.content.item.decal.DecalToolKind;

/** Adapter retained until decal menus and procedures move into canonical packages. */
final class LegacyDecalToolInteractionAdapter implements DecalToolInteractionService.Adapter {
    static final LegacyDecalToolInteractionAdapter INSTANCE =
            new LegacyDecalToolInteractionAdapter();

    private LegacyDecalToolInteractionAdapter() { }

    @Override
    public void use(DecalToolKind kind, Player player, InteractionHand hand) {
        if (!(player instanceof ServerPlayer serverPlayer)
                || !DecalToolPermissionService.execute(serverPlayer)) {
            return;
        }
        serverPlayer.openMenu(menuProvider(kind, player, hand), buffer -> {
            buffer.writeBlockPos(player.blockPosition());
            buffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
        });
    }

    @Override
    public void useOn(DecalToolKind kind, UseOnContext context) {
        if (kind == DecalToolKind.DELETION) {
            DECAL1deletion_USE_Procedure.execute(context.getLevel(), context.getPlayer());
            return;
        }
        DECAL1spawner_USE_Procedure.execute(
                context.getLevel(), context.getPlayer(), context.getClickedPos(),
                context.getClickedFace(), context.getItemInHand());
    }

    private static MenuProvider menuProvider(
            DecalToolKind kind, Player player, InteractionHand hand) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal(kind.menuTitle());
            }

            @Override
            public AbstractContainerMenu createMenu(
                    int id, Inventory inventory, Player menuPlayer) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                buffer.writeBlockPos(player.blockPosition());
                buffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                return new DECALScolorGUIMenu(id, inventory, buffer);
            }
        };
    }
}
