package net.ssc14.content.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.block.floor.FloorTileSupportPolicy;
import net.ssc14.content.block.catwalk.CatwalkSupportPolicy;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Canonical server-authoritative RSU construction modes. */
public final class RsuConstructionService {
    private record Config(int delay, int steps, double cost, BlockState state, boolean directed) { }

    private RsuConstructionService() { }

    public static boolean start(LevelAccessor world, BlockPos clicked, Direction face, Player player,
            ItemStack rsu, Item rsuItem, int mode) {
        if (!(world instanceof ServerLevel server) || player == null || !rsu.is(rsuItem)) return false;
        Config config = config(mode);
        if (config == null) return false;
        BlockPos target = clicked.relative(face);
        if (!server.getBlockState(target).isAir()) return false;
        if (mode == 3 && !FloorTileSupportPolicy.canSupport(server, target)) return false;
        if (mode == 5 && !CatwalkSupportPolicy.canSupport(server, target)) return false;
        if (RsuStateService.charges(rsu) < config.cost()) return false;
        Direction facing = facing(player).getOpposite();
        BlockState result = config.directed()
                ? config.state().setValue(BlockStateProperties.FACING, facing) : config.state();
        if (config.delay() == 0) {
            server.setBlock(target, result, 3);
            spend(rsu, config.cost());
            return true;
        }
        double x = player.getX(), y = player.getY(), z = player.getZ();
        build(server, target, player, rsu, rsuItem, result, config, 0, x, y, z);
        return true;
    }

    private static void build(ServerLevel server, BlockPos target, Player player, ItemStack rsu,
            Item rsuItem, BlockState result, Config config, int step, double x, double y, double z) {
        ServerWorkScheduler.schedule(server, config.delay(), () -> {
            if (!player.isAlive() || player.distanceToSqr(x, y, z) > 1.0e-6
                    || !rsu.is(rsuItem) || server.getBlockState(target).isAir() == false
                    || RsuStateService.charges(rsu) < config.cost()) return;
            if (step < config.steps()) {
                build(server, target, player, rsu, rsuItem, result, config, step + 1, x, y, z);
                return;
            }
            server.setBlock(target, result, 3);
            spend(rsu, config.cost());
        });
    }

    private static void spend(ItemStack stack, double cost) {
        double current = RsuStateService.charges(stack);
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(RsuStateService.CHARGES_KEY, current - cost));
    }

    private static Direction facing(Player player) {
        if (player.getXRot() < -45) return Direction.UP;
        if (player.getXRot() > 45) return Direction.DOWN;
        return player.getDirection();
    }

    private static Config config(int mode) {
        return switch (mode) {
            case 2 -> new Config(20, 2, 5, Ssc14Blocks.STEEL_WALL.get().defaultBlockState(), false);
            case 3 -> new Config(0, 0, 1, Ssc14Blocks.TITLE_STEEL.get().defaultBlockState(), false);
            case 4 -> new Config(20, 1, 1, Ssc14Blocks.SHEATHING.get().defaultBlockState(), false);
            case 5 -> new Config(0, 0, 1, Ssc14Blocks.CATWALK_FLOOR.get().defaultBlockState(), false);
            case 6 -> new Config(20, 1, 1, Ssc14Blocks.GRILLE.get().defaultBlockState(), false);
            case 7 -> new Config(20, 2, 3, Ssc14Blocks.BASE_WINDOW.get().defaultBlockState(), false);
            case 8 -> new Config(20, 1, 2, Ssc14Blocks.DIRECTED_BASE_WINDOW.get().defaultBlockState(), true);
            case 9 -> new Config(20, 1, 3, Ssc14Blocks.DIRECTED_ARMORED_WINDOW.get().defaultBlockState(), true);
            case 10 -> new Config(20, 2, 4, Ssc14Blocks.ARMORED_WINDOW.get().defaultBlockState(), false);
            default -> null;
        };
    }
}
