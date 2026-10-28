package net.ssc14.content.block.airlock;

import net.ssc14.content.menu.access.AccessConfigMENUMenu;
import net.ssc14.content.access.IdCardRegistry;
import net.ssc14.content.access.PlayerAccessService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Only the card inserted into the bound configurator grants edit permissions. */
public final class AirlockConfiguratorService {
    private AirlockConfiguratorService() { }
    public static long permissions(ItemStack configurator) {
        var contents = configurator.get(DataComponents.CONTAINER);
        if (contents == null) return 0;
        var card = contents.getStackInSlot(0);
        return IdCardRegistry.defaults(card.getItem()) == null ? 0 : PlayerAccessService.permissions(card);
    }
    public static void synchronize(Player player, boolean initialize) {
        if (player.level().isClientSide() || !(player.containerMenu instanceof AccessConfigMENUMenu menu)
                || !menu.stillValid(player)) return;
        var pos = new BlockPos(menu.x, menu.y, menu.z);
        var door = player.level().getBlockEntity(pos);
        if (door == null) return;
        var data = door.getPersistentData();
        long allowed = permissions(player.getMainHandItem());
        boolean changed = false;
        for (int i = 0; i < IdCardRegistry.KEYS.size(); i++) {
            String key = IdCardRegistry.KEYS.get(i);
            boolean current = data.getBooleanOr(key, false);
            boolean requested = menu.getMenuState(1, key, current);
            if (initialize || (allowed & (1L << i)) == 0) {
                if (initialize || requested != current) menu.sendMenuStateUpdate(player, 1, key, current, true);
            } else if (requested != current) {
                data.putBoolean(key, requested);
                changed = true;
            }
        }
        if (changed) {
            AirlockEventLogService.append(door, "ACCESS_CONFIGURED");
            door.setChanged();
            player.level().sendBlockUpdated(pos, door.getBlockState(), door.getBlockState(), 3);
        }
    }
}
