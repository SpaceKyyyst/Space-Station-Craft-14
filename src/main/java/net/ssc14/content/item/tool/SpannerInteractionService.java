package net.ssc14.content.item.tool;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Canonical staged interaction for repairing and dismantling wall-frame entities. */
public final class SpannerInteractionService {
    private static final int STEPS = 6;
    private static final int STEP_DELAY_TICKS = 2;
    private static final double POSITION_EPSILON = 1.0e-6;

    private SpannerInteractionService() { }

    public static void repair(LevelAccessor world, Entity target, Entity actor,
            ItemStack tool, Item requiredTool, Block replacement,
            Holder<Attribute> progressAttribute) {
        if (!(world instanceof ServerLevel server) || target == null || actor == null
                || tool.isEmpty() || tool.getItem() != requiredTool) return;
        BlockPos pos = target.blockPosition();
        if (!server.getBlockState(pos).isAir()) return;
        run(server, target, actor, tool, requiredTool, pos, replacement.defaultBlockState(),
                null, progressAttribute);
    }

    public static void dismantle(LevelAccessor world, Entity target, Entity actor,
            ItemStack tool, Item requiredTool, Item drop, Holder<Attribute> progressAttribute) {
        if (!(world instanceof ServerLevel server) || target == null || actor == null
                || tool.isEmpty() || tool.getItem() != requiredTool) return;
        run(server, target, actor, tool, requiredTool, target.blockPosition(), null,
                drop, progressAttribute);
    }

    private static void run(ServerLevel server, Entity target, Entity actor, ItemStack tool,
            Item requiredTool, BlockPos pos, BlockState replacement, Item drop,
            Holder<Attribute> progressAttribute) {
        double x = actor.getX(), y = actor.getY(), z = actor.getZ();
        setProgress(actor, progressAttribute, 1);
        step(server, 1, target, actor, tool, requiredTool, pos, replacement, drop,
                progressAttribute, x, y, z);
    }

    private static void step(ServerLevel server, int step, Entity target, Entity actor,
            ItemStack tool, Item requiredTool, BlockPos pos, BlockState replacement,
            Item drop, Holder<Attribute> progressAttribute, double x, double y, double z) {
        ServerWorkScheduler.schedule(server, STEP_DELAY_TICKS, () -> {
            boolean valid = actor.isAlive() && target.isAlive()
                    && actor.distanceToSqr(x, y, z) <= POSITION_EPSILON
                    && !tool.isEmpty() && tool.getItem() == requiredTool
                    && (replacement == null || server.getBlockState(pos).isAir());
            if (!valid) {
                setProgress(actor, progressAttribute, 0);
                return;
            }
            if (step < STEPS) {
                setProgress(actor, progressAttribute, step + 1);
                step(server, step + 1, target, actor, tool, requiredTool, pos, replacement,
                        drop, progressAttribute, x, y, z);
                return;
            }
            if (replacement != null) {
                server.setBlock(pos, replacement, 3);
            } else if (drop != null) {
                server.addFreshEntity(new ItemEntity(server, target.getX(), target.getY(),
                        target.getZ(), new ItemStack(drop)));
            }
            target.discard();
            setProgress(actor, progressAttribute, 0);
        });
    }

    private static void setProgress(Entity actor, Holder<Attribute> attribute, double value) {
        if (attribute != null && actor instanceof LivingEntity living
                && living.getAttributes().hasAttribute(attribute)) {
            living.getAttribute(attribute).setBaseValue(value);
        }
    }
}
