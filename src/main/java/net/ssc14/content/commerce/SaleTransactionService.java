package net.ssc14.content.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.ssc14.common.station.StationBudgetService;
import net.ssc14.content.block.Ssc14Blocks;

public final class SaleTransactionService {
    private static final int PALLET_SEARCH_RADIUS = 16;

    private SaleTransactionService() {
    }

    public static void sellNearby(LevelAccessor world, BlockPos consolePos, Player player) {
        if (!(world instanceof ServerLevel level) || player == null) return;
        int totalEarned = 0;
        BlockPos min = consolePos.offset(-PALLET_SEARCH_RADIUS,
                -PALLET_SEARCH_RADIUS, -PALLET_SEARCH_RADIUS);
        BlockPos max = consolePos.offset(PALLET_SEARCH_RADIUS,
                PALLET_SEARCH_RADIUS, PALLET_SEARCH_RADIUS);
        for (BlockPos palletPos : BlockPos.betweenClosed(min, max)) {
            if (!level.getBlockState(palletPos).is(Ssc14Blocks.SALE_PALLET.get())) continue;
            BlockPos itemPos = palletPos.above();
            AABB area = new AABB(itemPos.getX(), itemPos.getY() - 1, itemPos.getZ(),
                    itemPos.getX() + 1, itemPos.getY() + 1, itemPos.getZ() + 1);
            for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, area)) {
                ItemStack stack = itemEntity.getItem();
                totalEarned += SalePriceRegistry.price(stack) * stack.getCount();
                level.sendParticles(ParticleTypes.POOF, itemEntity.getX(),
                        itemEntity.getY() + 0.2, itemEntity.getZ(),
                        8, 0.1, 0.1, 0.1, 0.02);
                itemEntity.discard();
            }
        }

        if (totalEarned > 0) {
            StationBudgetService.credit(level, totalEarned);
            player.sendSystemMessage(Component.literal(
                    "§aТовары со склада успешно сбыты! Получено: +"
                            + totalEarned + " кредитов."));
        } else {
            player.sendSystemMessage(Component.literal(
                    "§cОшибка: В радиусе 16 блоков нет поддонов с подходящим товаром для продажи!"));
        }
    }
}
