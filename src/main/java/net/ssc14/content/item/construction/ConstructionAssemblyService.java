package net.ssc14.content.item.construction;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Server-owned staged assembly for construction sheets. */
public final class ConstructionAssemblyService {
    private static final int STAGE_COUNT = 6;
    private static final int STAGE_DELAY_TICKS = 3;
    private static final double POSITION_EPSILON = 1.0e-6;

    private ConstructionAssemblyService() { }

    public static void assembleWall(LevelAccessor world, BlockPos pos, Entity actor,
            ItemStack held, Item requiredItem, BlockState source, BlockState result,
            Holder<Attribute> progressAttribute) {
        if (!(world instanceof ServerLevel server) || actor == null
                || held.isEmpty() || held.getItem() != requiredItem
                || world.getBlockState(pos).getBlock() != source.getBlock()) {
            return;
        }
        double x = actor.getX();
        double y = actor.getY();
        double z = actor.getZ();
        setProgress(actor, progressAttribute, 1);
        scheduleStage(server, 1, pos, actor, x, y, z, held, requiredItem,
                source, result, progressAttribute);
    }

    private static void scheduleStage(ServerLevel server, int stage, BlockPos pos,
            Entity actor, double x, double y, double z, ItemStack held, Item requiredItem,
            BlockState source, BlockState result, Holder<Attribute> progressAttribute) {
        ServerWorkScheduler.schedule(server, STAGE_DELAY_TICKS, () -> {
            boolean valid = actor.isAlive() && actor.distanceToSqr(x, y, z) <= POSITION_EPSILON
                    && !held.isEmpty() && held.getItem() == requiredItem
                    && server.getBlockState(pos).getBlock() == source.getBlock();
            if (!valid) {
                setProgress(actor, progressAttribute, 0);
                return;
            }
            if (stage < STAGE_COUNT) {
                setProgress(actor, progressAttribute, stage + 1);
                scheduleStage(server, stage + 1, pos, actor, x, y, z, held,
                        requiredItem, source, result, progressAttribute);
                return;
            }
            server.setBlock(pos, copyProperties(server.getBlockState(pos), result), 3);
            held.shrink(1);
            setProgress(actor, progressAttribute, 0);
        });
    }

    public static void replaceBlock(LevelAccessor world, BlockPos pos, Entity actor,
            ItemStack held, Item requiredItem, Block source, Block result) {
        if (actor == null || held.isEmpty() || held.getItem() != requiredItem
                || world.getBlockState(pos).getBlock() != source) {
            return;
        }
        world.setBlock(pos, copyProperties(world.getBlockState(pos), result.defaultBlockState()), 3);
        held.shrink(1);
    }

    private static BlockState copyProperties(BlockState oldState, BlockState replacement) {
        BlockState copied = replacement;
        for (Property<?> oldProperty : oldState.getProperties()) {
            Property<?> newProperty = copied.getBlock().getStateDefinition()
                    .getProperty(oldProperty.getName());
            if (newProperty != null) {
                copied = copyProperty(oldState, copied, oldProperty, newProperty);
            }
        }
        return copied;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState copyProperty(BlockState oldState, BlockState replacement,
            Property oldProperty, Property newProperty) {
        try {
            return replacement.setValue(newProperty, oldState.getValue(oldProperty));
        } catch (IllegalArgumentException ignored) {
            return replacement;
        }
    }

    private static void setProgress(Entity actor, Holder<Attribute> attribute, double value) {
        if (attribute != null && actor instanceof LivingEntity living
                && living.getAttributes().hasAttribute(attribute)) {
            living.getAttribute(attribute).setBaseValue(value);
        }
    }
}
