package net.ssc14.content.commerce.cargo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.common.station.StationBudgetService;
import net.ssc14.content.block.Ssc14Blocks;

public final class CargoPurchaseService {
    private static final int PALLET_SEARCH_RADIUS = 32;
    private static final Identifier SUCCESS_SOUND = Identifier.parse("ssc_14:cargo_buy_success");
    private static final Identifier ERROR_SOUND = Identifier.parse("ssc_14:cargo_buy_error");

    private CargoPurchaseService() {
    }

    public static void purchase(LevelAccessor world, BlockPos consolePos, Player player, int itemIndex) {
        if (!(world instanceof ServerLevel serverLevel) || player == null) {
            return;
        }
        int currentTab = CargoOrderService.currentTab(world, consolePos);
        CargoCatalog.CargoItem cargoItem = CargoCatalog.getItemAt(currentTab, itemIndex);
        if (cargoItem == null) {
            return;
        }

        BlockPos palletPos = findNearestPallet(world, consolePos);
        if (palletPos == null) {
            player.sendSystemMessage(Component.literal(
                    "§cОшибка: Поддон заказа (ORDER_PALLET) не найден в радиусе 32 блоков от консоли!"));
            playSound(serverLevel, consolePos, ERROR_SOUND);
            return;
        }
        if (!StationBudgetService.trySpend(world, cargoItem.getPrice())) {
            player.sendSystemMessage(Component.literal(
                    "§cОшибка: Недостаточно средств на бюджете станции!"));
            playSound(serverLevel, consolePos, ERROR_SOUND);
            return;
        }

        BlockPos spawnPos = palletPos.above();
        for (int stackIndex = 0; stackIndex < cargoItem.getCount(); stackIndex++) {
            spawnItem(serverLevel, spawnPos, cargoItem.getStack());
        }
        playSound(serverLevel, consolePos, SUCCESS_SOUND);
        player.sendSystemMessage(Component.literal("§aЗаказ ["
                + cargoItem.getStack().getHoverName().getString()
                + "] успешно доставлен на поддон."));
    }

    private static BlockPos findNearestPallet(LevelAccessor world, BlockPos origin) {
        BlockPos nearest = null;
        double shortestDistance = Double.MAX_VALUE;
        for (BlockPos candidate : BlockPos.betweenClosed(
                origin.offset(-PALLET_SEARCH_RADIUS, -PALLET_SEARCH_RADIUS, -PALLET_SEARCH_RADIUS),
                origin.offset(PALLET_SEARCH_RADIUS, PALLET_SEARCH_RADIUS, PALLET_SEARCH_RADIUS))) {
            if (world.getBlockState(candidate).is(Ssc14Blocks.ORDER_PALLET.get())) {
                double distance = candidate.distSqr(origin);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearest = candidate.immutable();
                }
            }
        }
        return nearest;
    }

    private static void spawnItem(ServerLevel level, BlockPos pos, ItemStack stack) {
        ItemEntity item = new ItemEntity(level,
                pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, stack);
        item.setNoPickUpDelay();
        item.setDeltaMovement(item.getDeltaMovement().x * 0.1,
                item.getDeltaMovement().y, item.getDeltaMovement().z * 0.1);
        level.addFreshEntity(item);
    }

    private static void playSound(ServerLevel level, BlockPos pos, Identifier soundId) {
        level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.getValue(soundId),
                SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
