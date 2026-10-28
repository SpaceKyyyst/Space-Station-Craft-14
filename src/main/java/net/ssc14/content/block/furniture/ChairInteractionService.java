package net.ssc14.content.block.furniture;

import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.ssc14.content.entity.ChairEntityEntity;
import net.ssc14.content.entity.Ssc14EntityTypes;

/** Server-side seating and rotating-chair behavior shared by chair blocks and legacy adapters. */
public final class ChairInteractionService {
    private static final double SEAT_SEARCH_SIZE = 0.1;
    private static final double PLAYER_PROBE_INFLATE = 0.15 / 2.0;
    private static final double PLAYER_SEARCH_SIZE = 0.2;
    private static final float ROTATION_EPSILON = 0.1f;

    private ChairInteractionService() {
    }

    /** Keeps a spawned chair entity aligned with the directional chair block below it. */
    public static void tickChairEntity(LevelAccessor world, Entity entity) {
        if (entity == null) return;
        BlockPos pos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
        if (!world.getBlockState(pos).is(BlockTags.create(Identifier.parse("ssc14:chairs")))
                || !entity.isVehicle()) {
            if (!entity.level().isClientSide()) entity.discard();
            return;
        }
        Direction direction = blockDirection(world, pos);
        int yaw = yawFor(direction);
        entity.setYRot(yaw);
        entity.setXRot(0.0f);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);
        entity.yRotO = yaw;
        entity.xRotO = 0.0f;
        if (entity instanceof LivingEntity living) {
            living.yBodyRotO = yaw;
            living.yHeadRotO = yaw;
        }
    }

    public static void seat(LevelAccessor world, BlockPos pos, Entity entity) {
        seat(world, pos.getX(), pos.getY(), pos.getZ(), entity);
    }

    public static void seat(
            LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        Vec3 seatPosition = new Vec3(x + 0.5, y, z + 0.5);
        if (world instanceof ServerLevel level) {
            Entity spawned = Ssc14EntityTypes.CHAIR_ENTITY.get().spawn(
                    level, BlockPos.containing(x + 0.5, y, z + 0.5), EntitySpawnReason.MOB_SUMMONED);
            if (spawned != null) {
                spawned.setDeltaMovement(Vec3.ZERO);
            }
        }
        findNearest(world, ChairEntityEntity.class, seatPosition, SEAT_SEARCH_SIZE)
                .ifPresent(entity::startRiding);
    }

    public static void synchronizeRotation(LevelAccessor world, BlockPos pos) {
        synchronizeRotation(world, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void synchronizeRotation(
            LevelAccessor world, double x, double y, double z) {
        if (world.isClientSide()) {
            return;
        }
        Vec3 playerCenter = new Vec3(x + 0.5, y + 0.5, z + 0.5);
        if (world.getEntitiesOfClass(Player.class,
                new AABB(Vec3.ZERO, Vec3.ZERO).move(playerCenter).inflate(PLAYER_PROBE_INFLATE),
                candidate -> true).isEmpty()) {
            return;
        }
        Player player = findNearest(world, Player.class, playerCenter, PLAYER_SEARCH_SIZE).orElse(null);
        if (player == null || !player.isPassenger()
                || !(player.getRootVehicle() instanceof ChairEntityEntity)) {
            return;
        }
        Vec3 seatPosition = new Vec3(x + 0.5, y, z + 0.5);
        ChairEntityEntity chair =
                findNearest(world, ChairEntityEntity.class, seatPosition, SEAT_SEARCH_SIZE).orElse(null);
        if (chair == null) {
            return;
        }
        Direction direction = directionForYaw(player.getYRot());
        int yaw = yawFor(direction);
        applyEntityRotation(chair, yaw);
        BlockPos blockPos = BlockPos.containing(x, y, z);
        updateBlockDirection(world, blockPos, direction);
        if (world.getBlockState(blockPos).is(Blocks.STRUCTURE_BLOCK)
                && world instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.ASH, x, y, z,
                    0, 0.0, 0.0, 0.0, 0.0);
        }
    }

    public static Direction directionForYaw(float yaw) {
        if (yaw > -45.0f && yaw < 45.0f) {
            return Direction.SOUTH;
        }
        if (yaw >= 45.0f && yaw < 135.0f) {
            return Direction.WEST;
        }
        if (yaw <= -45.0f && yaw > -135.0f) {
            return Direction.EAST;
        }
        return Direction.NORTH;
    }

    private static int yawFor(Direction direction) {
        return switch (direction) {
            case SOUTH -> 0;
            case WEST -> 90;
            case EAST -> -90;
            default -> 180;
        };
    }

    private static void applyEntityRotation(ChairEntityEntity chair, int yaw) {
        if (Math.abs(chair.getYRot() - yaw) <= ROTATION_EPSILON) {
            return;
        }
        chair.setYRot(yaw);
        chair.setXRot(0.0f);
        chair.setYBodyRot(yaw);
        chair.setYHeadRot(yaw);
        chair.yRotO = yaw;
        chair.xRotO = 0.0f;
        if (chair instanceof LivingEntity livingEntity) {
            livingEntity.yBodyRotO = yaw;
            livingEntity.yHeadRotO = yaw;
        }
    }

    private static void updateBlockDirection(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos);
        Property<?> property = state.getBlock().getStateDefinition().getProperty("facing");
        if (property instanceof EnumProperty<?> enumProperty
                && enumProperty.getPossibleValues().contains(direction)) {
            @SuppressWarnings("unchecked")
            EnumProperty<Direction> facing = (EnumProperty<Direction>) enumProperty;
            if (state.getValue(facing) != direction) {
                world.setBlock(pos, state.setValue(facing, direction), 3);
                return;
            }
        }
        property = state.getBlock().getStateDefinition().getProperty("axis");
        if (property instanceof EnumProperty<?> enumProperty
                && enumProperty.getPossibleValues().contains(direction.getAxis())) {
            @SuppressWarnings("unchecked")
            EnumProperty<Direction.Axis> axis = (EnumProperty<Direction.Axis>) enumProperty;
            if (state.getValue(axis) != direction.getAxis()) {
                world.setBlock(pos, state.setValue(axis, direction.getAxis()), 3);
            }
        }
    }

    private static Direction blockDirection(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Property<?> property = state.getBlock().getStateDefinition().getProperty("facing");
        if (property != null && state.getValue(property) instanceof Direction direction) return direction;
        if (state.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS)) {
            return Direction.fromAxisAndDirection(
                    state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS),
                    Direction.AxisDirection.POSITIVE);
        }
        if (state.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_AXIS)) {
            return Direction.fromAxisAndDirection(
                    state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_AXIS),
                    Direction.AxisDirection.POSITIVE);
        }
        return Direction.NORTH;
    }

    private static <T extends Entity> java.util.Optional<T> findNearest(
            LevelAccessor world, Class<T> type, Vec3 center, double size) {
        return world.getEntitiesOfClass(type, AABB.ofSize(center, size, size, size), candidate -> true)
                .stream()
                .min(Comparator.comparingDouble(candidate -> candidate.distanceToSqr(center)));
    }
}
