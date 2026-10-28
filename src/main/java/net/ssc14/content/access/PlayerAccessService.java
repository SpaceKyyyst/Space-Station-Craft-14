package net.ssc14.content.access;

import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosApi;

/** Server-thread-only permission collection. Immutable component identity invalidates cached masks. */
public final class PlayerAccessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAccessService.class);
    private static final int MAX_CONTAINER_DEPTH = 16;
    private static final int MAX_NESTED_STACKS = 512;
    private static final Map<ItemStack, CachedAccess> CACHE = new WeakHashMap<>();
    private static boolean curiosFailureLogged;

    private record CachedAccess(CustomData data, ItemContainerContents contents, long mask) { }
    private PlayerAccessService() { }

    public static void tick(Player player) {
        if (player.level().isClientSide()) return;
        try (var timing = net.ssc14.core.diagnostics.TickPhase.start("player_access", player.getInventory().getContainerSize())) {
            synchronize(player);
        }
    }

    private static void synchronize(Player player) {
        long mask = currentPermissions(player);
        var data = player.getPersistentData();
        for (int index = 0; index < IdCardRegistry.KEYS.size(); index++) {
            String key = IdCardRegistry.KEYS.get(index);
            boolean granted = (mask & (1L << index)) != 0;
            if (data.getBooleanOr(key, false) != granted) data.putBoolean(key, granted);
        }
    }

    /** Reads currently held cards; authorization must not rely on last tick's player data. */
    public static long currentPermissions(Player player) {
        long mask = 0;
        var inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            mask |= permissions(inventory.getItem(slot));
        }
        try {
            long curiosMask = 0;
            var curios = CuriosApi.getCuriosInventory(player);
            if (curios.isPresent()) {
                for (var handler : curios.get().getCurios().values()) {
                    var slots = handler.getStacks();
                    for (int slot = 0; slot < slots.getSlots(); slot++) curiosMask |= permissions(slots.getStackInSlot(slot));
                }
            }
            mask |= curiosMask;
        } catch (RuntimeException exception) {
            if (!curiosFailureLogged) LOGGER.warn("Cannot read Curios ID cards; only vanilla inventory access applies", exception);
            curiosFailureLogged = true;
        }
        return mask;
    }

    public static long permissions(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        var contents = stack.get(DataComponents.CONTAINER);
        if (contents == null && IdCardRegistry.defaults(stack.getItem()) == null) return 0;
        var data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var cached = CACHE.get(stack);
        if (cached != null && cached.data == data && cached.contents == contents) return cached.mask;
        long mask = collect(stack, 0, new int[]{MAX_NESTED_STACKS});
        CACHE.put(stack, new CachedAccess(stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY), contents, mask));
        return mask;
    }

    private static long collect(ItemStack stack, int depth, int[] remaining) {
        if (stack.isEmpty() || depth > MAX_CONTAINER_DEPTH || remaining[0]-- <= 0) return 0;
        long mask = cardPermissions(stack);
        var contents = stack.get(DataComponents.CONTAINER);
        if (contents != null) {
            for (var template : contents.nonEmptyItems()) {
                if (remaining[0] <= 0) break;
                mask |= collect(template.create(), depth + 1, remaining);
            }
        }
        return mask;
    }

    private static long cardPermissions(ItemStack stack) {
        Long defaults = IdCardRegistry.defaults(stack.getItem());
        if (defaults == null) return 0;
        var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.getBooleanOr("spawned", false)) {
            tag.putBoolean("spawned", true);
            for (int index = 0; index < IdCardRegistry.KEYS.size(); index++) {
                if ((defaults & (1L << index)) != 0) tag.putBoolean(IdCardRegistry.KEYS.get(index), true);
            }
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
        long mask = 0;
        for (int index = 0; index < IdCardRegistry.KEYS.size(); index++) {
            if (tag.getBooleanOr(IdCardRegistry.KEYS.get(index), false)) mask |= 1L << index;
        }
        return mask;
    }
}


