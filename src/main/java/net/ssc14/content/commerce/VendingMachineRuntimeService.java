package net.ssc14.content.commerce;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.block.commerce.VendingMachineBlock;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

public final class VendingMachineRuntimeService {
    private static final int VEND_DELAY_TICKS = 20;
    private static final int INITIAL_STOCK = 4;
    private static final Identifier EJECT_SOUND =
            Identifier.parse("ssc_14:machines_vending_eject");
    private static final Map<Integer, Item> INITIAL_ITEMS = Map.of(
            1, Ssc14Items.CHEESE_HONKERS.get(),
            2, Ssc14Items.CHIPS.get(),
            3, Ssc14Items.BORITOS.get(),
            5, Ssc14Items.POPCORN.get(),
            6, Ssc14Items.ENERGY_BAR.get());

    private VendingMachineRuntimeService() { }

    public static void initialize(Level level, BlockPos pos) {
        level.setBlock(pos.above(), Ssc14Blocks.VENDING_KOSTIL.get().defaultBlockState(),
                Block.UPDATE_ALL);
        if (level.isClientSide() || !level.getBlockState(pos).is(Ssc14Blocks.VENDING_AUT_1.get())) {
            return;
        }
        if (level.getBlockEntity(pos) instanceof Container container) {
            INITIAL_ITEMS.forEach((slot, item) -> {
                container.setItem(slot, new ItemStack(item));
                putStock(level, pos, slot, INITIAL_STOCK);
            });
            container.setChanged();
        }
    }

    public static void validateUpperPlug(Level level, BlockPos pos) {
        if (!level.getBlockState(pos.above()).is(Ssc14Blocks.VENDING_KOSTIL.get())) {
            level.destroyBlock(pos, false);
        }
    }

    public static void handleCreativeAttack(Level level, BlockPos pos, Player player) {
        if (player != null && player.gameMode() == GameType.CREATIVE) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    public static void vend(LevelAccessor world, BlockPos pos, int buttonId) {
        if (!(world instanceof ServerLevel level)) return;
        int slot = VendingButtonIds.slotForButton(buttonId);
        if (slot < 0 || !(level.getBlockEntity(pos) instanceof Container container)) return;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        String lockKey = "but_" + slot;
        if (blockEntity == null || blockEntity.getPersistentData().getBooleanOr(lockKey, false)
                || container.getItem(slot).isEmpty()) return;

        blockEntity.getPersistentData().putBoolean(lockKey, true);
        sync(level, pos, blockEntity);
        float volume = (float) Mth.nextDouble(level.getRandom(), 0.9, 1.1);
        float pitch = (float) Mth.nextDouble(level.getRandom(), 0.9, 1.1);
        level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.getValue(EJECT_SOUND),
                SoundSource.BLOCKS, volume, pitch);
        ServerWorkScheduler.schedule(level, VEND_DELAY_TICKS,
                () -> finishVend(level, pos, slot));
    }

    private static void finishVend(ServerLevel level, BlockPos pos, int slot) {
        if (!(level.getBlockEntity(pos) instanceof Container container)) return;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        ItemStack stack = container.getItem(slot);
        if (blockEntity == null || stack.isEmpty()) return;

        blockEntity.getPersistentData().putBoolean("but_" + slot, false);
        spawn(level, pos, stack.copy());
        double remaining = blockEntity.getPersistentData().getDoubleOr("slot_" + slot, 0) - 1;
        blockEntity.getPersistentData().putDouble("slot_" + slot, remaining);
        if (remaining == 0) stack.shrink(1);
        container.setChanged();
        sync(level, pos, blockEntity);
    }

    private static void spawn(ServerLevel level, BlockPos pos, ItemStack stack) {
        Direction direction = level.getBlockState(pos).hasProperty(VendingMachineBlock.FACING)
                ? level.getBlockState(pos).getValue(VendingMachineBlock.FACING)
                : Direction.NORTH;
        double spawnX = pos.getX() + 0.5 + direction.getStepX() * 0.5;
        double spawnZ = pos.getZ() + 0.5 + direction.getStepZ() * 0.5;
        ItemEntity item = new ItemEntity(level, spawnX, pos.getY() + 0.5, spawnZ, stack);
        item.setPickUpDelay(0);
        item.setUnlimitedLifetime();
        level.addFreshEntity(item);
    }

    private static void putStock(Level level, BlockPos pos, int slot, int stock) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.getPersistentData().putDouble("slot_" + slot, stock);
            sync(level, pos, blockEntity);
        }
    }

    private static void sync(Level level, BlockPos pos, BlockEntity blockEntity) {
        blockEntity.setChanged();
        var state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
    }
}
