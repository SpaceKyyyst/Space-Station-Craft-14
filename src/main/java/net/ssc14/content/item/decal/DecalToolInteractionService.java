package net.ssc14.content.item.decal;

import java.util.Objects;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.UseOnContext;
import net.ssc14.content.decal.DecalPlacementService;
import net.ssc14.content.decal.DecalRemovalService;
import net.ssc14.content.decal.DecalToolPermissionService;
import net.ssc14.content.menu.decal.DECALScolorGUIMenu;

/** Owns canonical decal GUI and world interaction wiring. */
public final class DecalToolInteractionService {
    public interface Adapter {
        void use(DecalToolKind kind, Player player, InteractionHand hand);

        void useOn(DecalToolKind kind, UseOnContext context);
    }

    private static Adapter adapter = new CanonicalAdapter();

    private DecalToolInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(DecalToolKind kind, Player player, InteractionHand hand) {
        adapter.use(kind, player, hand);
    }

    public static void useOn(DecalToolKind kind, UseOnContext context) {
        adapter.useOn(kind, context);
    }

    private static final class CanonicalAdapter implements Adapter {
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
                DecalRemovalService.execute(context.getPlayer());
                return;
            }
            DecalPlacementService.execute(context.getLevel(), context.getPlayer(), context.getClickedPos(),
                    context.getClickedFace(), context.getItemInHand());
        }

        private static MenuProvider menuProvider(DecalToolKind kind, Player player, InteractionHand hand) {
            return new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal(kind.menuTitle());
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player menuPlayer) {
                    FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                    buffer.writeBlockPos(player.blockPosition());
                    buffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                    return new DECALScolorGUIMenu(id, inventory, buffer);
                }
            };
        }
    }
}
