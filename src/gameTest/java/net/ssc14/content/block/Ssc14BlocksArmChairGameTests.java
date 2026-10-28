package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.furniture.ArmChairBlock;
import net.ssc14.content.block.furniture.DirectionalChairBlock;
import net.ssc14.content.entity.ChairEntityEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksArmChairGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_armchair_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.ARM_CHAIR_BASE, "arm_chair_base"),
            expected(Ssc14Blocks.ARM_CHAIR_BROWN, "arm_chair_brown"),
            expected(Ssc14Blocks.ARM_CHAIR_CARP, "arm_chair_carp"));

    private Ssc14BlocksArmChairGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksArmChairGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_armchair_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        for (ExpectedBlock expected : EXPECTED) {
            Block block = expected.holder().get();
            BlockState state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(block instanceof ArmChairBlock,
                    expected.path() + " canonical implementation changed");
            helper.assertTrue(Float.compare(7.5f, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(7.5f, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(state.getSoundType() == SoundType.IRON, expected.path() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
            helper.assertTrue(state.getValue(DirectionalChairBlock.FACING) == Direction.NORTH,
                    expected.path() + " default facing changed");
            helper.assertTrue(state.rotate(Rotation.CLOCKWISE_90)
                    .getValue(DirectionalChairBlock.FACING) == Direction.EAST,
                    expected.path() + " rotation changed");
            helper.assertTrue(state.setValue(DirectionalChairBlock.FACING, Direction.EAST)
                    .mirror(Mirror.FRONT_BACK).getValue(DirectionalChairBlock.FACING) == Direction.WEST,
                    expected.path() + " mirror changed");
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState directed = state.setValue(DirectionalChairBlock.FACING, direction);
                helper.setBlock(relative, directed);
                helper.assertTrue(!Shapes.joinIsNotEmpty(
                                directed.getShape(helper.getLevel(), absolute), shape(direction),
                                BooleanOp.NOT_SAME),
                        expected.path() + " shape changed for " + direction);
                helper.assertTrue(directed.getCollisionShape(
                        helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " collision shape changed");
                helper.assertTrue(directed.getVisualShape(
                        helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " visual shape changed");
                helper.assertTrue(!directed.isRedstoneConductor(helper.getLevel(), absolute),
                        expected.path() + " became a redstone conductor");
            }
        }

        helper.setBlock(relative, Ssc14Blocks.ARM_CHAIR_BASE.get().defaultBlockState());
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.useBlock(relative, player);
        helper.assertTrue(player.isPassenger(), "armchair interaction no longer mounts the player");
        helper.assertTrue(player.getRootVehicle() instanceof ChairEntityEntity,
                "armchair interaction mounted a different entity type");
        ChairEntityEntity chair = (ChairEntityEntity) player.getRootVehicle();
        Vec3 expectedSeat = helper.absoluteVec(new Vec3(1.5, 2.0, 1.5));
        helper.assertTrue(chair.position().equals(expectedSeat), "chair entity spawn position changed");
        helper.assertTrue(chair.getDeltaMovement().equals(Vec3.ZERO), "chair entity velocity changed");
        helper.succeed();
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(1, 7, 2, 4, 11, 13), Block.box(12, 7, 2, 15, 11, 13),
                    Block.box(2, 7, 11, 14, 19, 15));
            case EAST -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(3, 7, 1, 14, 11, 4), Block.box(3, 7, 12, 14, 11, 15),
                    Block.box(1, 7, 2, 5, 19, 14));
            case WEST -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(2, 7, 12, 13, 11, 15), Block.box(2, 7, 1, 13, 11, 4),
                    Block.box(11, 7, 2, 15, 19, 14));
            default -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(12, 7, 3, 15, 11, 14), Block.box(1, 7, 3, 4, 11, 14),
                    Block.box(2, 7, 1, 14, 19, 5));
        };
    }

    private static ExpectedBlock expected(DeferredBlock<Block> holder, String path) {
        return new ExpectedBlock(holder, path);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(DeferredBlock<Block> holder, String path) {
    }
}
