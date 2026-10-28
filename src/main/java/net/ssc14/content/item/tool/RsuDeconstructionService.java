package net.ssc14.content.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.core.Holder;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Canonical staged deconstruction for RSU targets. */
public final class RsuDeconstructionService {
    private static final int NORMAL_STEPS = 8;
    private static final int STEP_DELAY_TICKS = 4;

    private RsuDeconstructionService() { }

    public static boolean start(LevelAccessor world, BlockPos pos, Player player,
            ItemStack rsu, Item rsuItem, Holder<Attribute> progressAttribute) {
        if (!(world instanceof ServerLevel server) || player == null || !rsu.is(rsuItem)) return false;
        BlockState state = server.getBlockState(pos);
        if (state.isAir() || !state.is(BlockTags.create(Identifier.fromNamespaceAndPath(
                "ssc14", "rcd_deconstruct")))) return false;
        int steps = isFast(state) ? 1 : NORMAL_STEPS;
        double x = player.getX(), y = player.getY(), z = player.getZ();
        run(server, pos, state, player, rsu, rsuItem, progressAttribute, steps, 0, x, y, z);
        return true;
    }

    private static void run(ServerLevel server, BlockPos pos, BlockState expected, Player player,
            ItemStack rsu, Item rsuItem, Holder<Attribute> progress, int steps, int step,
            double x, double y, double z) {
        ServerWorkScheduler.schedule(server, steps == 1 ? 0 : STEP_DELAY_TICKS, () -> {
            if (!player.isAlive() || player.distanceToSqr(x, y, z) > 1.0e-6
                    || !rsu.is(rsuItem) || server.getBlockState(pos).getBlock() != expected.getBlock()) {
                setProgress(player, progress, 0);
                return;
            }
            if (step < steps) {
                setProgress(player, progress, step + 1);
                run(server, pos, expected, player, rsu, rsuItem, progress, steps, step + 1, x, y, z);
                return;
            }
            server.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            double charges = RsuStateService.charges(rsu);
            if (charges < 30) {
                CustomData.update(DataComponents.CUSTOM_DATA, rsu,
                        tag -> tag.putDouble(RsuStateService.CHARGES_KEY, charges + 1));
            }
            setProgress(player, progress, 0);
        });
    }

    private static boolean isFast(BlockState state) {
        return state.is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tiles")))
                || state.is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tiles_up")));
    }

    private static void setProgress(Player player, Holder<Attribute> attribute, double value) {
        if (player.getAttributes().hasAttribute(attribute)) {
            player.getAttribute(attribute).setBaseValue(value);
        }
    }
}
