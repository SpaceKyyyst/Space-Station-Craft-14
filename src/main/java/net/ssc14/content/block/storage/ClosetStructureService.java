package net.ssc14.content.block.storage;

import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Maintains the two-block closet structure without depending on legacy registrations. */
public final class ClosetStructureService {
    private static final Identifier UPPER_PLUG_ID =
            Identifier.fromNamespaceAndPath("ssc_14", "up_closet_plug");
    private static final TagKey<Block> CLOSETS = TagKey.create(
            Registries.BLOCK, Identifier.fromNamespaceAndPath("ssc14", "closets"));
    private static final TagKey<net.minecraft.world.entity.EntityType<?>> LOCKABLE_ENTITIES =
            TagKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath("ssc14", "lockableentitys"));

    private ClosetStructureService() { }

    public static boolean canPlace(LevelAccessor level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.is(BlockTags.REPLACEABLE) || above.is(upperPlug());
    }

    public static void placeUpperPlug(LevelAccessor level, BlockPos pos, BlockState closetState) {
        BlockPos upperPos = pos.above();
        BlockState upperState = upperPlug().defaultBlockState();
        level.setBlock(upperPos, upperState, 3);
        Direction direction = closetState.getValue(StationClosetBlock.FACING);
        if (upperState.getBlock().getStateDefinition().getProperty("facing")
                instanceof EnumProperty<?> facingProperty
                && facingProperty.getPossibleValues().contains(direction)) {
            setEnum(level, upperPos, upperState, facingProperty, direction);
        }
    }

    public static void destroyCounterpart(LevelAccessor level, BlockPos pos) {
        BlockPos below = pos.below();
        if (level.getBlockState(below).is(CLOSETS)) {
            Block.dropResources(level.getBlockState(below), level, below, null);
            level.destroyBlock(below, false);
        } else if (level.getBlockState(pos.above()).is(upperPlug())) {
            level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
        }
        releaseEntities(level, pos);
    }

    private static Block upperPlug() {
        return BuiltInRegistries.BLOCK.getValue(UPPER_PLUG_ID);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void setEnum(LevelAccessor level, BlockPos pos, BlockState state,
            EnumProperty property, Comparable value) {
        level.setBlock(pos, state.setValue(property, value), 3);
    }

    private static void releaseEntities(LevelAccessor level, BlockPos pos) {
        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5);
        level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(0.2),
                        entity -> true).stream()
                .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(center)))
                .filter(ClosetStructureService::isLockable)
                .forEach(ClosetStructureService::release);
    }

    private static boolean isLockable(Entity entity) {
        return entity instanceof Player
                || BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(entity.getType()).is(LOCKABLE_ENTITIES);
    }

    private static void release(Entity entity) {
        entity.setDeltaMovement(Vec3.ZERO);
        entity.setInvisible(false);
        if (entity instanceof Player player) {
            player.fallDistance = 0;
            player.setOnGround(true);
        }
    }
}
