package net.ssc14.content.menu.access;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.common.network.SlotMenuStateAccess;

/** Canonical synchronization of access flags from the ID-code menu card. */
public final class IdCodeAccessStateService {
    private static final String[] ACCESS_KEYS = {
            "gun_room", "HoS", "Brig", "Security", "Detective", "PNT", "Crio", "CE",
            "Atmos", "Ingeneer", "Out", "Qm", "Utilizat", "Supply_Deportament", "CMO",
            "Chemistry", "Medical", "RD", "Scientist", "Technical", "Church"
    };

    private IdCodeAccessStateService() {
    }

    public static void refresh(Player player) {
        if (!(player.containerMenu instanceof SlotMenuStateAccess menu)
                || menu.getSlots().size() <= 1
                || menu.getSlots().get(0).getItem().isEmpty()
                || menu.getSlots().get(1).getItem().isEmpty()) {
            return;
        }
        ItemStack card = menu.getSlots().get(1).getItem();
        CustomData data = card.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        for (String key : ACCESS_KEYS) {
            menu.sendMenuStateUpdate(player, 1, key, data.copyTag().getBooleanOr(key, false), true);
        }
    }
}
