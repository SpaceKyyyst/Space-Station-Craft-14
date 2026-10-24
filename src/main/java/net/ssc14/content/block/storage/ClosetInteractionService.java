package net.ssc14.content.block.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/** Opens and closes standard closets while preserving legacy entity and item handling. */
public final class ClosetInteractionService {
    private static final String LOCKED_TAG = "ssc14:lockedInCloset";
    private static final TagKey<net.minecraft.world.entity.EntityType<?>> LOCKABLE_ENTITIES =
            TagKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath("ssc14", "lockableentitys"));

    private ClosetInteractionService() { }

    public static void toggle(LevelAccessor level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            return;
        }
        boolean open = state.getValue(StationClosetBlock.OPEN);
        level.setBlock(pos, state.setValue(StationClosetBlock.OPEN, !open), 3);
        playSound(level, pos, open ? "locker_close" : "locker_open");
        if (open) {
            close(level, pos);
        } else {
            open(level, pos);
        }
    }

    private static void open(LevelAccessor level, BlockPos pos) {
        extractItems(level, pos);
        for (Entity entity : entitiesInside(level, pos)) {
            if (isLockable(entity)) {
                release(entity);
            }
        }
    }

    private static void close(LevelAccessor level, BlockPos pos) {
        for (Entity entity : entitiesInside(level, pos)) {
            if (entity instanceof ItemEntity itemEntity) {
                insertItem(level, pos, itemEntity);
            } else if (isLockable(entity)) {
                lock(entity, pos);
            }
        }
    }

    private static void extractItems(LevelAccessor level, BlockPos pos) {
        IItemHandler handler = itemHandler(level, pos);
        if (handler == null) {
            return;
        }
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack stack = handler.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }
            ItemStack extracted = handler.extractItem(slot, stack.getCount(), false);
            if (!extracted.isEmpty()) {
                ItemEntity item = new ItemEntity((Level) level,
                        pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, extracted);
                item.setDeltaMovement(new Vec3(
                        level.getRandom().nextDouble() * 0.04 - 0.02,
                        0.03,
                        level.getRandom().nextDouble() * 0.04 - 0.02));
                item.setPickUpDelay(10);
                level.addFreshEntity(item);
            }
        }
    }

    private static void insertItem(LevelAccessor level, BlockPos pos, ItemEntity itemEntity) {
        IItemHandler itemHandler = itemHandler(level, pos);
        if (!(itemHandler instanceof IItemHandlerModifiable handler)) {
            return;
        }
        ItemStack remaining = ItemHandlerHelper.insertItemStacked(
                handler, itemEntity.getItem().copy(), false);
        if (remaining.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(remaining);
        }
    }

    private static IItemHandler itemHandler(LevelAccessor level, BlockPos pos) {
        if (!(level instanceof ILevelExtension extension)) {
            return null;
        }
        var capability = extension.getCapability(Capabilities.Item.BLOCK, pos, null);
        return capability == null ? null : IItemHandler.of(capability);
    }

    private static Iterable<Entity> entitiesInside(LevelAccessor level, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        return level.getEntitiesOfClass(Entity.class,
                new AABB(x + 2.0 / 16.0, y + 1.0 / 16.0, z + 2.0 / 16.0,
                        x + 14.0 / 16.0, y + 2.0 - 1.0 / 16.0, z + 14.0 / 16.0),
                entity -> true);
    }

    private static boolean isLockable(Entity entity) {
        return entity instanceof Player
                || BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(entity.getType()).is(LOCKABLE_ENTITIES);
    }

    private static void release(Entity entity) {
        entity.setDeltaMovement(Vec3.ZERO);
        entity.setInvisible(false);
        entity.getPersistentData().remove(LOCKED_TAG);
        if (entity instanceof Player player) {
            player.fallDistance = 0;
            player.setOnGround(true);
        }
    }

    private static void lock(Entity entity, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.08;
        double z = pos.getZ() + 0.5;
        entity.teleportTo(x, y, z);
        if (entity instanceof ServerPlayer player) {
            player.connection.teleport(x, y, z, entity.getYRot(), entity.getXRot());
        }
        entity.setDeltaMovement(Vec3.ZERO);
        entity.fallDistance = 0;
        entity.setOnGround(true);
        entity.setInvisible(true);
        entity.getPersistentData().putBoolean(LOCKED_TAG, true);
    }

    private static void playSound(LevelAccessor level, BlockPos pos, String path) {
        if (!(level instanceof Level serverLevel) || serverLevel.isClientSide()) {
            return;
        }
        BuiltInRegistries.SOUND_EVENT.get(
                Identifier.fromNamespaceAndPath("ssc_14", path))
                .ifPresent(sound -> serverLevel.playSound(
                        null, pos, sound.value(), SoundSource.BLOCKS, 1.0F, 1.0F));
    }
}
