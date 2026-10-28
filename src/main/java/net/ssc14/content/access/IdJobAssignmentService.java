package net.ssc14.content.access;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ssc14.common.network.SlotMenuStateAccess;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

public final class IdJobAssignmentService {
    private static final int ASSIGNMENT_DELAY_TICKS = 1;
    private static final String BUFFER_KEY = "Bufer_LG";
    private static final String JOB_STATE_KEY = "Job_Name";
    private static final String MISSING_CARDS = "ВСТАВЬТЕ КАРТЫ";

    private IdJobAssignmentService() { }

    public static void setAllAllowed(Player player, boolean enabled) {
        if (!(player.containerMenu instanceof SlotMenuStateAccess menu)
                || menu.getSlots().size() < 2) {
            return;
        }
        ItemStack source = stack(menu, 0);
        ItemStack target = stack(menu, 1);
        if (source.isEmpty() || target.isEmpty()) {
            return;
        }

        var sourceData = source.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        for (String accessKey : IdAccessRules.grantedAccessKeys(sourceData)) {
            menu.sendMenuStateUpdate(player, 1, accessKey, enabled, true);
        }
    }

    /** Synchronizes one access bit from the source card and menu state to the target card. */
    public static void syncAccessField(Player player, String accessKey) {
        if (!(player.containerMenu instanceof SlotMenuStateAccess menu)
                || menu.getSlots().size() < 2 || accessKey == null) {
            return;
        }
        ItemStack source = stack(menu, 0);
        ItemStack target = stack(menu, 1);
        if (source.isEmpty() || target.isEmpty()) return;
        var sourceData = source.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!sourceData.getBoolean(accessKey).orElse(false)) return;
        boolean enabled = menu.getMenuState(1, accessKey, false);
        CustomData.update(DataComponents.CUSTOM_DATA, target,
                tag -> tag.putBoolean(accessKey, enabled));
        menu.getSlots().get(1).set(target);
    }

    /** Synchronizes every access bit from the target ID card into the open menu. */
    public static void syncAllAccess(Player player) {
        if (!(player.containerMenu instanceof SlotMenuStateAccess menu)
                || menu.getSlots().size() < 2) return;
        ItemStack target = stack(menu, 1);
        if (target.isEmpty()) return;
        var data = target.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        for (String accessKey : IdJobCatalog.accessKeys()) {
            menu.sendMenuStateUpdate(player, 1, accessKey,
                    data.getBoolean(accessKey).orElse(false), true);
        }
    }

    public static void assign(Player player, BlockPos consolePos, int buttonId) {
        IdJobCatalog.Definition definition = IdJobCatalog.definition(buttonId);
        if (definition == null || !(player.level() instanceof ServerLevel level)
                || !(player.containerMenu instanceof SlotMenuStateAccess menu)) return;
        ItemStack source = stack(menu, 0);
        ItemStack target = stack(menu, 1);
        if (source.isEmpty() || target.isEmpty()) {
            menu.sendMenuStateUpdate(player, 0, JOB_STATE_KEY, MISSING_CARDS, true);
            return;
        }

        setBuffer(level, consolePos, true);
        ServerWorkScheduler.schedule(level, ASSIGNMENT_DELAY_TICKS,
                () -> finishAssignment(player, consolePos, definition));
    }

    private static void finishAssignment(Player player, BlockPos consolePos,
            IdJobCatalog.Definition definition) {
        if (!(player.containerMenu instanceof SlotMenuStateAccess menu)) return;
        ItemStack source = stack(menu, 0);
        ItemStack target = stack(menu, 1);
        if (source.isEmpty() || target.isEmpty() || !hasAllAccess(source)) return;

        CustomData.update(DataComponents.CUSTOM_DATA, target, tag -> {
            for (int index = 0; index < IdJobCatalog.accessKeys().size(); index++) {
                tag.putBoolean(IdJobCatalog.accessKeys().get(index), definition.grants(index));
            }
        });
        menu.sendMenuStateUpdate(player, 0, JOB_STATE_KEY, definition.name(), true);
        target.set(DataComponents.CUSTOM_NAME,
                Component.literal("ID карта (" + definition.name() + ")"));
        setBuffer((ServerLevel) player.level(), consolePos, false);
    }

    private static boolean hasAllAccess(ItemStack stack) {
        var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return IdAccessRules.hasAllAccess(tag);
    }

    private static ItemStack stack(SlotMenuStateAccess menu, int slotId) {
        var slot = menu.getSlots().get(slotId);
        return slot == null ? ItemStack.EMPTY : slot.getItem();
    }

    private static void setBuffer(ServerLevel level, BlockPos pos, boolean value) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;
        blockEntity.getPersistentData().putBoolean(BUFFER_KEY, value);
        blockEntity.setChanged();
        var state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, 3);
    }
}
