package net.ssc14.content.block;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.storage.StationClosetBlock;
import net.ssc14.content.block.storage.UpperClosetPlugBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksUpperClosetPlugGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_upper_closet_plug_contract");

    private Ssc14BlocksUpperClosetPlugGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksUpperClosetPlugGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_upper_closet_plug_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        UpperClosetPlugBlock plug =
                (UpperClosetPlugBlock) Ssc14Blocks.UP_CLOSET_PLUG.get();
        helper.assertTrue(id("up_closet_plug").equals(
                        BuiltInRegistries.BLOCK.getKey(plug)),
                "upper closet plug registry ID changed");

        BlockPos relativeLower = new BlockPos(2, 2, 2);
        BlockPos absoluteLower = helper.absolutePos(relativeLower);
        BlockPos absoluteUpper = absoluteLower.above();
        BlockState closetState = Ssc14Blocks.CLOSET_EMERG.get().defaultBlockState()
                .setValue(StationClosetBlock.FACING, Direction.EAST);
        helper.setBlock(relativeLower, closetState);
        BlockState plugState = helper.getLevel().getBlockState(absoluteUpper);
        helper.assertTrue(plugState.is(plug)
                        && plugState.getValue(UpperClosetPlugBlock.FACING) == Direction.EAST,
                "closet did not install a direction-matched canonical upper plug");

        for (var entry : expectedShapes().entrySet()) {
            VoxelShape actual = plug.defaultBlockState()
                    .setValue(UpperClosetPlugBlock.FACING, entry.getKey())
                    .getShape(helper.getLevel(), absoluteUpper);
            helper.assertTrue(!Shapes.joinIsNotEmpty(
                            entry.getValue(), actual, BooleanOp.NOT_SAME),
                    "upper closet plug shape changed for " + entry.getKey());
        }

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var hit = new BlockHitResult(Vec3.atCenterOf(absoluteUpper), Direction.UP,
                absoluteUpper, false);
        plug.useWithoutItem(plugState, helper.getLevel(), absoluteUpper, player, hit);
        helper.assertTrue(helper.getLevel().getBlockState(absoluteLower)
                        .getValue(StationClosetBlock.OPEN),
                "upper closet plug did not delegate interaction to the lower closet");

        plug.onDestroyedByPlayer(plugState, helper.getLevel(), absoluteUpper, player,
                ItemStack.EMPTY, false, Fluids.EMPTY.defaultFluidState());
        helper.assertTrue(helper.getLevel().getBlockState(absoluteLower).isAir(),
                "destroying the upper closet plug did not remove the lower closet");
        helper.succeed();
    }

    private static Map<Direction, VoxelShape> expectedShapes() {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        VoxelShape outer = Block.box(1, 0, 1, 15, 16, 15);
        shapes.put(Direction.NORTH, hollow(outer, Block.box(2, 0, 0, 14, 15, 14)));
        shapes.put(Direction.EAST, hollow(outer, Block.box(2, 0, 2, 16, 15, 14)));
        shapes.put(Direction.WEST, hollow(outer, Block.box(0, 0, 2, 14, 15, 14)));
        shapes.put(Direction.SOUTH, hollow(outer, Block.box(2, 0, 2, 14, 15, 16)));
        return shapes;
    }

    private static VoxelShape hollow(VoxelShape outer, VoxelShape interior) {
        return Shapes.join(outer, interior, BooleanOp.ONLY_FIRST);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
