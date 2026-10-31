package net.ssc14.content.item.tool;

import io.netty.buffer.Unpooled;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.menu.network.NetConfigGUIMenu;

/** Owns network-configurator target state and menu integration. */
public final class NetworkConfiguratorService {
    private static final String FIRST_TYPE = "First_Target_Type";
    private static final String[] TARGET_KEYS = {
        "First_Target_X", "First_Target_Y", "First_Target_Z", FIRST_TYPE,
        "Sec_Target_X", "Sec_Target_Y", "Sec_Target_Z"
    };
    private static Adapter adapter = new CanonicalAdapter();

    private NetworkConfiguratorService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(LevelAccessor level, BlockPos pos,
            Entity entity, ItemStack stack) {
        if (level == null || pos == null || entity == null || stack == null
                || !level.getBlockState(pos).is(BlockTags.create(
                Identifier.fromNamespaceAndPath("ssc14", "connectable")))) {
            return;
        }
        if (!hasFirstTarget(stack)) {
            if (entity instanceof ServerPlayer player) {
                player.sendSystemMessage(Component.literal("Вы начали соединение"), true);
            }
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putDouble("First_Target_X", pos.getX());
                tag.putDouble("First_Target_Y", pos.getY());
                tag.putDouble("First_Target_Z", pos.getZ());
                tag.putString(FIRST_TYPE, "" + level.getBlockState(pos));
            });
            if (entity instanceof ServerPlayer player) {
                player.sendSystemMessage(Component.literal("Выбрана первая цель"), false);
            }
        } else if (entity instanceof ServerPlayer player) {
            adapter.openMenu(player, pos);
        }
    }

    public static boolean hasFirstTarget(ItemStack stack) {
        if (stack == null) return false;
        return !stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getStringOr(FIRST_TYPE, "").isEmpty();
    }

    public static void reset(ItemStack stack) {
        if (stack == null) return;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            for (String key : TARGET_KEYS) {
                tag.remove(key);
            }
        });
    }

    public static void resetHeld(Entity entity) {
        if (entity instanceof LivingEntity living
                && living.getMainHandItem().is(Ssc14Items.NETWORK_CONFIGURATOR.get())) {
            reset(living.getMainHandItem());
        }
    }

    public static void closeWhenNotHeld(Entity entity) {
        if (entity instanceof Player player
                && !player.getMainHandItem().is(Ssc14Items.NETWORK_CONFIGURATOR.get())) {
            player.closeContainer();
        }
    }

    @FunctionalInterface
    public interface Adapter {
        void openMenu(ServerPlayer player, BlockPos pos);
    }

    private static final class CanonicalAdapter implements Adapter {
        @Override
        public void openMenu(ServerPlayer player, BlockPos pos) {
            player.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("NetConfigGUI");
                }

                @Override
                public boolean shouldTriggerClientSideContainerClosingOnOpen() {
                    return false;
                }

                @Override
                public AbstractContainerMenu createMenu(
                        int id, Inventory inventory, Player menuPlayer) {
                    return new NetConfigGUIMenu(id, inventory,
                            new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
    }
}
